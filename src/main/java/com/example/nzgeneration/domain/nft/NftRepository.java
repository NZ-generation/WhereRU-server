package com.example.nzgeneration.domain.nft;

import com.example.nzgeneration.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface NftRepository extends JpaRepository<Nft, Long> {
    List<Nft> findByUser(User user);
}
