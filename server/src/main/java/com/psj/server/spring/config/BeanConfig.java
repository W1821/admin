package com.psj.server.spring.config;

import com.psj.server.spring.security.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Administrator
 */
@Configuration
public class BeanConfig {

    /**
     * 密码加密、解密bean，此处加入spring容器中，可以通过@Autowired自动注入
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
