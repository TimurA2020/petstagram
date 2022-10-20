package com.main.petstagram.repos;

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
class PostRepoTest {

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

    @Test
    void testPostInsertion(){
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

        Assertions.assertNotNull(insertedPost);

        Assertions.assertEquals(post.getUser().getEmail(), insertedPost.getUser().getEmail());
        Assertions.assertEquals(post.getUser().getStatus(), insertedPost.getUser().getStatus());
        Assertions.assertEquals(post.getUser().getFirstName(), insertedPost.getUser().getFirstName());
        Assertions.assertEquals(post.getUser().getLastName(), insertedPost.getUser().getLastName());
        Assertions.assertEquals(post.getUser().getProfilePic(), insertedPost.getUser().getProfilePic());
        Assertions.assertEquals(post.getImage(), insertedPost.getImage());
        Assertions.assertEquals(post.getDate(), insertedPost.getDate());
        Assertions.assertEquals(post.getTitle(), insertedPost.getTitle());

    }

    @Test
    void findPostsByUserId() {
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

        Long user_id = userRepo.findAllByEmail(email).getId();

        List<Post> posts = postRepo.findPostsByUserId(user_id);

        Assertions.assertNotNull(posts);

        Assertions.assertEquals(posts.get(0).getTitle(), post.getTitle());
        Assertions.assertEquals(posts.get(0).getDate(), post.getDate());
        Assertions.assertEquals(posts.get(0).getUser().getEmail(), post.getUser().getEmail());
        Assertions.assertEquals(posts.get(0).getImage(), post.getImage());

    }
}