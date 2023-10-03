package ru.practicum.model;

import lombok.experimental.UtilityClass;
import ru.practicum.discriptions.ConstantManager;
import ru.practicum.dto.HitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class HitMapper {

    public Hit toHit(HitDto hitDto) {
        return Hit.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(LocalDateTime.parse(hitDto.getTimestamp(), DateTimeFormatter.ofPattern(ConstantManager.DATE_TIME_PATTERN)))
                .build();
    }
}