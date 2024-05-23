package com.example.nzgeneration.domain.trashcanerrorreport;

import com.example.nzgeneration.domain.trashcan.TrashcanRepository;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.domain.user.UserRepository;
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
    private final UserRepository userRepository;

    @Transactional
    public String addError(Long id) {

        //TODO - 유저 변경, 중복시 예외 처리
        User user = userRepository.findById(id)
            .orElseThrow(() -> new GeneralException(ErrorStatus._EMPTY_USER));

        TrashcanErrorReport trashcanErrorReport = TrashcanErrorReport.builder().trashcan(
                trashcanRepository.findById(id)
                    .orElseThrow(() -> new GeneralException(ErrorStatus._EMPTY_TRASHCAN))
            )
            .user(user)
            .build();

        trashcanErrorReportRepository.save(trashcanErrorReport);
        return "오류 제보 성공";
    }
}
