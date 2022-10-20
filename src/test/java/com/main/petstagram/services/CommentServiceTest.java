package com.main.petstagram.services;

import com.main.petstagram.dtos.CommentDTO;
import com.main.petstagram.entities.Comment;
import com.main.petstagram.entities.Post;
import com.main.petstagram.entities.User;
import com.main.petstagram.repos.CommentRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;




    @Test
    void testAddComment(){
        String email = "testuser@gmail.com";
        userService.registerUser("test", "user", email, "testuser");

        User user = userService.findByEmail(email);
        Post post = new Post();
        post.setUser(user);
        post.setDate(new Date());
        post.setTitle("test");
        post.setImage("testimage");
        Post addedPost = postService.addPost(post);

        Comment comment = new Comment();
        comment.setComment("testComment");
        comment.setPost(addedPost);
        comment.setDate(new Date());
        comment.setUser(user);
        comment.setPost(addedPost);
        Comment addedComment = commentService.addComment(comment, addedPost.getId());

        Assertions.assertEquals(addedComment.getComment(), comment.getComment());
        Assertions.assertEquals(addedComment.getDate(), comment.getDate());
        Assertions.assertEquals(addedComment.getPost().getId(), addedPost.getId());
        Assertions.assertEquals(addedComment.getPost().getTitle(), addedPost.getTitle());
        Assertions.assertEquals(addedComment.getUser(), user);

    }

    @Test
    void testDeleteComment(){
        String email = "testuser@gmail.com";
        userService.registerUser("test", "user", email, "testuser");

        User user = userService.findByEmail(email);
        Post post = new Post();
        post.setUser(user);
        post.setDate(new Date());
        post.setTitle("test");
        post.setImage("testimage");
        Post addedPost = postService.addPost(post);

        Comment comment = new Comment();
        comment.setComment("testComment");
        comment.setPost(addedPost);
        comment.setDate(new Date());
        comment.setUser(user);
        comment.setPost(addedPost);
        Comment addedComment = commentService.addComment(comment, addedPost.getId());
        Long addedComment_id = addedComment.getId();
        commentService.deleteComment(addedComment);

        assertThrows(NoSuchElementException.class,
                () ->{
                    commentService.getComment(addedComment_id);
                });
    }


}