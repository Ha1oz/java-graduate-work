package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Data
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Email(message = "Некорректный адрес электронной почты")
    private String eMail;

    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;

    public User() {
    }
}

