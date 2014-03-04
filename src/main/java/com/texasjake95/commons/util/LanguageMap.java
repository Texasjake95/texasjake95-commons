package com.texasjake95.commons.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.Maps;
import com.texasjake95.commons.file.FileInput;

public class LanguageMap {
	
	private Map<String, Map<String, String>> map = Maps.newTreeMap();
	
	public String translate(String string)
	{
		return getString(string);
	}
	
	private Map<String, String> getLangMap(String language)
	{
		Map<String, String> request = map.get(language);
		if (request == null)
		{
			Map<String, String> tempMap = Maps.newTreeMap();
			map.put(language, tempMap);
			request = tempMap;
		}
		return request;
	}
	
	private String getString(String string)
	{
		Map<String, String> langMap = getLangMap(LanguageHandler.instance().getCurrentLang());
		String translate = langMap.get(string);
		if (translate == null || translate.equals(""))
		{
			translate = this.getLangMap(LanguageHandler.defaultLang).get(string);
			if (translate == null || translate.equals(""))
			{
				translate = "translation not found: " + string;
			}
		}
		return translate;
	}
	
	public void addFile(String lang, File file)
	{
		Map<String, String> temp = this.getLangMap(lang);
		FileInput fi = new FileInput(file);
		ArrayList<String> entries = fi.getFileLines();
		for (String entry : entries)
		{
			String[] loc = entry.split("=");
			if (loc.length != 2)
			{
				try
				{
					throw new Exception("Not Valid Localization! " + entry);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			temp.put(loc[0], loc[1]);
		}
		this.map.put(lang, temp);
	}
	
	public void addLocization(String lang, String string, String loc)
	{
		this.getLangMap(lang).put(string, loc);
	}
}
