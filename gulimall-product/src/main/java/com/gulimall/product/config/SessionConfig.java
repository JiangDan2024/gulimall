package com.gulimall.product.config;

import com.gulimall.common.constant.AuthConstants;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson.SecurityJacksonModules;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.FlashMap;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
public class SessionConfig implements BeanClassLoaderAware {
    private ClassLoader loader;

    //redis使用json序列化
    /**
     * Note that the bean name for this bean is intentionally
     * {@code springSessionDefaultRedisSerializer}. It must be named this way to override
     * the default {@link RedisSerializer} used by Spring Session.
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new JacksonJsonRedisSerializer<>(objectMapper(), Object.class);
    }

    /**
     * Customized {@link JsonMapper} to add mix-in for class that doesn't have default
     * constructors
     * @return the {@link JsonMapper} to use
     */
    private
    JsonMapper objectMapper() {
        BasicPolymorphicTypeValidator.Builder typeValidatorBuilder = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(CopyOnWriteArrayList.class)
                .allowIfSubType(FlashMap.class)
                .allowIfSubType("com.gulimall."); // 允许 com.gulimall 及其子包下的所有类

        // 2. 将自定义的验证器传入 SecurityJacksonModules
        return JsonMapper.builder()
                .addModules(SecurityJacksonModules.getModules(this.loader, typeValidatorBuilder))
                .build();
//        return JsonMapper.builder().addModules(SecurityJacksonModules.getModules(this.loader)).build();
    }

    /*
     * @see
     * org.springframework.beans.factory.BeanClassLoaderAware#setBeanClassLoader(java.lang
     * .ClassLoader)
     */
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.loader = classLoader;
    }

    //自定义cookie的作用域
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(AuthConstants.GULIMALL_SESSION_NAME);
        serializer.setDomainName(AuthConstants.GULIMALL_SESSION_DOMAIN_NAME);
        return serializer;
    }
}
