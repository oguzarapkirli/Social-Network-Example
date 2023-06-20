package com.oguzarapkirli.socialnetworkexample.repository;

import com.oguzarapkirli.socialnetworkexample.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoryRepository extends JpaRepository<Story, UUID> {
    Optional<List<Story>> findAllByCreatorId(UUID creatorId);

}