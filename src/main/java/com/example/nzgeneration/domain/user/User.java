package com.example.nzgeneration.domain.user;


import com.example.nzgeneration.domain.auth.dto.AuthRequestDto.CreateUserRequest;
import com.example.nzgeneration.global.utils.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
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
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String email;

    private String profileImageUrl;

    private String walletAddress;

    private Integer badgeCount = 0;

    @ColumnDefault("0")
    private Integer cumulativePoint = 0;

    @ColumnDefault("0")
    private Integer currentPoint = 0;

    @ColumnDefault("0")
    private Integer nftCount = 0;

    @ColumnDefault("true")
    private Boolean isAllowLocationInfo;

    @ColumnDefault("true")
    private Boolean isAllowAdNotification;

    @ColumnDefault("0")
    private Long ranking;

    public void stamp(int point) {
        this.cumulativePoint += point;
        this.currentPoint += point;
    }

    private String accessToken;
    private String refreshToken;
    private String appleRefreshToken;

    public void updateToken(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public void signUpToken(String accessToken, String refreshToken, String appleRefreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.appleRefreshToken = appleRefreshToken;
    }

    public void updateNickName(String nickname){
        this.nickname = nickname;
    }

    public void updateWalletAddress(String walletAddress){
        this.walletAddress = walletAddress;
    }

    public void updateProfileImg(String imgUrl){
        this.profileImageUrl = imgUrl;
    }

    public void updateBadgeCount() {this.badgeCount++;}

    public String getPayload(){
        return this.getId()+"+nz";
    }

    public LocalDateTime getCreatedAt(){
        return super.getCreatedAt();
    }

    public static User toEntity(String email, CreateUserRequest createUserRequest){
        return User.builder()
            .nickname(createUserRequest.getNickName())
            .email(email)
            .profileImageUrl(null)
            .walletAddress(createUserRequest.getWalletAddress())
            .isAllowAdNotification(createUserRequest.getIsAllowAdInfo())
            .isAllowLocationInfo(createUserRequest.getIsAllowLocationInfo())
            .badgeCount(0)
            .cumulativePoint(0)
            .currentPoint(0)
            .nftCount(0)
            .build();
    }
}