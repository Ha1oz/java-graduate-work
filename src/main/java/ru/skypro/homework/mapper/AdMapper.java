package ru.skypro.homework.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;

import java.util.List;

public interface AdMapper {


    AdMapper INSTANCT = Mappers.getMapper(AdMapper.class);
    /**Mapstruct поддерживает сопоставление одного компонента с другим компонентом,
     * по умолчанию используется карта с тем же именем,
     * разные имена настраиваются с помощью  @Mapping,
     * а поля, которые не хотят сопоставляться,
     * могут быть настроены без участия mapping
     */

    /**
     * Преобразовать в объект Ads
     * @param  createOrUpdateAdDto CreateOrUpdateAdDto
     * @return  Ads
     */
    Ads  toAdsFromCreateOrUpdateAds (CreateOrUpdateAdDto createOrUpdateAdDto);

    /**
     * Преобразовать в объект AdsDto
     * @param  ads Ads
     * @return  AdsDto
     */
    @Mapping(target = "author", source = "user")
    @Mapping(target = "pk", source = "id")
    @BeanMapping(ignoreUnmappedSourceProperties = { "description" })
    AdsDto toAdsDto(Ads ads);

    /**
     * Преобразовать в объект  List<AdsDto>
     * @param  List<Ads>  List<Ads>
     * @return  List<AdsDto>
     */
    List<AdsDto> toDto (List<Ads> adsList);

    /**
     * Преобразовать в объект   Ads
     * @param ad Ad
     * @return  Ads
     */
     @Mapping(target = "pk", source = "id")
     @Mapping(target = "authorFirstName",source = "user.firstName")
     @Mapping(target = "authorLastName",source = "user.lastName")
     @Mapping(target = "email",source = "user.email")
     @Mapping(target = "phone",source = "user.phone")
     Ads toAds(Ad ad);

    /**
     * Обновляем  Ads From CreateOrUpdateAdsDto and Ads
     * @param createOrUpdateAdDto CreateOrUpdateAdDto,
     * @param  ads Ads
     */
    void  adsFromCreateOrUpdateAdsDto (CreateOrUpdateAdDto createOrUpdateAdDto, @MappingTarget Ads ads);
}
