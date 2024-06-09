package com.example.nzgeneration.domain.user.dto;

import com.example.nzgeneration.domain.user.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserSigningSimpleInfo {

        private String nickName;
        private long howManyDays;

        public static UserSigningSimpleInfo toDTO(User user, long days) {
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
    public static class UserMyPageDetailInfo {

        private String nickName;
        private String profileImgUrl;
        private Integer currentPoint;
        private Integer badgeCount;
        private Integer nftCount;

        public static UserMyPageDetailInfo toDTO(User user) {
            return UserMyPageDetailInfo.builder()
                .nickName(user.getNickname())
                .profileImgUrl(user.getProfileImageUrl())
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
    public static class UserEditingPageDetailInfo {

        private String profileImgUrl;
        private String nickName;
        private String walletAddress;

        public static UserEditingPageDetailInfo toDTO(User user) {
            return UserEditingPageDetailInfo.builder()
                .profileImgUrl(user.getProfileImageUrl())
                .nickName(user.getNickname())
                .walletAddress(user.getWalletAddress())
                .build();
        }

    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RankingInfo {

        private UserRankingInfo firstRanker;
        private UserRankingInfo secondRanker;
        private UserRankingInfo thirdRanker;
        private List<UserRankingInfo> userRankingList;

        public static RankingInfo toDTO(UserRankingInfo firstRanker, UserRankingInfo secondRanker,
            UserRankingInfo thirdRanker, List<UserRankingInfo> userRankingList) {
            return RankingInfo.builder()
                .firstRanker(firstRanker)
                .secondRanker(secondRanker)
                .thirdRanker(thirdRanker)
                .userRankingList(userRankingList)
                .build();
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserRankingInfo {

        private Long rank;
        private String profileImageUrl;
        private String nickname;
        private int points;
        private Boolean isCurrentUser;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BadgeInfo {

        List<UserBadgeInfo> userBadgeInfoList;
    }


    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserBadgeInfo {

        private String badgeName;
        private String badgeImageUrl;
    }

}