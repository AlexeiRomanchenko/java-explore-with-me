package ru.practicum.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.dto.CommentMapper;
import ru.practicum.comment.dto.CommentResponseDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.discriptions.FromSizeRequest;
import ru.practicum.discriptions.MessageManager;
import ru.practicum.event.Event;
import ru.practicum.event.EventRepository;
import ru.practicum.event.EventState;
import ru.practicum.exceptions.CommentNotFoundException;
import ru.practicum.exceptions.EventNotFoundException;
import ru.practicum.exceptions.ForbiddenException;
import ru.practicum.exceptions.UserNotFoundException;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public CommentResponseDto createComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        log.info("Create comment from user with ID = {} ,for event with ID = {}, : {}", userId, eventId, newCommentDto);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findByIdAndState(eventId, EventState.PUBLISHED)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        Comment comment = CommentMapper.toComment(newCommentDto);
        comment.setEvent(event);
        comment.setAuthor(user);
        comment.setState(CommentState.PENDING);
        comment.setCreatedOn(LocalDateTime.now());
        return CommentMapper.toCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getEventComments(Long eventId, int from, int size) {
        PageRequest page = FromSizeRequest.of(from, size);
        log.info("Get comments for event with ID = {}", eventId);
        Event event = eventRepository.findByIdAndState(eventId, EventState.PUBLISHED)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        List<Comment> comments = commentRepository.findByEvent(event, page);
        return comments.stream().map(CommentMapper::toCommentResponseDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponseDto getCommentById(Long commentId) {
        log.info("Get comment by ID = {}", commentId);
        return CommentMapper.toCommentResponseDto(commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId)));
    }

    @Override
    public CommentResponseDto updateComment(Long userId, Long commentId, NewCommentDto newCommentDto) {
        log.info("Update comment with ID = {}, from user with ID = {}, : {}", commentId, userId, newCommentDto);
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ForbiddenException(MessageManager.canNotUpdateCommentOtherUser);
        }
        if (comment.getState() == CommentState.CONFIRMED) {
            throw new ForbiddenException(MessageManager.canNotUpdateConfirmedComment);
        }
        comment.setText(newCommentDto.getText());
        comment.setUpdatedOn(LocalDateTime.now());
        comment.setState(CommentState.PENDING);
        return CommentMapper.toCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        log.info("Delete comment with ID = {} from user with ID = {} ", commentId, userId);
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ForbiddenException(MessageManager.canNotDeleteCommentOtherUser);
        }
        if (comment.getState() == CommentState.CONFIRMED) {
            throw new ForbiddenException(MessageManager.canNotDeleteConfirmedComment);
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentResponseDto updateCommentStatusByAdmin(Long commentId, boolean isConfirm) {
        log.info("Confirm/reject comment with ID = {}. New state: {}", commentId, isConfirm);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        if (isConfirm) {
            comment.setState(CommentState.CONFIRMED);
        } else {
            comment.setState(CommentState.REJECTED);
        }
        comment.setPublishedOn(LocalDateTime.now());
        return CommentMapper.toCommentResponseDto(commentRepository.save(comment));
    }
}