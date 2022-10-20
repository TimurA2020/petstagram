package com.main.petstagram.repos;

import com.main.petstagram.entities.Comment;
import com.main.petstagram.entities.Post;
import com.main.petstagram.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepoTest {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @AfterEach
    void deleteData(){
        postRepo.deleteAll();
    }

    @AfterEach
    void deleteUserData(){
        userRepo.deleteAll();
    }

    @AfterEach
    void deleteCommentData(){
        commentRepo.deleteAll();
    }

    @Test
    void findAllCommentByPostId() {
        User testUser = new User();
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setStatus("Status");
        testUser.setProfilePic("testurl");
        String email = "Hello@testuser.com";
        testUser.setEmail(email);

        userRepo.save(testUser);
        Post post = new Post();
        post.setUser(testUser);
        post.setDate(new Date());
        post.setTitle("test");
        post.setImage("testimage");

        Post insertedPost = postRepo.save(post);

        Comment comment = new Comment();
        comment.setComment("Test comment");
        comment.setDate(new Date());
        comment.setUser(testUser);
        comment.setPost(post);

        Comment addedComment = commentRepo.save(comment);

        Assertions.assertNotNull(addedComment);

        List<Comment> commentList = commentRepo.findAllCommentByPostId(insertedPost.getId());

        Assertions.assertEquals(addedComment, commentList.get(0));

    }

    @Test
    void findAllCommentByUserId() {
        User testUser = new User();
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setStatus("Status");
        testUser.setProfilePic("testurl");
        String email = "Hello@testuser.com";
        testUser.setEmail(email);

        userRepo.save(testUser);
        Post post = new Post();
        post.setUser(testUser);
        post.setDate(new Date());
        post.setTitle("test");
        post.setImage("testimage");

        postRepo.save(post);

        Comment comment = new Comment();
        comment.setComment("Test comment");
        comment.setDate(new Date());
        comment.setUser(testUser);
        comment.setPost(post);

        Comment addedComment = commentRepo.save(comment);

        User user = userRepo.findAllByEmail(email);

        List<Comment> comments = commentRepo.findAllCommentByUserId(user.getId());

        Assertions.assertEquals(addedComment, comments.get(0));

    }


}