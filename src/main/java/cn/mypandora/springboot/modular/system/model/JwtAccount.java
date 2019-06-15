package cn.mypandora.springboot.modular.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * JwtAccount
 *
 * @author hankaibo
 * @date 2019/1/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAccount implements Serializable {
    private String tokenId;
    private String appId;
    private String issuer;
    private Date issuedAt;
    private String audience;
    private String roles;
    private String perms;
    private String host;
}
