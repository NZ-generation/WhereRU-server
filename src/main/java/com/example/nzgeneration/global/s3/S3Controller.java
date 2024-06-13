package com.example.nzgeneration.global.s3;


import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import com.example.nzgeneration.global.s3.dto.S3Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@Slf4j
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping("api/file")
    public ResponseEntity<S3Result> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        try {
            S3Result result = s3Service.uploadFile(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error(e.toString());
            throw new GeneralException(ErrorStatus._FILE_UPLOAD_ERROR);
        }
    }
}
