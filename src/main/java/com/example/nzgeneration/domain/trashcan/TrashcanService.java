package com.example.nzgeneration.domain.trashcan;

import com.example.nzgeneration.domain.retool.dto.GetPercentCategoryResponse;
import com.example.nzgeneration.domain.trashcan.dto.TrashcanResponseDto.GetTrashcanResponse;
import com.example.nzgeneration.domain.trashcan.dto.TrashcanResponseDto.GetTrashcanResponses;
import com.example.nzgeneration.domain.trashcanreport.TrashcanReport;
import com.example.nzgeneration.domain.trashcanreport.TrashcanReportRepository;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrashcanService {

    private final TrashcanRepository trashcanRepository;
    private final TrashcanReportRepository trashcanReportRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public GetTrashcanResponse getTrashcan(Long id) {
        Trashcan trashcan = trashcanRepository.findById(id)
            .orElseThrow(() -> new GeneralException(ErrorStatus._EMPTY_TRASHCAN));

        return GetTrashcanResponse.builder()
            .trashCategory(trashcan.getTrashCategory().toString())
            .imageUrl(trashcan.getRepresentativeImageUrl())
            .build();
    }

    /**
     * 원형 거점 내 쓰레기통 카테고리 별 조회
     */
    public GetTrashcanResponses getTrashcans(Polygon polygon, TrashCategory trashCategory) {
        List<Trashcan> trashcans = null;

        //전체 조회
        if (trashCategory.equals(TrashCategory.ALL)) {
            trashcans = trashcanRepository.findTrashcansByPolygon(polygon);
        }
        //카테고리별 조회
        else {
            trashcans = trashcanRepository.findTrashcansByPolygonAndTrashCategory(polygon,
                trashCategory);
        }
        List<GetTrashcanResponse> trashcanResponses = trashcans.stream().map(trashcan ->
            GetTrashcanResponse.builder()
                .id(trashcan.getId())
                .trashcanPoint(new Point(trashcan.getTrashcanPoint().getX(),
                    trashcan.getTrashcanPoint().getY()))
                .imageUrl(trashcan.getRepresentativeImageUrl())
                .trashCategory(trashcan.getTrashCategory().toString())
                .build()
        ).toList();
        return new GetTrashcanResponses(trashcanResponses);
    }

    public GetPercentCategoryResponse findPercentByCategory() {
        List<TrashCategory> categories = Arrays.asList(
            TrashCategory.GENERAL,
            TrashCategory.PLASTIC,
            TrashCategory.PAPER,
            TrashCategory.ALL
        );

        long totalCount = trashcanRepository.count();

        List<Integer> counts = new ArrayList<>();
        for (TrashCategory category : categories) {
            int count = trashcanRepository.countByTrashCategory(category);
            counts.add(count);
        }

        List<Double> percentages = counts.stream()
            .map(count -> (count / (double) totalCount) * 100)
            .toList();

        List<String> labels = categories.stream()
            .map(TrashCategory::name)
            .toList();

        return GetPercentCategoryResponse.builder()
            .labels(labels)
            .values(percentages)
            .build();
    }

    @Transactional
    public void addTrashcan(Long reportId) {

        TrashcanReport trashcanReport = trashcanReportRepository.findById(reportId)
            .orElseThrow(() -> new GeneralException(ErrorStatus._EMPTY_TRASHCAN_REPORT));

        org.locationtech.jts.geom.Point trashcanPoint = geometryFactory.createPoint(new Coordinate(trashcanReport.getX(), trashcanReport.getY()));

        User user = trashcanReport.getTrashcanReportUser();
        user.receiveReportPoint(300);

        Trashcan trashcan = Trashcan.builder()
            .trashCategory(trashcanReport.getTrashCategory())
            .representativeImageUrl(trashcanReport.getTrashcanReportImageUrl())
            .trashcanPoint(trashcanPoint)
            .build();

        trashcanRepository.save(trashcan);
    }
}