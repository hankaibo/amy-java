package cn.mypandora.springboot.core.utils;

import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;
import java.sql.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * JsonWebTokenUtil
 *
 * @author hankaibo
 * @date 2019/6/18
 */
@NoArgsConstructor
public class JsonWebTokenUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final int COUNT_3 = 3;
    private static CompressionCodecResolver codecResolver = new DefaultCompressionCodecResolver();
    private static Key KEY = generalKey();

    /**
     * 创建jwt.
     *
     * @param id          令牌id
     * @param issuer      header中该JWT的签发者
     * @param subject     header中该JWT所面向的用户
     *                    audience    header中接收该JWT的一方
     * @param period      有效时间（毫秒），分解为以下两个
     *                    iat         header中(issued at) 在什么时候签发的
     *                    exp         header中(expires)  什么时候过期，这里是一个Unix时间戳
     * @param roles       payload中的角色信息
     * @param permissions payload中的权限信息
     * @param algorithm   Signature中的签名算法
     * @return jws
     */
    public static String createJwt(String id, String subject, String issuer, Long period, String roles, String permissions, SignatureAlgorithm algorithm) {
        Long currentTimeMillis = System.currentTimeMillis();

        JwtBuilder jwtBuilder = Jwts.builder();
        // 设置header相关信息
        if (StringUtils.isNotEmpty(id)) {
            jwtBuilder.setId(id);
        }
        if (StringUtils.isNotEmpty(issuer)) {
            jwtBuilder.setIssuer(issuer);
        }
        if (StringUtils.isNotEmpty(subject)) {
            jwtBuilder.setSubject(subject);
        }
        jwtBuilder.setIssuedAt(new Date(currentTimeMillis));
        if (null != period) {
            jwtBuilder.setExpiration(new Date(currentTimeMillis + period * 1000));
        }
        // 设置payload相关信息
        if (StringUtils.isNotEmpty(roles)) {
            jwtBuilder.claim("roles", roles);
        }
        if (StringUtils.isNotEmpty(permissions)) {
            jwtBuilder.claim("perms", permissions);
        }
        jwtBuilder.compressWith(CompressionCodecs.GZIP);
        jwtBuilder.signWith(generalKey());

        return jwtBuilder.compact();
    }

    /**
     * 解析jwt.
     *
     * @param jwt 签发的jwt
     * @return JwtAccount
     */
    public static JwtAccount parseJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(jwt)
                .getBody();
        JwtAccount jwtAccount = new JwtAccount();
        //令牌ID
        jwtAccount.setTokenId(claims.getId());
        // 签发者
        jwtAccount.setIssuer(claims.getIssuer());
        // 客户标识
        jwtAccount.setAppId(claims.getSubject());
        // 签发时间
        jwtAccount.setIssuedAt(claims.getIssuedAt());
        // 接收方
        jwtAccount.setAudience(claims.getAudience());
        // 访问主张-角色
        jwtAccount.setRoles(claims.get("roles", String.class));
        // 访问主张-权限
        jwtAccount.setPerms(claims.get("perms", String.class));
        return jwtAccount;
    }

    /**
     * @param jwt
     * @return
     */
    public static String parseJwtPayload(String jwt) {
        Assert.hasText(jwt, "JWT String argument cannot be null or empty.");

        String[] arr = jwt.split("\\.");
        if (arr.length != COUNT_3) {
            String msg = "JWT strings must contain exactly 2 period characters. Found: " + arr.length;
            throw new MalformedJwtException(msg);
        }

        String base64UrlEncodedHeader = arr[0];
        String base64UrlEncodedPayload = arr[1];
        String base64UrlEncodedSignature = arr[2];

        if (base64UrlEncodedPayload == null) {
            String msg = "JWT string '" + jwt + "' is missing a body/payload.";
            throw new MalformedJwtException(msg);
        }
        // Header
        Header header = null;
        CompressionCodec compressionCodec = null;
        if (base64UrlEncodedHeader != null) {
            String origValue = TextCodec.BASE64URL.decodeToString(base64UrlEncodedHeader);
            Map<String, Object> m = readValue(origValue);
            if (base64UrlEncodedSignature != null) {
                header = new DefaultJwsHeader(m);
            } else {
                header = new DefaultHeader(m);
            }
            compressionCodec = codecResolver.resolveCompressionCodec(header);
        }
        // Body
        String payload;
        if (compressionCodec != null) {
            byte[] decompressed = compressionCodec.decompress(TextCodec.BASE64URL.decode(base64UrlEncodedPayload));
            payload = new String(decompressed, Strings.UTF_8);
        } else {
            payload = TextCodec.BASE64URL.decodeToString(base64UrlEncodedPayload);
        }
        return payload;
    }

    /**
     * description 从json数据中读取格式化map
     *
     * @param val 1
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> readValue(String val) {
        try {
            return MAPPER.readValue(val, Map.class);
        } catch (IOException e) {
            throw new MalformedJwtException("Unable to read JSON value: " + val, e);
        }
    }

    /**
     * 分割字符串进SET
     */
    @SuppressWarnings("unchecked")
    public static Set<String> split(String str) {
        Set<String> set = new HashSet<>();
        if (StringUtils.isEmpty(str)) {
            return set;
        }
        set.addAll(CollectionUtils.arrayToList(str.split(",")));
        return set;
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    private static SecretKey generalKey() {
        if (KEY == null) {
            KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
        return (SecretKey) KEY;
    }

}
