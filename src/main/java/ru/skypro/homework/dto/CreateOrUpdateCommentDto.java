package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateOrUpdateCommentDto {
    private String text;

    public Integer getPk() {
        return null;
    }

    public Integer getAuthor() {
        return null;
    }
}
