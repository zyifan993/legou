package com.zyf.auth.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateCrtKey;
import java.util.HashMap;
import java.util.Map;

public class jwtTest {

    /**
     * 使用私钥生成令牌
     */
    @Test
    public void testCreateJwt() throws Exception{
        //创建秘钥的工厂对象
        String key_location = "mickey.jks";
        //秘钥库密码
        String keystore_password = "mickey";
        //访问证书路径
        ClassPathResource resource = new ClassPathResource(key_location);
        //秘钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource,keystore_password.toCharArray());
        //秘钥的密码，此密码要与别名匹配
        String keypassword = "mickey";
        //秘钥别名
        String alias = "mickey";
        //秘钥对(秘钥和公钥)
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias,keypassword.toCharArray());
        //私钥
        RSAPrivateCrtKey aPrivate = (RSAPrivateCrtKey) keyPair.getPrivate();
        //定义payload信息
        Map<String,Object> tokenMap = new HashMap<>();
        tokenMap.put("user_name","admin");
        tokenMap.put("client_id","client");
        tokenMap.put("authorities",new String[]{"ROLE_ADMIN"});

        //生成jwt令牌
        Jwt jwt = JwtHelper.encode(new ObjectMapper().writeValueAsString(tokenMap),new RsaSigner(aPrivate));

        //取出jwt令牌
        String token = jwt.getEncoded();
        System.out.println(token);
    }

    @Test
    public void testVerify(){
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6InIwMSwgcjAyLCBhZG1pbiIsIm5hbWUiOiJtaWNrZXkiLCJpZCI6MTIzfQ.LQpmt7c63yQJHS4ngbdnSWmDH0xD68ZMq1NBGBmQkThIMtOOye9goU78CsE_AEN5YNyED0ANqHR8mUZxV4tlaIbevk3O9C0_jhY2sE2F9ai1qNoQadjlbaVXTqdR7XI1m_SnrAvLtw-ra6dIphaqlK-DZOmP_v4PULuhnHyhvUqg5DEhlSh52RIQcKCnkN4LS0B-_hfOyY1u1qe7zvNz2TfGhOl0aVH10ZiGAwSxMY4VMAbzd-bzox-6kimRVzfPJm0JgMiDbky7wPg3mkvq-N73rVTw_6HHmhP2DCzRKAh5xrBCO_KpeqtcKcKMchv03-i8T5f-Bq-NvMbgdWrC9A";
        //公钥
        String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgfjRYBO6tsIRke5id40G\n" +
                "jfUfyOGzqSQbmakATomTT2sjThqMY2gPjsZz93Z5P+GaQpqJyC4Ewqh4o0VmvoMd\n" +
                "f3VMsTLO1zeZI+qpiTDzUeVM93x0/TD7cAZFQXF0I9DcdQ19tmFBRWvfUMsDogqC\n" +
                "ILbGRiKNKq+5eU+iGSCwjINni5iywRub7bKWztKD31Qwwc9A2TPOOIJ2OPMzVZ/7\n" +
                "qi0itlMPmiq5s+lWJ1ldV+TbcbDGlBKlRE6hO6uvn4a2ntJPdOkxMjIFNE4AArQU\n" +
                "FBI0Bi75uh3cQQP+1vX2j9Z0oFs35J489q5J3syBW8qgLOx6KW7b68DoR1UE/mXl\n" +
                "OwIDAQAB\n" +
                "-----END PUBLIC KEY-----";
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        String claims = jwt.getClaims();
        System.out.println(claims);
    }
}
