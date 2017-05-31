package org.zhp.beans;


import com.zhp.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class BeanUtils {

    public static <T> T instantiateClass(Class<T> clazz) {
        if (clazz.isInterface()) {
            throw new RuntimeException(clazz + "接口不能实例化。");
        }
        try {
            return instantiateClass(clazz.getDeclaredConstructor());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T instantiateClass(Constructor<T> ctor, Object... args) {
        ReflectionUtils.makeAccessible(ctor);
        try {
            return ctor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
