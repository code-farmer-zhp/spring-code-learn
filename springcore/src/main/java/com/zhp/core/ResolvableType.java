package com.zhp.core;

import java.io.Serializable;
import java.lang.reflect.Type;


public class ResolvableType implements Serializable {

    private final Class<?> resolved;

    private final Type type;


    private ResolvableType(Class<?> clazz) {
        resolved = clazz == null ? Object.class : clazz;
        type = this.resolved;
    }
}
