package com.main.petstagram.mappers;

import com.main.petstagram.dtos.CommentDTO;
import com.main.petstagram.dtos.PostDTO;
import com.main.petstagram.entities.Comment;
import com.main.petstagram.entities.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostMapperTest {

    @Autowired
    private PostMapper postMapper;


    @Test
    void toPostDTO() {
        Post post = new Post();
        post.setId(-1L);
        post.setDate(new Date());
        post.setTitle("Test post");
        post.setImage("testurl");

        PostDTO postDTO = postMapper.toPostDTO(post);

        Assertions.assertEquals(post.getId(), postDTO.getId());
        Assertions.assertEquals(post.getDate(), postDTO.getDate());
        Assertions.assertEquals(post.getImage(), postDTO.getImage());
        Assertions.assertEquals(post.getTitle(), postDTO.getTitle());
    }

    @Test
    void toPostDtoList() {
        Post post = new Post();
        post.setId(-1L);
        post.setDate(new Date());
        post.setTitle("Test post");
        post.setImage("testurl");

        Post post2 = new Post();
        post2.setId(-2L);
        post2.setDate(new Date());
        post2.setTitle("Test post 2");
        post2.setImage("testurl 2");

        List<Post> posts = new ArrayList<>();
        posts.add(post);
        posts.add(post2);

        List<PostDTO> postDTOList = postMapper.toPostDtoList(posts);

        for(int i = 0; i < posts.size(); i++){
            Assertions.assertEquals(posts.get(i).getId(), postDTOList.get(i).getId());
            Assertions.assertEquals(posts.get(i).getTitle(), postDTOList.get(i).getTitle());
            Assertions.assertEquals(posts.get(i).getDate(), postDTOList.get(i).getDate());
            Assertions.assertEquals(posts.get(i).getImage(), posts.get(i).getImage());
        }

    }
}