package com.psj.pojo.dto.ueditor;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 上传文件成功返回数据对象
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UploadState extends State {

    private String url;
    private String title;
    private String original;

    public UploadState(String state, String url, String title, String original) {
        super(state);
        this.url = url;
        this.title = title;
        this.original = original;
    }
}
