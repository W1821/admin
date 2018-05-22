package com.psj.common.annotation;

import java.lang.annotation.*;

/**
 * 菜单权限注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface MenuAuth {

    /**
     * 是否验证登陆 true=验证 ,false = 不验证
     */
    boolean verifyLogin() default true;

    /**
     * 是否验证URL true=验证 ,false = 不验证
     */
    boolean verifyURL() default true;

}