package com.example.nzgeneration.domain.user.dto;

import com.example.nzgeneration.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserSigningSimpleInfo{
        private String nickName;
        private long howManyDays;

        public static UserSigningSimpleInfo toDTO(User user, long days){
            return UserSigningSimpleInfo.builder()
                .nickName(user.getNickname())
                .howManyDays(days)
                .build();

        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserMyPageDetailInfo{
        private String nickName;
        private int currentPoint;
        private int badgeCount;
        private int nftCount;

        public static UserMyPageDetailInfo toDTO(User user){
            return UserMyPageDetailInfo.builder()
                .nickName(user.getNickname())
                .badgeCount(user.getBadgeCount())
                .currentPoint(user.getCurrentPoint())
                .nftCount(user.getNftCount())
                .build();
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserEditingPageDetailInfo{
        private String profileImgUrl;
        private String nickName;
        private String walletAddress;

        public static UserEditingPageDetailInfo toDTO(User user){
            return UserEditingPageDetailInfo.builder()
                .profileImgUrl(user.getProfileImageUrl())
                .nickName(user.getNickname())
                .walletAddress(user.getWalletAddress())
                .build();
        }

    }

}
