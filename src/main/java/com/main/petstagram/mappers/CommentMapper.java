package com.main.petstagram.mappers;

import com.main.petstagram.dtos.CommentDTO;
import com.main.petstagram.entities.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDTO toCommentDTO(Comment comment);

    List<CommentDTO> toCommentDTOList(List<Comment> comments);


}
