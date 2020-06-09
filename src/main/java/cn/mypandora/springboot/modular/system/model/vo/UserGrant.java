package cn.mypandora.springboot.modular.system.model.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author hankaibo
 * @date 2020/5/30
 */
@Data
public class UserGrant {

    /**
     * 新添加的角色id
     */
    @NotNull(message = "添加角色主键列表不能为null")
    private Long[] plusRoleIds;

    /**
     * 要删除的角色id
     */
    @NotNull(message = "删除角色主键列表不能为null")
    private Long[] minusRoleIds;
}
