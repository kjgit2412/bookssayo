package org.koreait.models.files;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.FileInfo;
import org.koreait.repositories.FileInfoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileInfoService {
    @Value("${file.upload.path}")
    private String uploadPath;

    private final FileInfoRepository repository;

    /**
     * 파일 등록 번호로 개별 조회
     *
     * @param id
     * @return
     */
    public FileInfo get(Long id) {
        return null;
    }

    public List<FileInfo> getList(String gid, String location, String mode) {

        return null;
    }
}
