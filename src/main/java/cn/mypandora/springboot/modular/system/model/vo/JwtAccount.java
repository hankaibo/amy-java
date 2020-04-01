package cn.mypandora.springboot.modular.system.model.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JwtAccount
 *
 * @author hankaibo
 * @date 2019/6/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAccount implements Serializable {
    private static final long serialVersionUID = 7206625063123589636L;

    private String tokenId;
    private String appId;
    private String issuer;
    private Date issuedAt;
    private String audience;
    private Long userId;
    /**
     * 角色。 与shiro的roles属性相对应
     */
    private String roles;
    private String resourceCodes;
    private String host;

}
