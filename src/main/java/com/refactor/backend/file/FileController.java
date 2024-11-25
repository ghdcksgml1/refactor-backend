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
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // 파일 생성
    @PostMapping("/directories/{userName}/files")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFile(
            @ModelAttribute FileSaveRequest request,
            @PathVariable String userName
    ) {
        fileService.saveFile(userName, request.getFilename(), request.getFile());
    }

    // 파일구조 가져오기
    @GetMapping("/directories")
    public List<List<FileInfo>> findAllUserFiles() {
        return fileService.findAllUserFiles();
    }

    // 파일 리소스 가져오기
    @GetMapping("/directories/{userName}/files/{filename}")
    public ResponseEntity<Resource> getResource(
            @PathVariable String userName,
            @PathVariable String filename
    ) {
        FileResource userFileResource = fileService.getUserFileResource(userName, filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, userFileResource.getContentType())
                .body(userFileResource.getFileResource());
    }

    // 파일 리소스 다운로드
    @GetMapping("/directories/{userName}/files/{filename}/download")
    public ResponseEntity<Resource> downloadResource(
            @PathVariable String userName,
            @PathVariable String filename
    ) {
        FileResource userFileResource = fileService.getUserFileResource(userName, filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, userFileResource.getContentType())
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", userFileResource.getFilename()))
                .body(userFileResource.getFileResource());
    }


    // 파일 리소스 삭제
    @DeleteMapping("/directories/{userName}/files/{filename}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFile(
            @PathVariable String userName,
            @PathVariable String filename
    ) {
        fileService.deleteUserFileResource(userName, filename);
    }
}
