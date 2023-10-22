package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.api.CommentService;
import ru.skypro.homework.service.api.UserService;

import java.time.LocalDate;

import static ru.skypro.homework.mapper.UserMapper.mapToDto;

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
                .orElseThrow(CommentsNotFoundException::new); //TO DO Exception
        return CommentMapper.mapToDto(comment);
    }

    @Override
    public CommentDto addComment(Integer pk, CommentDto commentDto) {
//        Ad ad = adService.getAdById(pk);
//        User currentUser = userService.getUser(authentication.getName());

        Comment comment = CommentMapper.mapToEntity(commentDto);
//        comment.setAd(ad);
//        comment.setUser(currentUser);
        comment.setCreatedAt(LocalDate.now().atStartOfDay());
        Comment result = commentRepository.save(comment);
        return CommentMapper.mapToDto(result);
    }

    @Override
    public void deleteComment(Integer pk) {
        Comment comment = commentRepository.findAdComments(pk)
                .orElseThrow(CommentNotFoundException::new); //TO DO Exception
//        securityService.checkIfUserHasPermissionToAlter(authentication, comment.getUser().getEmail());
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto updateComment(Integer pk, CommentDto commentDto) {
        Comment comment = commentRepository.findAdComments(pk)
                .orElseThrow(CommentNotFoundException::new); //TO DO Exception
//        securityService.checkIfUserHasPermissionToAlter(authentication, comment.getUser().getEmail());
        comment.setText(commentDto.getText());
        comment.setCreatedAt(LocalDate.parse(commentDto.getCreatedAt()));
        commentRepository.save(comment);
        return CommentMapper.mapToDto(comment);
    }
}
