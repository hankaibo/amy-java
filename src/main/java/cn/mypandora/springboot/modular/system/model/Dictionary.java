package cn.mypandora.springboot.modular.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Dictionary
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@ApiModel(value = "字典对象", description = "字典信息")
@Data
@Table(name = "sys_dict")
public class Dictionary {
    /**
     * ID
     */
    @ApiModelProperty(value = "字典id", example = "101")
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "字典父级id", example = "101")
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称", example = "性别")
    private String name;

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码", example = "code")
    private String code;

    /**
     * 状态 1:开启，0:禁用
     */
    @ApiModelProperty(value = "字典状态", example = "1")
    private Integer state;

    /**
     * 排序
     */
    @ApiModelProperty(value = "字典顺序", example = "1")
    private Integer sort;

    /**
     * 字典描述
     */
    @ApiModelProperty(value = "字典描述", example = "这是用户性别字典码。")
    private String description;


    /**
     * 创建时间
     */
    @ApiModelProperty(value = "字典创建时间", example = "1970-01-01:08:00:00")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "字典修改时间", example = "1970-01-01:08:00:00")
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "字典创建人id", example = "101")
    @Column(name = "create_user")
    private Long createUser;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "字典修改人id", example = "101")
    @Column(name = "modify_user")
    private Long modifyUser;
}
