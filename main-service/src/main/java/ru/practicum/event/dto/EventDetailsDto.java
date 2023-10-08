package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class EventDetailsDto {
    private String description;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private long confirmedRequests;
}