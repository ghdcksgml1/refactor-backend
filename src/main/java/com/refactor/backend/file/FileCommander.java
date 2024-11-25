package com.refactor.backend.file;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileCommander {
    public static final Path BASE_PATH = Path.of("storage");

    private FileCommander() {
    }

    public static void saveFile(String userName, String filename, MultipartFile file) {
        Path savedFilePath = BASE_PATH.resolve(Path.of(userName, filename)); // storage/chhong/abce.png
        createParentDirectories(savedFilePath);
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(savedFilePath);
        ) {
            StreamUtils.copy(is, os);
        } catch (IOException e) {
            throw new RuntimeException("저장 중 예외가 발생했습니다.");
        }
    }

    public static Resource getResource(String userName, String filename) {
        File file = BASE_PATH.resolve(Path.of(userName, filename)).toFile();
        return new InputStreamResource(new FileSystemResource(file));
    }

    private static void createParentDirectories(Path filePath) {
        Path parentDirectoryPath = filePath.getParent();
        if (!Files.exists(parentDirectoryPath)) {
            try {
                Files.createDirectories(parentDirectoryPath);
            } catch (IOException e) {
                throw new RuntimeException("상위 폴더 생성 중 예외가 발생했습니다.");
            }
        }
    }

    public static void deleteFile(String userName, String filename) {
        Path filePathToDelete = BASE_PATH.resolve(Path.of(userName, filename));
        try {
            Files.delete(filePathToDelete);
        } catch (IOException e) {
            throw new RuntimeException("삭제 중 예외가 발생했습니다.");
        }
    }
}
