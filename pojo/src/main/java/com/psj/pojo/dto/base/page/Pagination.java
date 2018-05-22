package com.psj.pojo.dto.base.page;

import com.psj.pojo.BaseBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>返回前端分页数据<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
@NoArgsConstructor
@Getter
@Setter
public class Pagination extends BaseBean {

    private int number;
    private int size;
    private int numberOfElements;
    private boolean hasContent;
    private Sort sort;
    private boolean isFirst;
    private boolean isLast;
    private boolean hasNext;
    private boolean hasPrevious;
    private int totalPages;
    private long totalElements;

    private List content;

    public Pagination(Page page) {
        this.number = page.getNumber();
        this.size = page.getSize();
        this.numberOfElements = page.getNumberOfElements();
        this.hasContent = page.hasContent();
        this.sort = page.getSort();
        this.isFirst = page.isFirst();
        this.isLast = page.isLast();
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }


}
