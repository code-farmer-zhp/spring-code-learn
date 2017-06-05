package com.zhp.env;

public interface ConfigurablePropertyResolver extends PropertyResolver{

    void validateRequiredProperties();
}
