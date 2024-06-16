package com.example.nzgeneration.domain.nft;

import com.example.nzgeneration.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NftRepository extends JpaRepository<Nft, Long> {
    List<Nft> findByUser(User user);
    Optional<Nft> findByUserAndId(User user, Long id);
    Page<Nft> findByBoardIsNotNull(Pageable pageable);

}
