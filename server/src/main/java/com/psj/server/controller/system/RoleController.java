package com.psj.server.controller.system;

import com.psj.common.util.ResponseMessageUtil;
import com.psj.core.service.system.RoleService;
import com.psj.pojo.dto.base.message.ResponseMessage;
import com.psj.pojo.dto.system.role.RoleDTO;
import com.psj.pojo.dto.system.role.RoleSearchDTO;
import com.psj.server.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <br>
 * <b>功能：</b>系统角色<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 列表数据
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> list(@Valid @RequestBody RoleSearchDTO dto) {
        return ResponseMessageUtil.createResponseEntity(roleService.list(dto));
    }

    /**
     * 查询一个
     */
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> query(@PathVariable("id") Long id) {
        return ResponseMessageUtil.createResponseEntity(roleService.query(id));
    }

    /**
     * 保存、更新
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> save(@Valid @RequestBody RoleDTO dto) {
        return ResponseMessageUtil.createResponseEntity(roleService.save(dto));
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> delete(@PathVariable("id") Long id) {
        return ResponseMessageUtil.createResponseEntity(roleService.delete(id));
    }

    /**
     * 当前登录人的角色
     */
    @RequestMapping(value = "/main/list", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> getRoleList() {
        return ResponseMessageUtil.createResponseEntity(roleService.getRoleList());
    }
}
