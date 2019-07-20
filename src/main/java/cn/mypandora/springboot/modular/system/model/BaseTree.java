package cn.mypandora.springboot.modular.system.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Max;

/**
 * BaseTree
 *
 * @author hankaibo
 * @date 2019/7/17
 * @see <a href="https://www.iteye.com/topic/602979">hierarchical-data</a>
 */
@Data
public abstract class BaseTree extends BaseEntity {

    private static final long serialVersionUID = -548775816575400157L;

    /**
     * 节点名称
     */
    @ApiModelProperty(value = "名称")
    protected String name;

    /**
     * 节点右值
     */
    protected Long rgt;

    /**
     * 节点左值
     */
    protected Long lft;

    /**
     * 层级
     */
    protected Long level;

    /**
     * 父节点
     */
    @Column(name = "parent_id")
    protected Long parentId;
}
