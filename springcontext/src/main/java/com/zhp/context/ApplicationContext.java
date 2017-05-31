package com.zhp.context;


public interface ApplicationContext {
    String getId();

    ApplicationContext getParent();
}
