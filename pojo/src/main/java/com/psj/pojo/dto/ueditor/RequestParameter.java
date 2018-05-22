package com.psj.pojo.dto.ueditor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 统一请求参数
 *
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestParameter {

    /**
     * 功能名称
     */
    private String action;
    /**
     * jsonp请求
     */
    private String callbackName;
    /**
     * 查询分页起始页
     */
    private Integer start;
    /**
     * 查询分页大小
     */
    private Integer size;
    /**
     * 涂鸦上传
     */
    private String scrawlBase64;
    /**
     * 远程抓图
     */
    private List<String> source;


    /**
     * 一定要放在最后面。不然和涂鸦base64数据冲突。
     * 数据绑定的时候按照顺序来绑定。
     * 文件
     */
    private MultipartFile upfile;

}
