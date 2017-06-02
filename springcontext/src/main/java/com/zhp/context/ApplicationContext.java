package com.zhp.context;


import com.zhp.env.EnvironmentCapable;

public interface ApplicationContext extends EnvironmentCapable {
    String getId();

    ApplicationContext getParent();
}
