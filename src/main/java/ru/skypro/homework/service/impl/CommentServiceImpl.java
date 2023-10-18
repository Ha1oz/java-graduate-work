package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.service.inter.CommentService;

//Class-plug
@Service
public class CommentServiceImpl implements CommentService {
    public CommentsDto getComments(Integer id) {
        return new CommentsDto();
    }

    public CommentDto addComment(Integer id, CreateOrUpdateCommentDto createComment, String name) {
        return new CommentDto();
    }

    public void deleteComment(Integer adId, Integer commentId) {
    }

    public CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto updateComment) {
        return new CommentDto();
    }
}
