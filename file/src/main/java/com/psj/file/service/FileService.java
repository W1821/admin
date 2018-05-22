package com.psj.file.service;

import com.psj.common.constant.GlobalCodeEnum.ErrorCode;
import com.psj.common.util.ExceptionUtil;
import com.psj.common.util.FileUtil;
import com.psj.config.file.FileUploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 */
@Service
public class FileService {

    private final FileUploadConfig fileUploadConfig;

    @Autowired
    public FileService(FileUploadConfig fileUploadConfig) {
        this.fileUploadConfig = fileUploadConfig;
    }

    /**
     * 上传图片
     *
     * @param file 图片文件
     * @return 图片访问url
     */
    public String uploadImage(MultipartFile file) {
        // 判断文件大小
        long fileSize = file.getSize();
        long maxImageSize = fileUploadConfig.getMaxSize(fileUploadConfig.getMaxImageSize());
        if (fileSize > maxImageSize) {
            ExceptionUtil.throwError(ErrorCode.ERROR_1102);
        }

        String rootPath = fileUploadConfig.getRootPath();
        String imageUrlPrefix = fileUploadConfig.getImageUrlPrefix();
        return FileUtil.writeSingleFileToDisk(file, rootPath, imageUrlPrefix);
    }


}
