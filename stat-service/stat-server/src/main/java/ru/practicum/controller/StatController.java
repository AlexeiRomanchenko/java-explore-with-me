package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.discriptions.ConstantManager;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatDto;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@RequestBody HitDto hitDto) {
        log.info("Stat server: POST hit={}", hitDto);
        statService.addHit(hitDto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<StatDto> getStats(@RequestParam String start,
                                  @RequestParam String end,
                                  @RequestParam(required = false) List<String> uris,
                                  @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Stat server: GET stats start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return statService.getStats(LocalDateTime.parse(start, ConstantManager.formatter),
                LocalDateTime.parse(end, ConstantManager.formatter), uris, unique);
    }
}