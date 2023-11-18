package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.exception.UserWithEmailNotFoundException;
import ru.skypro.homework.exception.UserWithIdNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.inter.UserService;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ImageService imageService;
    private final UserMapper userMapper;

    /**
     * Устанавливает новый пароль пользователю.
     * Использует методы:
     * {@link UserRepository#findByEmail(String)},
     * {@link PasswordEncoder#matches(CharSequence, String)},
     * {@link UserRepository#save(Object)}.
     * @param newPasswordDto Объект NewPasswordDto с данными для установки нового пароля.
     * @param email          Адрес электронной почты пользователя.
     * @return true, если пароль успешно обновлен, иначе false (если указан неверный текущий пароль).
     */
    @Override
    public boolean setPassword(NewPasswordDto newPasswordDto, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (encoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
                user.setPassword(encoder.encode(newPasswordDto.getNewPassword()));
                userRepository.save(user);
                log.trace("Updated password");
                return true;
            }
        }
        log.trace("Password not update");
        return false;
    }

    /**
     * Получает объект UserDto по адресу электронной почты пользователя.
     * Использует методы:
     * {@link UserRepository#findByEmail(String)},
     * {@link UserWithEmailNotFoundException(String)},
     * {@link UserMapper#toUserDto(User)}.
     * @param email Адрес электронной почты пользователя.
     * @return Объект UserDto с данными пользователя.
     * @throws UserWithEmailNotFoundException - ошибка, eсли пользователь с указанным адресом электронной почты не найден.
     */
    @Override
    public UserDto getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserWithEmailNotFoundException(email));
        return userMapper.toUserDto(user);
    }

    /**
     * Обновить данные пользователя.
     * Использует методы:
     * {@link UserRepository#findByEmail(String)},
     * {@link UserNotFoundException(String)},
     * {@link UserMapper#updateUserFromUserDto(UserDto, User)},
     * {@link UserRepository#save(Object)}.
     *
     * @param userDto Объект UserDto с обновленными данными пользователя.
     * @param email   Адрес электронной почты пользователя.
     * @return Объект UserDto с обновленными данными пользователя.
     * @throws UserNotFoundException ошибка, eсли пользователь с указанным адресом электронной почты не найден.
     */
    @Override
    public UserDto updateUser(UserDto userDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        userMapper.updateUserFromUserDto(userDto, user);
        userRepository.save(user);
        log.trace("User updated");
        return userMapper.toUserDto(user);
    }

    /**
     * Обновить аватар пользователя.
     * Методы:
     * {@link UserRepository#findByEmail(String)},
     * {@link UserWithEmailNotFoundException(String)},
     * {@link ImageService#deleteFile(String)},
     * {@link ImageService#uploadImage(MultipartFile, String)},
     * {@link UserRepository#save(Object)}.
     * @param image Объект MultipartFile с новым аватаром пользователя.
     * @param email Адрес электронной почты пользователя.
     * @throws UserWithEmailNotFoundException - ошибка, если пользователь с указанным адресом электронной почты не найден.
     */
    @Override
    public void updateAvatar(MultipartFile image, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserWithEmailNotFoundException(email));
        imageService.deleteFile(user.getImage());
        user.setImage(imageService.uploadImage(image, "/users"));
        userRepository.save(user);
        log.trace("Avatar updated");
    }

    /**
     * Получить изображение по его имени.
     * метод {@link ImageService#downloadImage (String)} для получения изображения по имени.
     *
     * @param name Имя изображения, которое нужно получить.
     * @return Массив байтов, представляющий изображение.
     * @throws IOException - ошибка при получении изображения.
     */
    @Override
    public byte[] getImage(String name) throws IOException {
        return imageService.downloadImage (name);
    }

    @Override
    public UserDto updateUserDto(UpdateUserDto updateUserDto, Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserWithIdNotFoundException("Update user with id:", id));
        userMapper.updateUser(updateUserDto, user);
        userRepository.save(user);
        log.trace("Update user with id:  ", id);
        return userMapper.toUserDto(user);
    }
}


