package com.zhp.web.context;

import com.zhp.core.io.ClassPathResource;
import com.zhp.core.io.support.PropertiesLoaderUtils;

import javax.servlet.ServletContext;
import java.util.Properties;

public class ContextLoader {

    private static final String DEFAULT_STRATEGIES_PATH = "ContextLoader.properties";

    public static final String CONTEXT_CLASS_PARAM = "contextClass";

    private static final Properties defaultStrategies;

    private WebApplicationContext context;

    public ContextLoader() {
    }

    public ContextLoader(WebApplicationContext context) {
        this.context = context;
    }

    static {
        try {
            ClassPathResource resource = new ClassPathResource(DEFAULT_STRATEGIES_PATH, ContextLoader.class);
            defaultStrategies = PropertiesLoaderUtils.loadProperties(resource);
            System.out.println(defaultStrategies);
        } catch (Exception ex) {
            throw new IllegalStateException("Could not load 'ContextLoader.properties': " + ex.getMessage());
        }
    }

    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        if (servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE) != null) {
            throw new IllegalStateException("已存在WebApplicationContext对象");
        }
        if (context == null) {
            context = createWebApplicationContext(servletContext);
        }
        return null;

    }

    protected WebApplicationContext createWebApplicationContext(ServletContext servletContext) {
        Class<?> contextClass = determineContextClass(servletContext);
        return null;
    }

    private Class<?> determineContextClass(ServletContext servletContext) {
        //通过web.xml 配置的自定义class
        String contextClassName = servletContext.getInitParameter(CONTEXT_CLASS_PARAM);
        if (contextClassName != null) {
            //加载自定义class
        }
        return null;
    }

    public static void main(String[] args) {
        new ContextLoader();
    }
}
