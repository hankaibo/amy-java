package cn.mypandora.springbootdemo.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Permission
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Data
@Table(name = "sys_permission")
public class Permission {
    /**
     * 权限ID
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
     * 权限名称
     */
    @Column(name = "permission_name")
    private String permissionName;
    /**
     * 权限编码
     */
    @Column(name = "permission_code")
    private String permissionCode;
    /**
     * 权限描述
     */
    private String description;
}
