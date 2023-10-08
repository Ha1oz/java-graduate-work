package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class CommentsController {
    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDto> getComments(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(new CommentsDto());
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable(value = "id") Integer id,
                                                 @RequestBody CreateOrUpdateCommentDto createOrUpdateComment) {
        return ResponseEntity.ok(new CommentDto());
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable(value = "adId") Integer adId,
                                              @PathVariable(value = "commentId") Integer commentId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "adId") Integer adId,
                                                 @PathVariable(value = "commentId") Integer commentId,
                                                 @RequestBody CreateOrUpdateCommentDto createOrUpdateComment) {
        //...
        return ResponseEntity.ok(new CommentDto());
    }
}
