package ru.practicum.request.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.discriptions.ConstantManager;
import ru.practicum.request.ParticipationRequest;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class ParticipationRequestMapper {

    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus().toString())
                .created(request.getCreated().format(DateTimeFormatter.ofPattern(ConstantManager.DATE_TIME_PATTERN)))
                .build();
    }
}