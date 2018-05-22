package com.psj.core.service.system;

import com.psj.common.constant.GlobalEnum;
import com.psj.common.util.ResponseMessageUtil;
import com.psj.core.bo.system.RoleBO;
import com.psj.core.bo.system.UserBO;
import com.psj.core.dao.repository.system.MenuRepository;
import com.psj.core.dao.repository.system.RoleRepository;
import com.psj.core.service.BaseService;
import com.psj.pojo.dto.base.message.ResponseMessage;
import com.psj.pojo.dto.base.page.Pagination;
import com.psj.pojo.dto.system.role.RoleDTO;
import com.psj.pojo.dto.system.role.RoleSearchDTO;
import com.psj.pojo.po.system.RoleModel;
import com.psj.pojo.po.system.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class RoleService extends BaseService {

    private final RoleRepository roleRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, MenuRepository menuRepository) {
        this.roleRepository = roleRepository;
        this.menuRepository = menuRepository;
    }

    /**
     * 查询所有
     */
    public ResponseMessage list(RoleSearchDTO dto) {
        // 分页查询
        Page<RoleModel> pageList = roleRepository.findAll(RoleBO.createSpecification(dto), getPageable(dto));
        // 处理数据返回到前端
        Pagination pagination = new Pagination(pageList);
        pagination.setContent(pageList.stream().map(this::poToDto).collect(Collectors.toList()));
        return ResponseMessageUtil.success(pagination);
    }

    /**
     * 查询一个
     */
    public ResponseMessage query(Long id) {
        RoleBO bo = RoleBO.builder().repository(roleRepository).dto(new RoleDTO(id)).build();
        return bo.queryOne();
    }

    /**
     * 保存
     */
    public ResponseMessage save(RoleDTO dto) {
        RoleBO bo = RoleBO.builder()
                .repository(roleRepository)
                .menuRepository(menuRepository)
                .dto(dto).build();
        return bo.save();
    }

    /**
     * 删除
     */
    public ResponseMessage delete(Long id) {
        RoleBO bo = RoleBO.builder().repository(roleRepository).dto(new RoleDTO(id)).build();
        return bo.delete();
    }


    /**
     * 当前登陆人的角色
     */
    public ResponseMessage getRoleList() {

        // 当前登陆人
        UserModel model = getUserDetail();

        List<RoleModel> roleList;
        UserBO userBO = UserBO.builder().model(model).build();
        // 如果是超级管理员,查询所有角色
        if (userBO.userIsSuperAdmin()) {
            roleList = roleRepository.findByDeleted(GlobalEnum.DELETED.NO.getKey());
        } else {
            roleList = model.getRoles();
        }
        // 返回DTO
        List<RoleDTO> roleDTOList = roleList.stream().map(this::poToDto).collect(Collectors.toList());
        return ResponseMessageUtil.success(roleDTOList);
    }

    /**
     * 数据库对象转dto对象
     */
    private RoleDTO poToDto(RoleModel model) {
        return RoleBO.builder().model(model).build().poToDto();
    }

}
