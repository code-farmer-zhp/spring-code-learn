package com.zhp.web.context;


import com.zhp.context.ConfigurableApplicationContext;

import javax.servlet.ServletContext;

public interface ConfigurableWebApplicationContext extends WebApplicationContext, ConfigurableApplicationContext {

    String APPLICATION_CONTEXT_ID_PREFIX = WebApplicationContext.class.getName() + ":";

    void setServletContext(ServletContext servletContext);

    void setConfigLocation(String configLocation);
}
