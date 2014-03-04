package com.texasjake95.commons.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LoggerFormatter extends Formatter {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	
	public String format(LogRecord record)
	{
		StringBuilder msg = new StringBuilder();
		Level lvl = record.getLevel();
		msg.append(this.dateFormat.format(Long.valueOf(record.getMillis())));
		String name = lvl.getLocalizedName();
		if (name == null)
		{
			name = lvl.getName();
		}
		if ((name != null) && (name.length() > 0))
		{
			msg.append(" [" + name + "] ");
		}
		else
		{
			msg.append(" ");
		}
		if (record.getLoggerName() != null)
		{
			msg.append("[" + record.getLoggerName() + "] ");
		}
		else
		{
			msg.append("[] ");
		}
		msg.append(record.getMessage());
		msg.append(TXLogger.LINE_SEPARATOR);
		Throwable thr = record.getThrown();
		if (thr != null)
		{
			StringWriter thrDump = new StringWriter();
			thr.printStackTrace(new PrintWriter(thrDump));
			msg.append(thrDump.toString());
		}
		return msg.toString();
	}
}
