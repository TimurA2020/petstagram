package com.main.petstagram.api;

import com.main.petstagram.dtos.PostDTO;
import com.main.petstagram.entities.User;
import com.main.petstagram.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "api/posts")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<PostDTO> getAllPosts(){
        return postService.getPostDTOList();
    }

    @GetMapping(value = "{id}")
    public PostDTO getPostDTO(@PathVariable (name = "id") Long id){
        return postService.getPostDTO(id);
    }

    @GetMapping(value = "user")
    public List<PostDTO> getPostsByUser(){
        return postService.getPostsByUser(getCurrentUser().getId());
    }

    @GetMapping(value = "userposts/{id}")
    public List<PostDTO> getUserPosts(@PathVariable (name = "id") Long id){
        return postService.getPostsByUser(id);
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }
}
