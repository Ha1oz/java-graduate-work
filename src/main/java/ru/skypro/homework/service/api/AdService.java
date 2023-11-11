package ru.skypro.homework.service.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;

import javax.validation.constraints.NotNull;


public interface AdService {
    AdsDto getAllAds();
    AdDto addAd(@NotNull Authentication authentication, CreateOrUpdateAdDto createAdDto, MultipartFile image);
    ExtendedAdDto getAds(Integer pk);
    void removeAd(Integer pk, Authentication authentication);
    AdDto updateAd(Integer pk, CreateOrUpdateAdDto updateAdDto, Authentication authentication);
    AdsDto getAdsMe(String username, Authentication authentication);
}
