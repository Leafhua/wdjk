package com.wdjk.webdemo624.controller.filter;



import com.wdjk.webdemo624.utils.PublicParamsUtil;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  api 过滤器
 *
 *  @author Suvan
 */
@Component
@WebFilter(urlPatterns = "/api/*",filterName = "AddResponseHeaderTokenFilter")
public class ApiFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException { }

    @Override
    public void destroy() { }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //set thread local param
        PublicParamsUtil.setRequest(request);
        PublicParamsUtil.setResponse(response);

        //pass filter, http to the chain
        chain.doFilter(request, response);

        //clear thread local param information
        PublicParamsUtil.clearAllInfo();
    }
}
