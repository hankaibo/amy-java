package cn.mypandora.springboot.modular.system.model.po;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import cn.mypandora.springboot.core.validate.AddGroup;
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
    @NotBlank(message = "用户名称不能为空")
    private String username;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /**
     * 真实名称
     */
    @ApiModelProperty(value = "用户真实姓名")
    private String realName;

    /**
     * 密码
     */
    @ApiModelProperty(hidden = true)
    @NotBlank(groups = {AddGroup.class}, message = "密码不能为空")
    private String password;

    /**
     * 盐
     */
    @ApiModelProperty(hidden = true)
    private String salt;

    /**
     * 状态
     */
    @ApiModelProperty(value = "用户状态,1表示开启")
    @Range(min = 0, max = 1)
    private Integer status;

    /**
     * 头像
     */
    @ApiModelProperty(value = "用户头像地址")
    private String avatar;

    /**
     * Email
     */
    @ApiModelProperty(value = "用户邮箱")
    @Email
    private String email;

    /**
     * 座机
     */
    @ApiModelProperty(value = "用户电话号码")
    private String phone;

    /**
     * 电话
     */
    @ApiModelProperty(value = "用户手机号码")
    private String mobile;

    /**
     * 性别
     */
    @ApiModelProperty(value = "用户性别")
    private Byte sex;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "用户最近登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * 用户所在部门主键ID 方便转换显示，不存数据库
     */
    @ApiModelProperty(value = "用户部门id")
    @Transient
    private List<Long> departmentIdList;

}
