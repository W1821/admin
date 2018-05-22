package com.psj.server.controller.system;

import com.psj.common.util.ResponseMessageUtil;
import com.psj.core.service.system.MenuService;
import com.psj.pojo.dto.base.message.ResponseMessage;
import com.psj.pojo.dto.system.menu.MenuDTO;
import com.psj.server.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>系统菜单<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
@Api(description = "系统菜单控制器")
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 列表数据
     */
    @ApiOperation(value = "获取菜单列表数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage<List<MenuDTO>>> list() {
        return ResponseMessageUtil.createResponseEntity(menuService.list());
    }

    /**
     * 查询一个
     */
    @ApiOperation(value = "获取一个菜单数据")
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage<MenuDTO>> query(@PathVariable("id") Long id) {
        return ResponseMessageUtil.createResponseEntity(menuService.query(id));
    }

    /**
     * 保存、更新
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> save(@Valid @RequestBody MenuDTO dto) {
        return ResponseMessageUtil.createResponseEntity(menuService.save(dto));
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> delete(@PathVariable("id") Long id) {
        return ResponseMessageUtil.createResponseEntity(menuService.delete(id));
    }

    /**
     * 当前登录人的菜单
     */
    @RequestMapping(value = "/main/list", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseMessage> getMenuList() {
        return ResponseMessageUtil.createResponseEntity(menuService.getMenuList());
    }


}
