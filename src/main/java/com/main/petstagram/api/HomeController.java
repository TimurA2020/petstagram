package com.main.petstagram.api;

import com.main.petstagram.dtos.FriendRequestDTO;
import com.main.petstagram.dtos.UserDTO;
import com.main.petstagram.entities.Comment;
import com.main.petstagram.entities.FriendRequest;
import com.main.petstagram.entities.Post;
import com.main.petstagram.entities.User;
import com.main.petstagram.services.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Value("${uploadURL}")
    private String fileUploadURL;

    @Autowired
    private UserService userService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private FriendRequestService friendRequestService;

    //Reading
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/")
    public String index(Model model){
        User user = getCurrentUser();
        List<FriendRequestDTO> friendRequestDTOS = friendRequestService.getFriendRequests(user.getId());
        List<User> friends = userService.getFriends(user.getId());
        model.addAttribute("friendrequests", friendRequestDTOS);
        model.addAttribute("friends", friends);
        model.addAttribute("user", user);
        return "index";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String profile(Model model){
        User user = getCurrentUser();
        model.addAttribute("user", user);
        List<User> friends = userService.getFriends(user.getId());
        model.addAttribute("friends", friends);
        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/privacy")
    public String privacy(Model model){
        model.addAttribute("user_id", getCurrentUser().getId());
        return "privacy";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profiledetails/{id}")
    public String profileDetails(@PathVariable(name = "id") Long id,
                                 Model model){
        User profileUser = userService.getUserEntity(id);
        if(getCurrentUser().getId().equals(id)){
            return "redirect:/profile";
        }
        boolean request = friendRequestService.checkRequest(getCurrentUser().getId(), id);
        boolean isFriend = userService.checkFriend(getCurrentUser().getId(), id);
        boolean showOption = false;
        if(!request && !isFriend){
            showOption = true;
        }
        model.addAttribute("user", profileUser);
        model.addAttribute("showoption", showOption);
        model.addAttribute("isfriend", isFriend);
        model.addAttribute("request", request);
        return "profiledetails";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/postdetails/{id}")
    public String getPostDetails(@PathVariable(name = "id") Long id,
                                 Model model){
        User user = getCurrentUser();
        List<FriendRequestDTO> friendRequestDTOS = friendRequestService.getFriendRequests(user.getId());
        List<User> friends = userService.getFriends(user.getId());
        model.addAttribute("friendrequests", friendRequestDTOS);
        model.addAttribute("friends", friends);
        model.addAttribute("user_id", user.getId());
        model.addAttribute("user", user);
        model.addAttribute("post_id", id);
        model.addAttribute("comment_count", commentService.countComments(id));
        return "postdetails";
    }

    //Getting current User
    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }

    @GetMapping(value = "/profilepics/{url}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] profilepic(@PathVariable(name = "url") String url) throws IOException {

        String picURL = fileUploadURL + "user.png";
        if(url != null){
            picURL = fileUploadURL + url + ".jpg";
        }
        InputStream in;
        try{
            ClassPathResource resource = new ClassPathResource(picURL);
            in = resource.getInputStream();
        }catch (Exception e){
            picURL = fileUploadURL + "user.png";
            ClassPathResource resource = new ClassPathResource(picURL);
            in = resource.getInputStream();
        }

        return IOUtils.toByteArray(in);
    }
    // ./reading

    // Creating
    @PreAuthorize("isAnonymous()")
    @PostMapping("/register")
    public String register(@RequestParam(name = "email") String email,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "firstName") String firstName,
                           @RequestParam(name = "lastName") String lastName,
                           @RequestParam(name = "password2") String password2){
        if(password.equals(password2)){
            return "redirect:/login?" + userService.registerUser(firstName, lastName, email, password);
        }
        return "redirect:/login?passworderror=true";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/sendrequest")
    public String sendrequest(@RequestParam(name = "id") Long receiver_id){
        Long sender_id = getCurrentUser().getId();
        friendRequestService.addFriendRequest(sender_id, receiver_id);
        return "redirect:/profiledetails/" + receiver_id;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/addpost")
    public String addPost(@RequestParam(name = "pic") MultipartFile file,
                          @RequestParam(name = "title") String title){
        Post post = new Post();
        post.setUser(getCurrentUser());
        post.setDate(new Date());
        post.setTitle(title);
        postService.addPost(post);
        fileUploadService.uploadPostPic(file, post);
        return "redirect:/?postuploadsuccess=true";
    }



    //upload picture
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/uploadphoto")
    public String uploadPhoto(@RequestParam(name = "profile_pic") MultipartFile file){
        if(file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")){
            fileUploadService.uploadProfilePic(file, getCurrentUser());
        }
        return "redirect:/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addfriend")
    public String addFriend(@RequestParam(name = "id") Long request_id){
        friendRequestService.addAsFriend(request_id);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addcomment")
    public String addComment(@RequestParam(name = "post_id") Long post_id,
                             @RequestParam(name = "comment") String comment_text){
        Comment comment = new Comment();
        comment.setUser(getCurrentUser());
        comment.setDate(new Date());
        comment.setComment(comment_text);
        commentService.addComment(comment, post_id);
        return "redirect:/postdetails/" + post_id;
    }

    // ./Creating

    //Updating
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/updatecredentials")
    public String updateCredentials(@RequestParam(name = "firstName") String firstName,
                                    @RequestParam(name = "lastName") String lastName,
                                    @RequestParam(name = "status") String status){
        userService.updateCredentials(getCurrentUser(), firstName, lastName, status);
        return "redirect:/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/updatepassword")
    public String updatePassword(@RequestParam(name = "oldPassword") String oldPassword,
                                 @RequestParam(name = "newPassword") String newPassword,
                                 @RequestParam(name = "reNewPassword") String reNewPassword){
        User user = getCurrentUser();
        if(!newPassword.equals(reNewPassword)){
            return "redirect:/profile?errorcode=2";
        }
        if(user != null)
            userService.updatePassword(user, oldPassword, newPassword);

        return "redirect:/profile?passwordchangesuccess=true";
    }

    // ./updating

    //Deleting
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/declinerequest")
    public String declineRequest(@RequestParam(name = "id") Long request_id){
        friendRequestService.declineRequest(request_id);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/deletefriend")
    public String deleteFriend(@RequestParam(name = "id") Long friend_id){
        friendRequestService.deleteFriend(getCurrentUser().getId(), friend_id);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/deletepost")
    public String deletePost(@RequestParam(name = "id") Long post_id){
        postService.deletePost(post_id);
        return "redirect:/";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/deleteuser")
    public String deleteUser(@RequestParam(name = "id") Long id){
        userService.deleteUser(id);
        return "redirect:/logout";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/deletecomment")
    public String deleteComment(@RequestParam(name = "comment_id") Long comment_id){
        Comment comment = commentService.getComment(comment_id);
        Long post_id = comment.getPost().getId();
        commentService.deleteComment(comment);
        return "redirect:/postdetails/" + post_id;
    }

    // ./deleting

}
