package ru.skypro.homework.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Users;
import ru.skypro.homework.exception.UserWithEmailNotFoundException;
import ru.skypro.homework.exception.UserWithIdNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class UserServiceImpl implements ru.skypro.homework.service.inter.UserService {
    private final UserDetailsManager manager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    /**
     *  Конструктор не нужен если мы используем аннотацию @RequiredArgsConstructor - генерирует
     *  конструктор с 1 параметром для каждого поля, который требует специальной обработки.
     *  Все неинициализированные final поля получают параметр, а также любые поля, помеченные
     *  как @NonNull которые не инициализированы там, где они объявлены. Для полей, помеченных
     *  @NonNull, также генерируется явная проверка на нулевое значение. Конструктор выдаст
     *    NullPointerException, если какой-либо из параметров, предназначенных для полей,
     *  отмеченных знаком, @NonNull содержит null. Порядок параметров соответствует порядку,
     *  в котором поля отображаются в вашем классе.
     *  */

    /**
     * Обновление пароля (установка нового):
     * Методы:
     * метод userExists, чтобы определить, существует ли пользователь.
     * UserRepository#findById(Integer) - вызывает у репозитория  метод который ищет по id
     * .isPresent() - Этот метод возвращает ответ, существует ли искомый объект или нет, в виде Boolean     *
     * Метод get() возвращает объект, запакованный в Optional.
     * String encode(CharSequence rawPassword) -   Закодируйте необработанный пароль.
     * Boolean  matches(CharSequence rawPassword, String encodedPassword) -Убедитесь, что закодированный
     * пароль, полученный из хранилища, совпадает с отправленным необработанным паролем после того,
     * как он тоже закодирован.
     *
     * @param rawPassword     необработанный пароль для кодирования и сопоставления
     * @param encodedPassword закодированный пароль из хранилища для сравнения      *
     * @param newPassword     Объект NewPassword с данными для установки нового пароля.
     * @param userId          id пользователя.
     * @return true, если пароль успешно обновлен, иначе false (если указан неверный текущий пароль).
     * @возвращает значение true, если исходный пароль после кодирования совпадает с закодированным паролем
     * из  хранения, в противном случае false.
     * UserRepository#save(Object)  - сохраняет объект в БД
     */
    @Override
    public boolean updatePasswordForRegister(NewPasswordDto newPassword, Integer userId, Register register) {
        if (manager.userExists(register.getUsername())) {
            return false;
        }
        Optional<Users> userReg = userRepository.findById(userId);
        if (userReg.isPresent()) {
            Users user = userReg.get();
            if (encoder.matches(newPassword.getNewPassword(), user.getPassword())) {
                user.setPassword(encoder.encode(newPassword.getNewPassword()));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    /**
     * Найти и получить объект UserDto по адресу электронной почты пользователя (String email).
     * Использует методы:
     * {@link UserRepository#findByEmail(String)},
     * {@link UserMapper#toUserDto(user)}.
     *
     * @param email aдрес электронной почты пользователя.
     * @return oбъект UserDto с параметрами пользователя.
     * @throws UserWithEmailNotFoundException - исключение eсли пользователь с указанным адресом электронной почты не найден.
     */
    @Override
    public UserDto getUser(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserWithEmailNotFoundException(email));
        return userMapper.toUserDto(user);
    }

    /**
     * Найти и получить объект UserDto по id пользователя (Integer userId).
     * Использует методы:
     * {@link UserRepository#findById(userId)} (userId)},
     * {@link UserMapper#toUserDto(user)}.
     *
     * @param userId пользователя.
     * @return oбъект UserDto с параметрами пользователя.
     * @throws UserWithIdNotFoundException - исключение eсли пользователь с указанным id не найден.
     */
    @Override
    public UserDto getUserById(Integer userId) {
        Users user = userRepository.findById(userId)
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
        List<Users> users = userRepository.findAll();
        return users.stream().map((user) -> userMapper.toUserDto(user))
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
        Users user = userMapper.usersFromDTO(userDto);
        Users savedUser = userRepository.save(user);

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
     * {@link UserRepository#findById(id)},
     * {@link userMapper.toUserDto(updatedUser)} - обновить существующего пользователя
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
        Users userWithId = userRepository.findById(id)
                .orElseThrow(() -> new UserWithIdNotFoundException("Нет пользователя с таким id"));
        userWithId.setFirstName(userWithId.getFirstName());
        userWithId.setLastName(userWithId.getLastName());
        userWithId.setEmail(userWithId.getEmail());
        Users updatedUser = userRepository.save(userWithId);
        return userMapper.toUserDto(updatedUser);
    }

    /**
     * Обновить данные пользователя.
     * Использует методы:
     * {@link UserRepository#findByEmail(String)},
     * {@link UserMapper#usersFromUserDto(userDto, existingUser)} - обновить существующего пользователя
     * {@link UserRepository#save(Object)} - сохранить обновленного пользователя.
     * Выбрасывает исключения:
     *
     * @param userDto Объект UserDto с обновленными данными пользователя.
     * @param email   Адрес электронной почты пользователя.
     * @return Объект UserDto с обновленными параметрами пользователя.
     * @throws UserWithEmailNotFoundException если пользователь с указанным адресом электронной почты не найден.
     */
    @Override
    public UserDto updateUser(UserDto userDto, String email) {
        Users existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserWithEmailNotFoundException(email));
        userMapper.usersFromUserDto(userDto, existingUser);
        Users updatedUser = userRepository.save(existingUser);
        return userMapper.toUserDto(updatedUser);
    }

    /**
     * Удалить пользователя.
     * Использует методы:
     * {@link  userRepository.deleteById(userId)},
     *
     * @param userId пользователя.
     */
    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}






























