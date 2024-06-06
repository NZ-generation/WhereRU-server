package com.example.nzgeneration.domain.trashcanreport;

import com.example.nzgeneration.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrashcanReportRepository extends JpaRepository<TrashcanReport, Long> {
    List<TrashcanReport> findByTrashcanReportUser(User user);
}
