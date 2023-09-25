package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.discriptions.ConstantManager;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatDto;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@RequestBody HitDto hitDto) {
        log.info("Stat server: POST hit={}", hitDto);
        statService.addHit(hitDto);
    }

    @GetMapping("/stats")
    public List<StatDto> getStats(
            @RequestParam @DateTimeFormat(pattern = ConstantManager.DATE_TIME_PATTERN) LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = ConstantManager.DATE_TIME_PATTERN) LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Stat server: GET stats start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return statService.getStats(start, end, uris, unique);
    }
}