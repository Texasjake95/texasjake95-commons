package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.texasjake95.commons.helpers.StreamHelper;

public abstract class SaveNumber extends SaveBase {

	public SaveNumber(byte id, String name)
	{
		super(id, name);
	}

	protected String getValue(DataInput in) throws IOException
	{
		int type = in.readByte();
		Object value = null;
		switch (type)
		{
			case LONG:
				value = in.readLong();
				break;
			case INT:
				value = in.readInt();
				break;
			case SHORT:
				value = in.readShort();
				break;
			case BYTE:
				value = in.readByte();
				break;
			case DOUBLE:
				value = in.readDouble();
				break;
			case FLOAT:
				value = in.readFloat();
				break;
			default:
				break;
		}
		if (value == null)
			value = 0;
		return value.toString();
	}

	protected void saveToOutputStream(DataOutput out, Number number) throws IOException
	{
		byte type = 0;
		if (number instanceof Long || number instanceof Integer || number instanceof Short || number instanceof Byte)
		{
			long temp = number.longValue();
			if (temp < 0)
				temp = -temp;
			type = BYTE;
			if (temp > Byte.MAX_VALUE)
			{
				type = SHORT;
				if (temp > Short.MAX_VALUE)
				{
					type = INT;
					if (temp > Integer.MAX_VALUE)
						type = LONG;
				}
			}
		}
		else
		{
			type = DOUBLE;
			double temp = number.doubleValue();
			if (temp < 0)
				temp = -temp;
			int exp = Math.getExponent(temp);
			if (exp < Float.MAX_EXPONENT && exp > Float.MIN_EXPONENT && temp < Float.MAX_VALUE)
				type = FLOAT;
		}
		out.writeByte(type);
		switch (type)
		{
			case LONG:
				out.writeLong(number.longValue());
				break;
			case INT:
				out.writeInt(number.intValue());
				break;
			case SHORT:
				out.writeShort(number.shortValue());
				break;
			case BYTE:
				out.writeByte(number.byteValue());
				break;
			case DOUBLE:
				out.writeDouble(number.doubleValue());
				break;
			case FLOAT:
				out.writeFloat(number.floatValue());
				break;
			default:
				break;
		}
	}

	private static final byte LONG = 0;
	private static final byte INT = 1;
	private static final byte SHORT = 2;
	private static final byte BYTE = 3;
	private static final byte DOUBLE = 4;
	private static final byte FLOAT = 5;
}
