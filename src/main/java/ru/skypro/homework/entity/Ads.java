package ru.skypro.homework.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity(name="ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer price;
    private String title;
    private String description;
    private String image;
    /** Много объектов (@link Ads) могут ссылаться на один объект  типа User (у  User может быть много объявлений)
     */
    @ManyToOne
    @JsonIgnore
    /**  С помощью @JoinColumn мы указали в какой колонке нашей таблицы хранятся id объектa типа User
    */

    @JoinColumn(name = "user_id")
    private Users user;

    public Ads( ) {

    }
}
