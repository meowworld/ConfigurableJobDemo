package com.example.demo.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *  @see {@link ApplicationContextAware} 拿到应用上下文，可以获取我们想要的容器中的已经注入的bean
 * 解决不能使用 @Autowired 注解的问题
 */

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
