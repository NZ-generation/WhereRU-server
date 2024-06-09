package com.example.nzgeneration.domain.trashcan;

import java.util.List;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrashcanRepository extends JpaRepository<Trashcan, Long> {

    //polygon안에 trashcanPoint가 포함되는지 확인
    @Query(value = "SELECT tc  FROM Trashcan tc WHERE ST_CONTAINS(:polygon, tc.trashcanPoint) = true AND tc.trashCategory = :trashCategory")
    List<Trashcan> findTrashcansByPolygonAndTrashCategory(@Param("polygon") Polygon polygon, @Param("trashCategory") TrashCategory trashCategory);

    @Query(value = "SELECT tc  FROM Trashcan tc WHERE ST_CONTAINS(:polygon, tc.trashcanPoint)")
    List<Trashcan> findTrashcansByPolygon(@Param("polygon") Polygon polygon);

    int countByTrashCategory(TrashCategory trashCategory);
}