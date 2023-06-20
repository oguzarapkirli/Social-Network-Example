package com.oguzarapkirli.socialnetworkexample.repository;

import com.oguzarapkirli.socialnetworkexample.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {
    Optional<List<Like>> findAllByPostId (UUID postId);
    Optional<Like> findByPostIdAndCreatorId (UUID postId, UUID creatorId);
}
