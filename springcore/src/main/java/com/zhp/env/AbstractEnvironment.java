package com.zhp.env;


public abstract class AbstractEnvironment implements ConfigurableEnvironment {

    @Override
    public void validateRequiredProperties() {
        //TODO
    }

    @Override
    public MutablePropertySources getPropertySources() {
        return null;
    }

    @Override
    public void merge(ConfigurableEnvironment parent) {

    }
}
