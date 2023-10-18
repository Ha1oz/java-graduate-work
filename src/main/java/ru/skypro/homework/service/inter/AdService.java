package ru.skypro.homework.service.inter;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;

public interface AdService {
    AdsDto getAllAds();
    AdDto addAd(CreateOrUpdateAdDto createAd, String name, MultipartFile image);
    AdDto updateAds(CreateOrUpdateAdDto updateAd, Integer id);
    void removeAd(Integer id);
    AdsDto getAdMe(String name);
    AdDto updateAdImage(Integer id, MultipartFile image);
}
