package cn.mypandora.springboot.modular.system.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Account
 *
 * @author hankaibo
 * @date 2019/6/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    private static final long serialVersionUID = 2483676971124711127L;

    private String appId;
    private String password;
    private String salt;
}
