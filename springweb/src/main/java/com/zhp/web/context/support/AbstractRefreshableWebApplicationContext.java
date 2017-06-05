package com.zhp.web.context.support;


import com.zhp.context.support.AbstractRefreshableConfigApplicationContext;
import com.zhp.web.context.ConfigurableWebApplicationContext;

import javax.servlet.ServletContext;

public abstract class AbstractRefreshableWebApplicationContext extends AbstractRefreshableConfigApplicationContext
        implements ConfigurableWebApplicationContext {

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
