package com.example.nzgeneration.domain.user;


import com.example.nzgeneration.global.utils.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String email;

    private String profileImageUrl;

    private String walletAddress;

    @ColumnDefault("0")
    private int badgeCount = 0;

    @ColumnDefault("0")
    private int cumulativePoint = 0;

    @ColumnDefault("0")
    private int currentPoint = 0;

    public void stamp(int point) {
        this.cumulativePoint += point;
        this.currentPoint += point;
    }
}