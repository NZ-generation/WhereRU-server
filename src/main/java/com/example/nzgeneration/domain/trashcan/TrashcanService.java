package com.example.nzgeneration.domain.trashcan;

import com.example.nzgeneration.domain.trashcan.dto.TrashcanResponseDto.GetTrashcanResponse;
import com.example.nzgeneration.domain.trashcan.dto.TrashcanResponseDto.GetTrashcanResponses;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrashcanService {

    private final TrashcanRepository trashcanRepository;

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
        if(trashCategory.equals(TrashCategory.ALL)) {
            trashcans = trashcanRepository.findTrashcansByPolygon(polygon);
        }
        //카테고리별 조회
        else {
            trashcans = trashcanRepository.findTrashcansByPolygonAndTrashCategory(polygon, trashCategory);
        }
        List<GetTrashcanResponse> trashcanResponses = trashcans.stream().map(trashcan ->
            GetTrashcanResponse.builder()
                .id(trashcan.getId())
                .trashcanPoint(new Point(trashcan.getTrashcanPoint().getX(), trashcan.getTrashcanPoint().getY()))
                .imageUrl(trashcan.getRepresentativeImageUrl())
                .build()
        ).toList();
        return new GetTrashcanResponses(trashcanResponses);
    }
}