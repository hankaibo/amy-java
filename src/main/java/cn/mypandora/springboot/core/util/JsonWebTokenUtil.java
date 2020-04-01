package cn.mypandora.springboot.core.util;

import java.io.IOException;
import java.sql.Date;
import java.util.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.mypandora.springboot.config.exception.BusinessException;
import cn.mypandora.springboot.modular.system.model.vo.JwtAccount;
import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Assert;
import lombok.NoArgsConstructor;

/**
 * JsonWebTokenUtil
 *
 * @author hankaibo
 * @date 2019/6/18
 * @see <a href="http://www.conyli.cc/archives/2617" />
 */
@NoArgsConstructor
public class JsonWebTokenUtil {
    private static final int BEARER_TOKEN_LENGTH = 2;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 创建jwt.
     *
     * @param id
     *            令牌id
     * @param issuer
     *            header中该JWT的签发者
     * @param subject
     *            header中该JWT所面向的用户 audience header中接收该JWT的一方
     * @param period
     *            有效时间（毫秒），分解为以下两个 iat header中(issued at) 在什么时候签发的 exp header中(expires) 什么时候过期，这里是一个Unix时间戳
     * @param userId
     *            payload中的用户id
     * @param roleIds
     *            payload中的角色信息
     * @param roleCodes
     *            payload中的角色信息
     * @param resourceCodes
     *            payload中的资源信息
     * @return jwt
     */
    public static String createJwt(String id, String issuer, String subject, Long period, Long userId, String roleIds,
        String roleCodes, String resourceCodes) {
        long currentTimeMillis = System.currentTimeMillis();

        JwtBuilder jwtBuilder = Jwts.builder();
        // 设置 Registered Claim 信息
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
        // 设置 Custom Claim 信息
        if (userId != null) {
            jwtBuilder.claim("userId", userId);
        }
        if (StringUtils.isNotEmpty(roleIds)) {
            jwtBuilder.claim("roleIds", roleIds);
        }
        if (StringUtils.isNotEmpty(roleCodes)) {
            jwtBuilder.claim("roleCodes", roleCodes);
        }
        if (StringUtils.isNotEmpty(resourceCodes)) {
            jwtBuilder.claim("resourceCodes", resourceCodes);
        }
        // 使用密钥进行签名
        // signWith(key)会让jjwt自动根据key的长度选择算法，在计算出签名的同时，会在header中写入alg键值对。
        // signWith(key, SignatureAlgorithm.RS512)可以自行指定算法。
        jwtBuilder.signWith(generalKey());
        jwtBuilder.compressWith(CompressionCodecs.GZIP);

        return jwtBuilder.compact();
    }

    /**
     * 解析jwt.
     *
     * @param jwt
     *            签发的jwt
     * @return JwtAccount
     */
    public static JwtAccount parseJwt(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(jwt).getBody();
        JwtAccount jwtAccount = new JwtAccount();
        // 令牌ID
        jwtAccount.setTokenId(claims.getId());
        // 签发者
        jwtAccount.setIssuer(claims.getIssuer());
        // 客户标识
        jwtAccount.setAppId(claims.getSubject());
        // 签发时间
        jwtAccount.setIssuedAt(claims.getIssuedAt());
        // 接收方
        jwtAccount.setAudience(claims.getAudience());
        //
        jwtAccount.setUserId(claims.get("userId", Long.class));
        // 访问主张-角色
        jwtAccount.setRoles(claims.get("roleCodes", String.class));
        // 访问主张-权限
        jwtAccount.setResourceCodes(claims.get("resourceCodes", String.class));
        return jwtAccount;
    }

    /**
     * @param jwt
     * @return payload
     */
    public static String parseJwtPayload(String jwt) {
        Assert.hasText(jwt, "JWT String argument cannot be null or empty.");
        Claims claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(jwt).getBody();
        Map<String, Object> map = new LinkedHashMap<>(7);
        map.put("jti", claims.getId());
        map.put("iss", claims.getIssuer());
        map.put("sub", claims.getSubject());
        map.put("iat", claims.getIssuedAt());
        map.put("exp", claims.getExpiration());
        map.put("roleIds", claims.get("roleIds"));
        map.put("roleCodes", claims.get("roleCodes"));
        map.put("resourceCodes", claims.get("resourceCodes"));

        return JSON.toJSONString(map);
    }

    /**
     * description 从json数据中读取格式化map
     *
     * @param val
     *            1
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
     * 从 Bearer Token 中提取出token。
     *
     * @param token
     *            Bearer token
     * @return token
     */
    public static String unBearer(String token) {
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(HttpServletRequest.class, "token为空");
        }
        String[] arrToken = token.split(" ");
        if (arrToken.length != BEARER_TOKEN_LENGTH) {
            throw new RuntimeException("token格式不对。");
        }
        return arrToken[1];
    }

    /**
     * 由字符串生成加密key
     *
     * @return key
     */
    private static SecretKey generalKey() {
        // 一个字符串，相当于私钥，只有服务端知道
        // 根据指定字符串生成Key，相同字符串生成的Key也相同的，这个字符串至少要有256bit长，推荐长一些，生成的密钥也会变长。
        // 推荐这种做法，每次都会生成同样的一串Key来使用
        String secretString =
            "uC4p(VGW_dNYJ<!RLjg)KF=22N76Pii7j5L9[kY}LFUM5-jSt&xC'}4NBW=9_336k8^z.d\\k-!29Y&8--#<j4dqT4g}<C";

        // 本地的密码解码
        byte[] encodedKey = Base64.getEncoder().encode(secretString.getBytes());

        // 根据给定的字节数组使用HS512加密算法构造一个密钥
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, SignatureAlgorithm.HS512.getJcaName());
    }

}
