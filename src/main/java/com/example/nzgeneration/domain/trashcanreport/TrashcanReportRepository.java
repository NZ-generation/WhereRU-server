package com.example.nzgeneration.domain.trashcanreport;

import com.example.nzgeneration.domain.user.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrashcanReportRepository extends JpaRepository<TrashcanReport, Long> {
    List<TrashcanReport> findByTrashcanReportUser(User user);

    @Query("select count(r) FROM TrashcanReport r")
    int countAllReports();

    @Query("SELECT COUNT(r) FROM TrashcanReport r WHERE r.createdAt BETWEEN :startDate AND :endDate")
    int countReportsBetween(@Param("startDate") LocalDateTime startDateTime, @Param("endDate") LocalDateTime endDateTime);
}
