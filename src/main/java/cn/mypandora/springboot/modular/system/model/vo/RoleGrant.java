package cn.mypandora.springboot.modular.system.model.vo;

import java.util.List;

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
    @NotNull
    private List<Long> plusResourceIdList;

    /**
     * 要删除的资源id
     */
    @NotNull
    private List<Long> minusResourceIdList;
}
