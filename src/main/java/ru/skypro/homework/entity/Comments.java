package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime createdAt;
    private String text;
    /** Много объектов (@link Comment) могут ссылаться на один объект  типа User (у User может быть много комметариев)
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    /** Много объектов (@link Comment) могут ссылаться на один объект  типа Ad  (у Ad  может быть много комметариев)
     */
    @ManyToOne
    /**  С помощью @JoinColumn мы указали в какой колонке нашей таблицы хранятся id объектa типа Ad
     */
    @JoinColumn(name = "ads_id")
    private Ads ads;

    public Comments( ) {

    }
}
