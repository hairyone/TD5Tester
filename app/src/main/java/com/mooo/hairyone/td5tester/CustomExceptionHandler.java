package com.mooo.hairyone.td5tester;

import org.apache.log4j.Logger;

public class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {

    // https://stackoverflow.com/questions/601503/how-do-i-obtain-crash-data-from-my-android-application
    Logger log = Log4jHelper.getLogger(CustomExceptionHandler.class);

    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

    public CustomExceptionHandler() {
        this.mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public void uncaughtException(Thread t, Throwable e) {
        try {
            log.error("Oops!", e);
            mUncaughtExceptionHandler.uncaughtException(t, e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}