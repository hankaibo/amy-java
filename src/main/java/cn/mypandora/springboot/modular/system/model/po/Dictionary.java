package cn.mypandora.springboot.modular.system.model.po;

import cn.mypandora.springboot.modular.system.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;

/**
 * Dictionary
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@ApiModel(value = "字典对象", description = "字典信息")
@Data
@Table(name = "sys_dict")
@NameStyle(Style.camelhumpAndLowercase)
public class Dictionary extends BaseEntity {

    private static final long serialVersionUID = -2023943502971950908L;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "字典父级id")
    private Long parentId;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String name;

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码")
    private String code;

    /**
     * 状态 1:开启，0:禁用
     */
    @ApiModelProperty(value = "字典状态")
    private Integer status;

    /**
     * 排序
     */
    @ApiModelProperty(value = "字典顺序")
    private Integer sort;

    /**
     * 字典描述
     */
    @ApiModelProperty(value = "字典描述")
    private String description;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "字典创建人id")
    private Long createUser;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "字典修改人id")
    private Long modifyUser;

}
