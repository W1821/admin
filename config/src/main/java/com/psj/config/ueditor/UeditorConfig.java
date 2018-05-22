package com.psj.config.ueditor;

import com.psj.common.constant.ueditor.UeditorEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "ueditor")
public class UeditorConfig {

    /**
     * 上传图片配置项
     */
    protected Long imageMaxSize = 2048000L;
    protected String[] imageAllowFiles = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};
    protected boolean imageCompressEnable = true;
    protected Integer imageCompressBorder = 1600;
    protected String imageInsertAlign = "none";
    protected String imagePathFormat = "/image/";

    /**
     * 涂鸦图片上传配置项
     */
    protected Long scrawlMaxSize = 2048000L;
    protected String scrawlPathFormat = "/image/";

    /**
     * 截图工具上传
     */
    protected String snapscreenPathFormat = "/image/";

    /**
     * 抓取远程图片配置
     */
    protected String[] catcherLocalDomain = {"127.0.0.1", "localhost", "img.baidu.com"};
    protected Long catcherMaxSize = 2048000L;
    protected String[] catcherAllowFiles = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};
    protected String catcherPathFormat = "/image/";

    /**
     * 上传视频配置
     */
    protected Long videoMaxSize = 102400000L;
    protected String[] videoAllowFiles = {
            ".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",
            ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid"};
    protected String videoPathFormat = "/video/";

    /**
     * 上传文件配置
     */
    protected Long fileMaxSize = 51200000L;
    protected String[] fileAllowFiles = {
            ".png", ".jpg", ".jpeg", ".gif", ".bmp",
            ".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",
            ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid",
            ".rar", ".zip", ".tar", ".gz", ".7z", ".bz2", ".cab", ".iso",
            ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf", ".txt", ".md", ".xml"
    };
    protected String filePathFormat = "/file/";

    /**
     * 列出指定目录下的图片
     */
    protected String imageManagerListPath = "/image/";
    protected Integer imageManagerListSize = 20;
    protected String[] imageManagerAllowFiles = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};

    /**
     * 列出指定目录下的文件
     */
    protected String fileManagerListPath = "/file/";
    protected Integer fileManagerListSize = 20;
    protected String[] fileManagerAllowFiles = {
            ".png", ".jpg", ".jpeg", ".gif", ".bmp",
            ".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",
            ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid",
            ".rar", ".zip", ".tar", ".gz", ".7z", ".bz2", ".cab", ".iso",
            ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf", ".txt", ".md", ".xml"
    };

    /**
     * 上传图片配置项
     */
    private String imageActionName = UeditorEnum.ACTION.UPLOAD_IMAGE.getValue();
    private String imageFieldName = "upfile";
    private String imageUrlPrefix = "";

    /**
     * 涂鸦图片上传配置项
     */
    private String scrawlActionName = UeditorEnum.ACTION.UPLOAD_SCRAWL.getValue();
    private String scrawlFieldName = "scrawlBase64";
    private String scrawlUrlPrefix = "";
    private String scrawlInsertAlign = "none";

    /**
     * 截图工具上传
     */
    private String snapscreenActionName = UeditorEnum.ACTION.UPLOAD_IMAGE.getValue();
    private String snapscreenUrlPrefix = "";
    private String snapscreenInsertAlign = "none";

    /**
     * 抓取远程图片配置
     */
    private String catcherActionName = UeditorEnum.ACTION.CATCH_IMAGE.getValue();
    private String catcherFieldName = "source";
    private String catcherUrlPrefix = "";

    /**
     * 上传视频配置
     */
    private String videoActionName = UeditorEnum.ACTION.UPLOAD_VIDEO.getValue();
    private String videoFieldName = "upfile";
    private String videoUrlPrefix = "";

    /**
     * 上传文件配置
     */
    private String fileActionName = UeditorEnum.ACTION.UPLOAD_FILE.getValue();
    private String fileFieldName = "upfile";
    private String fileUrlPrefix = "";


    /**
     * 列出指定目录下的图片
     */
    private String imageManagerActionName = UeditorEnum.ACTION.LIST_IMAGE.getValue();
    private String imageManagerUrlPrefix = "";
    private String imageManagerInsertAlign = "none";


    /**
     * 列出指定目录下的文件
     */
    private String fileManagerActionName = UeditorEnum.ACTION.LIST_FILE.getValue();
    private String fileManagerUrlPrefix = "";


}
