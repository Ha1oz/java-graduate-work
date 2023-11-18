package ru.skypro.homework.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import  ru.skypro.homework.entity.Ads;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  ru.skypro.homework.dto.*;
import  ru.skypro.homework.entity.User;
import  ru.skypro.homework.exception.AdsNotFoundException;
import  ru.skypro.homework.exception.UserWithEmailNotFoundException;
import  ru.skypro.homework.mapper.AdsMapper;
import  ru.skypro.homework.repository.AdsRepository;
import  ru.skypro.homework.repository.CommentRepository;
import  ru.skypro.homework.repository.UserRepository;
import  ru.skypro.homework.service.inter.AdsService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

/**
 *  Aннотация @Slf4j Заставляет lombok генерировать поле регистратора.
 *  public class LogExample {
 *  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);
 *  }
 */

@Slf4j
@Service
@RequiredArgsConstructor

public class AdsServiceImpl  implements AdsService{
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private final ImageService imageService;

    @Autowired
    private   AdsMapper adsMapper;


    /**
     * Получить список всех объявлений.
     * использует методы {@link AdsRepository#findAll()} и {@link AdsMapper#toDtos(List)}
     *
     * @return Объект {@link AdsDto} с оберткой содержащей список объявлений и статус ответа.
     */
    @Override
    public AdsDto getAllAds() {
        List<Ads> adsList = adsRepository.findAll();
        List<AdDto> adDtoList = adsMapper.toDtos(adsList);
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(adsList.size());
        adsDto.setResults(adDtoList);
        return adsDto;
    }

    /**
     * Получает список объявлений, принадлежащих пользователю с указанным адресом электронной почты.
     * Использует методы {@link AdsRepository#findByUser(User)} и {@link AdsMapper#toDtos(List)}.
     *
     * @param email Адрес электронной почты пользователя, для которого нужно получить объявления.
     * @return Объект {@link AdsDto} с оберткой, содержащей список DTO объявлений и статус ответа.
     * @throws UserWithEmailNotFoundException Если пользователя с указанным адресом электронной почты не найдено.
     */
    @Override
    public AdsDto getAdsMe(String email) {
        List<Ads> adsList = adsRepository.findByUser(userRepository.findByEmail(email)
                .orElseThrow(() -> new UserWithEmailNotFoundException(email)));
        List<AdDto> adDtoList = adsMapper.toDtos(adsList);
        AdsDto adsDto = new AdsDto();
        adsDto.setResults(adDtoList);
        adsDto.setCount(adsList.size());
        return adsDto;
    }

    /**
     * Добавляет новое объявление в базу данных.
     * Использует методы {@link AdsMapper#toAdsFromCreateAds(CreateOrUpdateAdDto)},
     * {@link UserRepository#findByEmail(String)},
     * //     * {@link ImageService#uploadImage(MultipartFile, String)} и {@link AdsRepository#save(Object)}.
     *
     * @param createAds Объект CreateOrUpdateAdDto, содержащий информацию для создания нового объявления.
     * @param email     Адрес электронной почты пользователя, который будет ассоциирован с добавляемым объявлением.
     * @param image     Объект MultipartFile с изображением для объявления.
     * @return Объект AdsDto, содержащий информацию о добавленном объявлении.
     * @throws UserWithEmailNotFoundException Если пользователя с указанным адресом электронной почты не найдено.
     */
    @Override
    public AdDto addAd(CreateOrUpdateAdDto createAds, String email, MultipartFile image) {
        Ads ads = adsMapper.toAdsFromCreateAds(createAds);
        ads.setUser(userRepository.findByEmail(email)
                .orElseThrow(() -> new UserWithEmailNotFoundException(email)));
        ads.setImage(imageService.uploadImage(image, "/ads"));
        adsRepository.save(ads);
        return adsMapper.toAdsDto(ads);
    }

    /**
     * Получает полную информацию об объявлении по его идентификатору.
     * Использует методы {@link AdsRepository#findById(Object)} и {@link AdsMapper#toExtendedAds(Ads)}.
     *
     * @param id Идентификатор объявления, для которого нужно получить полную информацию.
     * @return Объект типа ExtendedAdDto, содержащий полную информацию об объявлении.
     * @throws AdsNotFoundException Если объявление с указанным идентификатором не найдено.
     */
    @Override
    public ExtendedAdDto getAds(Integer id) {
        Ads ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found by id: " + id));
        return adsMapper.toExtendedAds (ads);
    }

    /**
     * Удаляет объявление по его идентификатору.
     * Метод помечен аннотацией {@link org.springframework.transaction.annotation.Transactional},
     * что обозначает транзакционное выполнение данного метода.
     * <p>
     * Использует методы:
     * {@link CommentRepository#deleteAllByAds_Id(Integer)},
     * {@link AdsRepository#findById(Object)},
     * {@link ImageService#deleteFile(String)} и
     * {@link AdsRepository#delete(Object)}.
     *
     * @param id Идентификатор объявления, которое нужно удалить.
     * @throws AdsNotFoundException Если объявление с указанным идентификатором не найдено.
     */
    @Transactional
    @Override
    public void removeAd(Integer id) {
        commentRepository.deleteAllByAds_Id(id);
        Ads ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found by id: " + id));
        imageService.deleteFile(ads.getImage());
        log.trace("Removed Ads with id: ", id);
        adsRepository.delete(ads);
    }

    /**
     * Обновляет информацию об объявлении по его идентификатору.
     * Использует методы:
     * {@link AdsRepository#findById(Object)},
     * {@link AdsMapper#updateAds(CreateOrUpdateAdDto, Ads)},
     * {@link AdsRepository#save(Object)},
     * {@link AdsMapper#toAdsDto(Ads)}.
     *
     * @param createOrUpdateAdDto Объект CreateOrUpdateAdDto с обновленными данными для объявления.
     * @param id        Идентификатор объявления, которое нужно обновить.
     * @return Объект AdsDto, содержащий обновленную информацию об объявлении.
     * @throws AdsNotFoundException Если объявление с указанным идентификатором не найдено.
     */
    @Override
    public AdDto updateAds(CreateOrUpdateAdDto createOrUpdateAdDto, Integer id) {
        Ads ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found by id: " + id));
        adsMapper.updateAds(createOrUpdateAdDto, ads);
        adsRepository.save(ads);
        log.trace("Updated Ads with id: ", id);
        return adsMapper.toAdsDto(ads);
    }
    /**
     * Обновить изображение объявления по его идентификатору.
     *
     * @param id    Идентификатор объявления, для которого нужно обновить изображение.
     * @param image Объект {@link MultipartFile} с новым изображением для объявления.
     * @return Объект {@link ResponseEntity} с пустым телом ответа и статусом NO_CONTENT в случае успешного обновления.
     * @see AdsService#updateAdsImage(Integer, MultipartFile)
     */
    @Override
    public void updateAdsImage(Integer id, MultipartFile image) {
        Ads ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found"));
        imageService.deleteFile(ads.getImage());
        ads.setImage(imageService.uploadImage(image, "/ads"));
        adsRepository.save(ads);
    }

    /**
     * Получить изображение по его имени.
     * <p>
     * Использует метод {@link ImageService#downloadImage(String)} для получения изображения по имени.
     *
     * @param name Имя изображения, которое нужно получить.
     * @return Массив байтов, представляющий изображение.
     * @throws IOException Если произошла ошибка при получении изображения.
     */
    @Override
    public byte[] getImage(String name) throws IOException {
        return imageService.downloadImage(name);
    }
}