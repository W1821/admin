package com.psj.server.controller.system;

import com.psj.common.constant.GlobalCodeEnum.ErrorCode;
import com.psj.common.util.ResponseMessageUtil;
import com.psj.config.web.AdminConfig;
import com.psj.core.service.system.UserService;
import com.psj.pojo.dto.base.message.ResponseMessage;
import com.psj.pojo.dto.system.user.ModifyPwdDTO;
import com.psj.pojo.dto.system.user.UserDTO;
import com.psj.pojo.dto.system.user.UserSearchDTO;
import com.psj.server.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * <br>
 * <b>功能：</b>系统人员<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private final AdminConfig adminConfig;
    private final UserService userService;

    @Autowired
    public UserController(AdminConfig adminConfig, UserService userService) {
        this.adminConfig = adminConfig;
        this.userService = userService;
    }

    /**
     * 列表数据
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> list(@RequestBody UserSearchDTO dto) {
        return ResponseMessageUtil.createResponseEntity(userService.list(dto));
    }

    /**
     * 查询一个
     */
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> query(@PathVariable("id") Long id) {
        return ResponseMessageUtil.createResponseEntity(userService.query(id));
    }

    /**
     * 判断手机号码是否重复
     */
    @RequestMapping(value = "/check/number/{phoneNumber}", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> phoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        return ResponseMessageUtil.createResponseEntity(userService.checkNumber(phoneNumber));
    }

    /**
     * 保存、更新
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> save( @RequestBody UserDTO dto) {
        ResponseMessage message;
        if (!dto.getPhoneNumber().matches(adminConfig.getPhoneNumberRegexp())) {
            message = ResponseMessageUtil.error(ErrorCode.ERROR_1201);
        } else {
            message = userService.save(dto);
        }
        return ResponseMessageUtil.createResponseEntity(message);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> delete(@PathVariable("id") Long id) {
        return ResponseMessageUtil.createResponseEntity(userService.delete(id));
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/modify/ownPwd", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> modifyOwnPassword(@Valid @RequestBody ModifyPwdDTO dto, Principal principal) {
        ResponseMessage message;
        if (!dto.getNewPwd().equals(dto.getVerifiedPwd())) {
            message = ResponseMessageUtil.error(ErrorCode.ERROR_1201);
        } else {
            message = userService.modifyOwnPassword(dto, principal.getName());
        }
        return ResponseMessageUtil.createResponseEntity(message);
    }

}
