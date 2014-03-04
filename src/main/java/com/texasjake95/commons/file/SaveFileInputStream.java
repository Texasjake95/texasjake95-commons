package com.texasjake95.commons.file;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class SaveFileInputStream implements DataInput {
	
	private DataInputStream dis;
	
	public SaveFileInputStream(InputStream input) throws IOException
	{
		dis = new DataInputStream(new GZIPInputStream(input));
	}
	
	public int read(byte[] b) throws IOException
	{
		return dis.read(b);
	}
	
	public int read(byte[] b, int off, int len) throws IOException
	{
		return dis.read(b, off, len);
	}
	
	public boolean readBoolean() throws IOException
	{
		return dis.readBoolean();
	}
	
	public byte readByte() throws IOException
	{
		return dis.readByte();
	}
	
	public char readChar() throws IOException
	{
		return dis.readChar();
	}
	
	public double readDouble() throws IOException
	{
		return dis.readDouble();
	}
	
	public float readFloat() throws IOException
	{
		return dis.readFloat();
	}
	
	public void readFully(byte[] b) throws IOException
	{
		dis.readFully(b);
	}
	
	public void readFully(byte[] b, int off, int len) throws IOException
	{
		dis.readFully(b, off, len);
	}
	
	public int readInt() throws IOException
	{
		return dis.readInt();
	}
	
	@Deprecated
	/**
	 * Use readUTF() instead until this is removed
	 * @return
	 * @throws IOException
	 */
	public String readLine() throws IOException
	{
		return dis.readLine();
	}
	
	public long readLong() throws IOException
	{
		return dis.readLong();
	}
	
	public short readShort() throws IOException
	{
		return dis.readShort();
	}
	
	public int readUnsignedByte() throws IOException
	{
		return dis.readUnsignedByte();
	}
	
	public int readUnsignedShort() throws IOException
	{
		return dis.readUnsignedShort();
	}
	
	public String readUTF() throws IOException
	{
		return dis.readUTF();
	}
	
	public int skipBytes(int n) throws IOException
	{
		return dis.skipBytes(n);
	}
	
	public int available() throws IOException
	{
		return dis.available();
	}
	
	public void close() throws IOException
	{
		dis.close();
	}
	
	public void mark(int readlimit) throws IOException
	{
		dis.mark(readlimit);
	}
	
	public boolean markSupported() throws IOException
	{
		return dis.markSupported();
	}
	
	public void reset() throws IOException
	{
		dis.reset();
	}
	
	public long skip(long n) throws IOException
	{
		return dis.skip(n);
	}
}
