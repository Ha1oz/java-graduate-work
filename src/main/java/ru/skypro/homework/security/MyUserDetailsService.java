package ru.skypro.homework.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import  ru.skypro.homework.dto.MyUserDetailsDto;
import ru.skypro.homework.exception.CommentNotFoundException;
import  ru.skypro.homework.exception.UserWithEmailNotFoundException;
import  ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.CommentRepository;
import  ru.skypro.homework.repository.UserRepository;


/**
 *«Пользователь» – это просто Object. В большинстве случаев он может быть
 * приведен к классу UserDetails. UserDetails можно представить, как адаптер между БД пользователей
 * и тем что требуется Spring Security внутри SecurityContextHolder.
 * Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:
 * UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
 */



@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MyUserDetails myUserDetails;
    private final UserMapper userMapper;

    /**
     * В нашей реализации  ищем пользователя по его  email внутри базы данных
     * Получить объект  UserDetails  по  Email()  .
     * методы:
     * {@link  UserRepository#findByEmail (email)}
     * {@link  UserMapper#toMyUserDetailsDto (u)}
     * @param email   Идентификатор комментария, который нужно получить.
     * @return Объект UserDetails
     * @throws UserWithEmailNotFoundException Если пользователь с указанным  Email  не найден.
     */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MyUserDetailsDto myUserDetailsDto = userRepository.findByEmail(email)
                .map(user -> userMapper.toMyUserDetailsDto(user))
                .orElseThrow(() -> new UserWithEmailNotFoundException(email));
        myUserDetails.setMyUserDetailsDto(myUserDetailsDto);
        return myUserDetails;
    }
}

