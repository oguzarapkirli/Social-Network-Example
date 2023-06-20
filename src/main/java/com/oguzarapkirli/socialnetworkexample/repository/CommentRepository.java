package com.oguzarapkirli.socialnetworkexample.repository;

import com.oguzarapkirli.socialnetworkexample.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Optional<List<Comment>> findAllByPostId(UUID postId);

}
