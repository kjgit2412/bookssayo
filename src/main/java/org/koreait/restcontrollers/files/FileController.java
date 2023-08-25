package org.koreait.restcontrollers.files;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.rests.JSONData;
import org.koreait.entities.FileInfo;
import org.koreait.models.files.FileDownloadService;
import org.koreait.models.files.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("restFileController")
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileUploadService uploadService;
    private final FileDownloadService downloadService;
    //private final FileDeleteService deleteService;

    /**
     * 파일 업로드 처리
     * @param files
     * @param gid
     * @param location
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity<JSONData<List<FileInfo>>> uploadPs(MultipartFile[] files, String gid, String location) {
        List<FileInfo> items = uploadService.upload(files, gid, location);

        JSONData<List<FileInfo>> data = new JSONData<>();
        data.setSuccess(true);
        data.setData(items);

        return ResponseEntity.ok(data);
    }

    @RequestMapping("/download/{id}")
    public void download(@PathVariable Long id) {
        downloadService.download(id);
    }

    /**
    @RequestMapping("/delete/{id}")
    public ResponseEntity<JSONData<Long>> delete(Long id) {
        deleteService.delete(id);

        JSONData<Long> data = new JSONData<>();
        data.setSuccess(true);
        data.setData(id);

        return ResponseEntity.ok(data);
    }
    */
}
