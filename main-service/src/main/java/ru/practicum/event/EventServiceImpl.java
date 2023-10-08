package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.StatClient;
import ru.practicum.category.Category;
import ru.practicum.category.CategoryRepository;
import ru.practicum.discriptions.ConstantManager;
import ru.practicum.discriptions.FromSizeRequest;
import ru.practicum.discriptions.MessageManager;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatDto;
import ru.practicum.event.dto.*;
import ru.practicum.exceptions.*;
import ru.practicum.location.Location;
import ru.practicum.location.LocationRepository;
import ru.practicum.location.dto.LocationMapper;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstantManager.DATE_TIME_PATTERN);
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final StatClient statClient;

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        log.info("Adding a new event: user_id = {}, event = {}", userId, newEventDto);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Category category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(newEventDto.getCategory()));
        Event event = EventMapper.toEvent(newEventDto);
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationRequestException(MessageManager.cannotCreateTheEvent2Hours);
        }
        event.setCategory(category);
        event.setCreatedOn(LocalDateTime.now());
        event.setInitiator(user);
        event.setState(EventState.PENDING);
        event.setLocation(locationRepository.save(LocationMapper.toLocation(newEventDto.getLocation())));
        event.setViews(0L);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEvents(Long userId, int from, int size) {
        PageRequest page = FromSizeRequest.of(from, size);
        log.info("Getting events added by the current user: user_id = {}, from = {}, size = {}", userId, from, size);
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<Event> events = eventRepository.findByInitiatorId(userId, page);
        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventById(Long userId, Long eventId) {
        log.info("Getting full information about the event added by the current user: user_id = {}, event_id = {}",
                userId, eventId);
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return EventMapper.toEventFullDto(eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId)));
    }

    @Override
    public EventFullDto updateEventByUser(Long userId, Long eventId,
                                          UpdateEventUserRequestDto updateEventUserRequestDto) {
        log.info("Updating event information: user_id = {}, event_id = {}, update_event = {}",
                userId, eventId, updateEventUserRequestDto);
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (event.getState() != null
                && event.getState() != EventState.PENDING
                && event.getState() != EventState.CANCELED) {
            throw new ForbiddenException(MessageManager.onlyChangedEvents);
        }
        if (updateEventUserRequestDto.getEventDate() != null
                && updateEventUserRequestDto.getEventDate()
                .isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationRequestException(
                    String.format("Event date must not be before 2 hours from current time. New value: %s",
                            updateEventUserRequestDto.getEventDate()));
        }
        if (updateEventUserRequestDto.getTitle() != null) {
            event.setTitle(updateEventUserRequestDto.getTitle());
        }
        if (updateEventUserRequestDto.getAnnotation() != null) {
            event.setAnnotation(updateEventUserRequestDto.getAnnotation());
        }
        if (updateEventUserRequestDto.getCategory() != null) {
            event.setCategory(categoryRepository.findById(updateEventUserRequestDto.getCategory())
                    .orElseThrow(() -> new CategoryNotFoundException(updateEventUserRequestDto.getCategory())));
        }
        if (updateEventUserRequestDto.getDescription() != null) {
            event.setDescription(updateEventUserRequestDto.getDescription());
        }
        if (updateEventUserRequestDto.getEventDate() != null) {
            event.setEventDate(updateEventUserRequestDto.getEventDate());
        }
        if (updateEventUserRequestDto.getLocation() != null) {
            Location location = event.getLocation();
            location.setLat(updateEventUserRequestDto.getLocation().getLat());
            location.setLon(updateEventUserRequestDto.getLocation().getLon());
            event.setLocation(location);
            locationRepository.save(location);
        }
        if (updateEventUserRequestDto.getPaid() != null) {
            event.setPaid(updateEventUserRequestDto.getPaid());
        }
        if (updateEventUserRequestDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequestDto.getParticipantLimit());
        }
        if (updateEventUserRequestDto.getRequestModeration() != null) {
            event.setRequestModeration(updateEventUserRequestDto.getRequestModeration());
        }
        if (updateEventUserRequestDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequestDto.getParticipantLimit());
        }
        if (updateEventUserRequestDto.getStateAction() == null) {
            return EventMapper.toEventFullDto(eventRepository.save(event));
        }
        if (updateEventUserRequestDto.getStateAction() == StateUserAction.CANCEL_REVIEW) {
            event.setState(EventState.CANCELED);
        }
        if (updateEventUserRequestDto.getStateAction() == StateUserAction.SEND_TO_REVIEW) {
            event.setState(EventState.PENDING);
        }
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequestDto updateEventAdminRequestDto) {
        log.info("updating information about the event by the administrator: event_id = {}, update_event = {}",
                eventId, updateEventAdminRequestDto);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (updateEventAdminRequestDto.getStateAction() != null) {
            if (updateEventAdminRequestDto.getStateAction() == StateAdminAction.PUBLISH_EVENT) {
                if (event.getState() != EventState.PENDING) {
                    throw new ForbiddenException(MessageManager.cannotPublisherNotInRightState + event.getState());
                }
                if (event.getPublishedOn() != null
                        && event.getEventDate().isAfter(event.getPublishedOn().minusHours(1))) {
                    throw new ValidationRequestException(
                            MessageManager.cannotCreateTheEvent1Hours);
                }
                event.setPublishedOn(LocalDateTime.now());
                event.setState(EventState.PUBLISHED);
            }
            if (updateEventAdminRequestDto.getStateAction() == StateAdminAction.REJECT_EVENT) {
                if (event.getState() == EventState.PUBLISHED) {
                    throw new ForbiddenException(MessageManager.cannotRejectAlreadyPublisher);
                } else {
                    event.setState(EventState.CANCELED);
                }
            }
        }
        if (updateEventAdminRequestDto.getEventDate() != null
                && updateEventAdminRequestDto.getEventDate()
                .isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationRequestException(
                    String.format("Event date must not be before 2 hours from current time. New value: %s",
                            updateEventAdminRequestDto.getEventDate()));
        }
        if (updateEventAdminRequestDto.getTitle() != null) {
            event.setTitle(updateEventAdminRequestDto.getTitle());
        }
        if (updateEventAdminRequestDto.getAnnotation() != null) {
            event.setAnnotation(updateEventAdminRequestDto.getAnnotation());
        }
        if (updateEventAdminRequestDto.getCategory() != null) {
            event.setCategory(categoryRepository.findById(updateEventAdminRequestDto.getCategory())
                    .orElseThrow(() -> new CategoryNotFoundException(updateEventAdminRequestDto.getCategory())));
        }
        if (updateEventAdminRequestDto.getDescription() != null) {
            event.setDescription(updateEventAdminRequestDto.getDescription());
        }
        if (updateEventAdminRequestDto.getEventDate() != null) {
            event.setEventDate(updateEventAdminRequestDto.getEventDate());
        }
        if (updateEventAdminRequestDto.getLocation() != null) {
            Location location = event.getLocation();
            location.setLat(updateEventAdminRequestDto.getLocation().getLat());
            location.setLon(updateEventAdminRequestDto.getLocation().getLon());
            event.setLocation(location);
            locationRepository.save(location);
        }
        if (updateEventAdminRequestDto.getPaid() != null) {
            event.setPaid(updateEventAdminRequestDto.getPaid());
        }
        if (updateEventAdminRequestDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventAdminRequestDto.getParticipantLimit());
        }
        if (updateEventAdminRequestDto.getRequestModeration() != null) {
            event.setRequestModeration(updateEventAdminRequestDto.getRequestModeration());
        }
        if (updateEventAdminRequestDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventAdminRequestDto.getParticipantLimit());
        }
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> getEventsByAdmin(List<Long> users, List<String> states, List<Long> categories,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        PageRequest page = FromSizeRequest.of(from, size);
        log.info("Search for events by parameters: user_ids = {}, states = {}, categories = {},"
                        + " rangeStart = {}, rangeEnd = {}",
                users, states, categories, rangeStart, rangeEnd);

        List<Event> events = eventRepository.findEvents(users,
                states, categories,
                rangeStart,
                rangeEnd,
                page);
        return events
                .stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getPublishedEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                                  LocalDateTime rangeEnd, boolean onlyAvailable,
                                                  String sort, int from, int size,
                                                  HttpServletRequest request) {
        PageRequest page = FromSizeRequest.of(from, size);
        log.info("Search for published events by parameters: text = {}, categories = {}, paid = {}," +
                        " rangeStart = {}, rangeEnd = {}, onlyAvailable = {}, sort = {}, from = {}, size = {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        log.info("Client ip: {}", request.getRemoteAddr());
        log.info("Endpoint path: {}", request.getRequestURI());
        statClient.addHit(HitDto.builder()
                .app("ewm-main-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(formatter))
                .build());
        if (rangeStart != null && rangeEnd != null &&
                rangeStart.isAfter(rangeEnd)) {
            throw new ValidationRequestException(MessageManager.dateStartAfterEnd);
        }
        List<Event> events = eventRepository.findPublishedEvents(
                text,
                categories,
                paid,
                rangeStart != null ? rangeStart : LocalDateTime.now(),
                rangeEnd,
                page);
        List<EventShortDto> eventShortDtos = Collections.emptyList();
        if (events != null) {
            eventShortDtos = events.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
            if (onlyAvailable) {
                eventShortDtos = events.stream().filter(event ->
                        EventMapper.countConfirmedRequests(event.getRequests()) < event.getParticipantLimit()
                ).map(EventMapper::toEventShortDto).collect(Collectors.toList());
            }
            if (sort != null) {
                switch (EventSort.valueOf(sort)) {
                    case EVENT_DATE:
                        eventShortDtos.sort(Comparator.comparing(EventShortDto::getEventDate));
                        break;
                    case VIEWS:
                        eventShortDtos.sort(Comparator.comparing(EventShortDto::getViews));
                        break;
                    default:
                        throw new ValidationRequestException(MessageManager.notValid);
                }
            }
        }
        return eventShortDtos;
    }

    @Override
    public EventFullDto getPublishedEventById(Long eventId, HttpServletRequest request) {
        log.info("Getting information about a published event by ID: event_id = {}", eventId);
        Event event = eventRepository.findByIdAndState(eventId, EventState.PUBLISHED)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        Integer countHits = getCountHits(request);
        statClient.addHit(HitDto.builder()
                .app("ewm-main-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(formatter))
                .build());
        Integer newCountHits = getCountHits(request);
        if (newCountHits != null && newCountHits > countHits) {
            event.setViews(event.getViews() + 1);
            eventRepository.save(event);
        }
        return EventMapper.toEventFullDto(event);
    }

    private Integer getCountHits(HttpServletRequest request) {
        log.info("Client ip: {}", request.getRemoteAddr());
        log.info("Endpoint path: {}", request.getRequestURI());
        ResponseEntity<StatDto[]> response = statClient.getStats(
                LocalDateTime.now().minusYears(100).format(formatter),
                LocalDateTime.now().format(formatter),
                new String[]{request.getRequestURI()},
                true);
        Optional<StatDto> statDto;
        int hits = 0;
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            statDto = Arrays.stream(response.getBody()).findFirst();
            if (statDto.isPresent()) {
                hits = statDto.get().getHits();
            }
        }
        return hits;
    }
}