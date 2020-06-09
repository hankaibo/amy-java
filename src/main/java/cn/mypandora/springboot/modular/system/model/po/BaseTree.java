package cn.mypandora.springboot.modular.system.model.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import cn.mypandora.springboot.core.validate.AddGroup;
import cn.mypandora.springboot.core.validate.UpdateGroup;
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
    @NotBlank(groups = {AddGroup.class, UpdateGroup.class}, message = "名称不能为空")
    @Size(min = 1, max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "名称长度请在1至255字符之间")
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
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "父节点不能为空")
    @Positive(groups = {AddGroup.class, UpdateGroup.class}, message = "父节点必须为正整数")
    protected Long parentId;

    /**
     * 可更新（1可更新，0不可更新）
     */
    @ApiModelProperty(hidden = true)
    protected Integer isUpdate;

}
