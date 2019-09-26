package cn.mypandora.springboot.core.util;

import cn.mypandora.springboot.modular.system.model.po.Department;
import cn.mypandora.springboot.modular.system.model.po.Resource;
import cn.mypandora.springboot.modular.system.model.vo.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * TreeUtil
 *
 * @author hankaibo
 * @date 2019/7/17
 * @see <a href="https://blog.csdn.net/MassiveStars/article/details/53911620"> more</a>
 */
public class TreeUtil {

    /**
     * 将左右节点数据转换为父子节点数据。
     *
     * @param resourceList 左右节点数据
     * @return 父子节点数据(children无数据)
     */
    public static List<TreeNode> lr2Node(List<Resource> resourceList) {
        List<TreeNode> treeNodeList = new ArrayList<>();

        for (Resource resource : resourceList) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(resource.getId());
            treeNode.setKey(resource.getId());
            treeNode.setValue(resource.getId().toString());
            treeNode.setTitle(resource.getName());
            treeNode.setParentId(resource.getParentId());
            // 列表字段
            treeNode.setStatus(resource.getStatus());
            treeNode.setUri(resource.getUri());
            treeNode.setCode(resource.getCode());
            treeNode.setMethod(resource.getMethod());
            treeNode.setDescription(resource.getDescription());
            treeNodeList.add(treeNode);
        }

        return treeNodeList;
    }

    /**
     * 将左右节点数据转换为父子节点树数据。
     *
     * @param resourceList 左右节点数据
     * @return 父子节点树数据(children有数据)
     */
    public static List<TreeNode> lr2Tree(List<Resource> resourceList) {
        List<TreeNode> result = new ArrayList<>();
        List<TreeNode> treeNodeList = lr2Node(resourceList);
        for (TreeNode treeNode : treeNodeList) {
            // 因为设置了数据库主键不能为空，所以这里用0代替null进行判断。
            if (treeNode.getParentId() == 0) {
                result.add(findChildren(treeNode, treeNodeList));
            }
        }
        return result;
    }


    /**
     * 将部门数据转换为父子节点树数据。
     *
     * @param departmentList
     * @return
     */
    public static List<TreeNode> department2Tree(List<Department> departmentList) {
        List<TreeNode> result = new ArrayList<>();
        List<TreeNode> treeNodeList = department2Node(departmentList);
        for (TreeNode treeNode : treeNodeList) {
            if (treeNode.getParentId() == null) {
                result.add(findChildren(treeNode, treeNodeList));
            }
        }
        return result;
    }

    /**
     * 将左右节点数据转换为父子节点数据。
     *
     * @param departmentList 左右节点数据
     * @return 父子节点数据(children无数据)
     */
    public static List<TreeNode> department2Node(List<Department> departmentList) {
        List<TreeNode> treeNodeList = new ArrayList<>();

        for (Department department : departmentList) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(department.getId());
            treeNode.setKey(department.getId());
            treeNode.setValue(department.getId().toString());
            treeNode.setTitle(department.getName());
            treeNode.setParentId(department.getParentId());
            // 列表字段
            treeNode.setStatus(department.getStatus());
            treeNode.setDescription(department.getDescription());
            treeNodeList.add(treeNode);
        }

        return treeNodeList;
    }

    public static TreeNode findChildren(TreeNode treeNode, List<TreeNode> treeNodeList) {
        for (TreeNode it : treeNodeList) {
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodeList));
            }
        }
        return treeNode;
    }

}
