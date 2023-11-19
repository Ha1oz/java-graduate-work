package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
    List<Comment> findAllByAdsId(Integer id);
    List<Comment> findAllByUser(User user);

    void deleteByAdsIdAndId(Integer adId, Integer id);

    Optional<Comment> findCommentByIdAndAds_Id(Integer id, Integer adsId);

    void deleteAllByAds_Id(Integer id);
}
