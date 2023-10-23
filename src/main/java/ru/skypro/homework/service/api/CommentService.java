package ru.skypro.homework.service.api;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

public interface CommentService {
    CommentDto getComments(Integer pk);

    CommentDto addComment(Integer pk, CreateOrUpdateCommentDto createOrUpdateCommentDto);

    void deleteComment(Integer pk);

    CommentDto updateComment(Integer pk, CommentDto commentDto);
}
