package com.zhp.context.support;


import org.zhp.beans.factory.config.ConfigurableListableBeanFactory;
import org.zhp.beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private final Object beanFactoryMonitor = new Object();

    private DefaultListableBeanFactory beanFactory;

    private Boolean allowBeanDefinitionOverriding;

    private Boolean allowCircularReferences;

    @Override
    protected void refreshBeanFactory() {
        if (hasBeanFactory()) {
            destroyBeans();
            closeBeanFactory();
        }
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        beanFactory.setSerializationId(getId());
        customizeBeanFactory(beanFactory);
        loadBeanDefinitions(beanFactory);
        synchronized (beanFactoryMonitor) {
            this.beanFactory = beanFactory;
        }

    }

    protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
        if (this.allowBeanDefinitionOverriding != null) {
            beanFactory.setAllowBeanDefinitionOverriding(this.allowBeanDefinitionOverriding);
        }
        if (this.allowCircularReferences != null) {
            beanFactory.setAllowCircularReferences(this.allowCircularReferences);
        }

    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }


    protected void closeBeanFactory() {
        synchronized (beanFactoryMonitor) {
            beanFactory.setSerializationId(null);
            beanFactory = null;
        }

    }

    protected void destroyBeans() {
        getBeanFactory().destroySingletons();
    }

    protected boolean hasBeanFactory() {
        synchronized (beanFactoryMonitor) {
            return beanFactory != null;
        }
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        synchronized (beanFactoryMonitor) {
            if (beanFactory == null) {
                throw new RuntimeException("DefaultListableBeanFactory 为null");
            }
            return beanFactory;
        }
    }
}
