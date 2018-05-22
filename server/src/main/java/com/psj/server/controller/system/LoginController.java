package com.psj.server.controller.system;

import com.psj.common.constant.GlobalCodeEnum.SuccessCode;
import com.psj.common.util.ResponseMessageUtil;
import com.psj.core.service.system.LoginService;
import com.psj.pojo.dto.base.message.ResponseMessage;
import com.psj.pojo.dto.system.user.UserDTO;
import com.psj.server.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录、登出
 *
 * @author Administrator
 */
@Api(description = "登录、登出控制器")
@RestController
@RequestMapping("/system")
public class LoginController extends BaseController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 登录
     * 1
     * $2a$10$Joh6UCftJL5rYwBxtd7qc.xu8ANntPrcHzp55NtWFWodFWAlOsi.G
     */
    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage<UserDTO>> login() {
        return ResponseMessageUtil.createResponseEntity(loginService.login());
    }

    /**
     * 登出
     */
    @ApiOperation(value = "退出")
    @ApiResponse(code = 200, message = "退出成功", response = ResponseMessage.class)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> logout() {
        return ResponseMessageUtil.createResponseEntity(ResponseMessageUtil.success(SuccessCode.SUCCESS_2002));
    }


}
