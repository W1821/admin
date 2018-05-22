package com.psj.core.service.system;

import com.psj.common.constant.GlobalCodeEnum;
import com.psj.common.constant.system.UserEnum;
import com.psj.common.util.ResponseMessageUtil;
import com.psj.core.bo.system.UserBO;
import com.psj.core.service.BaseService;
import com.psj.pojo.dto.base.message.ResponseMessage;
import com.psj.pojo.dto.system.user.UserDTO;
import com.psj.pojo.po.system.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class LoginService extends BaseService {

    /**
     * 登录
     *
     * @return 用户信息
     */
    public ResponseMessage login() {
        log.debug("-----------------登录开始----------------");
        UserModel user = getUserDetail();
        // 帐号被禁用
        if (user.getAccountStatus() == null || user.getAccountStatus() == UserEnum.ACCOUNT_STATUS.DISABLE.getKey()) {
            log.debug("-----------------用户帐号禁用----------------");
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1011);

        }

        UserBO userBO = UserBO.builder().model(user).build();
        log.debug("-----------------登录结束----------------");
        return ResponseMessageUtil.success(userBO.poToDto());
    }

}
