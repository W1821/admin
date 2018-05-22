package com.psj.core.bo.system;

import com.psj.pojo.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 区域表
 *
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "area")
public class AreaModel extends BaseBean {

    /**
     * ID,6位数字
     */
    @Id
    private Integer id;
    /**
     * 省级ID
     */
    private Integer provinceId;
    /**
     * 市级ID
     */
    private Integer cityId;
    /**
     * 县级ID
     */
    private Integer countyId;
    /**
     * 名称,最多15个字符
     */
    private String name;
    /**
     * 省级名称,最多15个字符
     */
    private String provinceName;
    /**
     * 市级名称,最多15个字符
     */
    private String cityName;
    /**
     * 县级名称,最多15个字符
     */
    private String countyName;
    /**
     * 电话区号,最多4个字符
     */
    private String telAreaCode;
    /**
     * 邮政编码,最多5个字符
     */
    private String postalCode;
    /**
     * 级别,SHENG:省级;SHI:市级;XIAN:县级
     */
    private String level;
    /**
     * 简拼,最多15个字符
     */
    private String shortName;
    /**
     * 启用状态,QY:启用;TY:停用
     */
    private String status;


}



