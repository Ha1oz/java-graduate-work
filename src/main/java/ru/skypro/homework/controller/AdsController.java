package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.exception.AdsNotFoundException;
import ru.skypro.homework.service.api.AdService;
import ru.skypro.homework.service.api.ImageService;
import ru.skypro.homework.entity.Image;


import javax.validation.constraints.NotNull;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {

    private final AdService adService;
    private final ImageService imageService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public AdsDto getAllAds (){
        return ResponseEntity.ok(adService.getAllAds()).getBody();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AdDto addAd (@NotNull Authentication authentication,
                        @RequestParam CreateOrUpdateAdDto createAdDto,
                        @RequestParam MultipartFile image){
        return ResponseEntity.status(HttpStatus.CREATED).body(adService.addAd(authentication, createAdDto, image)).getBody();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "{id}")
    public ResponseEntity<Object> getAds (@RequestParam Integer pk){
        ExtendedAdDto result = null;
        try {
            result = adService.getAds(pk);
        } catch (AdsNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void removeAd (@RequestParam Integer pk,
                          Authentication authentication){
        adService.removeAd(pk, authentication);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public AdDto updateAd(@RequestParam Integer pk,
                          @RequestBody CreateOrUpdateAdDto updateAdDto,
                          Authentication authentication){
        try {
            return ResponseEntity.ok(adService.updateAd(pk, updateAdDto, authentication)).getBody();
        } catch (AdsNotFoundException e) {
            return (AdDto) ResponseEntity.notFound();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/me")
    public AdsDto getAdsMe (@RequestParam String username, Authentication authentication){
        return ResponseEntity.ok(adService.getAdsMe(username, authentication)).getBody();
    }

    @PatchMapping(path = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateAdImage (@PathVariable("id") Integer id,
                                                 @RequestParam("image") MultipartFile image,
                                                 Authentication authentication){
        ExtendedAdDto ad = adService.getAds(id);
        byte[] imageBytes = imageService.updateAdImage(id, image, authentication);
        return ResponseEntity.ok(imageBytes);
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImageAds(@PathVariable Integer id) {
        Image imageAd = imageService.getImageByAds(id);
        return ResponseEntity.ok(imageAd.getData());
    }
}
