package com.zipte.platform.server.application.out.external.image;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImagePort {

    /// 저장
    String uploadFile(MultipartFile file);

    String uploadFiles(List<MultipartFile> files);

    /*
        조회는 링크에서 직접 조회
     */

    /// 삭제
    String deleteFile(String fileName);

}
