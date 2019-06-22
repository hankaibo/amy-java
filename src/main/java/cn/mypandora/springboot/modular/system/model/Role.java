package cn.mypandora.springboot.modular.system.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;

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
    @KeySql(useGeneratedKeys = true)
    private Long id;

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
    private Integer state;

    /**
     * 角色描述
     */
    private String description;

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
}
