package cn.mypandora.springbootdemo.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Role
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Data
@Table(name = "sys_role")
public class Role {
    /**
     * 角色ID
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
     * 角色名
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色编码
     */
    @Column(name = "role_code")
    private String roleCode;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色拥有的权限
     */
    @Transient
    private List<Permission> permissionList;
}
