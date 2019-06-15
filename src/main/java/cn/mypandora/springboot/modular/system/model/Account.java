package cn.mypandora.springboot.modular.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Account
 *
 * @author hankaibo
 * @date 2019/1/15
 */
@Data
@AllArgsConstructor
public class Account implements Serializable {
    private String appId;
    private String password;
    private String salt;
}
