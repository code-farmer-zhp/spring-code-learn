package com.zhp.env;


public interface ConfigurableEnvironment extends Environment, ConfigurablePropertyResolver {

    void merge(ConfigurableEnvironment parent);

    MutablePropertySources getPropertySources();
}
