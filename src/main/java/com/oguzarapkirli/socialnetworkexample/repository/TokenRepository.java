package com.oguzarapkirli.socialnetworkexample.repository;

import com.oguzarapkirli.socialnetworkexample.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    List<Token> findAllValidTokenByUserId(UUID userId);

    Optional<Token> findByToken(String token);
}
