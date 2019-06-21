package cn.mypandora.springboot.modular.system.service.impl;

import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ResourceServiceImpl
 *
 * @author hankaibo
 * @date 2019/1/15
 */
@Service("ResourceService")
public class ResourceServiceImpl implements ResourceService {
    @Override
    public List<Resource> selectMenusByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Resource> selecMenus() {
        return null;
    }

    @Override
    public Boolean addMenu(Resource menu) {
        return null;
    }

    @Override
    public Boolean updateMenu(Resource menu) {
        return null;
    }

    @Override
    public Boolean deleteMenuByMenuId(Long menuId) {
        return null;
    }

    @Override
    public List<Resource> selectApiTeamList() {
        return null;
    }

    @Override
    public List<Resource> selectApiList() {
        return null;
    }

    @Override
    public List<Resource> selectApiListByTeamId(Long teamId) {
        return null;
    }

    @Override
    public List<Resource> selectApisByRoleId(Long roleId) {
        return null;
    }

    @Override
    public List<Resource> selectMenusByRoleId(Long roleId) {
        return null;
    }

    @Override
    public List<Resource> selectNotApisByRoleId(Long roleId) {
        return null;
    }

    @Override
    public List<Resource> selectNotMenusByRoleId(Long roleId) {
        return null;
    }
}
