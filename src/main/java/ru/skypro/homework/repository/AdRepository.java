package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ad;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {

    List<Ad> findAdsByUserId(Integer id);

    @Query(value = "select * from advert where lower(title) like lower(concat('%', ?1,'%'))", nativeQuery = true)
    List<Ad> findAds(String search);

    List<Ad> findAdsByUserEmail(String username);
}
