package org.koreait.models.files;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.koreait.entities.FileInfo;
import org.koreait.repositories.FileInfoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileInfoService {
    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload.url}")
    private String uploadUrl;


    private final HttpServletRequest request;

    private final FileInfoRepository repository;

    /**
     * 파일 등록 번호로 개별 조회
     *
     * @param id
     * @return
     */
    public FileInfo get(Long id) {

        FileInfo item = repository.findById(id).orElseThrow(FileNotFoundException::new);

        addFileInfo(item);

        return item;
    }

    public List<FileInfo> getList(Options opts) {

        return null;
    }

    /**
     * - 파일 업로드 서버 경로(filePath)
     * - 파일 서버 접속 URL (fileUrl)
     * - 썸네일 경로(thumbsPath), 썸네일 URL(thumbsUrl)
     *
     * @param item
     */
    public void addFileInfo(FileInfo item) {
        long id = item.getId();
        String extension = item.getExtension();
        String fileName = extension == null || extension.isBlank() ? "" + id : id + "." + extension;
        long folder = id % 10L;

        // 파일 업로드 서버 경로
        String filePath = uploadPath + folder + "/" + fileName;
        
        // 파일 서버 접속 URL (fileUrl)
        String fileUrl = request.getContextPath() + uploadUrl + folder + "/" + fileName;

        // 썸네일 경로(thumbsPath)
        String thumbPath = getUploadThumbPath() + folder;
        File thumbDir = new File(thumbPath);
        if (!thumbDir.exists()) {
            thumbDir.mkdirs();
        }

        String[] thumbsPath = thumbDir.list((dir,name) -> name.indexOf("_" + fileName) != -1);

        // 썸네일 URL(thumbsUrl)
        String[] thumbsUrl = Arrays.stream(thumbsPath)
                .map(s -> s.replace(uploadPath, request.getContextPath() + uploadUrl)).toArray(String[]::new);

        item.setFilePath(filePath);
        item.setFileUrl(fileUrl);
        item.setThumbsPath(thumbsPath);
        item.setThumbsUrl(thumbsUrl);
    }

    private String getUploadThumbPath( ){
        return uploadPath + "thumbs/";
    }

    private String getUploadThumbUrl() {
        return uploadUrl + "thumbs/";
    }

    @Data @Builder
    static class Options {
        private String gid;
        private String location;
        private SearchMode mode = SearchMode.ALL;
    }

    static enum SearchMode {
        ALL,
        DONE,
        UNDONE
    }
}
