package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comment.CommentService;
import ru.practicum.comment.dto.CommentResponseDto;
import ru.practicum.discriptions.MessageManager;

import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class AdminCommentController {
    private final CommentService commentService;

    @PatchMapping("/admin/comment/{commentId}")
    public CommentResponseDto updateCommentStatusByAdmin(@PathVariable @Positive Long commentId,
                                                         @RequestParam boolean isConfirm) {
        log.info(MessageManager.receivedPatch, "/admin/comment/{commentId}", commentId);
        return commentService.updateCommentStatusByAdmin(commentId, isConfirm);
    }
}