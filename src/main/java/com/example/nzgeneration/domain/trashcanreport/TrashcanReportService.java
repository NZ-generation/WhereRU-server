package com.example.nzgeneration.domain.trashcanreport;

import com.example.nzgeneration.domain.trashcan.TrashCategory;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.domain.user.UserRepository;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
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

    public int findCountReport() {
        return trashcanReportRepository.countAllReports();
    }

    public List<Integer> findReportCountList() {
        List<Integer> reportCountList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 6; i++) {
            YearMonth yearMonth = YearMonth.from(currentDate.minusMonths(i));
            LocalDate startDate = yearMonth.atDay(1);
            LocalDate endDate = yearMonth.atEndOfMonth();
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
            int count = trashcanReportRepository.countReportsBetween(startDateTime, endDateTime);
            reportCountList.add(count);
        }

        return reportCountList;
    }

}