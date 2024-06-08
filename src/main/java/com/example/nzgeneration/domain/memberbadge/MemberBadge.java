package com.example.nzgeneration.domain.memberbadge;

import com.example.nzgeneration.domain.badge.Badge;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.global.utils.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
public class MemberBadge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Badge badge;

    public static MemberBadge toEntity(User user, Badge badge) {
        return MemberBadge.builder()
            .user(user)
            .badge(badge)
            .build();
    }
}
