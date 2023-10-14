package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity(name = "ad")
public class Ad {

    private Integer author;
    private String image;

    @Id
    @GeneratedValue
    private Integer pk;

    private Integer price;
    private String title;

    public Ad() {
    }
}
