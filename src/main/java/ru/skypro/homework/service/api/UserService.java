package ru.skypro.homework.service.api;

import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

public interface UserService {
    UserDto updateUser(UserDto userDto, String username);
    User getUser(String username);
    UserDto getUserDtoByUsername(String username);
}
