package cn.mypandora.springboot.modular.system.model.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DepartmentTree
 *
 * @author hankaibo
 * @date 2019/10/31
 * @see <a href="https://ant.design/components/tree-cn/#components-tree-demo-directory">tree</a>
 */
@ApiModel(value = "ant design tree 组件")
@Data
public class DepartmentTree extends Tree {

    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点数组", example = "[]")
    private List<DepartmentTree> children;
}
