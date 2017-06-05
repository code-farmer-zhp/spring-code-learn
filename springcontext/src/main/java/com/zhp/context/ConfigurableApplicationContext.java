package com.zhp.context;

import com.zhp.env.ConfigurableEnvironment;

public interface ConfigurableApplicationContext extends ApplicationContext {

    String CONFIG_LOCATION_DELIMITERS = ",; \t\n";


    void setId(String id);

    boolean isActive();

    void setParent(ApplicationContext parent);

    ConfigurableEnvironment getEnvironment();

    void refresh();
}
