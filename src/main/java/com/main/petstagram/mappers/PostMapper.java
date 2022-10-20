package com.main.petstagram.mappers;

import com.main.petstagram.dtos.PostDTO;
import com.main.petstagram.entities.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDTO toPostDTO(Post post);

    List<PostDTO> toPostDtoList(List<Post> posts);
}
