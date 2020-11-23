package com.ylz.springboot.config.filter;

import com.google.common.collect.Sets;
import com.ylz.springboot.config.exception.GlobalException;
import com.ylz.springboot.oauth.common.RequestHolder;
import com.ylz.springboot.oauth.vo.UserVo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * RequestFilter
 * 请求过滤器
 *
 * @author: Chris
 * @time: 2019.02.19
 */
@WebFilter(filterName = "RequestFilter", urlPatterns = "/*")
public class RequestFilter implements Filter {

    private static Set<String> URL_WHITE_LIST = Sets.newHashSet();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        URL_WHITE_LIST.add("/unauth");
        URL_WHITE_LIST.add("/login");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUrl = request.getRequestURI();
        if (URL_WHITE_LIST.contains(requestUrl)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        UserVo userVo = (UserVo) request.getSession().getAttribute("user");
        if (userVo == null) {
            request.getRequestDispatcher("/unauth").forward(request, response);
            return;
        }
        RequestHolder.add(userVo);
        RequestHolder.add(request);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
