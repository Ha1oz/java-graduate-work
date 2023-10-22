package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "ad")
public class Ad {

    @Id
    @GeneratedValue
    private Integer pk;

    @ManyToOne
    @JoinColumn(name = "author")
    private User user;

    private String image;


    private Integer price;
    private String title;

    public Ad() {
    }

    @OneToMany(mappedBy = "ad")
    private List<Comment> comments;
}
