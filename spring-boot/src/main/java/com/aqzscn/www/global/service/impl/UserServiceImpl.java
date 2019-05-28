package com.aqzscn.www.global.service.impl;

import com.aqzscn.www.global.component.MailService;
import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.co.GlobalCaches;
import com.aqzscn.www.global.domain.co.GlobalNames;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import com.aqzscn.www.global.mapper.User;
import com.aqzscn.www.global.mapper.UserMapper;
import com.aqzscn.www.global.service.UserService;
import com.aqzscn.www.global.component.RedisUtil;
import com.aqzscn.www.global.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.Random;

/**
 * 用户服务 包括Spring Security认证相关内容
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserMapper userMapper;
    private final MailService mailService;
    private final TemplateEngine templateEngine;
    private final RedisUtil redisUtil;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    public UserServiceImpl(UserMapper userMapper, MailService mailService, TemplateEngine templateEngine, RedisUtil redisUtil) {
        this.userMapper = userMapper;
        this.mailService = mailService;
        this.templateEngine = templateEngine;
        this.redisUtil = redisUtil;
    }

    /**
     * 为SpringSecurity提供用户信息以判断用户身份
     *
     * @param s 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException 用户名未找到异常
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userMapper.loadUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        user.setRoles(this.userMapper.getRolesByUid(user.getId()));
        return user;
    }

    @Override
    public boolean reg(User user) throws RuntimeException {
        // 补充及过滤某些信息 防止恶意注册
        user.setId(null);
        user.setLocked(0);
        user.setEnabled(1);
        user.setRegTime(new Date());
        // 对密码加密存储
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        // 直接使用邮箱作为用户名，防止遗忘
        user.setUsername(user.getEmail());
        // 直接插入即可，如果插入失败，即代表用户信息中某些字段与其他人重复了
        if (this.userMapper.insert(user) > 0) {
            // 先为每个注册用户创建一个基本的用户角色 管理员只有一个人，因此直接在数据库中修改，但怎样获取基本角色主键是个问题
            Long userRole = GlobalCaches.ROLES.get("USER");
            if(relateRole(user.getId(), userRole)){
                // 向用户邮箱发送验证邮件
                sendMail(user.getUsername(), user.getEmail(), user.getRealName());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean active(String code, Long id) throws RuntimeException {
        return false;
    }

    /**
     * 为用户设置角色
     * @param uid 用户主键
     * @param rid 角色主键
     * @return 是否成功
     */
    private boolean relateRole(Long uid, Long rid) {
        return this.userMapper.setRoles(uid, rid) > 0;
    }

    // 发送验证邮件
    private void sendMail(String username, String to, String name) throws RuntimeException {
        // 验证该用户是否可以重新发送邮件
        String canSendMailKey = GlobalNames.USER_REDIS + GlobalNames.CAN_SEND_MAIL + username;
        if (this.redisUtil.isExist(canSendMailKey)) {
            throw AppException.of("不能在短时间重复发送邮件，请稍后再试");
        }
        // 生成并存储该用户名和对应的验证码
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String key = GlobalNames.USER_REDIS + GlobalNames.MAIL_CODE + username;
        redisUtil.set(key, checkCode);
        Date date = DateUtil.pushByMinute(10);
        // 设置过期时间为十分钟
        redisUtil.expireAt(key, date);
        logger.info("[" + username + "]的验证码为：" + checkCode + "；将于" + DateUtil.format(date) + "后过期");
        // 发送验证码邮件
        Context context = new Context();
        context.setVariable("mailcode", checkCode);
        context.setVariable("name", name);
        String tmp = templateEngine.process("/mail/register.html", context);
        mailService.sendTemplateMail(to, "验证邮件 - 爱情终是残念 （此乃系统邮件，请勿回复）", tmp);
        // 邮件发送成功后设置一分钟内不允许再次发送
        redisUtil.set(canSendMailKey, "该用户暂时不允许发送邮件");
        redisUtil.expireAt(canSendMailKey, DateUtil.pushByMinute(1));
    }
}
