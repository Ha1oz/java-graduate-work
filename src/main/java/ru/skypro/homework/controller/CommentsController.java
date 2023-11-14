package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.service.inter.CommentsService;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")

public class CommentsController {
    private final CommentsService commentsService;

    /**
     * Получить список комментариев объявления по его идентификатору.
     * @param id Идентификатор объявления, для которого нужно получить комментарии.
     * @return Объект {@link ResponseEntity} с оберткой {@link CommentsDto}, содержащей список комментариев.
     */


    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDto> getComments(@PathVariable Integer id) {
        return ResponseEntity.ok(commentsService.getComments(id));
    }

    /**
     * Добавить комментарий объявления по его идентификатору.
     * @param id        Идентификатор объявления, для которого нужно добавить комментарий.
     * @param createOrUpdateCommentDto  Объект {@link CreateOrUpdateCommentDto} с данными нового комментария.
     * @param  @AuthenticationPrincipal UserDetails userDetails Объект {@link    UserDetails userDetails } с информацией об аутентифицированном пользователе.
     * @return Объект {@link ResponseEntity} с созданным комментарием и статусом ответа.

     */

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id,
                                                 @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(commentsService.addComment(id, createOrUpdateCommentDto, userDetails.getUsername()));
    }

    /**
     * Удалить комментарий объявления по его идентификатору.     *
     * @param adId      Идентификатор объявления, для которого нужно удалить комментарий.
     * @param commentId Идентификатор комментария, который нужно удалить.
     * @return Объект {@link ResponseEntity} с пустым телом ответа и статусом NO_CONTENT в случае успешного удаления.

     */


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        commentsService.deleteComment(adId, commentId);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    /**
     * Обновить комментарий объявления по его идентификаторам.     *
     * @param adId          Идентификатор объявления, для которого нужно обновить комментарий.
     * @param commentId     Идентификатор комментария, который нужно обновить.
     * @param  createOrUpdateCommentDto Объект {@link CreateOrUpdateCommentDto} с обновленными данными для комментария.
     * @return Объект {@link ResponseEntity} с обновленным комментарием и статусом ответа.

     */


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer adId,
                                                    @PathVariable Integer commentId,
                                                    @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        return ResponseEntity.ok(commentsService.updateComment(adId, commentId, createOrUpdateCommentDto));
    }
}
