package cn.mypandora.springboot.modular.system.model.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RoleTree
 *
 * @author hankaibo
 * @date 2019/11/6
 * @see <a href="https://ant.design/components/tree-cn/">tree</a>
 */
@ApiModel(value = "ant design tree 组件")
@Data
public class RoleTree extends Tree {

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    private String code;

    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点数组")
    private List<RoleTree> children;
}
