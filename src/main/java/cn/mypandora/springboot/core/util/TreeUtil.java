package cn.mypandora.springboot.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.mypandora.springboot.modular.system.model.po.Department;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.po.Role;
import cn.mypandora.springboot.modular.system.model.vo.DepartmentTree;
import cn.mypandora.springboot.modular.system.model.vo.ResourceTree;
import cn.mypandora.springboot.modular.system.model.vo.RoleTree;

/**
 * TreeUtil
 *
 * @author hankaibo
 * @date 2019/7/17
 * @see <a href="https://blog.csdn.net/MassiveStars/article/details/53911620"> more</a>
 * @see <a href="https://stackoverflow.com/questions/18017869/build-tree-array-from-flat-array-in-javascript"> more</a>
 */
public class TreeUtil {

    /**
     * 将资源数据转换为树。
     *
     * @param resourceList
     *            资源列表
     * @return 资源树
     */
    public static List<ResourceTree> resource2Tree(List<Resource> resourceList) {
        List<ResourceTree> roots = new ArrayList<>();
        // 1. 转换数据类型并将不存在的父id置空
        List<ResourceTree> fragmentTree = new ArrayList<>();
        for (Resource resource : resourceList) {
            ResourceTree resourceTree = new ResourceTree();
            resourceTree.setId(resource.getId());
            resourceTree.setKey(resource.getId().toString());
            resourceTree.setValue(resource.getId().toString());
            resourceTree.setTitle(resource.getName());
            resourceTree.setDisabled(resource.getStatus() == 0);
            resourceTree.setParentId(resource.getParentId());
            // 列表字段
            resourceTree.setStatus(resource.getStatus());
            resourceTree.setUri(resource.getUri());
            resourceTree.setCode(resource.getCode());
            resourceTree.setMethod(resource.getMethod());
            resourceTree.setDescription(resource.getDescription());
            fragmentTree.add(resourceTree);
        }
        fragmentTree.stream().filter(item -> {
            if (fragmentTree.stream().noneMatch(it -> it.getId().equals(item.getParentId()))) {
                item.setParentId(null);
            }
            return true;
        }).collect(Collectors.toList());
        // 2.
        ResourceTree node;
        Map<Long, Integer> map = new HashMap<>(fragmentTree.size());
        int i;
        for (i = 0; i < fragmentTree.size(); i += 1) {
            map.put(fragmentTree.get(i).getId(), i);
            fragmentTree.get(i).setChildren(new ArrayList<>());
        }
        for (i = 0; i < fragmentTree.size(); i += 1) {
            node = fragmentTree.get(i);
            if (node.getParentId() != null) {
                // if you have dangling branches check that map[node.parentId] exists
                fragmentTree.get(map.get(node.getParentId())).getChildren().add(node);
            } else {
                roots.add(node);
            }
        }
        return roots;
    }

    /**
     * 将部门数据转换为树。
     *
     * @param departmentList
     *            部门列表
     * @return 部门树
     */
    public static List<DepartmentTree> department2Tree(List<Department> departmentList) {
        List<DepartmentTree> roots = new ArrayList<>();
        // 1.
        List<DepartmentTree> fragmentTree = new ArrayList<>();
        for (Department department : departmentList) {
            DepartmentTree departmentTree = new DepartmentTree();
            departmentTree.setId(department.getId());
            departmentTree.setKey(department.getId().toString());
            departmentTree.setValue(department.getId().toString());
            departmentTree.setTitle(department.getName());
            departmentTree.setDisabled(department.getStatus() == 0);
            departmentTree.setParentId(department.getParentId());
            // 列表字段
            departmentTree.setStatus(department.getStatus());
            departmentTree.setDescription(department.getDescription());
            fragmentTree.add(departmentTree);
        }
        fragmentTree.stream().filter(item -> {
            if (fragmentTree.stream().noneMatch(it -> it.getId().equals(item.getParentId()))) {
                item.setParentId(null);
            }
            return true;
        }).collect(Collectors.toList());
        // 2.
        DepartmentTree node;
        Map<Long, Integer> map = new HashMap<>(fragmentTree.size());
        int i;
        for (i = 0; i < fragmentTree.size(); i += 1) {
            map.put(fragmentTree.get(i).getId(), i);
            fragmentTree.get(i).setChildren(new ArrayList<>());
        }
        for (i = 0; i < fragmentTree.size(); i += 1) {
            node = fragmentTree.get(i);
            if (node.getParentId() != null) {
                // if you have dangling branches check that map[node.parentId] exists
                fragmentTree.get(map.get(node.getParentId())).getChildren().add(node);
            } else {
                roots.add(node);
            }
        }
        return roots;
    }

    /**
     * 将角色数据转换为树。
     *
     * @param roleList
     *            角色列表
     * @return 角色树
     */
    public static List<RoleTree> role2Tree(List<Role> roleList) {
        List<RoleTree> roots = new ArrayList<>();
        // 1.
        List<RoleTree> fragmentTree = new ArrayList<>();
        for (Role role : roleList) {
            RoleTree roleTree = new RoleTree();
            roleTree.setId(role.getId());
            roleTree.setKey(role.getId().toString());
            roleTree.setValue(role.getId().toString());
            roleTree.setTitle(role.getName());
            roleTree.setDisabled(role.getStatus() == 0);
            roleTree.setParentId(role.getParentId());
            // 列表字段
            roleTree.setCode(role.getCode());
            roleTree.setStatus(role.getStatus());
            roleTree.setDescription(role.getDescription());
            fragmentTree.add(roleTree);
        }
        fragmentTree.stream().filter(item -> {
            if (fragmentTree.stream().noneMatch(it -> it.getId().equals(item.getParentId()))) {
                item.setParentId(null);
            }
            return true;
        }).collect(Collectors.toList());
        // 2.
        RoleTree node;
        Map<Long, Integer> map = new HashMap<>(fragmentTree.size());
        int i;
        for (i = 0; i < fragmentTree.size(); i += 1) {
            map.put(fragmentTree.get(i).getId(), i);
            fragmentTree.get(i).setChildren(new ArrayList<>());
        }
        for (i = 0; i < fragmentTree.size(); i += 1) {
            node = fragmentTree.get(i);
            if (node.getParentId() != null && map.get(node.getParentId()) != null) {
                // if you have dangling branches check that map[node.parentId] exists
                fragmentTree.get(map.get(node.getParentId())).getChildren().add(node);
            } else {
                roots.add(node);
            }
        }
        return roots;
    }

}
