package com.example.nzgeneration.domain.trashcanerrorreport;

import com.example.nzgeneration.domain.trashcanreport.TrashcanReport;
import com.example.nzgeneration.domain.user.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrashcanErrorReportRepository extends JpaRepository<TrashcanErrorReport, Long> {
    List<TrashcanErrorReport> findByUser(User user);

    @Query("SELECT COUNT(e) FROM TrashcanErrorReport e")
    int countAllErrors();

    @Query("SELECT COUNT(e) FROM TrashcanErrorReport e WHERE e.createdAt BETWEEN :startDate AND :endDate")
    int countErrorReportsBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
