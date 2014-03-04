package com.texasjake95.commons.file.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.texasjake95.commons.util.SortableCustomList;

public class ConfigCatagory {
	
	private final HashMap<String, ConfigProperty> properties = Maps.newHashMap();
	private final HashMap<String, ConfigCatagory> subCatagories = Maps.newHashMap();
	private final HashMap<String, String> subCatagoryComments = Maps.newHashMap();
	private String name;
	
	public ConfigCatagory(String name)
	{
		this.name = name;
	}
	
	public ConfigProperty addProperty(ConfigProperty prop)
	{
		if (properties.get(prop.getName()) == null)
			properties.put(prop.getName(), prop);
		return getProperty(prop.getName());
	}
	
	public ConfigProperty addProperty(String prop, Object value)
	{
		ConfigProperty temp = new ConfigProperty(prop);
		temp.value = value.toString();
		if (properties.get(prop) == null)
			properties.put(prop, temp);
		return getProperty(prop);
	}
	
	public ConfigProperty addPropertyWithComment(String prop, Object value, String comment)
	{
		this.addProperty(prop, value);
		this.addComment(this.getProperty(prop), comment);
		return this.getProperty(prop);
	}
	
	public void addComment(ConfigProperty prop, String comment)
	{
		prop.addComment(comment);
	}
	
	public ConfigProperty getProperty(String name)
	{
		if (properties.get(name) == null)
			addProperty(new ConfigProperty(name));
		return properties.get(name);
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String toString(String comment, int subIndex, boolean isSub)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getHeader(comment, subIndex));
		SortableCustomList<String> propsSort = new SortableCustomList<String>();
		SortableCustomList<String> propsSortWComment = new SortableCustomList<String>();
		for (Entry<String, ConfigProperty> prop : properties.entrySet())
		{
			if (prop.getValue().comment != null)
				propsSortWComment.add(prop.getKey());
			else
				propsSort.add(prop.getKey());
		}
		propsSort = propsSort.sort();
		propsSortWComment = propsSortWComment.sort();
		boolean isFirst = true;
		for (String propName : propsSortWComment)
		{
			sb.append(this.properties.get(propName).toString(subIndex, isFirst));
			if (isFirst)
				isFirst = false;
		}
		if (!propsSortWComment.list.isEmpty() && !propsSort.list.isEmpty())
			sb.append("\n");
		isFirst = true;
		for (String propName : propsSort)
		{
			sb.append(this.properties.get(propName).toString(subIndex, isFirst));
			if (isFirst)
				isFirst = false;
		}
		if (!this.properties.isEmpty() && !isSub && !this.subCatagories.isEmpty())
			sb.append("\n\n");
		if (!isSub && this.properties.isEmpty() && !this.subCatagories.isEmpty())
		{
		}
		ArrayList<String> names = new ArrayList<String>();
		for (String name : this.subCatagories.keySet())
		{
			names.add(name);
		}
		Collections.sort(names);
		for (String catagoryName : names)
		{
			sb.append(subCatagories.get(catagoryName).toString(this.subCatagoryComments.get(catagoryName), subIndex + 1, true));
			if (catagoryName != names.get(names.size() - 1))
				sb.append("\n\n");
		}
		if (!isSub)
			sb.append("\n");
		return sb.toString();
	}
	
	public String getHeader(String comment, int subIndex)
	{
		StringBuilder string = new StringBuilder();
		string.append(this.getSpacing(subIndex));
		for (int i = 0; i < this.name.length() + 4; i++)
		{
			string.append("~");
		}
		string.append(String.format("\n%s~ %s ~\n", this.getSpacing(subIndex), this.name));
		string.append(this.getSpacing(subIndex));
		for (int i = 0; i < this.name.length() + 4; i++)
		{
			string.append("~");
		}
		if (comment != null)
			string.append(String.format("\n%s** %s **\n", this.getSpacing(subIndex), comment));
		else
			string.append(String.format("%s%s", this.getSpacing(subIndex), "\n"));
		return string.toString();
	}
	
	private String getSpacing(int subIndex)
	{
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < subIndex; i++)
			string.append("      ");
		return string.toString();
	}
	
	public void addSubCatagoryComment(ConfigCatagory cat, String comment)
	{
		this.subCatagoryComments.put(cat.getName(), comment);
	}
	
	public ConfigCatagory getSubCatagory(String catagory)
	{
		if (subCatagories.get(catagory) == null)
			addSubCatagory(new ConfigCatagory(catagory));
		return subCatagories.get(catagory);
	}
	
	public void addSubCatagory(String catagory)
	{
		addSubCatagory(new ConfigCatagory(catagory));
	}
	
	public void addSubCatagory(ConfigCatagory cat)
	{
		if (subCatagories.get(cat.getName()) == null)
			this.subCatagories.put(cat.getName(), cat);
	}
	
	public void addSubCatagoryWithComment(String catagory, String comment)
	{
		addSubCatagory(catagory);
		addSubCatagoryComment(getSubCatagory(catagory), comment);
	}
}
