package com.psj.core.bo;

import com.psj.pojo.BaseBean;
import com.psj.pojo.dto.BaseDTO;
import com.psj.pojo.dto.base.message.ResponseMessage;
import com.psj.pojo.po.BasePO;

/**
 * @author Administrator
 */
public abstract class BaseBO extends BaseBean {

    /* ================================================================================= */

    /**
     * 查询一条数据
     *
     * @return 统一响应对象
     */
    public abstract ResponseMessage queryOne();

    /**
     * 添加或更新
     *
     * @return 统一响应对象
     */
    public abstract ResponseMessage save();

    /**
     * 删除
     *
     * @return 统一响应对象
     */
    public abstract ResponseMessage delete();

    /**
     * 数据库对象转数据传输对象
     *
     * @return 数据传输对象
     */
    public abstract BaseDTO poToDto();

    /**
     * 数据传输对象转数据库对象
     *
     * @return 数据库对象
     */
    public abstract BasePO dtoToPo();

}
