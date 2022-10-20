package com.main.petstagram.services;

import com.main.petstagram.dtos.PostDTO;
import com.main.petstagram.dtos.UserDTO;
import com.main.petstagram.entities.Post;
import com.main.petstagram.entities.Role;
import com.main.petstagram.entities.User;
import com.main.petstagram.mappers.UserMapper;
import com.main.petstagram.repos.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.main.petstagram.repos.RoleRepo;
import com.main.petstagram.repos.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FriendRequestService friendRequestService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findAllByEmail(username);
        if(user != null){
            return user;
        }else{
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }

    public List<UserDTO> getAllUserDtos(){
        List<User> users = userRepo.findAll();
        return userMapper.toUserDtoList(users);
    }

    public User getUserEntity(Long id){
        return userRepo.findById(id).orElseThrow();
    }
    public UserDTO getUserDTO(Long id){
        return userMapper.toUserDto(userRepo.findById(id).orElse(null));
    }



    public String registerUser(String firstName, String lastName, String email, String password){

        User checkUser = userRepo.findAllByEmail(email);

        if(checkUser == null){
            Role userRole = roleRepo.findByRole("ROLE_USER");
            ArrayList<Role> roles = new ArrayList<>();
            roles.add(userRole);
            User user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setStatus("New here!");
            userRepo.save(user);
            return "success=true";
        }
        return "usererror=true";
    }

    public User findByEmail(String email){
        return userRepo.findAllByEmail(email);
    }

    public String updatePassword(User user, String oldPassword, String newPassword){
        User currentUser = userRepo.findById(user.getId()).orElse(null);
        if(currentUser != null){
            if(passwordEncoder.matches(oldPassword, currentUser.getPassword())){
                currentUser.setPassword(passwordEncoder.encode(newPassword));
                userRepo.save(currentUser);
                return "redirect:/profile?passwordsuccess=true";
            }
        }
        return "redirect:/profile?errorcode=1";
    }

    public User updateRole(Long user_id, Long roleId){
        Role role = roleRepo.findById(roleId).orElse(null);
        User currentUser = userRepo.findById(user_id).orElse(null);
        if(currentUser != null){
            List<Role> userRoles = currentUser.getRoles();
            userRoles.add(role);
            currentUser.setRoles(userRoles);
            return userRepo.save(currentUser);
        }
        return null;
    }

    public User removeRole(Long user_id, Long roleId){
        Role role = roleRepo.findById(roleId).orElse(null);
        User currentUser = userRepo.findById(user_id).orElse(null);
        if(currentUser != null){
            List<Role> userRoles = currentUser.getRoles();
            userRoles.remove(role);
            currentUser.setRoles(userRoles);
            return userRepo.save(currentUser);
        }
        return null;
    }

    //Deleting user and on top of that deleting all other entities associated with it
    public void deleteUser(Long user_id){
        User currentUser = userRepo.findById(user_id).orElseThrow();
        List<Role> roles = currentUser.getRoles();
        roles.clear();
        currentUser.setRoles(roles);
        List<User> friends = currentUser.getFriends();

        //Removing friend relationships
        for(int i = 0; i < friends.size(); i++){
            friends.get(i).removeFriend(currentUser);
        }

        commentService.deleteCommentsByAuthor(user_id);
        postService.deletePostsByUser(user_id);
        friendRequestService.deleteRequestAssociatedWithUser(user_id);
        userRepo.delete(currentUser);
    }

    public void updateCredentials(User user, String firstName, String lastName, String status){
        user.setStatus(status);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userRepo.save(user);
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }

    public int countCommentsPerPost(Long post_id){
        return commentRepo.findAllCommentByPostId(post_id).size();
    }

    public List<User> getFriends(Long id){
        return userRepo.findById(id).orElseThrow().getFriends();
    }

    public boolean checkFriend(Long sender_id, Long receiver_id){

        User sender = userRepo.findById(sender_id).orElseThrow();

        List<User> friends = sender.getFriends();

        for(int i = 0; i < friends.size(); i++){
            if (Objects.equals(friends.get(i).getId(), receiver_id)){
                return true;
            }
        }

        return false;

    }

}
