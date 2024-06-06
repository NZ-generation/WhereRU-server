package com.example.nzgeneration.domain.memberbadge;

import com.example.nzgeneration.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {

    List<MemberBadge> findByUser(User user);
}
