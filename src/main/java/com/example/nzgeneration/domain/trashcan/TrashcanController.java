package com.example.nzgeneration.domain.trashcan;

import com.example.nzgeneration.domain.trashcan.dto.TrashcanRequestDto.GetTrashcansRequest;
import com.example.nzgeneration.domain.trashcan.dto.TrashcanResponseDto.GetTrashcanResponse;
import com.example.nzgeneration.domain.trashcan.dto.TrashcanResponseDto.GetTrashcanResponses;
import com.example.nzgeneration.global.common.response.ApiResponse;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrashcanController {

    private final TrashcanService trashcanService;

    @GetMapping("api/trashcan/{id}")
    @Operation(
        summary = "쓰레기통 상세 조회"
    )
    public ApiResponse<GetTrashcanResponse> getTrashcan(@PathVariable Long id) {
        return ApiResponse.onSuccess(trashcanService.getTrashcan(id));
    }

    @GetMapping("api/trashcan")
    @Operation(
        summary = "영역 내 쓰레기통 조회"
    )
    public ApiResponse<GetTrashcanResponses> getTrashcans(
        @RequestBody GetTrashcansRequest request) {
        Polygon polygon = null;

        try {
            GeometryFactory geometryFactory = new GeometryFactory();
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
        return ApiResponse.onSuccess(trashcanService.getTrashcans(polygon));
    }
}