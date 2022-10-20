package com.main.petstagram.repos;

import com.main.petstagram.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentRepo extends JpaRepository<Comment, Long> {

    List<Comment> findAllCommentByPostId(@Param("id") Long id);

    List<Comment> findAllCommentByUserId(@Param("id") Long id);

}
