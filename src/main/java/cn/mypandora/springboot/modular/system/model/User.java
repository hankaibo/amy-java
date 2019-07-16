package cn.mypandora.springboot.modular.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
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
public class User {

    /**
     * ID
     */
    @ApiModelProperty(value = "用户id")
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

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
    private Integer state;

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
     * 创建时间
     */
    @ApiModelProperty(value = "用户创建时间", example = "1970-01-01:08:00:00")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "用户修改时间", example = "1970-01-01:08:00:00")
    @Column(name = "modify_time")
    private Date modifyTime;

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
