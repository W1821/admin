package com.psj.pojo.dto.base.page;

import com.psj.pojo.BaseBean;
import lombok.*;

/**
 * <br>
 * <b>功能：</b>分页查询DTO<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 14:49<br>
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PageDTO extends BaseBean {

    /**
     * 第几页，从1开始。
     */
    private Integer page;
    /**
     * 页面容量，一页多少条。
     */
    private Integer size;

    public Integer getPage() {
        // 分页从0开始
        return page == null || page < 0 ? 0 : page - 1;
    }

    public Integer getSize() {
        return size == null || size < 1 || size > 100 ? 1 : size;
    }
}
