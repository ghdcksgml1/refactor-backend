package com.refactor.backend.file.dto;

import org.springframework.core.io.Resource;

public class FileResource {
    private final Resource fileResource;
    private final String contentType;
    private final String filename;

    public FileResource(Resource fileResource, String contentType, String filename) {
        this.fileResource = fileResource;
        this.contentType = contentType;
        this.filename = filename;
    }

    public Resource getFileResource() {
        return fileResource;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFilename() {
        return filename;
    }
}
