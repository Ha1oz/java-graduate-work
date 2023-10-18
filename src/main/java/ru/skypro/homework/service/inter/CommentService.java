package ru.skypro.homework.service.inter;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

public interface CommentService {
    CommentsDto getComments(Integer id);
    CommentDto addComment(Integer id, CreateOrUpdateCommentDto createComment, String name);
    void deleteComment(Integer adId, Integer commentId);
    CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto updateComment);
}
