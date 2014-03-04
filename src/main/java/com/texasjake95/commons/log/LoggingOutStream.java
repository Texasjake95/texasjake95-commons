package com.texasjake95.commons.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingOutStream extends ByteArrayOutputStream {
	
	private Logger log;
	private StringBuilder currentMessage;
	
	public LoggingOutStream(Logger log)
	{
		this.log = log;
		this.currentMessage = new StringBuilder();
	}
	
	@Override
	public void flush() throws IOException
	{
		String record;
		synchronized (TXLogger.class)
		{
			super.flush();
			record = this.toString();
			super.reset();
			currentMessage.append(record.replace(TXLogger.LINE_SEPARATOR, "\n"));
			// Are we longer than just the line separator?
			int lastIdx = -1;
			int idx = currentMessage.indexOf("\n", lastIdx + 1);
			while (idx >= 0)
			{
				log.log(Level.INFO, currentMessage.substring(lastIdx + 1, idx));
				lastIdx = idx;
				idx = currentMessage.indexOf("\n", lastIdx + 1);
			}
			if (lastIdx >= 0)
			{
				String rem = currentMessage.substring(lastIdx + 1);
				currentMessage.setLength(0);
				currentMessage.append(rem);
			}
		}
	}
}
