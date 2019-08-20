package cn.mypandora.springboot.modular.system.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * BaseTree
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
    @NotBlank
    protected String name;

    /**
     * 节点右值
     */
    @Positive
    protected Long rgt;

    /**
     * 节点左值
     */
    @Positive
    protected Long lft;

    /**
     * 层级
     */
    @Positive
    protected Long level;

    /**
     * 父节点
     */
    protected Long parentId;
}
