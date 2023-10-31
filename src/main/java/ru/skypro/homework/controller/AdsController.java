package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.inter.AdsService;


@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")

public class AdsController {
    private final AdsService adsService;

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
     * @param authentication Объект {@link Authentication} с информацией об аутентифицированном пользователе.
     * @return Объект {@link ResponseEntity} с оберткой {@link AdsDto}, содержащей список объявлений пользователя и статус ответа.
     */
     @GetMapping("/me")
    public ResponseEntity<AdsDto> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(adsService.getAdsMe(authentication.getName()));
    }

//    /**
//     * Добавить новое объявление.
//     *
//     * @param authentication Объект {@link Authentication} с информацией об аутентифицированном пользователе.
//     * @param createAds      Объект {@link CreateOrUpdateAdDto} с данными нового объявления.
//     * @param image          Объект {@link MultipartFile} с изображением объявления.
//     * @return Объект {@link ResponseEntity} с созданным объявлением и статусом ответа.

//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<AdsDto> addAd(Authentication authentication,
//                                        @RequestPart("properties") CreateOrUpdateAdDto createAds,
//                                        @RequestPart("image") MultipartFile image) {
//        return ResponseEntity.ok(adsService.addAd(createAds, authentication.getName(), image));
//    }

    /**
     * Получить объявление по его идентификатору.
     * @param id Идентификатор объявления, который нужно получить.
     * @return Объект {@link ResponseEntity} с найденным объявлением и статусом ответа.
     */

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAds(@PathVariable Integer id) {
        return ResponseEntity.ok(adsService.getAds(id));
    }

//    /**
//     * Удалить объявление по его идентификатору.
//     * @param id Идентификатор объявления, которое нужно удалить.
//     * @return Объект {@link ResponseEntity} с пустым телом ответа и статусом NO_CONTENT в случае успешного удаления.

//    @PreAuthorize("hasRole('ADMIN') or @adsServiceImpl.getAds(#id).getEmail() == authentication.principal.username")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> removeAd(@PathVariable Integer id) {
//        adsService.removeAd(id);
//        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
//    }

    /**
     * Обновить объявление по его идентификатору.
     * @param createOrUpdateAdDto Объект {@link CreateOrUpdateAdDto} с обновленными данными для объявления.
     * @param id        Идентификатор объявления, которое нужно обновить.
     * @return Объект {@link ResponseEntity} с обновленным объявлением и статусом ответа.
     */

    @PreAuthorize("hasRole('ADMIN') or @adsServiceImpl.getAds(#id).getEmail()==authentication.principal.username")
    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAds(@RequestBody CreateOrUpdateAdDto createOrUpdateAdDto,
                                           @PathVariable Integer id) {
        return ResponseEntity.ok(adsService.updateAds(createOrUpdateAdDto, id));
    }




//    /**
//     * Обновить изображение объявления по его идентификатору.
//     * @param id    Идентификатор объявления, для которого нужно обновить изображение.
//     * @param image Объект {@link MultipartFile} с новым изображением для объявления.
//     * @return Объект {@link ResponseEntity} с пустым телом ответа и статусом NO_CONTENT в случае успешного обновления.
//     */

//    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> updateAdsImage(@PathVariable Integer id, @RequestParam MultipartFile image) {
//        adsService.updateAdsImage(id, image);
//        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
//    }

//    /**
//     * Получить изображение по его имени.
//     * @param name Имя изображения, которое нужно получить.
//     * @return Массив байтов с содержимым изображения в формате PNG.
//     * @throws IOException Исключение, возникающее при ошибке чтения изображения.
//     */

//    @GetMapping(value = "/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
//    public byte[] getImages(@PathVariable String name) throws IOException {
//        return adsService.getImage(name);
//    }
}


