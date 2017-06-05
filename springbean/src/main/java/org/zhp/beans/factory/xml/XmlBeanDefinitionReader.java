package org.zhp.beans.factory.xml;

import org.zhp.beans.factory.support.BeanDefinitionRegistry;


public class XmlBeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(String configLocation) {

    }
}
