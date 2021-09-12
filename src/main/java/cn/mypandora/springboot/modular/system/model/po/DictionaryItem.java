package cn.mypandora.springboot.modular.system.model.po;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
 * DictionaryItem
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@ApiModel("字典项对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_dictionary_item")
@NameStyle(Style.camelhumpAndLowercase)
public class DictionaryItem extends BaseEntity {

    /**
     * 字典id
     */
    @ApiModelProperty(value = "字典id")
    @Positive(groups = {AddGroup.class, UpdateGroup.class}, message = "{base.id.positive}")
    protected Long dictionaryId;

    /**
     * 字典项名称
     */
    @ApiModelProperty(value = "字典项名称")
    @NotBlank
    private String name;

    /**
     * 字典项值
     */
    @ApiModelProperty(value = "字典项值")
    @NotBlank
    private String value;

    /**
     * 字典项顺序
     */
    @ApiModelProperty(value = "字典项顺序")
    @NotBlank
    private Integer sort;

    /**
     * 状态
     */
    @ApiModelProperty(value = "字典项状态")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "{dictionaryItem.status.notNull}")
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private StatusEnum status;

    /**
     * 字典项描述
     */
    @ApiModelProperty(value = "字典项描述")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "{dictionaryItem.description.size}")
    private String description;

}
