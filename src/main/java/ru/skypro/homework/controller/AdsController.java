package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.inter.AdsService;
import ru.skypro.homework.service.inter.UserService;

import java.io.IOException;
import java.util.Objects;


@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")

public class AdsController {
    private final AdsService adsService;
    private final UserService userService;

    /**
     * Получить список всех объявлений.
     * @return Объект {@link ResponseEntity} с оберткой {@link AdsDto}, содержащей список объявлений и статус ответа.
     */
    @GetMapping
    public ResponseEntity<AdsDto> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    /**
     * Получить список объявлений пользователя, выполнившего аутентификацию.
     * @param @AuthenticationPrincipal UserDetails userDetails Объект {@link @AuthenticationPrincipal UserDetails userDetails} с информацией об аутентифицированном пользователе.
     * @return Объект {@link ResponseEntity} с оберткой {@link AdsDto}, содержащей список объявлений пользователя и статус ответа.
     */
     @GetMapping("/me")
    public ResponseEntity<AdsDto> getAdsMe(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(adsService.getAdsMe(userDetails.getUsername()));
    }

    /**
     * Добавить новое объявление.
     *
     * @param @AuthenticationPrincipal UserDetails userDetails Объект {@link Authentication} с информацией об аутентифицированном пользователе.
     * @param createAds      Объект {@link CreateOrUpdateAdDto} с данными нового объявления.
     * @param image          Объект {@link MultipartFile} с изображением объявления.
     * @return Объект {@link ResponseEntity} с созданным объявлением и статусом ответа.
     */


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> addAd(@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestPart("properties") CreateOrUpdateAdDto createAds,
                                       @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(adsService.addAd(createAds, userDetails.getUsername(), image));
    }

    /**
     * Получить объявление по его идентификатору.
     * @param id Идентификатор объявления, который нужно получить.
     * @return Объект {@link ResponseEntity} с найденным объявлением и статусом ответа.
     */

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAds(@PathVariable Integer id) {
        return ResponseEntity.ok(adsService.getAds(id));
    }

    /**
     * Удалить объявление по его идентификатору.
     * @param id Идентификатор объявления, которое нужно удалить.
     * @return Объект {@link ResponseEntity} с пустым телом ответа и статусом NO_CONTENT в случае успешного удаления.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@AuthenticationPrincipal UserDetails userDetails,
                                      @PathVariable Integer id) {
        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                || adsService.getAdsMe(userDetails.getUsername()).getResults().stream().anyMatch(a -> Objects.equals(a.getPk(), id))) {
            adsService.removeAd(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Обновить объявление по его идентификатору.
     * @param createOrUpdateAdDto Объект {@link CreateOrUpdateAdDto} с обновленными данными для объявления.
     * @param id        Идентификатор объявления, которое нужно обновить.
     * @return Объект {@link ResponseEntity} с обновленным объявлением и статусом ответа.
     */

    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAds(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody CreateOrUpdateAdDto createOrUpdateAdDto,
                                           @PathVariable Integer id) {
        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                || adsService.getAdsMe(userDetails.getUsername()).getResults().stream().anyMatch(a -> Objects.equals(a.getPk(), id))) {
            return ResponseEntity.ok(adsService.updateAds(createOrUpdateAdDto, id));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }




    /**
     * Обновить изображение объявления по его идентификатору.
     * @param id    Идентификатор объявления, для которого нужно обновить изображение.
     * @param image Объект {@link MultipartFile} с новым изображением для объявления.
     * @return Объект {@link ResponseEntity} с пустым телом ответа и статусом NO_CONTENT в случае успешного обновления.
     */

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable Integer id,
                                            @RequestParam MultipartFile image) {
        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                || adsService.getAdsMe(userDetails.getUsername()).getResults().stream().anyMatch(a -> Objects.equals(a.getPk(), id))) {
            adsService.updateAdsImage(id, image);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Получить изображение по его имени.
     * @param name Имя изображения, которое нужно получить.
     * @return Массив байтов с содержимым изображения в формате PNG.
     * @throws IOException Исключение, возникающее при ошибке чтения изображения.
     */

    @GetMapping(value = "/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImages(@PathVariable String name) throws IOException {
        return adsService.getImage(name);
    }
}


