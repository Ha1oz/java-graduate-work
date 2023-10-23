package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.api.UserService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPassword) {
        return ResponseEntity.ok(newPassword);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser() {
        return ResponseEntity.ok(userService.getUserDtoByUsername(getUser().toString()));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,
                                              String username) {
        return ResponseEntity.ok(userService.updateUser(userDto, username));
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok().build();
    }
}
