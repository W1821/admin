package com.psj.server.ueditor;

import com.psj.common.constant.ueditor.UeditorConstant;
import com.psj.common.constant.ueditor.UeditorEnum;
import com.psj.common.util.ResponseUtil;
import com.psj.config.ueditor.UeditorConfig;
import com.psj.pojo.dto.ueditor.RequestParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
public class UeditorHandler {


    /**
     * ueditor 后台主方法
     */
    public static String execute(RequestParameter parameter, Action action, HttpServletRequest request, HttpServletResponse response) {
        // 设置响应头
        setHeader(response);

        // 校验参数
        String callbackName = parameter.getCallbackName();
        if (!validCallbackName(callbackName)) {
            return ResponseUtil.error(UeditorConstant.ILLEGAL_CALLBACK);
        }

        // 统一请求处理
        String jsonData = doAction(parameter, action, request, response);
        // 支持jsonp格式
        if (callbackName != null) {
            return supportJsonp(callbackName, jsonData);
        } else {
            return jsonData;
        }
    }

    /**
     * 设置响应头
     */
    private static void setHeader(HttpServletResponse response) {
        response.setHeader("Content-Type", "text/html");
    }

    /**
     * callback参数验证
     */
    private static boolean validCallbackName(String callbackName) {
        return callbackName == null || callbackName.matches("^[a-zA-Z_]+[\\w0-9_]*$");
    }

    /**
     * 执行请求
     */
    private static String doAction(RequestParameter parameter, Action action, HttpServletRequest request, HttpServletResponse response) {
        UeditorEnum.ACTION actionKey = UeditorEnum.ACTION.getActionByValue(parameter.getAction());
        switch (actionKey) {
            // 读取配置
            case CONFIG:
                return ResponseUtil.success(new UeditorConfig());

            // 图片
            case UPLOAD_IMAGE:
                return action.uploadImage(parameter.getUpfile(), response);
            // 涂鸦
            case UPLOAD_SCRAWL:
                return action.uploadScrawl(parameter.getScrawlBase64());
            // 视频
            case UPLOAD_VIDEO:
                return action.uploadVideo(parameter.getUpfile());
            // 附件
            case UPLOAD_FILE:
                return action.uploadFile(parameter.getUpfile());
            // 远程抓图
            case CATCH_IMAGE:
                return action.catchImage(parameter.getSource());

            // 图片列表
            case LIST_IMAGE:
                return action.listImage(parameter.getStart(), parameter.getSize());
            // 文件列表
            case LIST_FILE:
                return action.listFile(parameter.getStart(), parameter.getSize());
            default:
                return ResponseUtil.error(UeditorConstant.INVALID_ACTION);
        }
    }

    /**
     * 支持jsonp
     */
    private static String supportJsonp(String callbackName, String resultData) {
        return callbackName + "(" + resultData + ");";
    }


}
