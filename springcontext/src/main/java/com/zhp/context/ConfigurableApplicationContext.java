package com.zhp.context;

import com.zhp.env.ConfigurableEnvironment;

public interface ConfigurableApplicationContext extends ApplicationContext {
    void setId(String id);

    boolean isActive();

    void setParent(ApplicationContext parent);

    ConfigurableEnvironment getEnvironment();

    void refresh();
}
