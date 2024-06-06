package com.example.nzgeneration.domain.banner;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    @Query("SELECT b FROM Banner b ORDER BY b.createdAt DESC")
    Optional<Banner> findBanner();
}
