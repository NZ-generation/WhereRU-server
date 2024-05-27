package com.example.nzgeneration.domain.trashcanreport;

import com.example.nzgeneration.domain.trashcan.TrashCategory;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.domain.user.UserRepository;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrashcanReportService {

    private final TrashcanReportRepository trashcanReportRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addTrashcanReport(int mapX, int mapY, String imageUrl, TrashCategory trashCategory) {

        User user = userRepository.findById(1L)
            .orElseThrow(() -> new GeneralException(ErrorStatus._EMPTY_USER));

        //TODO - 유저 중복 처리
        TrashcanReport trashcanReport = TrashcanReport.builder()
            .trashcanReportUser(user)
            .trashcanReportImageUrl(imageUrl)
            .trashCategory(trashCategory)
            .build();

        trashcanReportRepository.save(trashcanReport);
    }
}