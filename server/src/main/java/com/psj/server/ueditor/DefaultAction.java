package com.psj.server.ueditor;


import com.psj.common.constant.ueditor.UeditorConstant;
import com.psj.common.util.FileUtil;
import com.psj.common.util.JsonUtil;
import com.psj.common.util.ResponseUtil;
import com.psj.config.ueditor.UeditorConfig;
import com.psj.config.ueditor.UeditorUploadConfig;
import com.psj.pojo.dto.ueditor.UploadState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
@Component
public class DefaultAction implements Action {

    private final UeditorConfig config;

    private final UeditorUploadConfig ueditorUploadConfig;

    @Autowired
    public DefaultAction(UeditorConfig config, UeditorUploadConfig ueditorUploadConfig) {
        this.config = config;
        this.ueditorUploadConfig = ueditorUploadConfig;
    }

    /**
     * 上传图片
     */
    @Override
    public String uploadImage(MultipartFile upfile, HttpServletResponse response) {
        // 判断文件是否存在
        if (upfile == null) {
            return ResponseUtil.error(UeditorConstant.NOT_FOUND_UPLOAD_DATA);
        }
        // 检查文件类型
        if (fileTypeIsNotAllow(upfile, config.getImageAllowFiles())) {
            return ResponseUtil.error(UeditorConstant.NOT_ALLOW_FILE_TYPE);
        }
        // 检验图片大小
        if (upfile.getSize() > config.getImageMaxSize()) {
            return ResponseUtil.error(UeditorConstant.MAX_SIZE);
        }
        // 保存图片
        String url = FileUtil.writeSingleFileToDisk(upfile, ueditorUploadConfig.getRootPath(), ueditorUploadConfig.getResourcePrefix());
        if (url == null) {
            return ResponseUtil.error(UeditorConstant.IO_ERROR);
        }
        String originalFileName = upfile.getOriginalFilename();
        return JsonUtil.objToJsonString(new UploadState(UeditorConstant.SUCCESS, url, originalFileName, originalFileName));
    }


    /**
     * 上传涂鸦
     *
     * @param base64 图片base64字符串
     */
    @Override
    public String uploadScrawl(String base64) {
        // 判断文件是否存在
        if (base64 == null) {
            return ResponseUtil.error(UeditorConstant.NOT_FOUND_UPLOAD_DATA);
        }
        // 检验图片大小
        if (base64.getBytes().length > config.getScrawlMaxSize()) {
            return ResponseUtil.error(UeditorConstant.MAX_SIZE);
        }
        // 保存图片
        String url = FileUtil.writeImageBase64ToFile(base64, ueditorUploadConfig.getRootPath(), ueditorUploadConfig.getResourcePrefix());
        if (url == null) {
            return ResponseUtil.error(UeditorConstant.IO_ERROR);
        }
        return JsonUtil.objToJsonString(new UploadState(UeditorConstant.SUCCESS, url, "", ""));
    }

    /**
     * 上传视频
     */
    @Override
    public String uploadVideo(MultipartFile upfile) {
        // 判断文件是否存在
        if (upfile == null) {
            return ResponseUtil.error(UeditorConstant.NOT_FOUND_UPLOAD_DATA);
        }
        // 检查文件类型
        if (fileTypeIsNotAllow(upfile, config.getVideoAllowFiles())) {
            return ResponseUtil.error(UeditorConstant.NOT_ALLOW_FILE_TYPE);
        }
        // 检验图片大小
        if (upfile.getSize() > config.getVideoMaxSize()) {
            return ResponseUtil.error(UeditorConstant.MAX_SIZE);
        }
        // 保存图片
        String url = FileUtil.writeSingleFileToDisk(upfile, ueditorUploadConfig.getRootPath(), ueditorUploadConfig.getResourcePrefix());
        if (url == null) {
            return ResponseUtil.error(UeditorConstant.IO_ERROR);
        }
        String originalFileName = upfile.getOriginalFilename();
        return JsonUtil.objToJsonString(new UploadState(UeditorConstant.SUCCESS, url, originalFileName, originalFileName));
    }

    /**
     * 上传附件
     */
    @Override
    public String uploadFile(MultipartFile upfile) {
        // 判断文件是否存在
        if (upfile == null) {
            return ResponseUtil.error(UeditorConstant.NOT_FOUND_UPLOAD_DATA);
        }
        // 检查文件类型
        if (fileTypeIsNotAllow(upfile, config.getFileAllowFiles())) {
            return ResponseUtil.error(UeditorConstant.NOT_ALLOW_FILE_TYPE);
        }
        // 检验图片大小
        if (upfile.getSize() > config.getFileMaxSize()) {
            return ResponseUtil.error(UeditorConstant.MAX_SIZE);
        }
        // 保存图片
        String url = FileUtil.writeSingleFileToDisk(upfile, ueditorUploadConfig.getRootPath(), ueditorUploadConfig.getResourcePrefix());
        if (url == null) {
            return ResponseUtil.error(UeditorConstant.IO_ERROR);
        }
        String originalFileName = upfile.getOriginalFilename();
        return JsonUtil.objToJsonString(new UploadState(UeditorConstant.SUCCESS, url, originalFileName, originalFileName));
    }

    /**
     * 这里不支持远程抓图，关闭此功能。这个功能有安全风险
     *
     */
    @Override
    public String catchImage(List<String> source) {
        return null;
    }

    @Override
    public String listImage(Integer start, Integer size) {
        return null;
    }

    @Override
    public String listFile(Integer start, Integer size) {
        return null;
    }


    /**
     * 检查文件类型
     */
    private boolean fileTypeIsNotAllow(MultipartFile upfile, String[] allowFiles) {
        String suffix = FileUtil.getFileSuffix(upfile.getOriginalFilename());
        // 检验格式是否正确
        return !Arrays.asList(allowFiles).contains(suffix);
    }

}
