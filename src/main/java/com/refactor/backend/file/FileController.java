package com.refactor.backend.file;

import com.refactor.backend.file.dto.FileInfo;
import com.refactor.backend.file.dto.FileResource;
import com.refactor.backend.file.dto.FileSaveRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FileController {

    // 모든 유저가 소유한 파일목록 조회
    @GetMapping("/directories")
    public List<List<FileInfo>> findAllUserFiles() {
        return List.of();
    }

    // 파일 리소스 가져오기
    @GetMapping("/directories/{userName}/files/{filename}")
    public ResponseEntity<Resource> getResource(
            @PathVariable String userName,
            @PathVariable String filename
    ) {
        return ResponseEntity.ok().build();
    }

    // 파일 다운로드
    @GetMapping("/directories/{userName}/files/{filename}/download")
    public ResponseEntity<Resource> downloadResource(
            @PathVariable String userName,
            @PathVariable String filename
    ) {
        return ResponseEntity.ok().build();
    }

    // 파일 저장
    @PostMapping("/directories/{userName}/files")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFile(
            @ModelAttribute FileSaveRequest request,
            @PathVariable String userName
    ) {
    }

    // 파일 삭제
    @DeleteMapping("/directories/{userName}/files/{filename}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFile(
            @PathVariable String userName,
            @PathVariable String filename
    ) {
    }
}
