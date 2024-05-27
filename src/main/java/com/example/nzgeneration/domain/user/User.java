package com.example.nzgeneration.domain.user;


import com.example.nzgeneration.domain.auth.dto.AuthRequestDto.CreateUserRequest;
import com.example.nzgeneration.global.utils.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    private String nickname;

    private String email;

    private String profileImageUrl;

    private String walletAddress;

    private int badgeCount;

    private int cumulativePoint;

    private int currentPoint;

    @ColumnDefault("true")
    private boolean isAllowLocationInfo;

    @ColumnDefault("true")
    private boolean isAllowAdNotification;

    public void stamp(int point) {
        this.cumulativePoint += point;
        this.currentPoint += point;
    }

    private String accessToken;
    private String refreshToken;

    public void updateToken(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getPayload(){
        return this.getId()+"+nz";
    }

    public static User toEntity(String email, CreateUserRequest createUserRequest){
        return User.builder()
            .nickname(createUserRequest.getNickName())
            .email(email)
            .profileImageUrl(createUserRequest.getProfileImgUrl())
            .walletAddress(createUserRequest.getWalletAddress())
            .isAllowAdNotification(createUserRequest.getIsAllowAdInfo())
            .isAllowLocationInfo(createUserRequest.getIsAllowLocationInfo())
            .badgeCount(0)
            .cumulativePoint(0)
            .currentPoint(0)
            .build();
    }
}