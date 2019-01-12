package cn.mypandora.springbootdemo.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * UserRole
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Data
@Table(name = "sys_user_role")
public class UserRole {
    /**
     * 用户角色关系ID
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
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;
}
