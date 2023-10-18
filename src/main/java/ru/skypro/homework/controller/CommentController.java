package ru.skypro.homework.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class CommentController {
    private final  CommentService commentService;


    /**
     * Получить комментарии по его идентификатору.
     * @param id Идентификатор объявления, для которого нужно получить комментарии.
     * @return Объект {@link  ResponseEntity} с оберткой {@link CommentDto}, содержащей список комментариев и статус ответа.
     */
    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentDto> getComments(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.getComments(id));
    }


    /**
     * Добавить комментарий объявления по его идентификатору.
     * @param id Идентификатор объявления, для которого нужно добавить комментарий.
     * @param createComment  Объект {@link CreateOrUpdateCommentDto} с данными нового комментария.
     * @param authentication Объект {@link Authentication} с информацией об аутентифицированном пользователе.
     * @return Объект {@link ResponseEntity} с созданным комментарием и статусом ответа.
     */
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id,
                                                 @RequestBody CreateOrUpdateCommentDto createComment,
                                                 @RequestBody Authentication authentication) {
        return ResponseEntity.ok(commentService.addComment(id, createComment, authentication.getName()));
    }


    /**
     * Удалить комментарий объявления по его идентификаторам.
     * @param adId      Идентификатор объявления, для которого нужно удалить комментарий.
     * @param commentId Идентификатор комментария, который нужно удалить.
     * @return Объект {@link ResponseEntity} с пустым телом ответа и статусом NO_CONTENT в случае успешного удаления.
     */
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }


    /**
     * Обновить комментарий объявления по его идентификаторам.
     * @param adId          Идентификатор объявления, для которого нужно обновить комментарий.
     * @param commentId     Идентификатор комментария, который нужно обновить.
     * @param updateComment Объект {@link CreateOrUpdateCommentDto} с обновленными данными для комментария.
     * @return Объект {@link ResponseEntity} с обновленным комментарием и статусом ответа.
     */
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer adId,
                                                    @PathVariable Integer commentId,
                                                    @RequestBody CreateOrUpdateCommentDto updateComment) {
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, updateComment));
    }
}
