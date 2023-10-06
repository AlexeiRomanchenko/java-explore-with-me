package ru.practicum.event.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.category.dto.CategoryMapper;
import ru.practicum.discriptions.ConstantManager;
import ru.practicum.event.Event;
import ru.practicum.location.dto.LocationMapper;
import ru.practicum.request.ParticipationRequest;
import ru.practicum.request.ParticipationRequestStatus;
import ru.practicum.user.dto.UserMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@UtilityClass
public class EventMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstantManager.DATE_TIME_PATTERN);

    public Event toEvent(NewEventDto newEventDto) {
        return Event.builder()
                .title(newEventDto.getTitle())
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(LocalDateTime.parse(newEventDto.getEventDate(), formatter))
                .location(LocationMapper.toLocation(newEventDto.getLocation()))
                .paid(newEventDto.isPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.isRequestModeration())
                .build();
    }

    public EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(formatter))
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.isRequestModeration())
                .confirmedRequests(countConfirmedRequests(event.getRequests()))
                .createdOn(event.getCreatedOn().format(formatter))
                .publishedOn(event.getPublishedOn() != null ? event.getPublishedOn().format(formatter) : null)
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .state(event.getState().toString())
                .views(event.getViews())
                .build();
    }

    public EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .eventDate(event.getEventDate().format(formatter))
                .confirmedRequests(countConfirmedRequests(event.getRequests()))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.isPaid())
                .views(event.getViews())
                .build();
    }

    public long countConfirmedRequests(List<ParticipationRequest> requests) {
        return requests != null
                ? requests.stream().filter(r -> r.getStatus() == ParticipationRequestStatus.CONFIRMED).count()
                : 0;
    }
}