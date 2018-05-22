package com.psj.core.service.system;

import com.psj.common.constant.GlobalCodeEnum.ErrorCode;
import com.psj.common.constant.GlobalEnum;
import com.psj.common.constant.system.UserEnum;
import com.psj.common.util.FileUtil;
import com.psj.common.util.ResponseMessageUtil;
import com.psj.config.file.FileUploadConfig;
import com.psj.core.bo.system.UserBO;
import com.psj.core.dao.repository.system.RoleRepository;
import com.psj.core.dao.repository.system.UserRepository;
import com.psj.core.service.BaseService;
import com.psj.pojo.dto.base.message.ResponseMessage;
import com.psj.pojo.dto.base.page.Pagination;
import com.psj.pojo.dto.system.user.ModifyPwdDTO;
import com.psj.pojo.dto.system.user.UserDTO;
import com.psj.pojo.dto.system.user.UserSearchDTO;
import com.psj.pojo.po.system.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author Administrator
 */
@Service
public class UserService extends BaseService {

    private final FileUploadConfig fileUploadConfig;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(FileUploadConfig fileUploadConfig, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.fileUploadConfig = fileUploadConfig;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 查询所有
     */
    public ResponseMessage list(UserSearchDTO dto) {
        // 分页查询
        Page<UserModel> pageList = userRepository.findAll(UserBO.createSpecification(dto), getPageable(dto));
        // 处理数据返回到前端
        Pagination pagination = new Pagination(pageList);
        pagination.setContent(pageList.stream().map(this::poToDto).collect(Collectors.toList()));
        return ResponseMessageUtil.success(pagination);
    }

    /**
     * 查询一个
     */
    public ResponseMessage query(Long id) {
        UserBO userBO = UserBO.builder().repository(userRepository).dto(UserDTO.builder().id(id).build()).build();
        return userBO.queryOne();
    }

    /**
     * 保存
     */
    public ResponseMessage save(UserDTO dto) {
        // 判断手机号码是否有重复
        if (checkPhoneNumber(dto)) {
            return ResponseMessageUtil.error(ErrorCode.ERROR_1200);
        }
        // 判断是否有图片
        if (FileUtil.checkIsBase64Image(dto.getHeadPictureBase64())) {
            String imageUrl = FileUtil.writeImageBase64ToFile(dto.getHeadPictureBase64(), fileUploadConfig.getRootPath(), fileUploadConfig.getImageUrlPrefix());
            dto.setHeadPictureUrl(imageUrl);
        }
        UserBO userBO = UserBO.builder()
                .repository(userRepository)
                .passwordEncoder(passwordEncoder)
                .roleRepository(roleRepository)
                .dto(dto)
                .build();
        return userBO.save();
    }

    /**
     * 删除
     */
    public ResponseMessage delete(Long id) {
        UserBO userBO = UserBO.builder().repository(userRepository).dto(UserDTO.builder().id(id).build()).build();
        return userBO.delete();
    }


    /**
     * 检查手机号码是否存在
     */
    public ResponseMessage checkNumber(String phoneNumber) {
        UserModel model = findByPhoneNumber(phoneNumber);
        if (model != null) {
            return ResponseMessageUtil.error(ErrorCode.ERROR_1200);
        }
        return ResponseMessageUtil.success();
    }

    /**
     * 修改密码
     */
    public ResponseMessage modifyOwnPassword(ModifyPwdDTO dto, String phoneNumber) {
        UserModel user = findByPhoneNumber(phoneNumber);
        if (!passwordEncoder.matches(dto.getOldPwd(), user.getPassword())) {
            return ResponseMessageUtil.error(ErrorCode.ERROR_1051);
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPwd()));
        userRepository.save(user);
        return ResponseMessageUtil.saveSuccess();
    }

    /**
     * 根据手机号查询
     */
    public UserModel findByPhoneNumber(String userName) {
        return userRepository.findByPhoneNumberAndDeleted(userName, GlobalEnum.DELETED.NO.getKey());
    }

    /**
     * 查询一个系统超级管理员
     *
     * @return 系统用户
     */
    public UserModel getOneSuperAdmin() {
        Page<UserModel> pageList = userRepository.findBySuperAdmin(UserEnum.SUPER_ADMIN.YES.getKey(), getPageable());
        return pageList.getContent().get(0);
    }

    /* ================================================ 私有方法 =================================================*/

    /**
     * 判断手机号码是否有重复
     */
    private boolean checkPhoneNumber(UserDTO dto) {
        UserModel userModel = findByPhoneNumber(dto.getPhoneNumber());
        if (userModel == null) {
            return false;
        }
        // 新增
        if (dto.getId() == null) {
            return true;
        }
        // 修改
        return !userModel.getId().equals(dto.getId());

    }

    private UserDTO poToDto(UserModel model) {
        return UserBO.builder().model(model).build().poToDto();
    }


}
