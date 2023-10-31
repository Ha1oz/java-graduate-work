package ru.skypro.homework.service.inter;

import ru.skypro.homework.dto.*;

public interface AdsService {
    AdsDto getAllAds();

    AdsDto getAdsMe(String email);

//    AdsDto addAd(CreateOrUpdateAdDto createAds, String email, MultipartFile image);



    ExtendedAdDto getAds(Integer id);

//    void removeAd(Integer id);

    AdDto updateAds(CreateOrUpdateAdDto createOrUpdateAdDto, Integer id);



//    void updateAdsImage(Integer id, MultipartFile image);

//    byte[] getImage(String name) throws IOException;


}

