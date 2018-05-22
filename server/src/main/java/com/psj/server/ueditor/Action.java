package com.psj.server.ueditor;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Administrator
 */
public interface Action {


    /**
     * 上传图片
     *
     * @param upfile   图片文件
     * @param response http响应对象
     * @return 图片访问路径
     */
    String uploadImage(MultipartFile upfile, HttpServletResponse response);

    /**
     * 上传涂鸦
     *
     * @param base64 涂鸦图片base64字符串
     * @return 图片访问路径
     */
    String uploadScrawl(String base64);

    /**
     * 上传视频
     *
     * @param upfile 文件
     * @return 文件访问路径
     */
    String uploadVideo(MultipartFile upfile);

    /**
     * 上传附件
     *
     * @param upfile 文件
     * @return 文件访问路径
     */
    String uploadFile(MultipartFile upfile);

    /**
     * 通过url抓取远程图片，保存本地
     *
     * @param source 远程图片url集合
     * @return 文件访问路径
     */
    String catchImage(List<String> source);

    /**
     * 返回图片集合
     *
     * @param start 开始页
     * @param size  显示数量
     * @return 文件访问路径
     */
    String listImage(Integer start, Integer size);

    /**
     * 返回文件集合
     *
     * @param start 开始页
     * @param size  显示数量
     * @return 文件访问路径
     */
    String listFile(Integer start, Integer size);

}
