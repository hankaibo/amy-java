package cn.mypandora.springboot.modular.system.model.po;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * BaseTree
 * <p>
 * 左右值树(预排序遍历树)的基类。
 * </p>
 *
 * @author hankaibo
 * @date 2019/7/17
 * @see <a href="https://www.iteye.com/topic/602979">hierarchical-data</a>
 */
@Data
@NameStyle(Style.camelhumpAndLowercase)
public abstract class BaseTree extends BaseEntity {

    private static final long serialVersionUID = -548775816575400157L;

    /**
     * 节点名称
     */
    @ApiModelProperty(value = "名称")
    @NotNull(message = "名称不能为空")
    @Size(min = 1, max = 255)
    protected String name;

    /**
     * 节点左值
     */
    @ApiModelProperty(hidden = true)
    protected Integer lft;

    /**
     * 节点右值
     */
    @ApiModelProperty(hidden = true)
    protected Integer rgt;

    /**
     * 层级
     */
    @ApiModelProperty(hidden = true)
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
    protected Integer isUpdate;

}
