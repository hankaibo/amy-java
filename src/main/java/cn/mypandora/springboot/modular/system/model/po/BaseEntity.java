package cn.mypandora.springboot.modular.system.model.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.IdentityDialect;
import tk.mybatis.mapper.code.Style;

/**
 * BaseEntity
 *
 * @author hankaibo
 * @date 2019/7/17
 */
@Data
@NameStyle(Style.camelhumpAndLowercase)
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 3961846886911015485L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    @KeySql(dialect = IdentityDialect.MYSQL)
    @PositiveOrZero
    @Id
    protected Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime updateTime;

}
