package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public AdsDto getAllAds (){
        return new AdsDto();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AdDto addAd (@RequestParam CreateOrUpdateAdDto createOrUpdateAdDto,
                        @RequestParam MultipartFile adWithImage){
        return new AdDto();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "{id}")
    public ExtendedAdDto getAds (@RequestParam Integer id){
        return new ExtendedAdDto();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void removeAd (@RequestParam Integer id){
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public AdDto updateAds (@RequestParam Integer id,
                            @RequestBody CreateOrUpdateAdDto updateAdDto){
        return new AdDto();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/me")
    public AdsDto getAdsMe (){
        return new AdsDto();
    }

    // TO DO
//    @PatchMapping(path = "/{id}/image")
//    public ResponseEntity<byte[]> updateImage (@PathVariable("id") Integer id,
//                                               @RequestParam("image") MultipartFile image){
//        byte[] updatedImageBytes = null;
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(updatedImageBytes);
//    }
}
