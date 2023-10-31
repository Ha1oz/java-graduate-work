package ru.skypro.homework.mapper;


import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.entity.Ads;
import org.mapstruct.Mapper;


import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.ExtendedAdDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    Ads toAdsFromCreateAds(CreateOrUpdateAdDto createOrUpdateAdDto);

    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "pk", source = "id")
    AdDto toAdsDto(Ads ads);

    List<AdDto> toDtos(List<Ads> adsList);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName",source = "user.firstName")
    @Mapping(target = "authorLastName",source = "user.lastName")
    @Mapping(target = "email",source = "user.email")
    @Mapping(target = "phone",source = "user.phone")
    ExtendedAdDto toExtendedAds(Ads ads);

    void updateAds(CreateOrUpdateAdDto createOrUpdateAdDto, @MappingTarget Ads ads);}