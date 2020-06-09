package cn.mypandora.springboot.modular.system.model.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author hankaibo
 * @date 2020/5/30
 */
@Data
public class RoleGrant {

    /**
     * 新添加的资源id
     */
    @NotNull(message = "添加资源主键列表不能为null")
    private Long[] plusResourceIds;

    /**
     * 要删除的资源id
     */
    @NotNull(message = "删除资源主键列表不能为null")
    private Long[] minusResourceIds;
}
