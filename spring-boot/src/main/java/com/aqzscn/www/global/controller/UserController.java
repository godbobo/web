package com.aqzscn.www.global.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制用户行为
 * Created by Godbobo on 2019/5/4.
 */
@RestController
@RequestMapping("/g/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 用户登录
     * @return token
     */
    @GetMapping("/token")
    public String login(HttpServletRequest request) throws Exception {
        logger.info(request.getMethod());
        throw new Exception();
    }

    /**
     * 注销登录
     * @return 是否成功
     */
    @DeleteMapping("/token")
    public String logout() throws Exception {
        throw new Exception();
    }

}
