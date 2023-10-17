package ru.skypro.homework.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Users;
import org.mapstruct.MappingTarget;
/**Mapstruct поддерживает сопоставление одного компонента с другим компонентом,
   по умолчанию используется карта с тем же именем,
   разные имена настраиваются с помощью  @Mapping,
   а поля, которые не хотят сопоставляться, могут быть настроены без участия mapping
 */

public interface UserMapper {
    UserMapper INSTANCT = Mappers.getMapper(UserMapper.class);

//    Пример
//    @Mapping(target = "userId", source = "id")
//    @Mapping(target = "password", ignore = true)
//    UserDto userToUserDto(Users user);
//
//    /**Двустороннее отображение можно использовать с помощью @inheritInverseConfigration для упрощения настройки
//     */
//
//    @InheritInverseConfiguration(name = "userToUserDto")
//    Users userDtoToUser(UserDto userDto);



    /**
     * Преобразовать в объект Users
     * @param  register Register
     * @return Users
     */

    /**@Mapping target - это свойства объекта (@link - User), которые должны быть сгенерированы и должны быть заполнены.
     * А source - параметр object properties, selection - (@link - Register)
     * Когда свойства двух объектов несовместимы, например, когда поле в объекте source Register не существует в Users
     * во время компиляции будет предупреждение,  поэтому вместо sourse ignore = true в @Mapping.
     * Поэтому полей, которых нет в source  обозначаем ignore = true. Это поля - id и image
     */



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", source = "username")
    @Mapping(target = "image", ignore = true)
    Users toUsers(Register register);



    /**
     * Преобразовать в объект UpdateUserDto
     * @param user Users
     * @return UpdateUserDto
     * Все поля есть в source   - (@link   Users)
     */
    UpdateUserDto toUpdateUserDto (Users user);



    /**
     * Преобразовать в объект UserDto
     * @param user Users
     * @return UserDto
     * Все поля есть в source   - (@link   Users)
     */

    UserDto toUserDto(Users user);





    /**   Когда свойства двух объектов несовместимы, например, когда поле в объекте  sourse UserDto не существует в Users,
     во время компиляции будет предупреждение, настроим ignore = true в @Mapping.
     */
    @Mapping(target = "email", source = "eMail")
    @Mapping(target = "password", ignore = true)

    void  usersFromUserDto(UserDto userDto, @MappingTarget Users user);

    Users usersFromDTO(UserDto userDto);
}
