package ru.skypro.homework.service.inter;

import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UserDto;

import java.util.List;

public interface UserService {
    boolean updatePasswordForRegister(NewPasswordDto newPassword, Integer userId, Register register);

    UserDto getUser(String email);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer id);

    UserDto updateUser(UserDto userDto, String email);

    void deleteUser(Integer userId);
}
