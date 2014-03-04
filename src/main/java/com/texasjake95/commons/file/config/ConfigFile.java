package com.texasjake95.commons.file.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.google.common.collect.Maps;
import com.texasjake95.commons.file.FileHelper;
import com.texasjake95.commons.file.FileInput;
import com.texasjake95.commons.file.FileOutput;
import com.texasjake95.commons.util.CustomArrayList;

public class ConfigFile {
	
	public static final String GENERAL = "General";
	public final File file;
	private final HashMap<String, ConfigCatagory> catagories = Maps.newHashMap();
	private final HashMap<String, String> comments = Maps.newHashMap();
	
	public ConfigFile(String parent, String name)
	{
		this(FileHelper.getFile(parent, name));
	}
	
	public ConfigFile(File file)
	{
		this.file = FileHelper.getFile("", file.getAbsolutePath());
	}
	
	public void addCatagoryComment(ConfigCatagory cat, String comment)
	{
		this.comments.put(cat.getName(), comment);
	}
	
	public ConfigProperty getProperty(String catagory, String Property)
	{
		if (catagories.get(catagory) == null)
			addCatagory(new ConfigCatagory(catagory));
		return catagories.get(catagory).getProperty(Property);
	}
	
	public ConfigCatagory getSubCatagory(String parentCatagories, String regex, String catagory)
	{
		return this.getSubCatagory(parentCatagories.split(regex), catagory);
	}
	
	public ConfigCatagory getCatagory(String catagory)
	{
		if (catagories.get(catagory) == null)
			addCatagory(new ConfigCatagory(catagory));
		return catagories.get(catagory);
	}
	
	public void addPropertyWithComment(String catagory, String name, Object value, String comment)
	{
		this.addProperty(catagory, name, value);
		this.addComment(catagory, name, comment);
	}
	
	public ConfigProperty addComment(String catagory, String name, String comment)
	{
		ConfigCatagory cat = getCatagory(catagory);
		ConfigProperty prop = cat.getProperty(name);
		if (prop.value == null)
			prop.value = "";
		cat.addComment(prop, comment);
		return catagories.get(catagory).addProperty(prop);
	}
	
	public ConfigProperty addProperty(String catagory, String name, Object value)
	{
		if (catagories.get(catagory) == null)
			addCatagory(catagory);
		ConfigProperty prop = new ConfigProperty(name);
		prop.value = value.toString();
		return catagories.get(catagory).addProperty(prop);
	}
	
	public void addCatagory(String catagory)
	{
		addCatagory(new ConfigCatagory(catagory));
	}
	
	public void addCatagory(ConfigCatagory cat)
	{
		if (catagories.get(cat.getName()) == null)
			this.catagories.put(cat.getName(), cat);
	}
	
	public void addCatagoryWithComment(String catagory, String comment)
	{
		addCatagory(catagory);
		addCatagoryComment(getCatagory(catagory), comment);
	}
	
	public void addSubCatagoryComment(String[] parentCatagories, ConfigCatagory cat, String comment)
	{
		CustomArrayList<String> cats = new CustomArrayList<String>().addAll(parentCatagories);
		String subCat = cats.list.remove(cats.list.size() - 1);
		this.getSubCatagory(cats.toArray(new String[0]), subCat).addSubCatagoryComment(cat, comment);
	}
	
	public void addSubCatagoryComment(String[] parentCatagories, String catagory, String comment)
	{
		this.addSubCatagoryComment(parentCatagories, this.getSubCatagory(parentCatagories, catagory), comment);
	}
	
	public ConfigCatagory getSubCatagory(String[] parentCatagories, String catagory)
	{
		if (parentCatagories.length > 0)
		{
			CustomArrayList<String> cats = new CustomArrayList<String>().addAll(parentCatagories);
			if (cats.list.size() > 0)
				cats.list.remove(cats.list.size() - 1);
			ConfigCatagory temp = this.getCatagory(parentCatagories[0]);
			for (String parent : cats)
				temp = temp.getSubCatagory(parent);
			return temp.getSubCatagory(catagory);
		}
		return this.getCatagory(catagory);
	}
	
	public void addSubCatagory(String[] parentCatagories, String catagory)
	{
		addSubCatagory(parentCatagories, new ConfigCatagory(catagory));
	}
	
	public void addSubCatagory(String[] parentCatagories, ConfigCatagory cat)
	{
		CustomArrayList<String> cats = new CustomArrayList<String>().addAll(parentCatagories);
		String subCat = cats.list.remove(cats.list.size() - 1);
		if (subCat.equals(parentCatagories[0]))
			this.getCatagory(subCat).addSubCatagory(cat);
		else
			this.getSubCatagory(cats.toArray(new String[0]), subCat).addSubCatagory(cat);
	}
	
	public void addSubCatagoryWithComment(String[] parentCatagories, String catagory, String comment)
	{
		addSubCatagory(parentCatagories, catagory);
		addSubCatagoryComment(parentCatagories, getSubCatagory(parentCatagories, catagory), comment);
	}
	
	public void load()
	{
		String line = "";
		try
		{
			FileInput fi = new FileInput(file);
			line = fi.readLine();
			CustomArrayList<ConfigCatagory> parentCatagories = new CustomArrayList<ConfigCatagory>();
			ConfigCatagory currentCatagory = null;
			String commentProp = null;
			int currentIndex = 0;
			int newIndex = 0;
			while (line != null)
			{
				newIndex = getIndex(line);
				line = removeTabs(line);
				if (isCatagory(line))
				{
					if (newIndex < currentIndex)
					{
						if (!parentCatagories.list.isEmpty())
							for (int i = currentIndex - 1; i >= newIndex; i--)
							{
								parentCatagories.list.remove(i);
								if (parentCatagories.list.isEmpty())
									break;
							}
						currentIndex = newIndex;
						currentCatagory = new ConfigCatagory(getCatagoryName(line));
						if (parentCatagories.list.isEmpty())
						{
							this.addCatagory(currentCatagory);
						}
						else
						{
							this.getParent(parentCatagories).addSubCatagory(currentCatagory);
						}
					}
					else if (newIndex > currentIndex)
					{
						parentCatagories.add(currentCatagory);
						currentCatagory = new ConfigCatagory(getCatagoryName(line));
						currentIndex = newIndex;
						if (parentCatagories.list.isEmpty())
						{
							this.addCatagory(currentCatagory);
						}
						else
						{
							this.getParent(parentCatagories).addSubCatagory(currentCatagory);
						}
					}
					else if (newIndex == currentIndex)
					{
						currentCatagory = new ConfigCatagory(getCatagoryName(line));
						if (parentCatagories.list.isEmpty())
						{
							this.addCatagory(currentCatagory);
						}
						else
						{
							this.getParent(parentCatagories).addSubCatagory(currentCatagory);
						}
					}
				}
				else if (isCatagoryComment(line))
				{
					String comment = getCatagoryComment(line);
					if (parentCatagories.list.isEmpty())
					{
						this.addCatagoryComment(currentCatagory, comment);
					}
					else
					{
						this.getParent(parentCatagories).addSubCatagoryComment(currentCatagory, comment);
					}
				}
				else if (isProperty(line))
				{
					String name = line.split(":")[0];
					String value = line.split(":")[1];
					if (newIndex == currentIndex)
					{
						if (commentProp != null)
						{
							currentCatagory.addPropertyWithComment(name, value, commentProp);
							commentProp = null;
						}
						else
							currentCatagory.addProperty(name, value);
						commentProp = null;
					}
					else
					{
						if (commentProp != null)
						{
							parentCatagories.getList().get(newIndex).addPropertyWithComment(name, value, commentProp);
							commentProp = null;
						}
						else
							parentCatagories.getList().get(newIndex).addProperty(name, value);
						commentProp = null;
					}
					commentProp = null;
				}
				else if (isPropertyComment(line))
				{
					commentProp = getPropertyComment(line);
				}
				line = fi.readLine();
			}
		}
		catch (Exception e)
		{
			System.out.println("Errored on: " + line);
			e.printStackTrace();
		}
	}
	
	private String getPropertyComment(String line)
	{
		return line.substring(2);
	}
	
	private boolean isPropertyComment(String line)
	{
		return line.startsWith("* ");
	}
	
	private boolean isProperty(String line)
	{
		return line.contains(":");
	}
	
	private String getCatagoryComment(String line)
	{
		if (line.length() < 3)
			return line;
		return line.substring(3, line.indexOf(" **"));
	}
	
	private boolean isCatagoryComment(String line)
	{
		return line.startsWith("**");
	}
	
	private ConfigCatagory getParent(CustomArrayList<ConfigCatagory> cats)
	{
		if (cats.list.isEmpty())
			return null;
		return cats.getList().get(cats.list.size() - 1);
	}
	
	private String getCatagoryName(String line)
	{
		return line.substring(2, line.indexOf(" ~"));
	}
	
	private boolean isCatagory(String line)
	{
		return line.contains("~ ");
	}
	
	private int getIndex(String line)
	{
		int spaces = 0;
		for (int i = 0; i < line.length(); i++)
		{
			if (line.charAt(i) == ' ')
			{
				spaces++;
			}
			else
			{
				break;
			}
		}
		return (int) (Math.ceil(spaces / 6D));
	}
	
	private String removeTabs(String line)
	{
		return line.trim();
	}
	
	public void save()
	{
		ArrayList<String> names = new ArrayList<String>();
		for (String name : catagories.keySet())
		{
			names.add(name);
		}
		Collections.sort(names);
		try
		{
			FileOutput fo = new FileOutput(file);
			for (String name : names)
			{
				ConfigCatagory cat = this.catagories.get(name);
				fo.println(cat.toString(comments.get(cat.getName()), 0, false));
			}
			fo.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
