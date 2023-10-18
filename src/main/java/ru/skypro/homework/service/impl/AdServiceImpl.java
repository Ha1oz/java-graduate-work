package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.service.inter.AdService;

//Class-plug
@Service
public class AdServiceImpl implements AdService {
    public AdsDto getAllAds() {
        return new AdsDto();
    }

    public AdDto addAd(CreateOrUpdateAdDto createAd, String name, MultipartFile image) {
        return new AdDto();
    }

    public AdDto updateAds(CreateOrUpdateAdDto updateAd, Integer id) {
        return new AdDto();
    }

    public void removeAd(Integer id) {
    }

    public AdsDto getAdMe(String name) {
        return new AdsDto();

    }

    public AdDto updateAdImage(Integer id, MultipartFile image) {
        return new AdDto();
    }
}
