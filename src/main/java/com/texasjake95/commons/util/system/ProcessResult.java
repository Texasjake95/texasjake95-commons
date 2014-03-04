package com.texasjake95.commons.util.system;

import java.util.ArrayList;

public class ProcessResult {
	
	public final ArrayList<String> output;
	public final ArrayList<String> error;
	
	public ProcessResult(ThreadedOutput output, ThreadedOutput error)
	{
		this.output = output.getLinesPrinted();
		this.error = error.getLinesPrinted();
	}
}
