package com.zhp.env;


public interface PropertyResolver {
    String resolveRequiredPlaceholders(String text);
}
