package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.api.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserDto updateUser(UserDto userDto, String username) {
        User user = userRepository.findUserByEmail(username).orElseThrow(RuntimeException::new);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        User response = userRepository.save(user);

        return userMapper.mapToDto(response);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findUserByEmail(username).orElseThrow(RuntimeException::new);
    }

    @Override
    public UserDto getUserDtoByUsername(String username) {
        User response = getUser(username);
        return userMapper.mapToDto(response);
    }
}
