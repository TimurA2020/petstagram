package com.main.petstagram.services;

import com.main.petstagram.entities.FriendRequest;
import com.main.petstagram.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FriendRequestServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendRequestService friendRequestService;

    @Test
    void checkAddFriendRequestAndOtherActions() {
        userService.registerUser("test", "user", "sender@gmail.com", "testuser");
        userService.registerUser("test", "user", "receiver@gmail.com", "testuser");

        Long sender_id = userService.findByEmail("sender@gmail.com").getId();
        Long receiver_id = userService.findByEmail("receiver@gmail.com").getId();

        FriendRequest friendRequest = friendRequestService.addFriendRequest(sender_id, receiver_id);

        Assertions.assertTrue(friendRequestService.checkRequest(sender_id, receiver_id));

        Assertions.assertNotNull(friendRequest);
        Assertions.assertEquals(friendRequest.getReceiver().getId(), receiver_id);
        Assertions.assertEquals(friendRequest.getSender().getId(), sender_id);
        Assertions.assertEquals(friendRequest.getSender().getEmail(), "sender@gmail.com");
        Assertions.assertEquals(friendRequest.getReceiver().getEmail(), "receiver@gmail.com");

        Long friendRequest_id = friendRequest.getId();

        friendRequestService.declineRequest(friendRequest_id);

        assertThrows(NoSuchElementException.class,
                () ->{
                    friendRequestService.getFriendRequest(friendRequest_id);
                });
    }

    @Test
    void addAsFriendAnd(){
        userService.registerUser("test", "user", "sender@gmail.com", "testuser");
        userService.registerUser("test", "user", "receiver@gmail.com", "testuser");

        User sender = userService.findByEmail("sender@gmail.com");
        User receiver = userService.findByEmail("receiver@gmail.com");

        FriendRequest friendRequest = friendRequestService.addFriendRequest(sender.getId(), receiver.getId());

        friendRequestService.addAsFriend(friendRequest.getId());

        User senderUpdated = userService.findByEmail("sender@gmail.com");
        User receiverUpdated = userService.findByEmail("receiver@gmail.com");

        Assertions.assertNotNull(senderUpdated.getFriends().get(0));
        Assertions.assertNotNull(receiverUpdated.getFriends().get(0));

        Assertions.assertEquals(senderUpdated.getFriends().get(0).getEmail(), "receiver@gmail.com");
        Assertions.assertEquals(receiverUpdated.getFriends().get(0).getEmail(), "sender@gmail.com");

    }

    @Test
    void checkdeleteRequestAssociatedWithUser(){
        userService.registerUser("test", "user", "sender@gmail.com", "testuser");
        userService.registerUser("test", "user", "receiver@gmail.com", "testuser");

        User sender = userService.findByEmail("sender@gmail.com");
        User receiver = userService.findByEmail("receiver@gmail.com");

        FriendRequest friendRequest = friendRequestService.addFriendRequest(sender.getId(), receiver.getId());

        Long friendRequest_id = friendRequest.getId();

        friendRequestService.deleteRequestAssociatedWithUser(sender.getId());

        assertThrows(NoSuchElementException.class,
                () ->{
                    friendRequestService.getFriendRequest(friendRequest_id);
                });

    }


}