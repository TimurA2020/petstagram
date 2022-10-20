package com.main.petstagram.services;

import com.main.petstagram.dtos.CommentDTO;
import com.main.petstagram.entities.Comment;
import com.main.petstagram.entities.Post;
import com.main.petstagram.mappers.CommentMapper;
import com.main.petstagram.repos.CommentRepo;
import com.main.petstagram.repos.PostRepo;
import com.ocpsoft.pretty.time.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostRepo postRepo;

    public List<CommentDTO> getCommentsByPost(Long id){
        List<CommentDTO> commentDTOList = commentMapper.toCommentDTOList(commentRepo.findAllCommentByPostId(id));
        for(int i = 0; i < commentDTOList.size(); i++){
            PrettyTime p = new PrettyTime();
            commentDTOList.get(i).setPrettyTime(p.format(commentDTOList.get(i).getDate()));
        }
        commentDTOList.sort(Comparator.comparing(CommentDTO::getDate).reversed());
        return commentDTOList;
    }

    public void deleteCommentsByPost(Long id){
        List<CommentDTO> commentDTOList = commentMapper.toCommentDTOList(commentRepo.findAllCommentByPostId(id));
    }

    public Comment addComment(Comment comment, Long post_id){
        Post post = postRepo.findById(post_id).orElseThrow();
        comment.setPost(post);
        return commentRepo.save(comment);
    }

    public Comment getComment(Long id){
        return commentRepo.findById(id).orElseThrow();
    }

    public void deleteComment(Comment comment){
        commentRepo.delete(comment);
    }


//    public void deleteCommentsByAuthor(Long id){
//        commentRepo.deleteAll(commentRepo.findAllCommentByUserId(id));
//    }

    public int countComments(Long id){
        return commentRepo.findAllCommentByPostId(id).size();
    }

    public void deleteCommentsByAuthor(Long id){
        commentRepo.deleteAll(commentRepo.findAllCommentByUserId(id));
    }

}
