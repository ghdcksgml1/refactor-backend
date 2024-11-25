package com.refactor.backend.file.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileSaveRequest {

    private final String filename;
    private final MultipartFile file;

    public FileSaveRequest(String filename, MultipartFile file) {
        this.filename = filename;
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public MultipartFile getFile() {
        return file;
    }
}
