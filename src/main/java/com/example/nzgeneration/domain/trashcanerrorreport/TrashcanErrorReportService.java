package com.example.nzgeneration.domain.trashcanerrorreport;

import com.example.nzgeneration.domain.member.Member;
import com.example.nzgeneration.domain.member.MemberRepository;
import com.example.nzgeneration.domain.trashcan.TrashcanRepository;
import com.example.nzgeneration.domain.trashcan.TrashcanService;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrashcanErrorReportService {

    private final TrashcanErrorReportRepository trashcanErrorReportRepository;
    private final TrashcanRepository trashcanRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public String addError(Long id) {

        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new GeneralException(ErrorStatus._EMPTY_USER));

        TrashcanErrorReport trashcanErrorReport = TrashcanErrorReport.builder().trashcan(
                trashcanRepository.findById(id)
                    .orElseThrow(() -> new GeneralException(ErrorStatus._EMPTY_TRASHCAN))
            )
            .member(member)
            .build();

        trashcanErrorReportRepository.save(trashcanErrorReport);
        return "오류 제보 성공";
    }
}
