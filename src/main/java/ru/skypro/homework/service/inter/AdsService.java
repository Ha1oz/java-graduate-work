package ru.skypro.homework.service.inter;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

import javax.transaction.Transactional;
import java.io.IOException;

public interface AdsService {
    AdsDto getAllAds();

    AdsDto getAdsMe(String email);

    AdDto addAd(CreateOrUpdateAdDto createAds, String email, MultipartFile image);



    ExtendedAdDto getAds(Integer id);



    @Transactional
    void removeAd(Integer id);

    AdDto updateAds(CreateOrUpdateAdDto createOrUpdateAdDto, Integer id);

    void updateAdsImage(Integer id, MultipartFile image);

    byte[] getImage(String name) throws IOException;





}

