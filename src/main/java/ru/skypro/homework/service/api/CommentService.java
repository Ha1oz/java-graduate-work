package ru.skypro.homework.service.api;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

public interface CommentService {
    CommentDto getComments(Integer pk);

    CommentDto addComment(Integer pk, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication);

    void deleteComment(Integer pk, Authentication authentication);

    CommentDto updateComment(Integer pk, CommentDto commentDto, Authentication authentication);
}
