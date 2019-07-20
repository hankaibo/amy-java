package cn.mypandora.springboot.core.utils;

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
            if (treeNode.getParentId() == null) {
                result.add(findChildren(treeNode, treeNodeList));
            }
        }
        return result;
    }

    private static TreeNode findChildren(TreeNode treeNode, List<TreeNode> treeNodeList) {
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
