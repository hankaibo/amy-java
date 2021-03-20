package cn.mypandora.springboot.modular.system.model.po;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.JdbcType;

import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.validate.AddGroup;
import cn.mypandora.springboot.core.validate.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * Message
 *
 * @author hankaibo
 * @date 2020/4/21
 */
@ApiModel("收件箱")
@Data
@Table(name = "sys_message_receiver")
@NameStyle(Style.camelhumpAndLowercase)
public class MessageReceiver extends BaseEntity {

    /**
     * 发信人ID
     */
    @ApiModelProperty(value = "发信人主键id")
    private Long sendId;

    /**
     * 发信人姓名
     */
    @ApiModelProperty(value = "发信人姓名")
    @Transient
    private String sendName;

    /**
     * 收信人ID
     */
    @ApiModelProperty(value = "收信人主键id")
    private Long receiveId;

    /**
     * 收信人姓名
     */
    @ApiModelProperty(value = "收信人姓名")
    @Transient
    private String receiveName;

    /**
     * 站内信内容ID
     */
    @ApiModelProperty(value = "站内信内容主键id")
    private Long contentId;

    /**
     * 站内信状态
     */
    @ApiModelProperty(value = "站内信状态")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "{messageReceive.status.notNull}")
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private StatusEnum status;

    /**
     * 是否已读
     */
    @ApiModelProperty(value = "是否已读")
    private Boolean isRead;
}
