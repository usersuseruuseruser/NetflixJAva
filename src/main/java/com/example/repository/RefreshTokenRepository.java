package com.example.repository;

import com.example.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    RefreshToken findByToken(String token);
    void deleteByToken(String token);
}
