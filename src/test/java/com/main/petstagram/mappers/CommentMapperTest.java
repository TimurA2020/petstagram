package com.main.petstagram.mappers;

import com.main.petstagram.dtos.CommentDTO;
import com.main.petstagram.entities.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    @Test
    void testToCommentDTO() {
        Comment comment = new Comment();
        comment.setComment("test comment");
        comment.setDate(new Date());
        comment.setId(-1L);

        CommentDTO commentDTO = commentMapper.toCommentDTO(comment);
        Assertions.assertEquals(comment.getId(), commentDTO.getId());
        Assertions.assertEquals(comment.getDate(), commentDTO.getDate());
        Assertions.assertEquals(comment.getComment(), commentDTO.getComment());

    }

    @Test
    void testToCommentDTOList() {
        Comment comment = new Comment();
        comment.setComment("test comment");
        comment.setDate(new Date());
        comment.setId(-1L);
        Comment comment2 = new Comment();
        comment2.setComment("test comment 2");
        comment2.setDate(new Date());
        comment2.setId(-2L);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comments.add(comment2);

        List<CommentDTO> commentDTOList = commentMapper.toCommentDTOList(comments);

        for(int i = 0; i < comments.size(); i++){
            Assertions.assertEquals(comments.get(i).getComment(), commentDTOList.get(i).getComment());
            Assertions.assertEquals(comments.get(i).getDate(), commentDTOList.get(i).getDate());
            Assertions.assertEquals(comments.get(i).getId(), commentDTOList.get(i).getId());
        }
    }


}