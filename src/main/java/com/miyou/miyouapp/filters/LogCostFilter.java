package com.miyou.miyouapp.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Date;

/***
 *      过滤器 拦截器
 *
 *     1、Filter是依赖于Servlet容器，属于Servlet规范的一部分，
 *      而拦截器则是独立存在的，可以在任何情况下使用。
 *
 * 　　2、Filter的执行由Servlet容器回调完成，
 *      而拦截器通常通过动态代理的方式来执行。
 *
 * 　　3、Filter的生命周期由Servlet容器管理，
 *      而拦截器则可以通过IoC容器来管理，因此可以通过注入等方式来获取其他Bean的实例，
 *      因此使用会更方便。
 */
@WebFilter(urlPatterns = "/*", filterName = "logFilter")
public class LogCostFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        System.out.println("LogFilter Execute start!" + new Date());
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("LogFilter Execute end!" + new Date());
        System.out.println("LogFilter Execute cost=" + (System.currentTimeMillis() - start));
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}
