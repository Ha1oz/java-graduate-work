package ru.skypro.homework.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentDTO {
    Integer author;
    //ссылка на аватар автора комментария
    String authorImage;
    String authorFirstName;
    //дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970 -  LocalDataTime???
    LocalDateTime createdAt;
    //id комментария
    Integer pk;
    String text;
}
