package com.zhp.env;


public abstract class AbstractEnvironment implements ConfigurableEnvironment {

    private final ConfigurablePropertyResolver propertyResolver = new PropertySourcesPropertyResolver();

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

    @Override
    public String resolveRequiredPlaceholders(String text) {
        return null;
    }
}
