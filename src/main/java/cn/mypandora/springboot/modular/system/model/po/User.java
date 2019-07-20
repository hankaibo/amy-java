package cn.mypandora.springboot.modular.system.model.po;

import cn.mypandora.springboot.modular.system.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * User
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@ApiModel(value = "用户对象", description = "用户信息")
@Data
@Table(name = "sys_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = -8978557733419584026L;

    /**
     * 用户名
     */
    @NotNull
    @ApiModelProperty(value = "用户名称")
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
    @Column(name = "real_name")
    private String realName;

    /**
     * 密码
     */
    @ApiModelProperty(hidden = true)
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
    @ApiModelProperty(value = "用户最近登录时间", example = "1970-01-01:08:00:00")
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 角色列表
     */
    @ApiModelProperty(hidden = true)
    @Transient
    private List<Role> roleList;

    /**
     * 资源列表
     */
    @ApiModelProperty(hidden = true)
    @Transient
    private List<Resource> resourceList;

}
