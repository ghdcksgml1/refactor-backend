package com.refactor.backend.file;

import com.refactor.backend.file.dto.FileInfo;
import com.refactor.backend.file.dto.FileResource;
import org.springframework.core.io.Resource;
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
        FileCommander.saveFile(userName, filename, file);
        fileRepository.reloadFileInfos();
    }

    public List<List<FileInfo>> findAllUserFiles() {
        return fileRepository.findAll();
    }

    public FileResource getUserFileResource(String userName, String filename) {
        Resource fileResource = FileCommander.getResource(userName, filename);
        return new FileResource(fileResource, "image/png", filename);
    }

    public void deleteUserFileResource(String userName, String filename) {
        FileCommander.deleteFile(userName, filename);
        fileRepository.reloadFileInfos();
    }
}

