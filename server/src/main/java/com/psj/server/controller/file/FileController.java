package com.psj.server.controller.file;

import com.psj.common.constant.GlobalCodeEnum.ErrorCode;
import com.psj.common.constant.GlobalEnum;
import com.psj.common.util.ExceptionUtil;
import com.psj.common.util.FileUtil;
import com.psj.file.service.FileService;
import com.psj.server.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 单图片上传
     *
     * @param file 上传的文件
     * @return 图片的url
     */
    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
    public String uploadImageSingle(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            ExceptionUtil.throwError(ErrorCode.ERROR_1100);
        }
        // 判断文件是否是图片
        if (!isImage(file)) {
            ExceptionUtil.throwError(ErrorCode.ERROR_1101);
        }
        return fileService.uploadImage(file);
    }

    /**
     * 多图片上传
     *
     * @param request http请求
     * @return 多个图片的url
     */
    @RequestMapping(value = "/upload/multiple/image", method = RequestMethod.POST)
    public List uploadImageMultiple(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        List<String> imageUrlList = new ArrayList<>();
        files.forEach(file -> {
            if (file != null && isImage(file)) {
                String imageUrl = fileService.uploadImage(file);
                if (imageUrl != null) {
                    imageUrlList.add(imageUrl);
                }
            }
        });
        return imageUrlList;
    }

    /**
     * 判断文件是否是图片
     */
    private boolean isImage(MultipartFile file) {
        return GlobalEnum.IMAGE_FIELDS.containsIgnoreCase(FileUtil.getFileSuffix(file.getOriginalFilename()));
    }

}
