package com.texasjake95.commons.file;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class SaveFileOutputStream implements DataOutput {
	
	private DataOutputStream dos;
	
	public SaveFileOutputStream(OutputStream input)
	{
		try
		{
			dos = new DataOutputStream(new GZIPOutputStream(input));
		}
		catch (IOException e)
		{
		}
	}
	
	public void flush() throws IOException
	{
		dos.flush();
	}
	
	public void write(byte[] b) throws IOException
	{
		dos.write(b);
	}
	
	public void write(byte[] b, int off, int len) throws IOException
	{
		dos.write(b, off, len);
	}
	
	public void writeBoolean(boolean v) throws IOException
	{
		dos.writeBoolean(v);
	}
	
	public void writeByte(int v) throws IOException
	{
		dos.writeByte(v);
	}
	
	public void writeChar(int v) throws IOException
	{
		dos.writeChar(v);
	}
	
	public void writeDouble(double v) throws IOException
	{
		dos.writeDouble(v);
	}
	
	public void writeFloat(float v) throws IOException
	{
		dos.writeFloat(v);
	}
	
	public void writeInt(int v) throws IOException
	{
		dos.writeInt(v);
	}
	
	public void writeLong(long v) throws IOException
	{
		dos.writeLong(v);
	}
	
	public void writeShort(int v) throws IOException
	{
		dos.writeShort(v);
	}
	
	public void writeUTF(String str) throws IOException
	{
		dos.writeUTF(str);
	}
	
	public void close() throws IOException
	{
		dos.close();
	}
	
	@Override
	public void write(int b) throws IOException
	{
		dos.write(b);
	}
	
	@Override
	public void writeBytes(String str) throws IOException
	{
		dos.writeBytes(str);
	}
	
	@Override
	public void writeChars(String str) throws IOException
	{
		dos.writeChars(str);
	}
	
	@Override
	public String toString()
	{
		return this.dos.toString();
	}
}
