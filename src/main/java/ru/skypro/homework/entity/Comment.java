package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.ExtendedAdDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    private Integer pk;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "author")
    private User user;

    private String text;

    public Comment() {
    }

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    public void setAd(ExtendedAdDto ad) {

    }
}
