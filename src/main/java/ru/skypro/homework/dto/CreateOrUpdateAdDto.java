package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateOrUpdateAdDto {
    private String description;
    private Long price;
    private String title;
}
