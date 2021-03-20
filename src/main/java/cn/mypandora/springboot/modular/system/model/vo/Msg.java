package cn.mypandora.springboot.modular.system.model.vo;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cn.mypandora.springboot.core.enums.MessageTypeEnum;
import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.util.CustomLocalDateTimeDeserializer;
import cn.mypandora.springboot.core.util.CustomLocalDateTimeSerializer;
import cn.mypandora.springboot.core.validate.AddGroup;
import cn.mypandora.springboot.core.validate.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hankaibo
 * @date 2020/6/10
 */
@Data
public class Msg {

    /**
     * 站内信主键id
     */
    @ApiModelProperty(value = "站内信主键id")
    @Positive(groups = {UpdateGroup.class}, message = "站内信主键id必须为正整数")
    @NotNull(groups = {UpdateGroup.class}, message = "站内信主键id不能为空")
    private Long id;

    /**
     * 发信人ID
     */
    @ApiModelProperty(value = "发信人主键id")
    private Long sendId;

    /**
     * 发信人姓名
     */
    @ApiModelProperty(value = "发信人姓名")
    private String sendName;

    /**
     * 收信人ID
     */
    @ApiModelProperty(value = "收信人主键ids")
    @NotEmpty(groups = {AddGroup.class, UpdateGroup.class}, message = "收信人主键id不能为空")
    private List<Long> receiveIdList;

    /**
     * 收信人姓名
     */
    @ApiModelProperty(value = "收信人姓名")
    @Transient
    private List<String> receiveNameList;

    /**
     * 站内信标题
     */
    @ApiModelProperty(value = "站内信标题")
    @NotBlank(groups = {AddGroup.class, UpdateGroup.class}, message = "站内信标题不能为空")
    @Size(min = 1, max = 128, groups = {AddGroup.class, UpdateGroup.class}, message = "站内信标题最大128个字符")
    private String title;

    /**
     * 站内信内容
     */
    @ApiModelProperty(value = "站内信内容")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "站内信内容最大255个字符")
    private String content;

    /**
     * 站内信类型
     */
    @ApiModelProperty(value = "站内信类型")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "站内信类型不可为空")
    private MessageTypeEnum type;

    /**
     * 站内信状态
     */
    @ApiModelProperty(value = "站内信状态")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "站内信状态不可为空")
    private StatusEnum status;

    /**
     * 是否发布
     */
    @ApiModelProperty(value = "是否发布")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "站内信发布状态不可为空")
    @Range(min = 0, max = 1, groups = {AddGroup.class, UpdateGroup.class}, message = "站内信发布状态状态可选值为0或者1")
    private Integer isPublish;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    @Future(groups = {AddGroup.class, UpdateGroup.class}, message = "站内信发岸上时间不能是过去时间")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime publishTime;

    /**
     * 是否已读
     */
    @ApiModelProperty(value = "是否已读")
    private Boolean isRead;

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
