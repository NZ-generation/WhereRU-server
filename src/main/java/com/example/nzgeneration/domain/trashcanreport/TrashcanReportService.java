package com.example.nzgeneration.domain.trashcanreport;

import com.example.nzgeneration.domain.member.Member;
import com.example.nzgeneration.domain.member.MemberRepository;
import com.example.nzgeneration.domain.trashcan.TrashCategory;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrashcanReportService {

    private final TrashcanReportRepository trashcanReportRepository;
    private final MemberRepository memberRepository;

    public void addTrashcanReport(int mapX, int mapY, String imageUrl, TrashCategory trashCategory) {

        Member member = memberRepository.findById(1L)
            .orElseThrow(() -> new GeneralException(ErrorStatus._EMPTY_USER));

        //TODO - 유저 중복 처리
        TrashcanReport trashcanReport = TrashcanReport.builder()
            .trashcanReportMember(member)
            .trashcanReportImageUrl(imageUrl)
            .trashCategory(trashCategory)
            .build();

        trashcanReportRepository.save(trashcanReport);
    }
}