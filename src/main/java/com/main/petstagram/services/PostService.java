package com.main.petstagram.services;

import com.main.petstagram.dtos.PostDTO;
import com.main.petstagram.entities.Comment;
import com.main.petstagram.entities.Post;
import com.main.petstagram.mappers.PostMapper;
import com.main.petstagram.repos.CommentRepo;
import com.main.petstagram.repos.PostRepo;
import com.ocpsoft.pretty.time.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    public PostDTO getPostDTO(Long id){
        PostDTO postDTO = postMapper.toPostDTO(postRepo.findById(id).orElseThrow());
        PrettyTime p = new PrettyTime();
        postDTO.setPrettyTime(p.format(postDTO.getDate()));
        return postDTO;
    }

    public Post getPost(Long id){
        return postRepo.findById(id).orElseThrow();
    }



    public List<PostDTO> getPostDTOList(){
        List<PostDTO> postDTOList = postMapper.toPostDtoList(postRepo.findAll());
        setPrettyTime(postDTOList);
        reverseSort(postDTOList);
        return postDTOList;
    }

    //Converting time from weird long format to whatever time has passed
    public void setPrettyTime(List<PostDTO> postDTOList){
        for(int i = 0; i < postDTOList.size(); i++){
            PrettyTime p = new PrettyTime();
            postDTOList.get(i).setPrettyTime(p.format(postDTOList.get(i).getDate()));
            postDTOList.get(i).setCommentCount(countComments(postDTOList.get(i).getId()));
        }
    }

    public void reverseSort(List<PostDTO> postDTOList){
        postDTOList.sort(Comparator.comparing(PostDTO::getDate).reversed());
    }

    public Post addPost(Post post){
        return postRepo.save(post);
    }

    //Posts for profile
    public List<PostDTO> getPostsByUser(Long id){
        List<PostDTO> postDTOList = postMapper.toPostDtoList(postRepo.findPostsByUserId(id));
        setPrettyTime(postDTOList);
        reverseSort(postDTOList);
        return postDTOList;
    }

    public List<Post> getPostEntitiesByUser(Long id){
        return postRepo.findPostsByUserId(id);
    }

    public void deletePostsByUser(Long id){
        List<Post> posts = postRepo.findPostsByUserId(id);
        for(int i = 0; i < posts.size(); i++){
             commentRepo.deleteAll((commentRepo.findAllCommentByPostId(posts.get(i).getId())));
        }
        postRepo.deleteAll(posts);
    }

    //Getting the number of comments
    public int countComments(Long id){
        return commentRepo.findAllCommentByPostId(id).size();
    }

    //Deleting the post, but to delete the post we would have to delete the comments that have FKs
    //referring to it
    public void deletePost(Long post_id){
        Post post = postRepo.findById(post_id).orElseThrow();
        List<Comment> commentList = commentRepo.findAllCommentByPostId(post_id);
        commentRepo.deleteAll(commentList);
        postRepo.delete(post);
    }




}
