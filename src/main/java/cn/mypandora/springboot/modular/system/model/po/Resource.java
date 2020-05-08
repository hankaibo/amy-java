package cn.mypandora.springboot.modular.system.model.po;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @NotBlank
    private String code;

    /**
     * 状态
     */
    @Range(min = 0, max = 1)
    private Integer status;

    /**
     * URI
     */
    private String uri;

    /**
     * 类型
     */
    @PositiveOrZero
    private Integer type;

    /**
     * 方法
     */
    private String method;

    /**
     * 图标
     */
    private String icon;

    /**
     * 描述
     */
    private String description;

    /**
     * 冗余字段，方便转换显示，不存数据库。
     */
    @ApiModelProperty(hidden = true)
    @Transient
    private String needRoles;

}
