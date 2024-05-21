package com.example.nzgeneration.domain.trashcan;

import com.example.nzgeneration.domain.trashcan.dto.TrashcanResponseDto.GetTrashcanResponse;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import lombok.RequiredArgsConstructor;
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
}