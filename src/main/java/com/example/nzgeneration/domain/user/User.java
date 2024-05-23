package com.example.nzgeneration.domain.user;


import com.example.nzgeneration.global.utils.BaseTimeEntity;
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

    @ColumnDefault("0")
    private int badgeCount = 0;

    @ColumnDefault("0")
    private int cumulativePoint = 0;

    @ColumnDefault("0")
    private int currentPoint = 0;

    private boolean isAllowLocationInfo;

    private boolean isAllowAdNotification;

    public void stamp(int point) {
        this.cumulativePoint += point;
        this.currentPoint += point;
    }

    public String getPayload(){
        return this.getId()+"nz";
    }

    public static User toEntity(String nickName, String email, String profileImageUrl, String walletAddress, boolean isAllowAdNotification, boolean isAllowLocationInfo){
        return User.builder()
            .nickname(nickName)
            .email(email)
            .profileImageUrl(profileImageUrl)
            .walletAddress(walletAddress)
            .isAllowAdNotification(isAllowAdNotification)
            .isAllowLocationInfo(isAllowLocationInfo)
            .build();
    }
}