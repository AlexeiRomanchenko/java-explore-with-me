package ru.practicum.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdateCompilationRequestDto {
    private List<Long> events;
    private Boolean pinned;
    @Size(min = 1, max = 50)
    private String title;
}