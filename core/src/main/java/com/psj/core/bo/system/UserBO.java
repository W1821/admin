package com.psj.core.bo.system;

import com.google.common.collect.Lists;
import com.psj.common.constant.GlobalEnum;
import com.psj.common.constant.system.UserEnum;
import com.psj.common.util.ExceptionUtil;
import com.psj.common.util.ResponseMessageUtil;
import com.psj.core.bo.BaseBO;
import com.psj.core.dao.repository.system.RoleRepository;
import com.psj.core.dao.repository.system.UserRepository;
import com.psj.pojo.dto.base.message.ResponseMessage;
import com.psj.pojo.dto.system.role.BaseRoleDTO;
import com.psj.pojo.dto.system.user.UserDTO;
import com.psj.pojo.dto.system.user.UserSearchDTO;
import com.psj.pojo.po.system.RoleModel;
import com.psj.pojo.po.system.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@AllArgsConstructor
@Getter
@ToString
@Builder
public class UserBO extends BaseBO {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    private UserDTO dto;
    private UserModel model;


    @Override
    public ResponseMessage queryOne() {
        model = repository.findById(dto.getId()).orElse(null);
        if (model == null) {
            return ResponseMessageUtil.idError();
        }
        poToDto();
        return ResponseMessageUtil.success(dto);
    }

    @Override
    public ResponseMessage save() {
        if (dto.getId() == null) {
            // 新增
            dtoToPo();
        } else {
            update();
        }
        // 更新人员和角色的关系
        List<Long> roleIds = dto.getRoles().stream().map(BaseRoleDTO::getId).collect(Collectors.toList());
        List<RoleModel> roles = roleRepository.findByIdIn(roleIds);
        model.setRoles(roles);
        // 保存数据库
        repository.save(model);
        return ResponseMessageUtil.saveSuccess();
    }

    @Override
    public ResponseMessage delete() {
        model = repository.findById(dto.getId()).orElse(null);
        if (model == null) {
            return ResponseMessageUtil.idError();
        }
        model.setDeleted(GlobalEnum.DELETED.YES.getKey());
        model.setUpdateTime(new Date());
        repository.save(model);
        return ResponseMessageUtil.deleteSuccess();
    }

    @Override
    public UserDTO poToDto() {
        if (dto == null) {
            dto = UserDTO.builder().build();
        }
        dto.setId(model.getId());
        dto.setPhoneNumber(model.getPhoneNumber());
        dto.setPassword(model.getPassword());
        dto.setUserName(model.getUserName());
        dto.setHeadPictureUrl(model.getHeadPictureUrl());
        dto.setAccountType(model.getAccountType());
        dto.setAccountStatus(model.getAccountStatus());
        dto.setCreateTime(model.getCreateTime());
        dto.setAreaId(model.getAreaId());
        dto.setAreaName(model.getAreaName());
        dto.setAddress(model.getAddress());
        // 用户角色信息
        List<BaseRoleDTO> roles = model.getRoles().stream()
                .filter(e -> RoleBO.builder().model(e).build().unDeleted())
                .map(BaseRoleDTO::new)
                .collect(Collectors.toList());
        dto.setRoles(roles);
        return dto;
    }

    @Override
    public UserModel dtoToPo() {
        model = new UserModel();
        model.setId(dto.getId());
        // 基本信息
        model.setUserName(dto.getUserName());
        model.setPassword(passwordEncoder.encode(dto.getPassword()));
        model.setPhoneNumber(dto.getPhoneNumber());
        model.setHeadPictureUrl(dto.getHeadPictureUrl());
        // 区域信息
        model.setAreaId(dto.getAreaId());
        model.setAreaName(dto.getAreaName());
        model.setAddress(dto.getAddress());
        // 状态信息
        model.setAccountType(dto.getAccountType());
        model.setAccountStatus(UserEnum.ACCOUNT_STATUS.ENABLE.getKey());
        model.setSuperAdmin(UserEnum.SUPER_ADMIN.NO.getKey());
        model.setDeleted(GlobalEnum.DELETED.NO.getKey());
        // 创建时间
        model.setCreateTime(new Date());
        return model;
    }


    /**
     * 创建JPA动态查询条件
     */
    public static Specification<UserModel> createSpecification(UserSearchDTO dto) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (!StringUtils.isEmpty(dto.getUserName())) {
                predicates.add(cb.like(root.get("userName"), "%" + dto.getUserName()));
            }
            predicates.add(cb.equal(root.get("deleted"), GlobalEnum.DELETED.NO.getKey()));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /**
     * 判断是否是超级管理员
     */
    public boolean userIsSuperAdmin() {
        return model.getSuperAdmin() != null && model.getSuperAdmin().equals(UserEnum.SUPER_ADMIN.YES.getKey());
    }

    /* ====================================================================================================== */

    /**
     * 更新
     */
    private void update() {
        model = repository.findById(dto.getId()).orElse(null);
        if (model == null) {
            ExceptionUtil.throwIdError();
        }
        model.setUserName(dto.getUserName());
        model.setPassword(passwordEncoder.encode(dto.getPassword()));
        model.setPhoneNumber(dto.getPhoneNumber());
        model.setHeadPictureUrl(dto.getHeadPictureUrl());
        model.setAreaId(dto.getAreaId());
        model.setAreaName(dto.getAreaName());
        model.setAddress(dto.getAddress());
        model.setAccountStatus(dto.getAccountStatus());
        model.setUpdateTime(new Date());
    }

}
