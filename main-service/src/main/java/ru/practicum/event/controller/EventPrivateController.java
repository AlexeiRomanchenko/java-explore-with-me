package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.discriptions.MessageManager;
import ru.practicum.event.EventService;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventUserRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class EventPrivateController {
    private final EventService eventService;

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable @Positive Long userId,
                                    @RequestBody @Valid NewEventDto newEventDto) {
        log.info(MessageManager.receivedPostId, "/users/{userId}/events", userId);
        return eventService.createEvent(userId, newEventDto);
    }

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> getEvents(@PathVariable @Positive Long userId,
                                         @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                         @RequestParam(defaultValue = "10") @Positive int size) {
        log.info(MessageManager.receivedGetId, "/users/{userId}/events", userId);
        return eventService.getEvents(userId, from, size);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEventById(@PathVariable @Positive Long userId,
                                     @PathVariable @Positive Long eventId) {
        log.info(MessageManager.receivedGetId, "/users/{userId}", "/events/{eventId}");
        return eventService.getEventById(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto updateEventByUser(@PathVariable @Positive Long userId,
                                          @PathVariable @Positive Long eventId,
                                          @RequestBody @Valid UpdateEventUserRequestDto updateEventUserRequestDto) {
        log.info(MessageManager.receivedPatch, "/users/{userId}", "/events/{eventId}");
        return eventService.updateEventByUser(userId, eventId, updateEventUserRequestDto);
    }
}