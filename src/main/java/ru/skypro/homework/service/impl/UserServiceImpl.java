package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.exception.UserWithEmailNotFoundException;
import ru.skypro.homework.exception.UserWithIdNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
//import com.sky.AVITO.service.ImageService;
import ru.skypro.homework.service.inter.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    //    private final ImageService imageService;
    private final UserMapper userMapper;

    /**
     * Устанавливает новый пароль пользователю.
     * Использует методы:
     * {@link UserRepository#findByEmail(String)},
     * {@link PasswordEncoder#matches(CharSequence, String)},
     * {@link UserRepository#save(Object)}.
     *
     * @param newPasswordDto Объект NewPasswordDto с данными для установки нового пароля.
     * @param email       Адрес электронной почты пользователя.
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
     *
     * @param email Адрес электронной почты пользователя.
     * @return Объект UserDto с данными пользователя.
     * @throws UserWithEmailNotFoundException Если пользователь с указанным адресом электронной почты не найден.
     */
    @Override
    public UserDto getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserWithEmailNotFoundException(email));
        return userMapper.toUserDto(user);
    }

    /**
<<<<<<< HEAD
     * Найти и получить объект UserDto по id пользователя (Integer userId).
     * Использует методы:
     * {@link UserRepository#findById(Object)},
     * {@link UserMapper#toUserDto(User)}.
     *
     * @param userId пользователя.
     * @return oбъект UserDto с параметрами пользователя.
     * @throws UserWithIdNotFoundException - исключение eсли пользователь с указанным id не найден.
     */
    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserWithIdNotFoundException("Нет пользователя с userId"));
        //return UserMapper.mapToUserDto(user);
        //return modelMapper.map(user, UserDto.class);
        return userMapper.toUserDto(user);
    }

    /**
     * Получить  всех  пользователей.
     * Использует методы:
     * userRepository.findAll()
     * проходит stream() по всему листу найденных пользователей users.stream().map((user) ->
     * userMapper.toUserDto(user) - преобразует User  в UserDto
     * .collect(Collectors.toList() собирает в лист
     * return users.stream().map((user) -> userMapper.toUserDto(user))
     * .collect(Collectors.toList());
     */

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Создать пользователя.
     * Использует методы:
     * userMapper.usersFromDTO(userDto) Преобразовать UserDTO в пользовательскую сущность Users JPA
     * userRepository.save(user) - схранить пользовательскую сущность в БД
     * userMapper.toUserDto(user) - Преобразовать пользовательскую сущность Users JPA в UserDTO
     *
     * @return Объект UserDto - savedUserDto.
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        /**
         * Преобразовать UserDTO в пользовательскую сущность Users JPA
         * Пользователь user = UserMapper.mapToUser(UserDTO);
         * Пользователь user = ModelMapper.map(UserDTO, Users.class );
         */
        User user = userMapper.toUser(userDto);
        User savedUser = userRepository.save(user);

        /**
         *  Преобразовать пользовательскую сущность Users JPA в UserDTO
         *  UserDTO savedUserDto = UserMapper.mapToUserDto(сохраненный пользователь);
         *  UserDTO savedUserDto = ModelMapper.map(сохраненный пользователь, UserDto.class );
         *
         */


        UserDto savedUserDto = userMapper.toUserDto(user);

        return savedUserDto;
    }


    /**
     * Обновить данные пользователя.
     * Использует методы:
     * {@link UserRepository#findById(Object)},
     * {@link UserMapper#toUserDto(User)} - обновить существующего пользователя
     * {@link UserRepository#save(Object)} - сохранить обновленного пользователя.
     * Выбрасывает исключения:
     * {@link UserWithEmailNotFoundException (String)},
     *
     * @param userDto Объект UserDto с обновленными данными пользователя.
     * @param id Уникальный id пользователя
     * @return Объект UserDto с обновленными параметрами пользователя.
     * @throws UserWithEmailNotFoundException если пользователь с указанным адресом электронной почты не найден.
     */

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        User userWithId = userRepository.findById(id)
                .orElseThrow(() -> new UserWithIdNotFoundException("Нет пользователя с таким id"));
        userWithId.setFirstName(userWithId.getFirstName());
        userWithId.setLastName(userWithId.getLastName());
        userWithId.setEmail(userWithId.getEmail());
        User updatedUser = userRepository.save(userWithId);
        return userMapper.toUserDto(updatedUser);
    }

    /**
     * Обновить данные пользователя.
=======
     * Обновляет данные пользователя.
>>>>>>> elena_feature__3
     * Использует методы:
     * {@link UserRepository#findByEmail(String)},
     * {@link UserNotFoundException(String)},
     * {@link UserMapper#updateUserFromUserDto(UserDto, User)},
     * {@link UserRepository#save(Object)}.
     *
     * @param userDto Объект UserDto с обновленными данными пользователя.
     * @param email   Адрес электронной почты пользователя.
     * @return Объект UserDto с обновленными данными пользователя.
     * @throws UserNotFoundException Если пользователь с указанным адресом электронной почты не найден.
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
     * Удалить пользователя.
     * Использует методы:
     * {@link  UserRepository#deleteById(Object)}.
     *
     * @param userId пользователя.
     */
    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

//    /**
//     * Обновляет аватар пользователя.
//     * Использует методы:
//     * {@link UserRepository#findByEmail(String)},
//     * {@link UserWithEmailNotFoundException(String)},
//     * {@link ImageService#deleteFileIfNotNull(String)},
//     * {@link ImageService#saveImage(MultipartFile, String)},
//     * {@link UserRepository#save(Object)}.
//     *
//     * @param image Объект MultipartFile с новым аватаром пользователя.
//     * @param email Адрес электронной почты пользователя.
//     * @throws UserWithEmailNotFoundException Если пользователь с указанным адресом электронной почты не найден.
//     */
//    @Override
//    public void updateAvatar(MultipartFile image, String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserWithEmailNotFoundException(email));
//        imageService.deleteFileIfNotNull(user.getImage());
//        user.setImage(imageService.saveImage(image, "/users"));
//        userRepository.save(user);
//        log.trace("Avatar updated");
//    }

//    /**
//     * Получает изображение по его имени.
//     * Использует метод {@link ImageService#getImage(String)} для получения изображения по имени.
//     *
//     * @param name Имя изображения, которое нужно получить.
//     * @return Массив байтов, представляющий изображение.
//     * @throws IOException Если произошла ошибка при получении изображения.
//     */
//    @Override
//    public byte[] getImage(String name) throws IOException {
//        return imageService.getImage(name);
//    }
}

