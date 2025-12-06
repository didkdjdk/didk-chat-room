package com.didk.commons.tools.xss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * XSS 配置文件
 *
 * @author jiujingz@126.com
 */
@Configuration
@EnableConfigurationProperties(XssProperties.class)
@ConditionalOnProperty(prefix = "renren.xss", value = "enabled")
public class XssConfig {
//
//    @Bean
//    public FilterRegistrationBean<XssFilter> xssFilter(XssProperties properties, PathMatcher pathMatcher) {
//        FilterRegistrationBean<XssFilter> bean = new FilterRegistrationBean<>();
//        bean.setDispatcherTypes(DispatcherType.REQUEST);
//        bean.setFilter(new XssFilter(properties, pathMatcher));
//        bean.addUrlPatterns("/*");
//        bean.setOrder(Integer.MAX_VALUE);
//        bean.setName("xssFilter");
//
//        return bean;
//    }
}
