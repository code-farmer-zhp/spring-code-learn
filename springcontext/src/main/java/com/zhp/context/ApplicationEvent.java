package com.zhp.context;


import java.util.EventObject;

public abstract class ApplicationEvent extends EventObject {

    private final long timestamp;

    public ApplicationEvent(Object source) {
        super(source);
        timestamp = System.currentTimeMillis();
    }

    public final long getTimestamp() {
        return timestamp;
    }

}
