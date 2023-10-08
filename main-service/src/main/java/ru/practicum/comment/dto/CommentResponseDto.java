package ru.practicum.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private EventShortDto event;
    private UserShortDto author;
    private String text;
    private String state;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private LocalDateTime publishedOn;
}