package cn.mypandora.springboot.modular.system.model.po;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.ibatis.type.JdbcType;

import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.validate.AddGroup;
import cn.mypandora.springboot.core.validate.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.ColumnType;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_department")
@NameStyle(Style.camelhumpAndLowercase)
public class Department extends BaseTree {

    private static final long serialVersionUID = 1722519175716048416L;

    /**
     * 状态
     */
    @ApiModelProperty(value = "部门状态")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "{department.status.notNull}")
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private StatusEnum status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "部门描述")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "{department.description.size}")
    private String description;

}
