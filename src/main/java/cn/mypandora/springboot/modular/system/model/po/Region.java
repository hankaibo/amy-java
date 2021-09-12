package cn.mypandora.springboot.modular.system.model.po;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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
 * Region
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@ApiModel("区域对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_region")
@NameStyle(Style.camelhumpAndLowercase)
public class Region extends BaseTree {

    private static final long serialVersionUID = -2023943502971950908L;

    /**
     * 区域编码
     */
    @ApiModelProperty(value = "区域编码")
    @NotBlank
    private String code;

    /**
     * 区域值
     */
    @ApiModelProperty(value = "区域值")
    @NotBlank
    private String value;

    /**
     * 状态
     */
    @ApiModelProperty(value = "区域状态")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "{region.status.notNull}")
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private StatusEnum status;

    /**
     * 区域描述
     */
    @ApiModelProperty(value = "区域描述")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "{region.description.size}")
    private String description;

}
