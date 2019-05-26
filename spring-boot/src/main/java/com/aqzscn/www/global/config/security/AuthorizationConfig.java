package com.aqzscn.www.global.config.security;

import com.aqzscn.www.global.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 授权服务配置
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private final RedisConnectionFactory redisConnectionFactory;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthorizationConfig(RedisConnectionFactory redisConnectionFactory, UserDetailsService userDetailsService,
                               AuthenticationManager manager) {
        // UserDetailsService和AuthenticationManager本来是没有实例的
        // 但由于在SecurityConfig中将父类中的这两个类加入到SpringSecurity的容器中
        // 因此这里可以进行注入操作
        this.redisConnectionFactory = redisConnectionFactory;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = manager;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 1 完成Redis缓存，将令牌信息存储到Redis缓存中
        // 2 用于支持password模式
        // 3 为刷新token提供支持
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 支持client_id和client_secret做登录认证
        security.allowFormAuthenticationForClients();
    }

    /**
     * 配置password授权模式
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 3 配置两种授权模式，若要实现token的刷新，就需要refresh_token模式
        // 4 配置token过期时间
        // 5 配置资源id
        // 6 配置密钥
        clients.inMemory()
                .withClient("password")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(1800)
                .resourceIds("rid")
                .scopes("all")
                .secret("123");
    }


}
