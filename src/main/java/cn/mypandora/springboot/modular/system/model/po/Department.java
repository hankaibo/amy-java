package cn.mypandora.springboot.modular.system.model.po;

import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * Department
 *
 * @author hankaibo
 * @date 2019/9/25
 */
@Data
@Table(name = "sys_department")
@NameStyle(Style.camelhumpAndLowercase)
public class Department extends BaseTree {

    private static final long serialVersionUID = 1722519175716048416L;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @PositiveOrZero
    private Integer status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

}
