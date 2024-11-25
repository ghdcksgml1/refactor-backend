package com.refactor.backend.file;

import com.refactor.backend.file.dto.FileInfo;
import com.refactor.backend.file.dto.FileResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void saveFile(String userName, String filename, MultipartFile file) {
    }

    public List<List<FileInfo>> findAllUserFiles() {
        return List.of();
    }

    public FileResource getUserFileResource(String userName, String filename) {
        return null;
    }

    public void deleteUserFileResource(String userName, String filename) {
    }
}
