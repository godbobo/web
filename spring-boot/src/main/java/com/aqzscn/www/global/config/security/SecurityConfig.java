package com.aqzscn.www.global.config.security;

import com.aqzscn.www.global.domain.dto.IErrorCode;
import com.aqzscn.www.global.domain.dto.ReturnError;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import com.aqzscn.www.global.domain.dto.ValidationResult;
import com.aqzscn.www.global.service.impl.UserServiceImpl;
import com.aqzscn.www.global.util.BeanRevertHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户认证配置
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userService;

    @Autowired
    public SecurityConfig(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * 配置密码编码器
     * NoOpPasswordEncoder 不对密码加密，NoOpPasswordEncoder.getInstance()可新建实例
     * BCryptPasswordEncoder 强哈希加密 new BCryptPasswordEncoder()可新建实例 传入参数10作为密钥迭代次数，默认为10
     * 一般会在用户注册时通过BCryptPasswordEncoder.encode()得到加密的密码后存到数据库
     * 登录时用户输入明文密码，用下面方法配置的密码加密器加密后与数据库中的加密过的密码进行比较
     *
     * @return 编码器
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 配置用户信息
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        基于内存的认证方式，此时配置用户角色时不需要添加 ROLE_ 前缀
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("123").roles("ADMIN", "USER")
//                .and()
//                .withUser("bobo").password("123").roles("USER");
//        使用基于数据库的配置
        auth.userDetailsService(userService);
    }

    /**
     * 配置每个访问路径需要对应的角色
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/g/**")
                .hasRole("ADMIN")
                .antMatchers("/blog/**")
                .access("hasAnyRole('ADMIN', 'USER')")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
                    // 组装返回信息
                    List<IErrorCode> errorCodes = new ArrayList<>(1);
                    errorCodes.add(new ValidationResult(403L,"403 Forbidden"));
                    ReturnVo vo = new ReturnVo();
                    vo.setErrors(errorCodes);
                    vo.setMsg("禁止访问");
                    // 设置响应体信息
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    httpServletResponse.setStatus(403);
                    ObjectMapper mapper = new ObjectMapper();
                    out.write(mapper.writeValueAsString(vo));
                    out.flush();
                    out.close();
                })
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
                .csrf()
                .disable();
        setLoginHandler(http);
    }



    /**
     * 默认登录成功会返回页面
     * 配置为返回JSON消息
     */
    private void setLoginHandler(HttpSecurity http) throws Exception {
        // .loginPage("/login_page") 用户未登录时跳转的页面，如果配置了就会跳转到开发者配置的页面而不是系统默认的登录页面
        // .loginProcessingUrl("/login") 登录请求处理接口 无论自定义登录页面还是移动端登录 都会使用
        http.formLogin()
                .loginProcessingUrl("/login")
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    // 组装返回信息
                    Object principal = authentication.getPrincipal();
                    ReturnVo vo = new ReturnVo();
                    vo.setData(principal);
                    // 设置响应体信息
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    httpServletResponse.setStatus(200);
                    ObjectMapper mapper = new ObjectMapper();
                    out.write(BeanRevertHelper.hideUserInfo(vo));
                    out.flush();
                    out.close();
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    // 组装返回信息
                    List<IErrorCode> errorCodes = new ArrayList<>(1);
                    if (e instanceof LockedException) {
                        errorCodes.add(new ValidationResult(401L,"账户被锁定"));
                    } else if (e instanceof BadCredentialsException) {
                        errorCodes.add(new ValidationResult(401L,"用户名或密码输入错误"));
                    } else if (e instanceof DisabledException) {
                        errorCodes.add(new ValidationResult(401L,"账户被禁用"));
                    } else if (e instanceof AccountExpiredException) {
                        errorCodes.add(new ValidationResult(401L,"账户已过期"));
                    } else if (e instanceof CredentialsExpiredException) {
                        errorCodes.add(new ValidationResult(401L,"密码已过期"));
                    } else {
                        errorCodes.add(new ValidationResult(401L,"登录失败"));
                    }
                    ReturnVo vo = new ReturnVo();
                    vo.setErrors(errorCodes);
                    vo.setMsg("登录失败");
                    // 设置响应体信息
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    httpServletResponse.setStatus(401);
                    ObjectMapper mapper = new ObjectMapper();
                    out.write(mapper.writeValueAsString(vo));
                    out.flush();
                    out.close();
                });
    }

    /**
     * 注销登录同样配置为返回JSON信息
     */
    private void setLogoutHandler(HttpSecurity http) throws Exception {
        http.logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .addLogoutHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    // 在这里做数据清除工作
                })
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    // 在这里返回JSON数据或重定向
                    httpServletResponse.sendRedirect("/login_page");
                });
    }

}
