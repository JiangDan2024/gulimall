package com.gulimall.member;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class GulimallMemberApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void passwordTest(){
        //e10adc3949ba59abbe56e057f20f883e
        String s = DigestUtils.md5Hex("123456");
        System.out.println(s);

        //盐值加密
//        String salt = java.util.UUID.randomUUID().toString().replace("-", "");
        //$1$XPRpYFia$UJlVqTvmBmeH9tnqGKHZM
        //$1$6nNaDtUn$RMRKsNVHvp2AkEjWj.fJc1
        //$1$11111111$EvXGwEcR22VZ3QijGuyJ5/
//        String s1 = Md5Crypt.md5Crypt("123456".getBytes(),"$1$11111111");
//        System.out.println(s1);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //$2a$10$3YzcUaM.NDSLgSferuqxEOhx3laOYsVoG2mMn52I7nD21dUCF4FWa
        //$2a$10$g2pP3wP5drBNebWubln85OyN9MlSaQVD.7p0D5qXsURkzlW1CgZuK
        String s2 = encoder.encode("123456");
        boolean matches = encoder.matches("123456", "$2a$10$g2pP3wP5drBNebWubln85OyN9MlSaQVD.7p0D5qXsURkzlW1CgZuK");
        System.out.println(matches);

    }
}
