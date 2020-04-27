package com.aqzscn.www.weixin.controller;

import com.aqzscn.www.global.domain.co.AppException;
import com.aqzscn.www.global.domain.co.GlobalCaches;
import com.aqzscn.www.weixin.domain.co.WeChatNames;
import com.aqzscn.www.weixin.service.WeixinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/wx")
@Api(tags = "微信服务")
public class WeixinController {

    private final Logger logger = LoggerFactory.getLogger(WeixinController.class);

    private static final long serialVersionUID = 1L;

    private HttpServletRequest request;

    private final WeixinService weixinService;

    //重复通知过滤
    private static ExpireKey expireKey = new DefaultExpireKey();

    public WeixinController(HttpServletRequest request, WeixinService weixinService) {
        this.request = request;
        this.weixinService = weixinService;
    }

    @ApiOperation("接收微信验证消息")
    @ApiImplicitParams({@ApiImplicitParam(value = "签名", paramType = "query", name = "signature", required = true),
            @ApiImplicitParam(value = "时间戳", paramType = "query", name = "timestamp", required = true),
            @ApiImplicitParam(value = "随机字符串", paramType = "query", name = "nonce", required = true),
            @ApiImplicitParam(value = "验证字符串", paramType = "query", name = "echostr")})
    @GetMapping("/msg")
    public String auth(@RequestParam(value = "signature") String signature,
                       @RequestParam(value = "timestamp") String timestamp,
                       @RequestParam(value = "nonce") String nonce,
                       @RequestParam(value = "echostr", defaultValue = "") String echostr) throws RuntimeException {
        //首次请求申请验证,返回echostr
        if (StringUtils.isNotBlank(echostr)) {
            return echostr;
        }
        //验证请求签名
        String token = GlobalCaches.PARAMS.get(WeChatNames.P_TOKEN);
        if (StringUtils.isBlank(token)) {
            throw AppException.of("未配置微信token，无法进行验证");
        }
        if (!signature.equals(SignatureUtil.generateEventMessageSignature(token, timestamp, nonce))) {
            this.logger.info("The request signature is invalid");
            return null;
        } else {
            return echostr;
        }
    }

    @ApiOperation("被动接收事件消息")
    @ApiImplicitParams({@ApiImplicitParam(value = "签名", paramType = "query", name = "signature", required = true),
            @ApiImplicitParam(value = "时间戳", paramType = "query", name = "timestamp", required = true),
            @ApiImplicitParam(value = "随机字符串", paramType = "query", name = "nonce", required = true),
            @ApiImplicitParam(value = "验证字符串", paramType = "query", name = "echostr")})
    @PostMapping("/msg")
    public String handleMsg(@RequestParam(value = "signature") String signature,
                            @RequestParam(value = "timestamp") String timestamp,
                            @RequestParam(value = "nonce") String nonce,
                            @RequestParam(value = "echostr", defaultValue = "") String echostr) throws RuntimeException, IOException {
        //首次请求申请验证,返回echostr
        if (StringUtils.isNotBlank(echostr)) {
            return echostr;
        }

        //验证请求签名
        String token = GlobalCaches.PARAMS.get(WeChatNames.P_TOKEN);
        if (StringUtils.isBlank(token)) {
            throw AppException.of("未配置微信token，无法进行验证");
        }
        if (!signature.equals(SignatureUtil.generateEventMessageSignature(token, timestamp, nonce))) {
            this.logger.info("The request signature is invalid");
            return null;
        }

        ServletInputStream inputStream = request.getInputStream();
        if (inputStream != null) {
            //转换XML
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);
            String key = eventMessage.getFromUserName() + "__"
                    + eventMessage.getToUserName() + "__"
                    + eventMessage.getMsgId() + "__"
                    + eventMessage.getCreateTime();
            if (expireKey.exists(key)) {
                //重复通知不作处理
                return null;
            } else {
                expireKey.add(key);
            }

            // 在此处调用对应的服务处理用户输入
            String receiveText = this.weixinService.doDispatch(eventMessage);

            //创建回复
            XMLMessage xmlTextMessage = new XMLTextMessage(
                    eventMessage.getFromUserName(),
                    eventMessage.getToUserName(),
                    receiveText);
            //回复
            return xmlTextMessage.toXML();
        }
        return null;
    }

}
