package com.aqzscn.www.global.config;

import com.aqzscn.www.global.component.SpringContextUtil;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Godbobo
 * @date 2019/5/4.
 */
@Configuration
@Aspect
// @ConditionalOnProperty(value = "server.ssl.enabled", havingValue = "true")
public class BaseConfiguration {
    //正常启用的https端口 如443
    @Value("${server.port}")
    Integer httpsPort;

//    @Bean
//    public SpringContextUtil springContextUtil() {
//        return new SpringContextUtil();
//    }

    // springboot2 写法
//    @Bean
//    public TomcatServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint constraint = new SecurityConstraint();
//                constraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                constraint.addCollection(collection);
//                context.addConstraint(constraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        return tomcat;
//    }

    /**
     * 该配置会在系统启动后读取，如果此时正在运行其他绑定了80端口的服务器，整个系统就会启动失败
     * 如果服务器是nginx服务器，可以配置反向代理将80端口的某个服务映射到自己的https端口上来并关掉这一项配置
     */
//    @Bean
//    public Connector httpConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        //Connector监听的http的端口号
//        connector.setPort(80);
//        connector.setSecure(false);
//        //监听到http的端口号后转向到的https的端口号
//        connector.setRedirectPort(httpsPort);
//        return connector;
//    }

}
