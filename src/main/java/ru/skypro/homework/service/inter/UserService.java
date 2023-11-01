package ru.skypro.homework.service.inter;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;

import java.util.List;


public interface UserService  {

    boolean setPassword(NewPasswordDto newPasswordDto, String email);
    UserDto getUser(String email);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer id);

    UserDto updateUser(UserDto userDto, String email);

    void deleteUser(Integer userId);

//    void updateAvatar(MultipartFile image, String email);
//
//    byte[] getImage(String name) throws IOException;
    }


