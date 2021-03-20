package cn.mypandora.springboot.modular.system.model.po;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.ibatis.type.JdbcType;

import cn.mypandora.springboot.core.enums.ResourceTypeEnum;
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
 * Resource
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@ApiModel("资源实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_resource")
@NameStyle(Style.camelhumpAndLowercase)
public class Resource extends BaseTree {

    private static final long serialVersionUID = -2978029310184453966L;

    /**
     * 资源编码
     */
    @ApiModelProperty(value = "资源编码")
    @NotBlank(groups = {AddGroup.class, UpdateGroup.class}, message = "{resource.code.notBlank}")
    @Size(min = 1, max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "{role.code.size}")
    private String code;

    /**
     * 状态
     */
    @ApiModelProperty(value = "资源状态")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "{resource.status.notNull}")
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private StatusEnum status;

    /**
     * URI
     */
    @ApiModelProperty(value = "资源URI")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "{resource.description.size}")
    private String uri;

    /**
     * 类型
     */
    @ApiModelProperty(value = "资源类型")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "{resource.status.notNull}")
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private ResourceTypeEnum type;

    /**
     * 方法
     */
    private String method;

    /**
     * 图标
     */
    @ApiModelProperty(value = "资源图标")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "{resource.description.size}")
    private String icon;

    /**
     * 描述
     */
    @ApiModelProperty(value = "资源描述")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "{resource.description.size}")
    private String description;

    /**
     * 冗余字段，方便转换显示，不存数据库。
     */
    @ApiModelProperty(hidden = true)
    @Transient
    private String needRoles;

}
