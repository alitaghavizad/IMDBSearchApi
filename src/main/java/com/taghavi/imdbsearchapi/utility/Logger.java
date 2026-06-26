package com.taghavi.imdbsearchapi.utility;

import org.slf4j.LoggerFactory;

public class Logger {
    public static void log(String message) {
        LoggerFactory.getLogger(getCallerClassName()).info(message);
    }

    private static String getCallerClassName() {
        return new Throwable().getStackTrace()[2].getClassName();
    }
}
