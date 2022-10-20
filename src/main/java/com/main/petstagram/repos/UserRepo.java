package com.main.petstagram.repos;

import com.main.petstagram.entities.FriendRequest;
import com.main.petstagram.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, Long> {

    User findAllByEmail(String email);

//    @Query("SELECT user FROM User user LEFT JOIN user.roles role WHERE role.id = ?1")
//    List<User> findUserByRole(Long role);

//    @Query ("SELECT user FROM User user LEFT JOIN user.friends friends WHERE friends.id = ?1")
//    List<User> findFriends(Long id);

}
