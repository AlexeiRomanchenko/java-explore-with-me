package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comment.CommentService;
import ru.practicum.comment.dto.CommentResponseDto;
import ru.practicum.discriptions.MessageManager;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class PublicCommentController {
    private final CommentService commentService;

    @GetMapping("/events/{eventId}/comments")
    public List<CommentResponseDto> getEventComments(@PathVariable @Positive Long eventId,
                                                     @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                     @RequestParam(defaultValue = "10") @Positive int size) {
        log.info(MessageManager.receivedGetId, "/events/{eventId}/comments", eventId);
        return commentService.getEventComments(eventId, from, size);
    }

    @GetMapping("/comment/{commentId}")
    public CommentResponseDto getCommentById(@PathVariable @Positive Long commentId) {
        log.info(MessageManager.receivedGetId, "/comment/{commentId}", commentId);
        return commentService.getCommentById(commentId);
    }
}