package cn.mypandora.springboot.modular.system.model.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ResourceTree
 *
 * @author hankaibo
 * @date 2019/7/17
 * @see <a href="https://ant.design/components/tree-cn/">tree</a>
 */
@ApiModel(value = "ant design tree 组件")
@Data
public class ResourceTree extends Tree {

    /**
     * URI
     */
    @ApiModelProperty(value = "url")
    private String uri;

    /**
     * 编码（标识符）
     */
    @ApiModelProperty(value = "code")
    private String code;

    /**
     * 方法
     */
    @ApiModelProperty(value = "方法")
    private String method;

    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点数组")
    private List<ResourceTree> children;

}
