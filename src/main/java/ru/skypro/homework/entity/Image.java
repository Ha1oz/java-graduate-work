package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import javax.persistence.*;
import java.util.Arrays;

@Entity(name = "images")
@Data
public class Image {

    @Id
    @GeneratedValue
    private Integer id;

    private Long fileSize;
    private String mediaType;
    @Lob
    @Type(type = "binary")
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "ads_id")
    @JsonIgnore
    private Ad ad;

    @OneToOne
    @JoinColumn(name = "author")
    @JsonIgnore
    private User user;

    public String toString() {
        return "AdEntity(id=" + this.getId() + ", image=" + Arrays.toString(this.getData()) + ")";
    }
}
