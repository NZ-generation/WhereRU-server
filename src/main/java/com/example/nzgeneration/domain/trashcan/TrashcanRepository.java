package com.example.nzgeneration.domain.trashcan;

import java.util.List;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrashcanRepository extends JpaRepository<Trashcan, Long> {

    @Query(value = "SELECT tc.id, tc.trashcanPoint FROM Trashcan tc WHERE ST_CONTAINS(:polygon, tc.trashcanPoint)", nativeQuery = true)
    List<Trashcan> findTrashcansByPolygon(@Param("polygon") Polygon polygon);
}