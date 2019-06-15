package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.modular.system.model.Resource;

import java.util.List;

/**
 * ResourceService
 *
 * @author hankaibo
 * @date 2019/1/15
 */
public interface ResourceService {

    /**
     * 查询某个用户的所有资源。
     *
     * @param userId 用户Id
     * @return 资源集合
     */
    List<Resource> selectMenusByUserId(Long userId);

    List<Resource> selecMenus();

    Boolean addMenu(Resource menu);

    Boolean updateMenu(Resource menu);

    Boolean deleteMenuByMenuId(Long menuId);

    List<Resource> selectApiTeamList();

    List<Resource> selectApiList();

    List<Resource> selectApiListByTeamId(Long teamId);

    List<Resource> selectApisByRoleId(Long roleId);

    List<Resource> selectMenusByRoleId(Long roleId);

    List<Resource> selectNotApisByRoleId(Long roleId);

    List<Resource> selectNotMenusByRoleId(Long roleId);
}
