package com.example.nzgeneration.domain.user;

import com.example.nzgeneration.domain.auth.AuthService;
import com.example.nzgeneration.domain.badge.BadgeService;
import com.example.nzgeneration.domain.memberbadge.MemberBadge;
import com.example.nzgeneration.domain.memberbadge.MemberBadgeRepository;
import com.example.nzgeneration.domain.nft.Nft;
import com.example.nzgeneration.domain.nft.NftRepository;
import com.example.nzgeneration.domain.trashcanerrorreport.TrashcanErrorReport;
import com.example.nzgeneration.domain.trashcanerrorreport.TrashcanErrorReportRepository;
import com.example.nzgeneration.domain.trashcanreport.TrashcanReport;
import com.example.nzgeneration.domain.trashcanreport.TrashcanReportRepository;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.BadgeInfo;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.PatchProfileImg;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.RankingInfo;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.UserEditingPageDetailInfo;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.UserMyPageDetailInfo;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.UserRankingInfo;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.UserSigningSimpleInfo;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import com.example.nzgeneration.global.s3.S3Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;




@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final AuthService authService;
    private final TrashcanReportRepository trashcanReportRepository;
    private final TrashcanErrorReportRepository trashcanErrorReportRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final NftRepository nftRepository;
    private final BadgeService badgeService;


    @Transactional
    public void addStamp(User user) {
        badgeService.giveMemberFirstBadge(user);
        user.stamp(20);
    }

    @Transactional
    public void updateNickName(User user, String name) {
        if (!authService.checkNickNameDuplicate(name)) {
            throw new GeneralException(ErrorStatus._DUPLICATE_NICKNAME);
        }
        user.updateNickName(name);
    }

    @Transactional
    public void updateWalletAddress(User user, String address) {
        user.updateWalletAddress(address);
    }

    @Transactional
    public PatchProfileImg updateUserProfileImage(User user, MultipartFile image) {
        String profileImgUrl = s3Service.uploadFile(image).getImgUrl();
        user.updateProfileImg(profileImgUrl);
        return PatchProfileImg.toDTO(profileImgUrl);
    }

    public UserSigningSimpleInfo getDaysSigning(User user) {
        LocalDateTime createdAt = user.getCreatedAt();
        LocalDateTime now = LocalDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(createdAt, now) + 1;
        return UserSigningSimpleInfo.toDTO(user, daysBetween);
    }

    public UserMyPageDetailInfo getMyPageInfo(User user) {
        return UserMyPageDetailInfo.toDTO(user);
    }

    public UserEditingPageDetailInfo getEditPageInfo(User user) {
        return UserEditingPageDetailInfo.toDTO(user);
    }

    public void deleteUserWithName(String userName){
        User user = userRepository.findByNickname(userName)
            .orElseThrow(()-> new GeneralException(ErrorStatus._EMPTY_USER));

        List<TrashcanReport> reportList = trashcanReportRepository.findByTrashcanReportUser(user);
        List<TrashcanErrorReport> errorReportList = trashcanErrorReportRepository.findByUser(user);
        List<Nft> nftList = nftRepository.findByUser(user);
        List<MemberBadge> memberBadgesList = memberBadgeRepository.findByUser(user);

        deleteTrashcanReportByUser(reportList);
        deleteTrashcanErrorReportByUser(errorReportList);
        deleteNftByUser(nftList);
        deleteMemberBadgeByUser(memberBadgesList);

        userRepository.delete(user);
    }

    @Transactional
    public void deleteTrashcanReportByUser( List<TrashcanReport> list){
        for(TrashcanReport report : list){
            report.setTrashcanReportUser(null);
            trashcanReportRepository.save(report);
        }
    }

    @Transactional
    public void deleteTrashcanErrorReportByUser(List<TrashcanErrorReport> list){
        for(TrashcanErrorReport report : list){
            report.setUser(null);
            trashcanErrorReportRepository.save(report);
        }
    }

    @Transactional
    public void deleteMemberBadgeByUser(List<MemberBadge> list){
        memberBadgeRepository.deleteAll(list);
    }

    @Transactional
    public void deleteNftByUser(List<Nft> list){
        nftRepository.deleteAll(list);
    }


    public RankingInfo getRanking(User user) {

        Long userRanking = user.getRanking();

        //상위 3명 가져오기
        List<User> top3Users = userRepository.findTop3ByOrderByRankingAsc();

        //하위 리스트
        //만약 유저가 3위 이내라면 4위부터 6개를 가져온다
        //아니라면 유저 순위 근처를 가져온다
        List<User> surroundingRankings;
        if (userRanking <= 3) {
            surroundingRankings = userRepository.findUsersByRankingGreaterThanOrderByRankingAsc(userRanking).stream()
                .limit(6)
                .collect(Collectors.toList());
        } else {
            surroundingRankings = userRepository.findUsersByRankingBetweenOrderByRankingAsc(userRanking - 2, userRanking + 3);
        }

        //상위 3개 매핑
        List<UserRankingInfo> top3RankingInfos = top3Users.stream()
            .map(u -> UserRankingInfo.builder()
                .rank(u.getRanking())
                .profileImageUrl(u.getProfileImageUrl())
                .nickname(u.getNickname())
                .points(u.getCumulativePoint())
                .isCurrentUser(u.getId().equals(user.getId()))
                .build())
            .collect(Collectors.toList());

        //하위 리스트 매핑
        List<UserRankingInfo> surroundingRankingInfos = surroundingRankings.stream()
            .map(u -> UserRankingInfo.builder()
                .rank(u.getRanking())
                .profileImageUrl(u.getProfileImageUrl())
                .nickname(u.getNickname())
                .points(u.getCumulativePoint())
                .isCurrentUser(u.getId().equals(user.getId()))
                .build())
            .collect(Collectors.toList());

        //동일 순위 예외 처리
        while (surroundingRankingInfos.size() > 6) {
            surroundingRankingInfos.remove(surroundingRankingInfos.size() - 1);
        }


        UserRankingInfo firstRanker = null;
        UserRankingInfo secondRanker = null;
        UserRankingInfo thirdRanker = null;

        if (top3RankingInfos.size() > 0) {
            firstRanker = top3RankingInfos.get(0);
        }

        if (top3RankingInfos.size() > 1) {
            secondRanker = top3RankingInfos.get(1);
        }

        if (top3RankingInfos.size() > 2) {
            thirdRanker = top3RankingInfos.get(2);
        }

        return RankingInfo.builder()
            .firstRanker(firstRanker)
            .secondRanker(secondRanker)
            .thirdRanker(thirdRanker)
            .userRankingList(surroundingRankingInfos)
            .build();
    }

}