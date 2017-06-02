package org.zhp.beans.factory.support;


import org.zhp.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory implements ConfigurableListableBeanFactory {

    private static final Map<String, Reference<DefaultListableBeanFactory>> serializableFactories = new ConcurrentHashMap<>(8);

    private String serializationId;

    private Boolean allowBeanDefinitionOverriding;

    private Boolean allowCircularReferences;

    public void setSerializationId(String serializationId) {
        if (serializationId != null) {
            serializableFactories.put(serializationId, new WeakReference<>(this));
        } else if (this.serializationId != null) {
            serializableFactories.remove(this.serializationId);
        }
        this.serializationId = serializationId;
    }

    public void setAllowBeanDefinitionOverriding(Boolean allowBeanDefinitionOverriding) {
        this.allowBeanDefinitionOverriding = allowBeanDefinitionOverriding;
    }

    public void setAllowCircularReferences(Boolean allowCircularReferences) {
        this.allowCircularReferences = allowCircularReferences;
    }

    @Override
    public void destroySingletons() {

    }
}
