package cn.mypandora.springboot.modular.system.model.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * BaseTree
 *
 * @author hankaibo
 * @date 2019/7/17
 * @see <a href="https://www.iteye.com/topic/602979">hierarchical-data</a>
 * @see <a href="https://www.jianshu.com/p/38179b0c816f">hierarchical-data</a>
 */
@Data
@NameStyle(Style.camelhumpAndLowercase)
public abstract class BaseTree extends BaseEntity {

    private static final long serialVersionUID = -548775816575400157L;

    /**
     * 节点名称
     */
    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    protected String name;

    /**
     * 节点左值
     */
    @ApiModelProperty(hidden = true)
    @Positive(message = "节点必须为数值")
    protected Integer lft;

    /**
     * 节点右值
     */
    @ApiModelProperty(hidden = true)
    @Positive(message = "节点必须为数值")
    protected Integer rgt;

    /**
     * 层级
     */
    @ApiModelProperty(hidden = true)
    @Positive(message = "层级必须为数值")
    protected Integer level;

    /**
     * 父节点
     */
    @ApiModelProperty(value = "父节点id")
    @Positive
    protected Long parentId;

    /**
     * 可更新（1可更新，0不可更新）
     */
    @ApiModelProperty(hidden = true)
    @Positive(message = "是否可更新必须为数值")
    protected Integer isUpdate;

}
