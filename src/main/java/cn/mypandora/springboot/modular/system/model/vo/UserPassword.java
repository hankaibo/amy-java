package cn.mypandora.springboot.modular.system.model.vo;

import lombok.Data;

/**
 * @author hankaibo
 * @date 2020/5/28
 */
@Data
public class UserPassword {

    private String newPassword;

    private String oldPassword;
}
