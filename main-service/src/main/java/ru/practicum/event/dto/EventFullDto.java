package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class EventFullDto {
    @JsonUnwrapped
    private EventInfoDto eventInfo;

    @JsonUnwrapped
    private EventDetailsDto eventDetails;

    @JsonUnwrapped
    private EventMetadataDto eventMetadata;

    @JsonUnwrapped
    private EventUserDto eventUser;

}