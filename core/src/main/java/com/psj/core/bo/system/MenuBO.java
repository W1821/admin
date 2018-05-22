package com.psj.core.bo.system;

import com.psj.common.constant.GlobalEnum;
import com.psj.common.util.ResponseMessageUtil;
import com.psj.core.bo.BaseBO;
import com.psj.core.dao.repository.system.MenuRepository;
import com.psj.pojo.dto.base.message.ResponseMessage;
import com.psj.pojo.dto.system.menu.MenuDTO;
import com.psj.pojo.po.system.MenuModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * 菜单
 *
 * @author Administrator
 */
@Getter
@ToString
@Builder
public class MenuBO extends BaseBO {

    private MenuRepository repository;
    private MenuDTO dto;
    private MenuModel model;

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
        dtoToPo();
        if (model.getId() == null) {
            model.setDeleted(GlobalEnum.DELETED.NO.getKey());
            model.setCreateTime(new Date());
        } else {
            model.setUpdateTime(new Date());
        }
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
        // 删除子菜单节点
        repository.deleteChildrenMenu(model.getId(), GlobalEnum.DELETED.YES.getKey());
        return ResponseMessageUtil.deleteSuccess();
    }

    @Override
    public MenuDTO poToDto() {
        if (dto == null) {
            dto = MenuDTO.builder().build();
        }
        dto.setId(model.getId());
        dto.setPid(model.getPid());
        dto.setUrl(model.getUrl());
        dto.setName(model.getName());
        dto.setIcon(model.getIcon());
        dto.setRank(model.getRank());
        dto.setActions(model.getActions());
        dto.setRouterUrls(model.getRouterUrls());
        return dto;
    }

    @Override
    public MenuModel dtoToPo() {
        model = new MenuModel();
        model.setId(dto.getId());
        model.setPid(dto.getPid());
        model.setPids(dto.getPids());
        model.setUrl(dto.getUrl());
        model.setName(dto.getName());
        model.setIcon(dto.getIcon());
        model.setRank(dto.getRank());
        model.setActions(dto.getActions());
        model.setRouterUrls(dto.getRouterUrls());
        return model;
    }


}
