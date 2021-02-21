package cn.mypandora.springboot.modular.system.model.po;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import cn.mypandora.springboot.core.validate.AddGroup;
import cn.mypandora.springboot.core.validate.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * Role
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@ApiModel("角色实体")
@Data
@Table(name = "sys_role")
@NameStyle(Style.camelhumpAndLowercase)
public class Role extends BaseTree {

    private static final long serialVersionUID = 6446663192497654471L;

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    @NotBlank(groups = {AddGroup.class, UpdateGroup.class}, message = "{role.code.notBlank}")
    @Size(min = 1, max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "{role.code.size}")
    private String code;

    /**
     * 状态
     */
    @ApiModelProperty(value = "角色状态")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "{role.status.notNull}")
    @Range(min = 0, max = 1, groups = {AddGroup.class, UpdateGroup.class}, message = "{role.status.range}")
    private Integer status;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "{role.description.size}")
    private String description;

}
