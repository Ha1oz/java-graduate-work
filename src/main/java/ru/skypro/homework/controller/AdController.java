package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")

public class AdController {

    private final AdService adService;
    /**
     * Получить список всех объявлений.
     * @return Объект {@link ResponseEntity} с оберткой {@link ResponseWrapperAds}, содержащей список объявлений и статус ответа.
     */
    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }



    /**
     * Добавить объявление.
     * @param authentication Объект {@link Authentication} с информацией о  пользователе, выполнившим аутентификацию.
     * @param createAd      Объект {@link CreateAd} с данными созданного объявления.
     * @param image          Объект {@link MultipartFile} с изображением объявления.
     * @return Объект {@link ResponseEntity} с созданным объявлением.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDTO> addAd(Authentication authentication,
                                       @RequestPart("properties") CreateAd createAd,
                                       @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(adService.addAd(createAd, authentication.getName(), image));
    }


    /**
     * Обновить объявление по его идентификатору.
     * @param createAd  Объект {@link CreateAd} с обновленными данными для объявления.
     * @param id        Идентификатор объявления, которое нужно обновить.
     * @return Объект {@link ResponseEntity} с обновленным объявлением.
     */
    @PatchMapping("/{id}") //@PatchMapping это составленная аннотация, которая действует как ярлык для @RequestMapping(method = RequestMethod.PATCH).
    public ResponseEntity<AdDto> updateAds(@RequestBody CreateAd createAd,
                                            @PathVariable Integer id) {
        return ResponseEntity.ok(adService.updateAds(createAd, id));
    }



    /**
     * Удалить объявление по его идентификатору.
     * @param id идентификатор объявления, которое нужно удалить.
     * @return Объект {@link ResponseEntity} с пустым телом ответа и статусом NO_CONTENT в случае  удаления.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@PathVariable Integer id) {
        adService.removeAd(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }



    /**
     * Получить список объявлений пользователя, выполнившего аутентификацию.
     * @param authentication Объект {@link Authentication} с информацией об аутентифицированном пользователе.
     * @return Объект {@link ResponseEntity} с оберткой {@link ResponseWrapperAds}, содержащей список объявлений пользователя.
     */

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdMe(Authentication authentication) {
        return ResponseEntity.ok(adService.getAdMe(authentication.getName()));
    }


    /**
     * Обновить изображение объявления по его идентификатору.
     * @param id    Идентификатор объявления, для которого нужно обновить изображение.
     * @param image Объект {@link MultipartFile} с новым изображением для объявления.
     * @return Объект {@link ResponseEntity} с   обновленным изображением объявления в случае успеха.
     */
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdImage(@PathVariable Integer id, @RequestParam MultipartFile image) {
        return ResponseEntity.ok(adService.updateAdImage (id, image));
    }
}
