package com.texasjake95.commons.file.config;

public class ConfigProperty implements Comparable<ConfigProperty> {
	
	private String name;
	public String value;
	public String comment = null;
	
	public ConfigProperty(String name)
	{
		this.name = name;
	}
	
	public String toString(int subIndex, boolean isFirst)
	{
		StringBuilder sb = new StringBuilder();
		if (!isFirst || this.comment == null)
			sb.append(String.format("%s%s", this.getSpacing(subIndex), "\n"));
		if (this.comment != null)
		{
			sb.append(String.format("%s%s", this.getSpacing(subIndex), "\n"));
			sb.append(String.format("%s%s", this.getSpacing(subIndex), "* " + this.comment));
			sb.append(String.format("%s%s", this.getSpacing(subIndex), "\n"));
		}
		sb.append(String.format("%s%s", this.getSpacing(subIndex), this.name + ":" + this.value));
		return sb.toString();
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public boolean getBoolean(boolean b)
	{
		try
		{
			return Boolean.valueOf(value);
		}
		catch (Exception e)
		{
			set(String.valueOf(b));
		}
		return Boolean.valueOf(value);
	}
	
	public int getInteger(int i)
	{
		try
		{
			return Integer.valueOf(value);
		}
		catch (Exception e)
		{
			set(String.valueOf(i));
		}
		return Integer.valueOf(value);
	}
	
	public String getString(String string)
	{
		try
		{
			return String.valueOf(value);
		}
		catch (Exception e)
		{
			set(String.valueOf(string));
		}
		return String.valueOf(value);
	}
	
	public void set(String newValue)
	{
		value = newValue;
	}
	
	@Override
	public int compareTo(ConfigProperty o)
	{
		if (o != null)
			if (o.name.equals(this.name))
				if (o.value == null && this.value != null)
					return -1;
				else if (o.value != null && this.value == null)
					return 1;
				else
					return o.value.compareTo(this.value);
			else
				return o.name.compareTo(this.name);
		else
			return 1;
	}
	
	public void addComment(String comment)
	{
		this.comment = comment;
	}
	
	private String getSpacing(int subIndex)
	{
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < subIndex; i++)
			string.append("      ");
		return string.toString();
	}
}
