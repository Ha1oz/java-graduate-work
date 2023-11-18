package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.exception.AdsNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.UserWithEmailNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.inter.CommentsService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Aннотация @Slf4j заставляет lombok генерировать поле регистратора.
 * public class LogExample {
 * private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentRepository  commentRepository;
    private final UserRepository userRepository;
    private final AdsRepository   adsRepository;

    @Autowired
    private CommentMapper commentMapper;

    /**
     * Получить список комментариев к объявлению по его идентификатору.
     * методы:
     * {@link CommentRepository#findAllByAdsId(Integer)},
     * {@link CommentMapper#toListDto(List)}.     *
     * @param id Идентификатор объявления.
     * @return Объект CommentsDto с оберткой, содержащей список DTO комментариев их количество.
     */
    @Override
    public CommentsDto getComments(Integer id) {
        List<Comment> commentList = commentRepository.findAllByAdsId(id);
        List<CommentDto> commentDtoList= commentMapper.toListDto(commentList);
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setResults(commentDtoList);
        commentsDto.setCount(commentDtoList.size());
        return commentsDto;
    }

    /**
     * Добавить новый комментарий к объявлению.
     *  методы:
     * {@link AdsRepository#findById(Object)},
     * {@link CommentMapper#toCommentFromCreateComment(CreateOrUpdateCommentDto)},
     * {@link CommentRepository#save(Object)},
     * {@link Comment#getId()} и
     * {@link CommentMapper#toCommentDtoFromComment(Comment)}.
     *
     * @param id            Идентификатор объявления, к которому нужно добавить комментарий.
     * @param createOrUpdateCommentDto Объект CreateComment с информацией о новом комментарии.
     * @param email         Адрес электронной почты пользователя, оставляющего комментарий.
     * @return Объект CommentDto, содержащий информацию о добавленном комментарии.
     * @throws AdsNotFoundException           Если объявление с указанным идентификатором не найдено.
     * @throws UserWithEmailNotFoundException Если пользователя с указанным адресом электронной почты не найдено.
     */
    @Override
    public CommentDto addComment(Integer id, CreateOrUpdateCommentDto createOrUpdateCommentDto, String email) {
        Ads ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found"));
        Comment comment = commentMapper.toCommentFromCreateComment(createOrUpdateCommentDto);
        comment.setAds(ads);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(userRepository.findByEmail(email).get());
        commentRepository.save(comment);
        log.trace("Added comment by  id: ", comment.getId());
        return commentMapper.toCommentDtoFromComment(comment);
    }

    /**
     * Удаляет комментарий по идентификаторам объявления и комментария.
     * Использует методы:
     * {@link CommentRepository#deleteByAdsIdAndId(Integer, Integer)}
     *
     * @param adId Идентификатор объявления, к которому привязан комментарий.
     * @param id   Идентификатор комментария, который нужно удалить.
     */
    @Override
    @Transactional
    public void deleteComment(Integer adId, Integer id) {
        commentRepository.deleteByAdsIdAndId(adId, id);
        log.trace("Deleted comment by    id: ", id);
    }

    /**
     * Обновляет текст комментария по идентификаторам объявления и комментария.
     * <p>
     * Использует методы:
     * {@link CommentRepository#findCommentByIdAndAds_Id(Integer, Integer)},
     * {@link CommentRepository#save(Object)},
     * {@link CommentMapper#toCommentDtoFromComment(Comment)}.
     *
     * @param adId          Идентификатор объявления, к которому привязан комментарий.
     * @param id            Идентификатор комментария, который нужно обновить.
     * @param createOrUpdateCommentDto Объект CreateComment с обновленными данными для комментария.
     * @return Объект CommentDto, содержащий обновленную информацию о комментарии.
     * @throws CommentNotFoundException Если комментарий с указанными идентификаторами не найден.
     */
    @Override
    public CommentDto updateComment(Integer adId, Integer id, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        Comment comment = commentRepository.findCommentByIdAndAds_Id(id, adId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        comment.setText(createOrUpdateCommentDto.getText());
        commentRepository.save(comment);
        log.trace("Updated comment by  id: ", id);
        return commentMapper.toCommentDtoFromComment(comment);
    }

    /**
     * Получает объект CommentDto по идентификаторам объявления и комментария.
     * <p>
     * Использует методы:
     * {@link CommentRepository#findCommentByIdAndAds_Id(Integer id, Integer adId )} и
     * {@link CommentMapper#toCommentDtoFromComment(Comment)}.
     * @param adId Идентификатор объявления, к которому привязан комментарий.
     * @param id   Идентификатор комментария, который нужно получить.
     * @return Объект CommentDto, содержащий информацию о комментарии.
     * @throws CommentNotFoundException Если комментарий с указанными идентификаторами не найден.
     */
    @Override
    public CommentDto getCommentDto(Integer adId, Integer id) {
        Comment comment = commentRepository.findCommentByIdAndAds_Id(id, adId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        return commentMapper.toCommentDtoFromComment(comment);
    }

    /**
     * Получить объект String .getUser().getEmail()  по идентификатору комментария.

     *   методы:
     * {@link CommentRepository#findById (Integer id )}


     * @param id   Идентификатор комментария, который нужно получить.
     * @return Объект String, содержащий    Email();
     * @throws CommentNotFoundException Если комментарий с указанными идентификаторами не найден.
     */
    @Override
    public String getUserNameOfComment(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"))
                .getUser().getEmail();
    }
}
