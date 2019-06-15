package cn.mypandora.springboot.core.utils;

import cn.mypandora.springboot.modular.system.model.JwtAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import io.jsonwebtoken.lang.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.*;

/**
 * JsonWebTokenUtil
 *
 * @author hankaibo
 * @date 2019/1/15
 */
public class JsonWebTokenUtil {

    public static final String SECRET_KEY = "?::4343fdf4fdf6cvf):";
    public static final ObjectMapper MAPPER = new ObjectMapper();
    private static CompressionCodecResolver compressionCodecResolver = new DefaultCompressionCodecResolver();

    private JsonWebTokenUtil() {
    }

    public static String issueJWT(String id, String subject, String issuer, Long period, String roles, String permissions, SignatureAlgorithm algorithm) {
        Long currentTimeMillis = System.currentTimeMillis();

        byte[] secreKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        JwtBuilder jwtBuilder = Jwts.builder();
        if (!StringUtils.isEmpty(id)) {
            jwtBuilder.setId(id);
        }
        if (!StringUtils.isEmpty(subject)) {
            jwtBuilder.setSubject(subject);
        }
        if (!StringUtils.isEmpty(issuer)) {
            jwtBuilder.setIssuer(issuer);
        }
        // 设置签发时间
        jwtBuilder.setIssuedAt(new Date(currentTimeMillis));
        // 设置到期时间
        if (null != period) {
            jwtBuilder.setExpiration(new Date(currentTimeMillis + period * 1000));
        }
        if (!StringUtils.isEmpty(roles)) {
            jwtBuilder.claim("roles", roles);
        }
        if (!StringUtils.isEmpty(permissions)) {
            jwtBuilder.claim("perms", permissions);
        }
        //
        jwtBuilder.compressWith(CompressionCodecs.DEFLATE);
        //
        jwtBuilder.signWith(algorithm, secreKeyBytes);
        return jwtBuilder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwt jwt字符串
     * @return
     */
    public static String parseJwtPayload(String jwt) {
        String base64UrlEncodedHeader = null;
        String base64UrlEncodedPayload = null;
        String base64UrlEncodedDigest = null;
        int delimiterCount = 0;
        StringBuilder sb = new StringBuilder(128);
        for (char c : jwt.toCharArray()) {
            if (c == '.') {
                CharSequence tokenSeq = io.jsonwebtoken.lang.Strings.clean(sb);
                String token = tokenSeq != null ? tokenSeq.toString() : null;

                if (delimiterCount == 0) {
                    base64UrlEncodedHeader = token;
                } else if (delimiterCount == 1) {
                    base64UrlEncodedPayload = token;
                }
                delimiterCount++;
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        if (delimiterCount != 2) {
            String msg = "JWT strings must contain exactly 2 period characters. Found: " + delimiterCount;
            throw new MalformedJwtException(msg);
        }
        if (sb.length() > 0) {
            base64UrlEncodedDigest = sb.toString();
        }
        if (base64UrlEncodedPayload == null) {
            throw new MalformedJwtException("JWT string '" + jwt + "' is missing a body/payload.");
        }
        // header
        Header header = null;
        CompressionCodec compressionCodec = null;
        if (base64UrlEncodedHeader != null) {
            String origValue = TextCodec.BASE64URL.decodeToString(base64UrlEncodedHeader);
            Map<String, Object> map = readValue(origValue);
            if (base64UrlEncodedDigest != null) {
                header = new DefaultJwsHeader(map);
            } else {
                header = new DefaultHeader(map);
            }
            compressionCodec = compressionCodecResolver.resolveCompressionCodec(header);
        }
        // body
        String payload;
        if (compressionCodec != null) {
            byte[] decompressed = compressionCodec.decompress(TextCodec.BASE64URL.decode(base64UrlEncodedPayload));
            payload = new String(decompressed, Strings.UTF_8);
        } else {
            payload = TextCodec.BASE64URL.decodeToString(base64UrlEncodedPayload);
        }
        return payload;
    }

    public static JwtAccount parseJwt(String jwt, String appKey) {
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(appKey)).parseClaimsJws(jwt).getBody();

        JwtAccount jwtAccount = new JwtAccount();
        jwtAccount.setTokenId(claims.getId());
        jwtAccount.setAppId(claims.getSubject());
        jwtAccount.setIssuer(claims.getIssuer());
        jwtAccount.setIssuedAt(claims.getIssuedAt());
        jwtAccount.setAudience(claims.getAudience());
        jwtAccount.setRoles(claims.get("roles", String.class));
        jwtAccount.setPerms(claims.get("perms", String.class));
        return jwtAccount;
    }

    public static Set<String> split(String str) {
        Set<String> set = new HashSet<>();
        if (StringUtils.isEmpty(str)) {
            return set;
        }
        set.addAll(CollectionUtils.arrayToList(str.split(",")));
        return set;
    }

    public static Map<String, Object> readValue(String origValue) {
        try {
            return MAPPER.readValue(origValue, Map.class);
        } catch (IOException e) {
            throw new MalformedJwtException("Unable to read JSON value: " + origValue, e);
        }
    }
}
