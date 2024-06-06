package com.example.nzgeneration.domain.user;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRefreshToken(String token);
    Optional<User> findByNickname(String name);

    List<User> findTop3ByOrderByRankingAsc();

    List<User> findUsersByRankingBetweenOrderByRankingAsc(Long start, Long end);

    List<User> findUsersByRankingGreaterThanOrderByRankingAsc(Long ranking);
}