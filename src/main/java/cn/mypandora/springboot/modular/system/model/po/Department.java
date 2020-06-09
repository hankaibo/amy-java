package cn.mypandora.springboot.modular.system.model.po;

import javax.persistence.Table;
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
 * Department
 *
 * @author hankaibo
 * @date 2019/9/25
 */
@ApiModel("部门实体")
@Data
@Table(name = "sys_department")
@NameStyle(Style.camelhumpAndLowercase)
public class Department extends BaseTree {

    private static final long serialVersionUID = 1722519175716048416L;

    /**
     * 状态
     */
    @ApiModelProperty(value = "部门状态")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "部门状态值不可为空")
    @Range(min = 0, max = 1, groups = {AddGroup.class, UpdateGroup.class}, message = "部门状态可选值为0或者1")
    private Integer status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "部门描述")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "部门描述最大255字符")
    private String description;

}
