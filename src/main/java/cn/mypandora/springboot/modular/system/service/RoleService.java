package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.po.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * RoleService
 *
 * @author hankaibo
 * @date 2019/6/14
 */
public interface RoleService {

    /**
     * 根据分页参数查询角色。
     *
     * @param pageNum  当前页码
     * @param pageSize 当前页数
     * @param role     角色条件
     * @return 角色列表
     */
    PageInfo<Role> selectRolePage(int pageNum, int pageSize, Role role);

    /**
     * 查询所有的角色。
     *
     * @return 所有角色
     */
    List<Role> selectRoleList();


    /**
     * 根据角色Id或者名称查询角色。
     *
     * @param id   角色id
     * @param name 角色名称
     * @return 角色信息
     */
    Role selectRoleByIdOrName(Long id, String name);

    /**
     * 新增角色。
     *
     * @param role 角色
     */
    void addRole(Role role);

    /**
     * 删除角色。
     *
     * @param id 角色id
     */
    void deleteRole(Long id);

    /**
     * 批量删除角色。
     *
     * @param ids '1,2,3,4'
     */
    void deleteBatchRole(String ids);

    /**
     * 更新角色。
     *
     * @param role 角色
     */
    void updateRole(Role role);

    /**
     * 启用|禁用角色。 1：开启；0：禁用。
     *
     * @param id     角色id
     * @param status 启用(1),禁用(0)
     * @return 是否成功
     */
    boolean enableRole(Long id, Integer status);

    /**
     * 查询该角色所包含的所有资源。
     *
     * @param id 角色主键id
     * @return 所有资源数据
     */
    List<Resource> selectResourceById(Long id);

    /**
     * 赋予角色某资源。
     *
     * @param roleId         角色Id
     * @param resourceListId 资源Id集合
     * @return 成功or失败
     */
    boolean giveRoleResource(Long roleId, Long[] resourceListId);

}
