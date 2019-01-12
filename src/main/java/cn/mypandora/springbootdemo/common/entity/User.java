package cn.mypandora.springbootdemo.common.entity;

import lombok.Data;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
     * 启用状态
     */
    @Column(name = "state_code")
    private Integer stateCode;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

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
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 角色列表
     */
    @Transient
    private List<Role> roleList;

    /**
     * 权限列表
     */
    @Transient
    private List<Permission> permissionList;
}
