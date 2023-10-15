package ru.skypro.homework.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Comments;

import java.util.List;

public interface  CommentMapper {

    CommentMapper INSTANCT = Mappers.getMapper(CommentMapper.class);
    /**Mapstruct поддерживает сопоставление одного компонента с другим компонентом,
     * по умолчанию используется карта с тем же именем,
     * разные имена настраиваются с помощью  @Mapping,
     * а поля, которые не хотят сопоставляться,
     * могут быть настроены без участия mapping
     */

    /**
     * Преобразовать в объект  List<CommentDto>
     * @param   commentList List<Comments>
     * @return List<CommentDto>
     */
    List<CommentDto> toListDto(List<Comments> commentList);


    /**
     * Преобразовать в объект  Comments
     * @param  createOrUpdateCommentDto CreateOrUpdateCommentDto
     * @return Comments
     */
    Comments toCommentsFromCreateComment(CreateOrUpdateCommentDto createOrUpdateCommentDto);


    /**
     * Преобразовать в объект  CommentDto
     * @param  comment Comments
     * @return  CommentDto
     */
    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "authorImage", source = "user.image")
    @Mapping(target = "pk", source = "id")
    CommentDto toCommentDtoFromComment(Comments comment);


    /**
     * Обновить  comment
     * @param commentDto CommentDto
     * @param comment    Comments
     */
    @Mapping(target = "user.id", ignore = true)
    @Mapping(target = "user.image",ignore = true)
    @Mapping(target = "user.firstName",ignore = true)
    void  commentFromCommentDto(CommentDto commentDto,@MappingTarget Comments comment);
}
