package ru.skypro.homework.service.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;

public interface ImageService {

    /**
     * Сохранение изображения объявления
     *
     * @param image    - изображение
     * @param savedAd - обьявление
     * @return - сохранение изображения
     */
    Image createImage(MultipartFile image, Ad savedAd);

    /**
     * Обновление изображения объявления
     *
     * @param id             - идентификатор изображения
     * @param file           - изображение
     * @param authentication - проверка авторизации
     * @return - обновление изображения
     */
    byte[] updateAdImage(Integer id, MultipartFile file, Authentication authentication);

    /**
     * Получение изображения объявления
     *
     * @param id - идентификатор изображения
     * @return - изображение
     */
    Image getImageByAds(Integer id);

    /**
     * Обновление аватара пользователя
     *
     * @param id             - идентификатор изображения
     * @param file           - изображение
     * @param authentication - проверка авторизации
     * @return - обновленный аватар
     */
    byte[] updateImageUser(Integer id, MultipartFile file, Authentication authentication);

    /**
     * Получение аватара пользователя
     *
     * @param id - идентификатор изображения
     * @return - аватар
     */
    Image getImageByUsers(Integer id);

    /**
     * Создание аватара пользователя
     *
     * @param file           - изображение
     * @param user           - пользователь
     * @param authentication - проверка авторизации
     * @return - сохранение аватара
     */
    byte[] createImageUser(MultipartFile file, User user, Authentication authentication);
}
