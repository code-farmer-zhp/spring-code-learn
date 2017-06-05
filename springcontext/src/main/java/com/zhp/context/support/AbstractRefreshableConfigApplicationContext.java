package com.zhp.context.support;


import com.zhp.util.StringUtils;

public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext {

    private String[] configLocations;


    protected String[] getConfigLocations() {
        return this.configLocations != null ? this.configLocations : getDefaultConfigLocations();
    }


    protected String[] getDefaultConfigLocations() {
        return null;
    }

    public void setConfigLocation(String configLocation) {
        setConfigLocations(StringUtils.tokenizeToStringArray(configLocation, CONFIG_LOCATION_DELIMITERS));
    }

    public void setConfigLocations(String... configLocations) {
        if (configLocations != null) {
            this.configLocations = new String[configLocations.length];
            for (int i = 0; i < configLocations.length; i++) {
                this.configLocations[i] = resolvePath(configLocations[i]);
            }

        }
    }

    protected String resolvePath(String configLocation) {
        return getEnvironment().resolveRequiredPlaceholders(configLocation);
    }
}
