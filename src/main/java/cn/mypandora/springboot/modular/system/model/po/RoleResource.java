package cn.mypandora.springboot.modular.system.model.po;

import cn.mypandora.springboot.modular.system.model.BaseEntity;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;

/**
 * RoleResource
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Data
@Table(name = "sys_role_resource")
public class RoleResource extends BaseEntity {

    private static final long serialVersionUID = -6210390263936550568L;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 资源ID
     */
    @Column(name = "resource_id")
    private Long resourceId;

}
