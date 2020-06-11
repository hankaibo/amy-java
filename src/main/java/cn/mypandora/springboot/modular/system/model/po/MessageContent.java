package cn.mypandora.springboot.modular.system.model.po;

import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * MessageContent
 *
 * @author hankaibo
 * @date 2019/10/26
 */
@ApiModel("站内信内容")
@Data
@Table(name = "sys_message_content")
@NameStyle(Style.camelhumpAndLowercase)
public class MessageContent extends BaseEntity {

    /**
     * 站内信标题
     */
    @ApiModelProperty(value = "站内信标题")
    private String title;

    /**
     * 站内信内容
     */
    @ApiModelProperty(value = "站内信内容")
    private String content;

    /**
     * 站内信类型
     */
    @ApiModelProperty(value = "站内信类型")
    private Integer type;

}
