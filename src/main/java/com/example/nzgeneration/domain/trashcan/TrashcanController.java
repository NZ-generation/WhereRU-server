package com.example.nzgeneration.domain.trashcan;

import com.example.nzgeneration.domain.trashcan.dto.TrashcanRequestDto.GetTrashcansRequest;
import com.example.nzgeneration.domain.trashcan.dto.TrashcanResponseDto.GetTrashcanResponse;
import com.example.nzgeneration.domain.trashcan.dto.TrashcanResponseDto.GetTrashcanResponses;
import com.example.nzgeneration.global.common.response.ApiResponse;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "쓰레기통 조회", description = "쓰레기통 조회 API")
public class TrashcanController {

    private final TrashcanService trashcanService;

    @GetMapping("api/trashcan/{id}")
    @Operation(
        summary = "쓰레기통 상세 조회"
    )
    public ApiResponse<GetTrashcanResponse> getTrashcan(@PathVariable Long id) {
        return ApiResponse.onSuccess(trashcanService.getTrashcan(id));
    }

    @PostMapping("api/trashcan-info")
    @Operation(
        summary = "영역 내 쓰레기통 조회",
        description = "영역의 네 좌표값으로 영역 내 거점을 조회.<br>영역의 좌표값은 위도와 경도 <br>카테고리는 all로 줄 경우 전체 조회"
    )
    public ApiResponse<GetTrashcanResponses> getTrashcans(
        @RequestBody GetTrashcansRequest request, @RequestParam TrashCategory trashCategory) {

        //다각형 객체 초기화
        Polygon polygon = null;

        try {
            GeometryFactory geometryFactory = new GeometryFactory();
            //request 객체에서 좌표를 가져와 Coordinate객체 리스트를 생성
            List<Coordinate> coordinatesPolygon = List.of(
                new Coordinate(request.getTopLeftPoint().getX(), request.getTopLeftPoint().getY()),
                new Coordinate(request.getBottomLeftPoint().getX(), request.getBottomLeftPoint().getY()),
                new Coordinate(request.getBottomRightPoint().getX(), request.getBottomRightPoint().getY()),
                new Coordinate(request.getTopRightPoint().getX(), request.getTopRightPoint().getY()),
                new Coordinate(request.getTopLeftPoint().getX(), request.getTopLeftPoint().getY())
            );
            polygon = geometryFactory.createPolygon(coordinatesPolygon.toArray(new Coordinate[0]));
        } catch (NullPointerException e) {
            throw new GeneralException(ErrorStatus._TRASHCAN_COORDINATE_INVALID);
        } catch (IllegalArgumentException e) {
            throw new GeneralException(ErrorStatus._TRASHCAN_POLYGON_INVALID);
        }
        return ApiResponse.onSuccess(trashcanService.getTrashcans(polygon, trashCategory));
    }

}