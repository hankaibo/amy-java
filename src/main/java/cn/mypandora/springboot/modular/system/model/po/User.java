package cn.mypandora.springboot.modular.system.model.po;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cn.mypandora.springboot.core.enums.StatusEnum;
import cn.mypandora.springboot.core.util.CustomLocalDateTimeDeserializer;
import cn.mypandora.springboot.core.util.CustomLocalDateTimeSerializer;
import cn.mypandora.springboot.core.validate.AddGroup;
import cn.mypandora.springboot.core.validate.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * User
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@ApiModel("用户实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_user")
@NameStyle(Style.camelhumpAndLowercase)
public class User extends BaseEntity {

    private static final long serialVersionUID = -8978557733419584026L;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名称")
    @NotBlank(groups = {AddGroup.class, UpdateGroup.class}, message = "{user.username.notBlank}")
    @Size(min = 1, max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "{user.username.size}")
    private String username;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "用户昵称")
    @Size(max = 32, groups = {AddGroup.class, UpdateGroup.class}, message = "{user.nickName.size}")
    private String nickname;

    /**
     * 密码
     */
    @ApiModelProperty(hidden = true)
    @NotBlank(groups = {AddGroup.class}, message = "{user.password.notBlank}")
    @Size(min = 6, max = 32, groups = {AddGroup.class}, message = "{user.password.size}")
    private String password;

    /**
     * 盐
     */
    @ApiModelProperty(hidden = true)
    private String salt;

    /**
     * 状态
     */
    @ApiModelProperty(value = "用户状态")
    @NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "{user.status.notNull}")
    private StatusEnum status;

    /**
     * 头像
     */
    @ApiModelProperty(value = "用户头像地址")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class}, message = "{user.avatar.size}")
    private String avatar;

    /**
     * Email
     */
    @ApiModelProperty(value = "用户邮箱")
    @Email(groups = {AddGroup.class, UpdateGroup.class}, message = "{user.email.email}")
    private String email;

    /**
     * 座机
     */
    @ApiModelProperty(value = "用户座机号码")
    @Size(max = 32, groups = {AddGroup.class, UpdateGroup.class}, message = "{user.phone.size}")
    private String phone;

    /**
     * 电话
     */
    @ApiModelProperty(value = "用户手机号码")
    @Size(max = 32, groups = {AddGroup.class, UpdateGroup.class}, message = "{user.mobile.size}")
    private String mobile;

    /**
     * 性别
     */
    @ApiModelProperty(value = "用户性别")
    @Range(min = 0, max = 2, groups = {AddGroup.class, UpdateGroup.class}, message = "{user.sex.range}")
    private Byte sex;

    /**
     * 用户个性签名
     */
    @ApiModelProperty(value = "用户个性签名")
    @Size(max = 255, message = "{user.signature.size}")
    private String signature;

    /**
     * 用户简介
     */
    @ApiModelProperty(value = "用户简介")
    @Size(max = 255, message = "{user.profile.size}")
    private String profile;

    /**
     * 用户街道地址
     */
    @ApiModelProperty(value = "用户地址")
    @Size(max = 255, message = "{user.address.size}")
    private String address;

    /**
     * 用户最近登录IP
     */
    @ApiModelProperty(value = "用户最近登录IP")
    @Size(max = 128, message = "{user.lastLoginIp.size}")
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "用户最近登录时间")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime lastLoginTime;

    /**
     * 用户所在部门主键ID 方便转换显示，不存数据库
     */
    @ApiModelProperty(value = "用户部门id")
    @Transient
    private List<Long> departmentIdList;

}
