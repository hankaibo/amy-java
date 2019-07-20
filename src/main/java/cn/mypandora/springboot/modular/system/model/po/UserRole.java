package cn.mypandora.springboot.modular.system.model.po;

import cn.mypandora.springboot.modular.system.model.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * UserRole
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Data
@Table(name = "sys_user_role")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = -8466855028083318498L;

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
