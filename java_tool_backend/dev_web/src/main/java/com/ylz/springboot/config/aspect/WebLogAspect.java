package com.ylz.springboot.config.aspect;

import com.google.common.collect.Sets;
import com.ylz.springboot.utils.IpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Set;

/**
 * WebLogAspect
 * Web请求日志记录AOP
 *
 * @author: Chris
 * @time: 2019.02.20
 */
@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger("webLogLogger");

    /**
     * 对登录、修改密码的带有用户信息的请求不做日志记录
     */
    private static Set<String> URL_WHITE_LIST = Sets.newHashSet("/login", "/unauth");

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.ylz.springboot..controller.*.*(..))")
    public void webLogPointcut() {
    }

    @Before("webLogPointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        startTime.set(System.currentTimeMillis());
        String info = String.format("[URL]: %s - [HTTP_METHOD]: %s - [IP]: %s - [ARGS]: %s",
                request.getRequestURL().toString(), request.getMethod(),
                IpUtil.getRemoteIp(request), Arrays.toString(joinPoint.getArgs()));
        logger.info("[Start Request]");
        logger.info(info);
    }

    @AfterReturning(returning = "ret", pointcut = "webLogPointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        String info = String.format("[RESPONSE]: %s - [SPEND_TIME]: %s", ret, (System.currentTimeMillis() - startTime.get()));
        // 处理完请求，返回内容
        logger.info(info);
        logger.info("[End Request]");
    }

}
