package com.aqzscn.www.global.config.security;

import com.aqzscn.www.global.domain.co.MyOauthException;
import com.aqzscn.www.global.domain.dto.ReturnError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.util.ThrowableAnalyzer;

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
                .userDetailsService(userDetailsService)
                .exceptionTranslator(e -> {
                    ThrowableAnalyzer analyzer = new DefaultThrowableAnalyzer();
                    Throwable[] causeChain = analyzer.determineCauseChain(e);
                    Throwable ase = analyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
                    MyOauthException myOauthException;
                    // 分类处理异常
                    if (ase instanceof InvalidGrantException) {
                        // 从数据库中查找当前用户的信息
                            myOauthException = new MyOauthException(ReturnError.INVALID_GRANT);
                    } else {
                        e.printStackTrace();
                        myOauthException = new MyOauthException(ReturnError.OAUTH_EXCEPTION);
                    }
                    // 从异常栈中获取第一个异常，判断是否是一下几种异常
//                    public static final String INVALID_REQUEST = "invalid_request";
//                    public static final String INVALID_CLIENT = "invalid_client";
//                    public static final String INVALID_GRANT = "invalid_grant";
//                    public static final String UNAUTHORIZED_CLIENT = "unauthorized_client";
//                    public static final String UNSUPPORTED_GRANT_TYPE = "unsupported_grant_type";
//                    public static final String INVALID_SCOPE = "invalid_scope";
//                    public static final String INSUFFICIENT_SCOPE = "insufficient_scope";
//                    public static final String INVALID_TOKEN = "invalid_token";
//                    public static final String REDIRECT_URI_MISMATCH = "redirect_uri_mismatch";
//                    public static final String UNSUPPORTED_RESPONSE_TYPE = "unsupported_response_type";
//                    public static final String ACCESS_DENIED = "access_denied";
                    return new ResponseEntity<OAuth2Exception>(myOauthException, HttpStatus.FORBIDDEN);
                });
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
                .secret(passwordEncoder().encode("123"));
    }


}
