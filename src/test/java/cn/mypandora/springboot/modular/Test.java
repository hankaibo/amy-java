package cn.mypandora.springboot.modular;

import cn.mypandora.springboot.core.utils.JsonWebTokenUtil;
import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Test
 *
 * @author hankaibo
 * @date 2019/7/16
 */
public class Test {
    public static void main(String[] args) {
        String jwt = JsonWebTokenUtil.createJwt(
                "id",
                "subject",
                "issuer",
                10000L,
                "roles",
                "perms",
                SignatureAlgorithm.HS256
        );
        System.out.println(jwt);

        JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwt);
        System.out.println(jwtAccount.toString());

        String payload = JsonWebTokenUtil.parseJwtPayload(jwt);
        System.out.println(payload);
    }

}
