package com.mooo.hairyone.td5tester;

import android.os.Environment;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class Log4jHelper {
    public static org.apache.log4j.Logger getLogger(Class clazz) {
        // https://stackoverflow.com/questions/21307968/log4j-in-android/21982550
        return Logger.getLogger(clazz);
    }

    public static void configure() {
        // https://github.com/eetac/android-logging-log4j/blob/master/src/test/java/de/mindpipe/android/logging/log4j/example/ConfigureLog4J.java
        final LogConfigurator logConfigurator = new LogConfigurator();

        logConfigurator.setFileName(Environment.getExternalStorageDirectory() + File.separator + "td5tester.log");
        logConfigurator.setRootLevel(Level.DEBUG);
        // logConfigurator.setLevel("com.mooo.hairyone.td5tester.fragments.ConnectFragment", Level.DEBUG);
        logConfigurator.setUseFileAppender(true);
        logConfigurator.setFilePattern("%d %-5p [%c{1}:%M] %m%n");
        logConfigurator.setMaxFileSize(1024 * 256);
        logConfigurator.setMaxBackupSize(30);
        logConfigurator.setImmediateFlush(true);
        logConfigurator.configure();
    }


}
