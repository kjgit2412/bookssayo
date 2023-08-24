package org.koreait.controllers.tests;

import lombok.RequiredArgsConstructor;
import org.koreait.models.files.FileUploadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/test/upload")
@RequiredArgsConstructor
public class TestController {

    private final FileUploadService uploadService;

    @GetMapping
    public String upload() {
        return "test";
    }


    @PostMapping
    @ResponseBody
    public void uploadPs(MultipartFile[] files, String gid, String location) {
        uploadService.upload(files, gid, location);
    }
}
