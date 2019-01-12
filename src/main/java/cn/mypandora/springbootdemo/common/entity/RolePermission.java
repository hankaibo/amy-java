package cn.mypandora.springbootdemo.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * RolePermission
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Data
@Table(name = "sys_role_permission")
public class RolePermission {
    /**
     * 角色权限关系ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 插入时间
     */
    @Column(name = "insert_time")
    private Date insertTime;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 权限ID
     */
    @Column(name = "permission_id")
    private Long permissionId;
}
