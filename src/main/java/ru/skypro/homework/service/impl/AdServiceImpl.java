package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.api.AdService;
import ru.skypro.homework.service.api.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final UserService userService;
    private final CommentRepository commentRepository;

    @Override
    public AdsDto getAllAds() {
        return null;
    }

    @Override
    public AdDto addAd(CreateOrUpdateAdDto createAdDto, MultipartFile image) {
        return null;
    }

    @Override
    public ExtendedAdDto getAds(Integer pk) {
        return null;
    }

    @Override
    public void removeAd(Integer pk) {

    }

    @Override
    public AdDto updateAd(Integer pk, CreateOrUpdateAdDto updateAdDto) {
        return null;
    }

    @Override
    public AdsDto getAdsMe(String username) {
        return null;
    }

    @Override
    public AdsDto findAds(String search) {
        return null;
    }
}
