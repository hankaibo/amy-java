package cn.mypandora.springboot.modular.system.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity
 *
 * @author hankaibo
 * @date 2019/7/17
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 3961846886911015485L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    @KeySql(useGeneratedKeys = true)
    @NotNull
    @Id
    protected Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @Column(name = "modify_time")
    private Date modifyTime;

}
