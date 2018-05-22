package com.psj.core.dao.repository.system;


import com.psj.pojo.po.system.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>
 * <b>功能：</b><br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/6/19 17:10<br>
 *
 * @author Administrator
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>, JpaSpecificationExecutor<UserModel> {

    /**
     * 根据手机号码查询未删除的用户
     *
     * @param userName 手机号码
     * @param deleted  删除状态
     * @return 用户
     */
    UserModel findByPhoneNumberAndDeleted(String userName, Integer deleted);

    /**
     * 查询所有超级管理员或所有非超级管理员
     *
     * @param superAdmin 1：查询所有超级管理员，0：查询所有非超级管理员
     * @return 用户集合
     */
    Page<UserModel> findBySuperAdmin(Integer superAdmin, Pageable pageable);
}
