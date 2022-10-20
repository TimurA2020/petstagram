package com.main.petstagram.repos;


import com.main.petstagram.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findPostsByUserId(@Param("id") Long id);

}
