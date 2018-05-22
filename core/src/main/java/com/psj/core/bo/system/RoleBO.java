package com.psj.core.bo.system;

import com.psj.common.constant.GlobalEnum;
import com.psj.common.constant.system.RoleEnum;
import com.psj.common.util.ExceptionUtil;
import com.psj.common.util.ResponseMessageUtil;
import com.psj.core.bo.BaseBO;
import com.psj.core.dao.repository.system.MenuRepository;
import com.psj.core.dao.repository.system.RoleRepository;
import com.psj.pojo.dto.base.message.ResponseMessage;
import com.psj.pojo.dto.system.role.RoleDTO;
import com.psj.pojo.dto.system.role.RoleSearchDTO;
import com.psj.pojo.po.system.MenuModel;
import com.psj.pojo.po.system.RoleModel;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class RoleBO extends BaseBO {

    private RoleRepository repository;
    private MenuRepository menuRepository;
    private RoleDTO dto;
    private RoleModel model;

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
        // 更新角色和菜单的关系
        setMenus();
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
        model.setUpdateTime(new Date());
        model.setDeleted(GlobalEnum.DELETED.YES.getKey());
        repository.save(model);
        return ResponseMessageUtil.deleteSuccess();
    }

    @Override
    public RoleDTO poToDto() {
        if (dto == null) {
            dto = RoleDTO.builder().build();
        }
        dto.setId(model.getId());
        dto.setRoleName(model.getRoleName());
        dto.setDescription(model.getDescription());
        dto.setRoleStatus(model.getRoleStatus());
        dto.setCreateTime(model.getCreateTime());
        dto.setMenuIds(model.getMenus().stream().map(MenuModel::getId).collect(Collectors.toList()));
        return dto;
    }

    @Override
    public RoleModel dtoToPo() {
        model = new RoleModel();
        model.setId(dto.getId());
        model.setRoleName(dto.getRoleName());
        model.setDescription(dto.getDescription());
        // 默认启用
        model.setRoleStatus(RoleEnum.ROLE_STATUS.ENABLE.getKey());
        // 默认未删除
        model.setDeleted(GlobalEnum.DELETED.NO.getKey());
        model.setCreateTime(new Date());
        return model;
    }

    /**
     * 创建JPA动态查询条件
     */
    public static Specification<RoleModel> createSpecification(RoleSearchDTO dto) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dto.getRoleName() != null && !"".equals(dto.getRoleName())) {
                predicates.add(cb.like(root.get("roleName"), "%" + dto.getRoleName()));
            }
            predicates.add(cb.equal(root.get("deleted"), GlobalEnum.DELETED.NO.getKey()));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /**
     * 判断是否删除
     *
     * @return true:已删除，false:未删除
     */
    public boolean unDeleted() {
        return model.getDeleted() != null && model.getDeleted().equals(GlobalEnum.DELETED.NO.getKey());
    }


    /**
     * 修改
     */
    private void update() {
        model = repository.findById(dto.getId()).orElse(null);
        if (model == null) {
            ExceptionUtil.throwIdError();
        }
        model.setRoleName(dto.getRoleName());
        model.setDescription(dto.getDescription());
        model.setUpdateTime(new Date());
    }

    /**
     * 更新角色和菜单的关系
     */
    private void setMenus() {
        List<MenuModel> menus = menuRepository.findByIdIn(dto.getMenuIds());
        model.setMenus(menus);
    }

}
