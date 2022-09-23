package com.zyf.legou.upload.config;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class FileDfsUtil {

    @Resource
    private FastFileStorageClient storageClient;

    /**
     * 上传文件
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public String upload(MultipartFile multipartFile) throws IOException {
        String exName = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(multipartFile.getInputStream(), multipartFile.getSize(), exName, null);
        return storePath.getFullPath();

    }
    public void deleteFile(String fileUrl){
        StorePath storePath = StorePath.parseFromUrl(fileUrl);
        storageClient.deleteFile(storePath.getFullPath());
    }
}