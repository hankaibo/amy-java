package cn.mypandora.springboot.modular.system.model.po;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * User
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Data
@Table(name = "sys_user")
public class User {
    /**
     * ID
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实名称
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 头像
     */
    private String avatar;

    /**
     * Email
     */
    private String email;

    /**
     * 座机
     */
    private String phone;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 性别
     */
    private Byte sex;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 角色列表
     */
    @Transient
    private List<Role> roleList;

    /**
     * 资源列表
     */
    @Transient
    private List<Resource> resourceList;

    @Transient
    private String token;
}
