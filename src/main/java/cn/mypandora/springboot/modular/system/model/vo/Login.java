package cn.mypandora.springboot.modular.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login
 *
 * @author hankaibo
 * @date 2019/10/26
 */
@ApiModel(value = "登录对象", description = "用户登录页面对象。")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称", required = true)
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码", required = true)
    private String password;
}
