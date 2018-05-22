package com.psj.server.controller.file;


import com.psj.pojo.dto.ueditor.RequestParameter;
import com.psj.server.ueditor.DefaultAction;
import com.psj.server.ueditor.UeditorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/ueditor")
public class UeditorController {

    private final DefaultAction action;

    @Autowired
    public UeditorController(DefaultAction action) {
        this.action = action;
    }

    /**
     * ueditor 后台主方法
     */
    @RequestMapping(value = "/main", method = {RequestMethod.GET, RequestMethod.POST})
    public String main(RequestParameter parameter, HttpServletRequest request, HttpServletResponse response) {
        // 使用默认action，也可以实现Action来自定义
        return UeditorHandler.execute(parameter, action, request, response);
    }


}
