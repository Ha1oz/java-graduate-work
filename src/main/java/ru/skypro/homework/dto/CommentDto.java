package ru.skypro.homework.dto;

import java.time.LocalDateTime;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CommentDto {
    private Integer author;
    //ссылка на аватар автора комментария
    private String authorImage;
    private String authorFirstName;
    private LocalDateTime createdAt;
    private Integer pk;
    private String text;
}

