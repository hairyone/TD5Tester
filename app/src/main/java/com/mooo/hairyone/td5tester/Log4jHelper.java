package com.mooo.hairyone.td5tester;

import android.os.Environment;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class Log4jHelper {
    public static org.apache.log4j.Logger getLogger(Class clazz) {
        final LogConfigurator logConfigurator = new LogConfigurator();
        logConfigurator.setFileName(Environment.getExternalStorageDirectory().toString() + File.separator + "td5tester.log");
        logConfigurator.setRootLevel(Level.ALL);
        logConfigurator.setLevel("com.mooo.hairyone.td5tester", Level.ALL);
        logConfigurator.setUseFileAppender(true);
        logConfigurator.setFilePattern("%d %-5p [%c{1}:%M] %m%n");
        logConfigurator.setMaxFileSize(1024 * 512);
        logConfigurator.setMaxBackupSize(30);
        logConfigurator.setImmediateFlush(true);
        logConfigurator.configure();
        return Logger.getLogger(clazz);
    }
}
