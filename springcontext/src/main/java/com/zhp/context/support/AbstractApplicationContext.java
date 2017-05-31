package com.zhp.context.support;


import com.zhp.context.ApplicationEvent;
import com.zhp.context.ConfigurableApplicationContext;
import com.zhp.env.ConfigurableEnvironment;
import com.zhp.env.StandardEnvironment;

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

    @Override
    public void refresh() {
        synchronized (startupShutdownMonitor) {
            prepareRefresh();
        }

    }

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
        if(environment == null){
            environment = createEnvironment();
        }
        return environment;
    }

    protected ConfigurableEnvironment createEnvironment() {
        return new StandardEnvironment();
    }
}
