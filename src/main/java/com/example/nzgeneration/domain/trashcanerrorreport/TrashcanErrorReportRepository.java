package com.example.nzgeneration.domain.trashcanerrorreport;

import com.example.nzgeneration.domain.trashcanreport.TrashcanReport;
import com.example.nzgeneration.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrashcanErrorReportRepository extends JpaRepository<TrashcanErrorReport, Long> {
    List<TrashcanErrorReport> findByUser(User user);
}
