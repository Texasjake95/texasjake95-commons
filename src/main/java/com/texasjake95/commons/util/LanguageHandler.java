package com.texasjake95.commons.util;

import java.io.File;

public class LanguageHandler {
	
	private LanguageMap map = new LanguageMap();
	private String currentLang;
	private static LanguageHandler instance = new LanguageHandler();
	public static final String defaultLang = "en_US";
	
	public LanguageHandler()
	{
		currentLang = "en_US";
	}
	
	public String translateString(String string)
	{
		return this.map.translate(string);
	}
	
	public void addLangFile(String lang, File file)
	{
		this.map.addFile(lang, file);
	}
	
	public void addLocization(String lang, String string, String loc)
	{
		this.map.addLocization(lang, string, loc);
	}
	
	public static LanguageHandler instance()
	{
		return instance;
	}
	
	public String getCurrentLang()
	{
		return currentLang;
	}
	
	public void setCurrentLang(String language)
	{
		this.currentLang = language;
	}
}
