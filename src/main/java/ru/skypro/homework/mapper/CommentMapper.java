package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class CommentMapper {
    private static UserRepository userRepository;

    public static CommentDto mapToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setAuthor(comment.getUser().getId());
        dto.setAuthorImage(comment.getUser().getImage());
        dto.setAuthorFirstName(comment.getUser().getFirstName());
        dto.setCreatedAt(comment.getCreatedAt().getSecond());
        dto.setPk(comment.getPk());
        dto.setText(comment.getText());
        return dto;
    }

    public static Comment mapToEntity(CreateOrUpdateCommentDto dto) {
        Comment comment = new Comment();
        User author = userRepository.findById(dto.getAuthor())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        comment.setUser(author);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setPk(dto.getPk());
        comment.setText(dto.getText());
        return comment;
    }
}
