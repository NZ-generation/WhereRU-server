package com.example.nzgeneration.domain.nft;

import com.example.nzgeneration.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NftRepository extends JpaRepository<Nft, Long> {
    List<Nft> findByUser(User user);
}
