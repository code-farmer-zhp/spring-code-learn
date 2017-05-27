package com.zhp.util;


public abstract class StringUtils {

    private static final String FOLDER_SEPARATOR = "/";

    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }

        int lastIndexOf = path.lastIndexOf(FOLDER_SEPARATOR);
        return lastIndexOf != -1 ? path.substring(lastIndexOf + 1) : path;

    }
}
