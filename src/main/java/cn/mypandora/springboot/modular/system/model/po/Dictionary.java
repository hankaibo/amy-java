package cn.mypandora.springboot.modular.system.model.po;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Dictionary
 *
 * @author hankaibo
 * @date 2019/6/14
 */
@Data
@Table(name = "sys_dict")
public class Dictionary {
    /**
     * ID
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    /**
     * 父级id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 状态 1:开启，2:禁用
     */
    private Integer state;

    /**
     * 排序
     */
    private String sort;

    /**
     * 字典描述
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

    /**
     * 创建人
     */
    @Column(name = "create_user")
    private Long createUser;

    /**
     * 修改人
     */
    @Column(name = "modify_user")
    private Long modifyUser;
}
