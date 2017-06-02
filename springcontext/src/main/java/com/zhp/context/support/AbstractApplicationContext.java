package com.zhp.context.support;


import com.zhp.context.ApplicationContext;
import com.zhp.context.ApplicationEvent;
import com.zhp.context.ConfigurableApplicationContext;
import com.zhp.env.ConfigurableEnvironment;
import com.zhp.env.Environment;
import com.zhp.env.StandardEnvironment;
import com.zhp.util.ObjectUtils;
import org.zhp.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractApplicationContext implements ConfigurableApplicationContext {

    private final Object startupShutdownMonitor = new Object();

    private long startupDate;

    private AtomicBoolean closed = new AtomicBoolean();

    private AtomicBoolean active = new AtomicBoolean();

    private ConfigurableEnvironment environment;

    private Set<ApplicationEvent> earlyApplicationEvents;

    private String id = ObjectUtils.identityToString(this);

    private ApplicationContext parent;

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isActive() {
        return active.get();
    }

    @Override
    public ApplicationContext getParent() {
        return parent;
    }

    @Override
    public void setParent(ApplicationContext parent) {
        this.parent = parent;
        Environment parentEnvironment = parent.getEnvironment();
        if (parentEnvironment instanceof ConfigurableEnvironment) {
            getEnvironment().merge((ConfigurableEnvironment) parentEnvironment);
        }

    }

    @Override
    public void refresh() {
        synchronized (startupShutdownMonitor) {
            prepareRefresh();

            ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
        }

    }

    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected abstract void refreshBeanFactory();

    public abstract ConfigurableListableBeanFactory getBeanFactory();

    protected void prepareRefresh() {
        startupDate = System.currentTimeMillis();
        closed.set(false);
        active.set(true);

        initPropertySources();

        getEnvironment().validateRequiredProperties();

        earlyApplicationEvents = new LinkedHashSet<>();
    }

    protected void initPropertySources() {
    }

    @Override
    public ConfigurableEnvironment getEnvironment() {
        if (environment == null) {
            environment = createEnvironment();
        }
        return environment;
    }

    protected ConfigurableEnvironment createEnvironment() {
        return new StandardEnvironment();
    }
}
