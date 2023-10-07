package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class EventMetadataDto {
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
    private String state;
    private long views;
}