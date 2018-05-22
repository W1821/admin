package com.psj.server.controller.system;

import com.psj.pojo.dto.base.message.ResponseMessage;
import com.psj.server.controller.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于处理angular路由，防止刷新404
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/")
public class MainController extends BaseController {

    /**
     * 默认处理/main下说有的请求，全部转发到index.html
     */
    @RequestMapping("")
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("routes", "路由跳转");
        request.getRequestDispatcher("index.html").forward(request, response);
    }

    /**
     * 默认处理/main下所有的请求，全部转发到index.html
     */
    @RequestMapping("/main/**")
    public void routes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("routes", "路由跳转");
        // 此处路径要打两点，如果直接写 index.html 会循环访问/web/index.html 造成死循环
        request.getRequestDispatcher("../index.html").forward(request, response);
    }


}