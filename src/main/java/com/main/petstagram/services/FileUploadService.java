package com.main.petstagram.services;

import com.main.petstagram.entities.Post;
import com.main.petstagram.entities.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadService {

    @Value("${uploadURL}")
    private String fileUploadURL;

    @Value("${targetURL}")
    private String targetURL;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public boolean uploadProfilePic(MultipartFile file, User user){
        try{
            String fileName = DigestUtils.sha1Hex(user.getId() + "petstagram");
            byte bytes[] = file.getBytes();
            Path path = Paths.get(targetURL + "/" + fileName + ".jpg");
            Files.write(path, bytes);
            user.setProfilePic(fileName);
            userService.saveUser(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean uploadPostPic(MultipartFile file, Post post){
        try{
            String fileName = DigestUtils.sha1Hex(post.getId() + "post");
            byte bytes[] = file.getBytes();
            Path path = Paths.get(targetURL + "/" + fileName + ".jpg");
            Files.write(path, bytes);
            post.setImage(fileName);
            postService.addPost(post);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
