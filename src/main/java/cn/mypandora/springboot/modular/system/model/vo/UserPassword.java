package cn.mypandora.springboot.modular.system.model.vo;

import javax.validation.constraints.NotBlank;

import cn.mypandora.springboot.core.validate.ResetPasswordGroup;
import cn.mypandora.springboot.core.validate.UpdatePasswordGroup;
import lombok.Data;

/**
 * @author hankaibo
 * @date 2020/5/28
 */
@Data
public class UserPassword {

    /**
     * 新密码（重置密码、修改密码）
     */
    @NotBlank(groups = {ResetPasswordGroup.class, UpdatePasswordGroup.class}, message = "新密码不能为空")
    private String newPassword;

    /**
     * 旧密码（修改密码)
     */
    @NotBlank(groups = {UpdatePasswordGroup.class}, message = "旧密码不能为空")
    private String oldPassword;
}
