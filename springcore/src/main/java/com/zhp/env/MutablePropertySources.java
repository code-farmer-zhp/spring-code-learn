package com.zhp.env;


import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MutablePropertySources implements PropertySources {

    private List<PropertySource<?>> propertySourceList = new CopyOnWriteArrayList<>();

    @Override
    public boolean contains(String name) {
        return false;
    }

    @Override
    public PropertySource<?> get(String name) {
        return null;
    }

    @Override
    public Iterator<PropertySource<?>> iterator() {
        return null;
    }

}
