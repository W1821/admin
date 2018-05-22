package com.psj.core.service;


import com.psj.common.util.UserDetailUtil;
import com.psj.pojo.dto.base.page.PageDTO;
import com.psj.pojo.po.system.UserModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * 基本的service
 *
 * @author Administrator
 */
public class BaseService {

    /* ================================================================================= */

    /**
     * 当前登录用户
     */
    protected UserModel getUserDetail() {
        return UserDetailUtil.getCurrentUser();
    }


    /* ================================================================================= */

    /**
     * 获取分页查询对象
     */
    protected Pageable getPageable() {
        return PageRequest.of(0, 1);
    }

    /**
     * 获取分页查询对象
     */
    protected Pageable getPageable(PageDTO dto) {
        Integer page = dto.getPage();
        Integer size = dto.getSize();
        return PageRequest.of(page, size);
    }

    /**
     * 获取分页查询对象
     */
    protected Pageable getPageableAsc(PageDTO dto, String... sortField) {
        Integer page = dto.getPage();
        Integer size = dto.getSize();
        Sort sort = new Sort(Sort.Direction.ASC, sortField);
        return PageRequest.of(page, size, sort);
    }

    /**
     * 获取分页查询对象
     */
    protected Pageable getPageableDesc(PageDTO dto, String... sortField) {
        Integer page = dto.getPage();
        Integer size = dto.getSize();
        Sort sort = new Sort(Sort.Direction.DESC, sortField);
        return PageRequest.of(page, size, sort);
    }

    /* ================================================================================= */

}
