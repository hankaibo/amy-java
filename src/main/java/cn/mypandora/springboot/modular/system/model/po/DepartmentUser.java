package cn.mypandora.springboot.modular.system.model.po;

import javax.persistence.Table;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * DepartmentUser
 *
 * @author hankaibo
 * @date 2019/9/25
 */
@Data
@Table(name = "sys_department_user")
@NameStyle(Style.camelhumpAndLowercase)
public class DepartmentUser extends BaseEntity {

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 用户id
     */
    private Long userId;

}
