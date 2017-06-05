package com.zhp.env;


public abstract class AbstractPropertyResolver implements ConfigurablePropertyResolver{

    @Override
    public String resolveRequiredPlaceholders(String text) {
        return null;
    }

    @Override
    public void validateRequiredProperties() {

    }
}
