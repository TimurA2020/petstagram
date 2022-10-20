package com.main.petstagram.services;

import com.main.petstagram.entities.Comment;
import com.main.petstagram.entities.Post;
import com.main.petstagram.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;


    @Test
    void deletePostsByUser() {
        String email = "testuser@gmail.com";
        userService.registerUser("test", "user", email, "testuser");

        User user = userService.findByEmail(email);
        Post post = new Post();
        post.setUser(user);
        post.setDate(new Date());
        post.setTitle("test");
        post.setImage("testimage");
        postService.addPost(post);

        Long user_id = user.getId();

        postService.deletePostsByUser(user.getId());

        assertThrows(NoSuchElementException.class,
                () ->{
                    postService.getPost(user_id);
                });
    }


    //adding a user and a comment to fully test the post removal
    @Test
    void testDeletePost(){
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

        postService.deletePost(addedPost.getId());

        assertThrows(NoSuchElementException.class,
                () ->{
                    postService.getPost(addedPost.getId());
                });
    }


}