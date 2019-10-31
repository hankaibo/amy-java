package cn.mypandora.springboot.modular.system.model.po;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

/**
 * UserRole
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Data
@Table(name = "sys_user_role")
@NameStyle(Style.camelhumpAndLowercase)
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = -8466855028083318498L;

    /**
     * 用户ID
     */
    @PositiveOrZero
    private Long userId;

    /**
     * 角色ID
     */
    @PositiveOrZero
    private Long roleId;

}
