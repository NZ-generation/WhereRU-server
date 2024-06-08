package com.example.nzgeneration.domain.badge;

import com.example.nzgeneration.domain.memberbadge.MemberBadge;
import com.example.nzgeneration.domain.memberbadge.MemberBadgeRepository;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.BadgeInfo;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.UserBadgeInfo;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;

    public BadgeInfo getMyBadgeList(User user) {
        List<MemberBadge> memberBadges = memberBadgeRepository.findByUser(user);
        List<UserBadgeInfo> userBadgeInfoList = memberBadges.stream()
            .map(memberBadge -> UserBadgeInfo.builder()
                .badgeName(memberBadge.getBadge().getName())
                .badgeImageUrl(memberBadge.getBadge().getBadgeImageUrl())
                .build())
            .collect(Collectors.toList());

        return BadgeInfo.builder()
            .userBadgeInfoList(userBadgeInfoList)
            .build();
    }

    //첫 스탬프 뱃지 추가
    @Transactional
    public void giveMemberFirstBadge(User user) {
        Badge badge = badgeRepository.findById(1L).get();

        int isPresent = memberBadgeRepository.countByUserAndBadge(user, badge);

        if(isPresent != 0) {
            throw  new GeneralException(ErrorStatus._DUPLICATED_BADGE);
        }
        MemberBadge memberBadge = MemberBadge.toEntity(user, badge);

        memberBadgeRepository.save(memberBadge);
    }
}
