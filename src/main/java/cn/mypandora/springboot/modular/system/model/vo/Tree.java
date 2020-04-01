package cn.mypandora.springboot.modular.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Tree
 *
 * @author hankaibo
 * @date 2019/10/31
 */
@ApiModel(value = "ant design tree 组件")
@Data
public class Tree {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", example = "1")
    private Long id;

    /**
     * 冗余字段，适配ant design tree组件。
     */
    @ApiModelProperty(value = "key", example = "1-1")
    private String key;

    /**
     * tree-select 默认根据此属性值进行筛选（其值在整个树范围内唯一）
     */
    @ApiModelProperty(value = "value", example = "1")
    private String value;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", example = "---")
    private String title;

    /**
     * 设置为叶子节点(设置了loadData时有效)
     */
    @ApiModelProperty(value = "设置为叶子节点(设置了loadData时有效)", example = "false")
    private Boolean isLeaf;

    /**
     * 设置节点是否可被选中
     */
    @ApiModelProperty(value = "设置节点是否可被选中", example = "true")
    private Boolean selectable;

    /**
     * 当树为 checkable 时，设置独立节点是否展示 Checkbox
     */
    @ApiModelProperty(value = "当树为 checkable 时，设置独立节点是否展示 Checkbox", example = "-")
    private Boolean checkable;

    /**
     * 禁掉 checkbox
     */
    @ApiModelProperty(value = "禁掉 checkbox", example = "false")
    private Boolean disableCheckbox;

    /**
     * 禁掉响应
     */
    @ApiModelProperty(value = "禁掉响应", example = "false")
    private Boolean disabled;

    /**
     * 自定义图标。 需要前台根据icon名称构造。
     */
    @ApiModelProperty(value = "自定义图标", example = "-")
    private String icon;

    /**
     * 父主键id
     */
    @ApiModelProperty(value = "主键父级id", example = "1")
    private Long parentId;

    /**
     * 状态 冗余字段，列表显示
     */
    @ApiModelProperty(value = "状态(列表显示)", example = "1")
    private Integer status;

    /**
     * 描述 冗余字段，列表显示
     */
    @ApiModelProperty(value = "描述(列表显示)", example = "description")
    private String description;
}
