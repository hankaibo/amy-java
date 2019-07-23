package cn.mypandora.springboot.modular.system.model.po;

import cn.mypandora.springboot.modular.system.model.BaseEntity;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;

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
    private Long roleId;

    /**
     * 资源ID
     */
    private Long resourceId;

}
