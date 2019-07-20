package cn.mypandora.springboot.modular.system.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TreeNode
 *
 * @author hankaibo
 * @date 2019/7/17
 * @see <a href="https://ant.design/components/tree-cn/#components-tree-demo-directory">tree</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {

    /**
     * 节点id
     */
    private Long id;

    /**
     * 冗余字段，适配ant design tree组件。
     */
    private Long key;

    /**
     * tree-select 默认根据此属性值进行筛选（其值在整个树范围内唯一）
     */
    private String value;

    /**
     * 标题
     */
    private String title;

    /**
     * 设置为叶子节点(设置了loadData时有效)
     */
    private Boolean isLeaf;

    /**
     * 设置节点是否可被选中
     */
    private Boolean selectable;

    /**
     * 当树为 checkable 时，设置独立节点是否展示 Checkbox
     */
    private Boolean checkable;

    /**
     * 禁掉 checkbox
     */
    private Boolean disableCheckbox;

    /**
     * 禁掉响应
     */
    private Boolean disable;

    /**
     * 自定义图标。
     * 需要前台根据icon名称构造。
     */
    private String icon;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 子节点
     */
    private List<TreeNode> children;

    /**
     * 状态
     * 冗余字段，列表显示
     */
    private Integer status;

    /**
     * URI
     */
    private String uri;

    /**
     * 方法
     */
    private String method;

    /**
     * 描述
     */
    private String description;

}
