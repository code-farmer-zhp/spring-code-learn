package com.zhp.web.context.support;


import org.zhp.beans.factory.support.DefaultListableBeanFactory;
import org.zhp.beans.factory.xml.XmlBeanDefinitionReader;

public class XmlWebApplicationContext extends AbstractRefreshableWebApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

        initBeanDefinitionReader(reader);
        loadBeanDefinitions(reader);
    }

    protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) {
        String[] configLocations = getConfigLocations();
        for (String configLocation : configLocations) {
            reader.loadBeanDefinitions(configLocation);
        }
    }

    protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader) {

    }
}
