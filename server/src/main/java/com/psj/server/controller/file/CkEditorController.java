package com.psj.server.controller.file;

import com.psj.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/ckeditor")
public class CkEditorController {

    private static final String CONTENT_TYPE_TEXT_HTML = "text/html; charset=UTF-8";
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    private static final String HEADER_CACHE_CONTROL_NO_CACHE = "no-cache";
    private static final String HEADER_X_FRAME_OPTIONS = "X-Frame-Options";
    private static final String HEADER_X_FRAME_OPTIONS_SAMEORIGIN = "SAMEORIGIN";

    private final FileService fileService;

    @Autowired
    public CkEditorController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * ckeditor 上传图片处理方法
     * 例如：http://localhost:8080/ck/upload/image?CKEditor=editor1&CKEditorFuncNum=1&langCode=zh-cn
     * Post请求中文件名 name="upload"
     *
     * @param upload   文件
     * @param funcNum  ckeditor的特有回调标志
     * @param response http响应
     */
    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
    public void uploadImage(MultipartFile upload, @RequestParam("CKEditorFuncNum") String funcNum, HttpServletResponse response) throws IOException {
        // 保存文件，返回文件访问路径
        String imageUrl = fileService.uploadImage(upload);
        uploadImage(response, imageUrl, funcNum);
    }


    /**
     * 上传图片
     */
    private static void uploadImage(HttpServletResponse response, String imageUrl, String ckEditorFuncNum) throws IOException {
        // 设置响应头
        setUpCkeditorResponse(response);
        // 将上传的图片的url返回给ckeditor
        String responseData = getCkeditorResponse(imageUrl, ckEditorFuncNum);
        // 返回响应流
        writerResponse(response, responseData);
    }

    /**
     * ckeditor 上传图片响应头
     *
     * @param response http响应
     */
    private static void setUpCkeditorResponse(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE_TEXT_HTML);
        response.setHeader(HEADER_CACHE_CONTROL, HEADER_CACHE_CONTROL_NO_CACHE);
        response.setHeader(HEADER_X_FRAME_OPTIONS, HEADER_X_FRAME_OPTIONS_SAMEORIGIN);
    }


    /**
     * ckeditor 响应字符串
     *
     * @param imageUrl 图片的url
     * @param callback ckeditor的特有回调标志
     * @return ckeditor特殊的回调字符串
     */
    private static String getCkeditorResponse(String imageUrl, String callback) {
        return "<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction(" + callback + ", '" + imageUrl + "');</script>";
    }


    /**
     * 返回响应流
     *
     * @param response     http响应对象
     * @param responseData 返回前端字符串
     * @throws IOException 可能出现的IO异常
     */
    private static void writerResponse(HttpServletResponse response, String responseData) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(responseData);
        out.flush();
        out.close();
    }


}
