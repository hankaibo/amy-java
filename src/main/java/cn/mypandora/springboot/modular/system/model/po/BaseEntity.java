package cn.mypandora.springboot.modular.system.model.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cn.mypandora.springboot.core.util.CustomLocalDateTimeDeserializer;
import cn.mypandora.springboot.core.util.CustomLocalDateTimeSerializer;
import cn.mypandora.springboot.core.validate.AddGroup;
import cn.mypandora.springboot.core.validate.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.IdentityDialect;
import tk.mybatis.mapper.code.Style;

/**
 * BaseEntity
 * 
 * <p>
 * 基类，所有 PO 实体都继承它。
 * </p>
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
    @Positive(groups = {AddGroup.class, UpdateGroup.class}, message = "主键必须为正整数")
    @NotNull(groups = {UpdateGroup.class}, message = "主键不能为空")
    @KeySql(dialect = IdentityDialect.MYSQL)
    @Id
    protected Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden = true)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

}
