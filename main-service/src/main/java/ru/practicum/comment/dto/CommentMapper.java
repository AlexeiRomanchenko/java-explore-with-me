package ru.practicum.comment.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.comment.Comment;
import ru.practicum.event.dto.EventMapper;
import ru.practicum.user.dto.UserMapper;

@UtilityClass
public class CommentMapper {

    public static Comment toComment(NewCommentDto newCommentDto) {
        return Comment.builder()
                .text(newCommentDto.getText())
                .build();
    }

    public static CommentResponseDto toCommentResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .event(EventMapper.toEventShortDto(comment.getEvent()))
                .author(UserMapper.toUserShortDto(comment.getAuthor()))
                .text(comment.getText())
                .state(comment.getState().toString())
                .createdOn(comment.getCreatedOn())
                .updatedOn(comment.getUpdatedOn() != null ? comment.getUpdatedOn() : null)
                .publishedOn(comment.getPublishedOn() != null ? comment.getPublishedOn() : null)
                .build();
    }
}