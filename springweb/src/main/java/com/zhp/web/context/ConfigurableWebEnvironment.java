package com.zhp.web.context;

import com.zhp.env.ConfigurableEnvironment;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;


public interface ConfigurableWebEnvironment extends ConfigurableEnvironment {

    void initPropertySources(ServletContext servletContext, ServletConfig servletConfig);
}
