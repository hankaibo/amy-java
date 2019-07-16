package cn.mypandora.springboot.modular.system.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;

/**
 * Resource
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Data
@Table(name = "sys_resource")
public class Resource {

    /**
     * 资源ID
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源编码
     */
    private String code;

    /**
     * 状态
     */
    private Integer state;

    /**
     * URI
     */
    private String uri;

    /**
     * 类型
     */
    private Short type;

    /**
     * 方法
     */
    private String method;

    /**
     * 图标
     */
    private String icon;

    /**
     * 描述
     */
    private String description;

    /**
     * 父Id
     */
    @Column(name = "parent_id")
    private Long parentId;

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
