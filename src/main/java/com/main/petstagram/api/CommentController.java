package com.main.petstagram.api;

import com.main.petstagram.dtos.CommentDTO;
import com.main.petstagram.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/comments")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "{id}")
    public List<CommentDTO> getCommentsByPost(@PathVariable(name = "id") Long id){
        return commentService.getCommentsByPost(id);
    }


}
