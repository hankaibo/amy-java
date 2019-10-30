package cn.mypandora.springboot.modular.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "基于antd组件Tree的数据结构。")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {

    /**
     * 节点id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 冗余字段，适配ant design tree组件。
     */
    @ApiModelProperty(value = "key")
    private String key;

    /**
     * tree-select 默认根据此属性值进行筛选（其值在整个树范围内唯一）
     */
    @ApiModelProperty(value = "value")
    private String value;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 设置为叶子节点(设置了loadData时有效)
     */
    @ApiModelProperty(value = "是否为叶子节点")
    private Boolean isLeaf;

    /**
     * 设置节点是否可被选中
     */
    @ApiModelProperty(value = "节点可选中")
    private Boolean selectable;

    /**
     * 当树为 checkable 时，设置独立节点是否展示 Checkbox
     */
    @ApiModelProperty(value = "字典父级id")
    private Boolean checkable;

    /**
     * 禁掉 checkbox
     */
    @ApiModelProperty(value = "是否禁用该节点checkbox")
    private Boolean disableCheckbox;

    /**
     * 禁掉响应
     */
    @ApiModelProperty(value = "禁止响应")
    private Boolean disable;

    /**
     * 自定义图标。
     * 需要前台根据icon名称构造。
     */
    @ApiModelProperty(value = "自定义图标")
    private String icon;

    /**
     * 父节点id
     */
    @ApiModelProperty(value = "字典父级id")
    private Long parentId;

    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点数组")
    private List<TreeNode> children;

    /**
     * 状态
     * 冗余字段，列表显示
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * URI
     */
    @ApiModelProperty(value = "url")
    private String uri;

    /**
     * 编码（标识符）
     */
    @ApiModelProperty(value = "code")
    private String code;

    /**
     * 方法
     */
    @ApiModelProperty(value = "方法")
    private String method;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

}
