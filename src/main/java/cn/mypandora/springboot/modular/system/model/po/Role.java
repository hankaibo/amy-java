package cn.mypandora.springboot.modular.system.model.po;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

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
@Data
@Table(name = "sys_role")
@NameStyle(Style.camelhumpAndLowercase)
public class Role extends BaseTree {

    private static final long serialVersionUID = 6446663192497654471L;

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    @NotBlank
    private String code;

    /**
     * 状态
     */
    @ApiModelProperty(value = "角色状态")
    @PositiveOrZero
    private Integer status;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String description;

}
