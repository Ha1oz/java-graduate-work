package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "username", target = "email")
    User toUser(Register register);
    User toUser(UserDto userDto);
    MyUserDetailsDto toMyUserDetailsDto(User user);

    UserDto toUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    void updateUserFromUserDto(UserDto userDto, @MappingTarget User user);


//
    void updateUser(UpdateUserDto updateAdDto, @MappingTarget User user);}

