package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.user.dto.UserShortDto;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class EventUserDto {
    private UserShortDto initiator;
}