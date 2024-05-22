package com.example.nzgeneration.domain.member;

import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void addStamp() {
        //TODO - 현재 멤버 불러오기
        Member member = memberRepository.findById(1L)
            .orElseThrow(() -> new GeneralException(ErrorStatus._EMPTY_USER));

        //TODO - 포인트 상수, 기준 정하기
        member.stamp(20);
    }
}
