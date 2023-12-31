package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.discriptions.MessageManager;
import ru.practicum.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.request.dto.ParticipationRequestDto;

import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class ParticipationRequestController {
    private final ParticipationRequestService participationRequestService;

    @PostMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createParticipationRequest(@PathVariable @Positive Long userId,
                                                              @RequestParam @Positive Long eventId) {
        log.info(MessageManager.receivedPostId, "/users/{userId}/requests", userId);
        return participationRequestService.createParticipationRequest(userId, eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelParticipationRequest(@PathVariable @Positive Long userId,
                                                              @PathVariable @Positive Long requestId) {
        log.info(MessageManager.receivedPatch, "/users/{userId}/requests/{requestId}/cancel", userId);
        return participationRequestService.cancelParticipationRequest(userId, requestId);
    }

    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> getParticipationRequests(@PathVariable @Positive Long userId) {
        log.info(MessageManager.receivedGetId, "/users/{userId}/requests", userId);
        return participationRequestService.getParticipationRequests(userId);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getParticipationRequestsForUserEvent(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId) {
        log.info(MessageManager.receivedGetId, "/users/{userId}/events/{eventId}/requests", userId);
        return participationRequestService.getParticipationRequestsForUserEvent(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResultDto changeParticipationRequestsStatus(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId,
            @RequestBody EventRequestStatusUpdateRequestDto eventRequestStatusUpdateRequest) {
        log.info(MessageManager.receivedPatch, "/users/{userId}/events/{eventId}/requests", userId);
        return participationRequestService.changeParticipationRequestsStatus(
                userId, eventId, eventRequestStatusUpdateRequest);
    }
}