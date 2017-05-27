package com.zhp.core.io;


public interface Resource extends InputStreamSource {

    String getFilename();

    String getDescription();
}
