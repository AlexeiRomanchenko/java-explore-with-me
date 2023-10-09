package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.CommentService;
import ru.practicum.comment.dto.CommentResponseDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.discriptions.MessageManager;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class PrivateCommentController {
    private final CommentService commentService;

    @PostMapping("/user/{userId}/events/{eventId}/comment/")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@PathVariable @Positive Long userId,
                                            @PathVariable @Positive Long eventId,
                                            @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info(MessageManager.receivedPostUserIdEventId, "/user/{userId}/events/{eventId}/comment/", userId, eventId);
        return commentService.createComment(userId, eventId, newCommentDto);
    }

    @PatchMapping("/user/{userId}/comment/{commentId}")
    public CommentResponseDto updateComment(@PathVariable @Positive Long userId,
                                            @PathVariable @Positive Long commentId,
                                            @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info(MessageManager.receivedPatchUserIdCommentId, "/user/{userId}/comment/{commentId}", userId, commentId);
        return commentService.updateComment(userId, commentId, newCommentDto);
    }

    @DeleteMapping("/user/{userId}/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable @Positive Long userId,
                              @PathVariable @Positive Long commentId) {
        log.info(MessageManager.receivedDeleteUserIdCommentId, "/user/{userId}/comment/{commentId}", userId, commentId);
        commentService.deleteComment(userId, commentId);
    }
}