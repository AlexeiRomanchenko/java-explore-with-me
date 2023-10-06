package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.practicum.location.dto.LocationDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class NewEventDto {

    @NotBlank
    @Size(min = 3, max = 120)
    private String title;

    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

    @PositiveOrZero
    private long category;

    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;

    @NotNull
    private String eventDate;

    @NotNull
    @Valid
    private LocationDto location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration = true;
}