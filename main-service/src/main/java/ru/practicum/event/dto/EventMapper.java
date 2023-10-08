package ru.practicum.event.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.category.dto.CategoryMapper;
import ru.practicum.event.Event;
import ru.practicum.location.dto.LocationMapper;
import ru.practicum.request.ParticipationRequest;
import ru.practicum.request.ParticipationRequestStatus;
import ru.practicum.user.dto.UserMapper;

import java.util.List;

@UtilityClass
public class EventMapper {

    public Event toEvent(NewEventDto newEventDto) {
        return Event.builder()
                .title(newEventDto.getTitle())
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .location(LocationMapper.toLocation(newEventDto.getLocation()))
                .paid(newEventDto.isPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.isRequestModeration())
                .build();
    }

    public EventFullDto toEventFullDto(Event event) {
        EventInfoDto eventInfoDto = EventInfoDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .eventDate(event.getEventDate())
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .build();

        EventDetailsDto eventDetailsDto = EventDetailsDto.builder()
                .description(event.getDescription())
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.isRequestModeration())
                .confirmedRequests(countConfirmedRequests(event.getRequests()))
                .build();

        EventMetadataDto eventMetadataDto = EventMetadataDto.builder()
                .createdOn(event.getCreatedOn())
                .publishedOn(event.getPublishedOn() != null ? event.getPublishedOn() : null)
                .state(event.getState().toString())
                .views(event.getViews())
                .build();

        EventUserDto eventUserDto = EventUserDto.builder()
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .build();

        return EventFullDto.builder()
                .eventInfo(eventInfoDto)
                .eventMetadata(eventMetadataDto)
                .eventDetails(eventDetailsDto)
                .eventUser(eventUserDto)
                .build();
    }

    public EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .eventDate(event.getEventDate())
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