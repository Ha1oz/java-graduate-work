package ru.skypro.homework.service.api;

import ru.skypro.homework.dto.CommentDto;

public interface CommentService {
    CommentDto getComments(Integer pk);

    CommentDto addComment(Integer pk, CommentDto commentDto);

    void deleteComment(Integer pk);

    CommentDto updateComment(Integer pk, CommentDto commentDto);
}
