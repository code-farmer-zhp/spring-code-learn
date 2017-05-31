package com.zhp.web.context;

import com.zhp.context.ApplicationContext;
import com.zhp.context.ApplicationContextInitializer;
import com.zhp.context.ConfigurableApplicationContext;
import com.zhp.core.io.ClassPathResource;
import com.zhp.core.io.support.PropertiesLoaderUtils;
import com.zhp.env.ConfigurableEnvironment;
import com.zhp.util.ClassUtils;
import com.zhp.util.ObjectUtils;
import com.zhp.util.StringUtils;
import org.zhp.beans.BeanUtils;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ContextLoader {

    private static final String DEFAULT_STRATEGIES_PATH = "ContextLoader.properties";

    private static final String CONTEXT_CLASS_PARAM = "contextClass";

    private static final String CONTEXT_ID_PARAM = "contextId";

    private static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";

    private static final String GLOBAL_INITIALIZER_CLASSES_PARAM = "globalInitializerClasses";

    private static final String CONTEXT_INITIALIZER_CLASSES_PARAM = "contextInitializerClasses";

    private final List<ApplicationContextInitializer<ConfigurableApplicationContext>> contextInitializers =
            new ArrayList<>();

    private static final String INIT_PARAM_DELIMITERS = ",; \t\n";

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
        if (context instanceof ConfigurableWebApplicationContext) {
            ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) context;
            if (!cwac.isActive()) {
                if (cwac.getParent() == null) {
                    ApplicationContext parent = loadParentContext(servletContext);
                    cwac.setParent(parent);
                }
                //配置WebApplicationContext
                configureAndRefreshWebApplicationContext(cwac, servletContext);
            }

        }
        return null;

    }

    protected void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext cwac, ServletContext sc) {
        //id为原始值
        if (ObjectUtils.identityToString(cwac).endsWith(cwac.getId())) {
            String idParam = sc.getInitParameter(CONTEXT_ID_PARAM);
            if (idParam != null) {
                cwac.setId(idParam);
            } else {
                cwac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX
                        + ObjectUtils.getDisplayString(sc.getContextPath()));
            }
        }

        cwac.setServletContext(sc);
        String configLocationParam = sc.getInitParameter(CONFIG_LOCATION_PARAM);
        if (configLocationParam != null) {
            cwac.setConfigLocation(configLocationParam);
        }
        ConfigurableEnvironment environment = cwac.getEnvironment();
        if (environment instanceof ConfigurableWebEnvironment) {
            ((ConfigurableWebEnvironment) environment).initPropertySources(sc, null);
        }

        customizeContext(sc, cwac);

        cwac.refresh();
    }

    protected void customizeContext(ServletContext sc, ConfigurableWebApplicationContext cwac) {
        List<Class<ApplicationContextInitializer<ConfigurableApplicationContext>>> initializerClasses = determineContextInitializerClasses(sc);

        for (Class<ApplicationContextInitializer<ConfigurableApplicationContext>> initializerClass : initializerClasses) {
            contextInitializers.add(BeanUtils.instantiateClass(initializerClass));
        }

        for (ApplicationContextInitializer<ConfigurableApplicationContext> contextInitializer : contextInitializers) {
            contextInitializer.initialize(cwac);
        }

    }

    @SuppressWarnings("unchecked")
    private Class<ApplicationContextInitializer<ConfigurableApplicationContext>> loadInitializerClass(String className) {
        try {
            Class<?> clazz = ClassUtils.forName(className, ClassUtils.getDefaultClassLoader());
            if (!ApplicationContextInitializer.class.isAssignableFrom(clazz)) {
                throw new RuntimeException(
                        "Initializer class does not implement ApplicationContextInitializer interface: " + clazz);
            }
            return (Class<ApplicationContextInitializer<ConfigurableApplicationContext>>) clazz;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<Class<ApplicationContextInitializer<ConfigurableApplicationContext>>> determineContextInitializerClasses(ServletContext sc) {
        List<Class<ApplicationContextInitializer<ConfigurableApplicationContext>>> classes = new ArrayList<>();

        String globalClassNames = sc.getInitParameter(GLOBAL_INITIALIZER_CLASSES_PARAM);
        String[] classNames = StringUtils.tokenizeToStringArray(globalClassNames, INIT_PARAM_DELIMITERS);
        for (String className : classNames) {
            classes.add(loadInitializerClass(className));
        }

        String localClassNames = sc.getInitParameter(CONTEXT_INITIALIZER_CLASSES_PARAM);
        for (String className : StringUtils.tokenizeToStringArray(localClassNames, INIT_PARAM_DELIMITERS)) {
            classes.add(loadInitializerClass(className));
        }

        return classes;
    }

    /**
     * 获取父类容器
     */
    protected ApplicationContext loadParentContext(ServletContext servletContext) {
        return null;
    }

    /**
     * 创建WebApplicationContext
     */
    protected WebApplicationContext createWebApplicationContext(ServletContext servletContext) {
        Class<?> contextClass = determineContextClass(servletContext);
        return (ConfigurableWebApplicationContext) BeanUtils.instantiateClass(contextClass);
    }

    private Class<?> determineContextClass(ServletContext servletContext) {
        //通过web.xml 配置的自定义class
        String contextClassName = servletContext.getInitParameter(CONTEXT_CLASS_PARAM);
        if (contextClassName != null) {
            //加载自定义class
            try {
                return ClassUtils.forName(contextClassName, ClassUtils.getDefaultClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(
                        "Failed to load custom context class [" + contextClassName + "]", e);
            }
        } else {
            //加载spring提供的默认类
            contextClassName = defaultStrategies.getProperty(WebApplicationContext.class.getName());
            try {
                return ClassUtils.forName(contextClassName, ClassUtils.getDefaultClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(
                        "Failed to load custom context class [" + contextClassName + "]", e);
            }

        }
    }

    public static void main(String[] args) {
        new ContextLoader();
    }
}
