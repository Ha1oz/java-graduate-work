package ru.skypro.homework.service.inter;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;

import java.io.IOException;
import java.util.List;


public interface UserService  {

    boolean setPassword(NewPasswordDto newPasswordDto, String email);
    UserDto getUser(String email);

//    UserDto getUserById(Integer userId);
//
//    List<UserDto> getAllUsers();
//
//    UserDto createUser(UserDto userDto);

//    UserDto updateUser(UserDto userDto, Integer id);

//    void deleteUser(Integer userId);

    UserDto updateUser(UserDto userDto, String email);

    void updateAvatar(MultipartFile image, String email);

    byte[] getImage(String name) throws IOException;


    UserDto updateUserDto(UpdateUserDto updateUserDto, Integer id);
}


