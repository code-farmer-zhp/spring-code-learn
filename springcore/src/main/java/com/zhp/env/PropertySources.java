package com.zhp.env;


public interface PropertySources extends Iterable<PropertySource<?>> {

    boolean contains(String name);

    PropertySource<?> get(String name);
}
