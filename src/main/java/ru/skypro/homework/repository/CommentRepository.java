package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Comments;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Integer> {
    List<Comments> findAllByAdsId(Integer id);
}
