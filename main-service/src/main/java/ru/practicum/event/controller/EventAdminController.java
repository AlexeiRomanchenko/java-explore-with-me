package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.discriptions.ConstantManager;
import ru.practicum.discriptions.MessageManager;
import ru.practicum.event.EventService;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventAdminRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class EventAdminController {
    private final EventService eventService;

    @GetMapping("/admin/events")
    public List<EventFullDto> getEventsByAdmin(@RequestParam(required = false) List<Long> users,
                                               @RequestParam(required = false) List<String> states,
                                               @RequestParam(required = false) List<Long> categories,
                                               @RequestParam(required = false)
                                               @DateTimeFormat(pattern = ConstantManager.DATE_TIME_PATTERN)
                                               LocalDateTime rangeStart,
                                               @RequestParam(required = false)
                                               @DateTimeFormat(pattern = ConstantManager.DATE_TIME_PATTERN)
                                               LocalDateTime rangeEnd,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                               @RequestParam(defaultValue = "10") @Positive int size) {
        log.info(MessageManager.receivedGet, "/admin/events");
        return eventService.getEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable @Positive Long eventId,
                                           @RequestBody @Valid UpdateEventAdminRequestDto updateEventAdminRequestDto) {
        log.info(MessageManager.receivedPatch, "/admin/events", eventId);
        return eventService.updateEventByAdmin(eventId, updateEventAdminRequestDto);
    }
}