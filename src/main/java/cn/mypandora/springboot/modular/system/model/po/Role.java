package cn.mypandora.springboot.modular.system.model.po;

import cn.mypandora.springboot.modular.system.model.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * Role
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Data
@Table(name = "sys_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 6446663192497654471L;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 角色描述
     */
    private String description;

}
