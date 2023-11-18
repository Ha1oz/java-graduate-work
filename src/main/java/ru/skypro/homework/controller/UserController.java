package ru.skypro.homework.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.inter.UserService;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * Установить новый пароль для пользователя.  *
     *
     * @param newPasswordDto           Объект {@link NewPasswordDto} с новым паролем.
     * @param userDetails @AuthenticationPrincipal UserDetails userDetails Объект {@link    UserDetails userDetails } с информацией об аутентифицированном пользователе.
     * @return Объект {@link ResponseEntity} с объектом {@link NewPasswordDto} и статусом ответа.
     */

    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPasswordDto,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        userService.setPassword(newPasswordDto, userDetails.getUsername());
        return ResponseEntity.ok(newPasswordDto);
    }

    /**
     * Получить информацию о текущем пользователе.
     *
     * @param userDetails @AuthenticationPrincipal UserDetails userDetails Объект {@link    UserDetails userDetails } с информацией об аутентифицированном пользователе.
     * @return Объект {@link ResponseEntity} с объектом {@link UserDto} и статусом ответа.
     */

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getUser(userDetails.getUsername()));
    }


    /**
     * Обновить информацию о текущем пользователе.
     *
     * @param userDto                  Объект {@link UserDto} с обновленными данными пользователя.
     * @param userDetails @AuthenticationPrincipal UserDetails userDetails Объект {@link    UserDetails userDetails } с информацией об аутентифицированном пользователе.
     * @return Объект {@link ResponseEntity} с обновленным объектом {@link UserDto} и статусом ответа.
     */

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.updateUser(userDto, userDetails.getUsername()));
    }

    /**
     * Обновить аватар пользователя.
     *
     * @param image          Объект {@link MultipartFile} с новым изображением для аватара.
     * @param userDetails @AuthenticationPrincipal UserDetails userDetails Объект {@link @AuthenticationPrincipal UserDetails userDetails} с информацией об аутентифицированном пользователе.
     * @return Объект {@link ResponseEntity} с пустым телом ответа и статусом NO_CONTENT в случае успешного обновления.
     */
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestParam MultipartFile image,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        userService.updateAvatar(image, userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    /**
     * Получить изображение пользователя по его имени.
     *
     * @param name Имя изображения пользователя, которое нужно получить.
     * @return Массив байтов с содержимым изображения в формате PNG.
     * @throws IOException Исключение, возникающее при ошибке чтения изображения.
     */

    @GetMapping(value = "/image/{name}", produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] getImages(@PathVariable String name) throws IOException {
        return userService.getImage(name);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserDto updateUserDto,
                                          @PathVariable Integer id) {
    return ResponseEntity.ok(userService.updateUserDto(updateUserDto, id));
}
}



