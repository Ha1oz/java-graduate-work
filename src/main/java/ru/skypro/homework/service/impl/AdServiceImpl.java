package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.api.AdService;
import ru.skypro.homework.service.api.ImageService;
import ru.skypro.homework.service.api.UserService;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;
    private final ImageRepository imageRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final CommentRepository commentRepository;

    @Override
    public AdsDto getAllAds() {

        return null;
    }

    @Override
    public AdDto addAd(@NotNull Authentication authentication, CreateOrUpdateAdDto createAdDto, MultipartFile image){
        Ad ad = adMapper.mapToEntity(createAdDto);
        Ad savedAd = adRepository.save(ad);
        savedAd.setImage(ad.getImage());
        return adMapper.mapToDto(savedAd);
    }

    @Override
    public ExtendedAdDto getAds(Integer pk) {
        Optional<Ad> ad = adRepository.findById(pk);
        return adMapper.mapToDto(ad);
    }

    @Override
    public void removeAd(Integer pk, Authentication authentication) {
        Optional<Ad> ad = adRepository.findById(pk);
        adRepository.delete(ad);
    }

    @Override
    public AdDto updateAd(Integer pk, CreateOrUpdateAdDto updateAdDto, Authentication authentication) {
        Ad oldAd = adRepository.getReferenceById(pk);
        Ad infoToUpdate = adMapper.mapToEntity(updateAdDto);
        oldAd.setPrice(infoToUpdate.getPrice());
        oldAd.setTitle(infoToUpdate.getTitle());
        Ad updatedAds = adRepository.save(oldAd);
        return adMapper.mapToDto(updatedAds);
    }

    @Override
    public AdsDto getAdsMe(String username, Authentication authentication) {
        Optional<User> user = userRepository.findUserByEmail(authentication.getName());
        List<AdDto> listAdsDto = adMapper.mapToDto(user.get());
        AdsDto myAdsDto = new AdsDto();
        myAdsDto.setResults((ArrayList<AdDto>) listAdsDto);
        myAdsDto.setCount(listAdsDto.size());
        return myAdsDto;
    }
}
