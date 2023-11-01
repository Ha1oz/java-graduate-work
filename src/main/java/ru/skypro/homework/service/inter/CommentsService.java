package ru.skypro.homework.service.inter;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;

import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

public interface CommentsService {
    CommentsDto getComments(Integer id);

    CommentDto addComment(Integer id, CreateOrUpdateCommentDto createComment, String email);

    void deleteComment(Integer adId, Integer id);

    CommentDto updateComment(Integer adId, Integer id, CreateOrUpdateCommentDto createComment);
    CommentDto getCommentDto(Integer adId,Integer id);

    String getUserNameOfComment(Integer id);
}
