package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.api.AdService;
import ru.skypro.homework.service.api.CommentService;
import ru.skypro.homework.service.api.UserService;
import ru.skypro.homework.exception.CommentsNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final AdService adService;

    @Override
    public CommentDto getComments(Integer pk) {
        Comment comment = commentRepository.findAdComments(pk)
                .orElseThrow(() -> new CommentsNotFoundException(log.getName()));
        return CommentMapper.mapToDto(comment);
    }

    @Override
    public CommentDto addComment(Integer pk, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication) {
        ExtendedAdDto ad = adService.getAds(pk);
//        User currentUser = userService.getUser(authentication.getName()); TODO

        Comment comment = CommentMapper.mapToEntity(createOrUpdateCommentDto);
        comment.setAd(ad);
//        comment.setUser(currentUser); TODO
        comment.setCreatedAt(LocalDate.now().atStartOfDay());
        Comment result = commentRepository.save(comment);
        return CommentMapper.mapToDto(result);
    }

    @Override
    public void deleteComment(Integer pk, Authentication authentication) {
        Comment comment = commentRepository.findAdComments(pk)
                .orElseThrow(() -> new CommentNotFoundException(log.getName()));
//        securityService.checkIfUserHasPermissionToAlter(authentication, comment.getUser().getEmail()); TODO
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto updateComment(Integer pk, CommentDto commentDto, Authentication authentication) {
        Comment comment = commentRepository.findAdComments(pk)
                .orElseThrow(() -> new CommentNotFoundException(log.getName()));
//        securityService.checkIfUserHasPermissionToAlter(authentication, comment.getUser().getEmail()); TODO
        comment.setText(commentDto.getText());
        comment.setCreatedAt(LocalDate.now().atStartOfDay());
        commentRepository.save(comment);
        return CommentMapper.mapToDto(comment);
    }
}
