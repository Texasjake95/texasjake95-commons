package com.texasjake95.commons.log;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TXLogger {
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static Logger TxLogger = Logger.getLogger("TxMain");
	private static boolean Init = false;
	private static boolean masterInit = false;
	
	private static void init()
	{
		if (!masterInit)
		{
			LogManager.getLogManager().reset();
			Logger globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
			globalLogger.setLevel(Level.OFF);
			masterInit = true;
		}
	}
	
	public static Logger getLogger()
	{
		init();
		if (!Init)
		{
			TxLogger.setLevel(Level.ALL);
			TxLogger.setUseParentHandlers(false);
			File file = new File("../logs", "TxLog-%g.log");
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			FileHandler fileHandler = null;
			try
			{
				fileHandler = new FileHandler(file.getPath(), 0, 3) {
					
					public synchronized void close() throws SecurityException
					{
						// We don't want this handler to reset
					}
				};
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			setHandlers(TxLogger, fileHandler);
			Init = true;
		}
		return TxLogger;
	}
	
	private static void setHandlers(Logger logger, FileHandler fileHandler)
	{
		fileHandler.setLevel(Level.ALL);
		fileHandler.setFormatter(new LoggerFormatter());
		logger.addHandler(fileHandler);
	}
}
