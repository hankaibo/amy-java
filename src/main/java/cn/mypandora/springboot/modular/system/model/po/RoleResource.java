package cn.mypandora.springboot.modular.system.model.po;

import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * RoleResource
 *
 * @author hankaibo
 * @date 2019/1/12
 */
@Data
@Table(name = "sys_role_resource")
@NameStyle(Style.camelhumpAndLowercase)
public class RoleResource extends BaseEntity {

    private static final long serialVersionUID = -6210390263936550568L;

    /**
     * 角色ID
     */
    @PositiveOrZero
    private Long roleId;

    /**
     * 资源ID
     */
    @PositiveOrZero
    private Long resourceId;

}
