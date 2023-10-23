package ru.skypro.homework.service.api;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;

import java.io.IOException;


public interface AdService {
    AdsDto getAllAds();
    AdDto addAd(CreateOrUpdateAdDto createAdDto, MultipartFile image);
    ExtendedAdDto getAds(Integer pk);
    void removeAd(Integer pk);
    AdDto updateAd(Integer pk, CreateOrUpdateAdDto updateAdDto);
    AdsDto getAdsMe(String username);
    //AdDto updateAdImage(Integer pk, MultipartFile image);
}
