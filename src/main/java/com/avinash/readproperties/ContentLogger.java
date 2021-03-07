package com.avinash.readproperties;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ContentLogger extends Logger{

	private static final String LOGGER_NAME = ContentLogger.class.getCanonicalName();
    private static final String CLASS_NAME = ContentLogger.class.getSimpleName();

	
	protected ContentLogger() {
		super(LOGGER_NAME, null);
		// TODO Auto-generated constructor stub
	}

    public static synchronized ContentLogger getLogger() {
    	ContentLogger logger = null;
        LogManager manager = LogManager.getLogManager();
        logger = (ContentLogger) manager.getLogger(LOGGER_NAME);
        if (logger == null) {
            logger = new ContentLogger();
            manager.addLogger(logger);
            return logger;
        } else {
            return logger;
        }
    }
	
}
