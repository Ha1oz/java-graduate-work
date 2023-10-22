package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
@Service
public class AdMapper {

    private UserRepository userRepository;

    public AdDto mapToDto(Ad ad) {
        AdDto dto = new AdDto();
        dto.setAuthor(ad.getUser().getId());
        dto.setImage(ad.getImage());
        dto.setPk(ad.getPk());
        dto.setPrice(ad.getPrice());
        dto.setTitle(ad.getTitle());
        return dto;
    }

    public Ad mapToEntity(AdDto dto) {
        Ad ad = new Ad();
        User author = userRepository.findById(dto.getAuthor())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        ad.setUser(author);
        ad.setImage(dto.getImage());
        ad.setPk(dto.getPk());
        ad.setPrice(dto.getPrice());
        ad.setTitle(dto.getTitle());
        return ad;
    }

    public List<AdsDto> mapToDto(List<Ad> all) {
        return null;
    }

    public Ad mapToEntity(CreateOrUpdateAdDto createAdDto) {
        return null;
    }

    public ExtendedAdDto mapToDto(Optional<Ad> ad) {
        return null;
    }

    public List<AdDto> mapToDto(User user) {
        return null;
    }
}
