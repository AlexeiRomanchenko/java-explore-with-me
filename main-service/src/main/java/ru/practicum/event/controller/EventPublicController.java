package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.discriptions.ConstantManager;
import ru.practicum.discriptions.MessageManager;
import ru.practicum.event.EventService;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class EventPublicController {
    private final EventService eventService;

    @GetMapping("/events")
    public List<EventShortDto> getPublishedEvents(@RequestParam(required = false) String text,
                                                  @RequestParam(required = false) List<Long> categories,
                                                  @RequestParam(required = false) Boolean paid,
                                                  @RequestParam(required = false)
                                                  @DateTimeFormat(pattern = ConstantManager.DATE_TIME_PATTERN)
                                                  LocalDateTime rangeStart,
                                                  @RequestParam(required = false)
                                                  @DateTimeFormat(pattern = ConstantManager.DATE_TIME_PATTERN)
                                                  LocalDateTime rangeEnd,
                                                  @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                                  @RequestParam(required = false) String sort,
                                                  @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                  @RequestParam(defaultValue = "10") @Positive int size,
                                                  HttpServletRequest request) {
        log.info(MessageManager.receivedGet, "events");
        return eventService.getPublishedEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size, request);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getPublishedEventById(@PathVariable @Positive Long id,
                                              HttpServletRequest request) {
        log.info(MessageManager.receivedGetId, "/events", id);
        return eventService.getPublishedEventById(id, request);
    }
}