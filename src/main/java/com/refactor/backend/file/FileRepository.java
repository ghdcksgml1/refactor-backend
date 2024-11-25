package com.refactor.backend.file;

import com.refactor.backend.file.dto.FileInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileRepository {

    // Key : 유저 이름
    // Value : 파일 정보
    private static Map<String, List<FileInfo>> fileInfoMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void loadFileInfos() {
        reloadFileInfos();
    }

    // 파일정보를 로드하는 메서드
    public void reloadFileInfos() {
        Map<String, List<FileInfo>> newFileInfoMap = new ConcurrentHashMap<>();
        for (String userName : FileCommander.BASE_PATH.toFile().list()) {
            File userDirectory = FileCommander.BASE_PATH.resolve(userName).toFile();
            if (!userDirectory.isDirectory()) continue;

            List<FileInfo> userFileInfos = new ArrayList<>();
            for (String filename : userDirectory.list()) {
                FileInfo fileInfo = new FileInfo(userName, filename);
                userFileInfos.add(fileInfo);
            }
            newFileInfoMap.put(userName, userFileInfos);
        }

        fileInfoMap = newFileInfoMap;
    }

    public List<List<FileInfo>> findAll() {
        return fileInfoMap.values().stream().toList();
    }

    public Optional<FileInfo> findByKeyAndFileName(String name, String fileName) {
        return fileInfoMap.get(name).stream()
                .filter(fileInfo -> fileInfo.filename().equals(fileName))
                .findFirst();
    }
}
