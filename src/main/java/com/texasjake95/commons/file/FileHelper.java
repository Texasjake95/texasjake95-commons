package com.texasjake95.commons.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.common.collect.Lists;

public class FileHelper {
	
	public static final String PARENTFILE = "../";
	public static final String CURRENTFILE = ".";
	
	public static String createFileName(String... strings)
	{
		StringBuilder fileName = new StringBuilder();
		for (int index = 0; index < strings.length; index++)
		{
			if (index == strings.length - 1)
				fileName.append(strings[index]);
			fileName.append(strings[index] + "/");
		}
		return fileName.toString();
	}
	
	public static File getFile(String parentName, String fileName)
	{
		if (parentName == null)
			parentName = "";
		TXFile file = new TXFile(parentName, fileName);
		try
		{
			if (!file.exists())
			{
				file.mkdirs();
				file.createNewFile();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return file;
	}
	
	public static String downloadURLToFile(String fileName, String URL)
	{
		try
		{
			ReadableByteChannel rbc = Channels.newChannel(new URL(URL).openStream());
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		}
		catch (Exception e)
		{
		}
		return fileName;
	}
	
	public static String copyFileTo(String fileName, String target)
	{
		try
		{
			ReadableByteChannel rbc = Channels.newChannel(new FileInputStream(fileName));
			FileOutputStream fos = new FileOutputStream(target);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			rbc.close();
			return target;
		}
		catch (Exception e)
		{
		}
		return null;
	}
	
	/**
	 * 
	 * @param zipFile
	 *            File name to unzip
	 * @param extractTo
	 * @param unzip
	 *            Unzip ZipFiles found while unzipping
	 */
	public static String[] extractFolder(String zipFile, String extractTo, boolean unzip)
	{
		ArrayList<String> fileNames = Lists.newArrayList();
		try
		{
			System.out.println(zipFile);
			ZipFile zip = new ZipFile(new File(zipFile));
			String newPath = extractTo;
			new TXFile(newPath).mkdirs();
			Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();
			while (zipFileEntries.hasMoreElements())
			{
				ZipEntry entry = zipFileEntries.nextElement();
				File destFile = new File(newPath, entry.getName());
				destFile.getParentFile().mkdirs();
				if (!entry.isDirectory())
				{
					ReadableByteChannel rbc = Channels.newChannel((zip.getInputStream(entry)));
					FileOutputStream fos = new FileOutputStream(destFile.getAbsoluteFile());
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
					fos.close();
					rbc.close();
					fileNames.add(destFile.getAbsolutePath());
				}
				else
				{
					destFile.mkdirs();
				}
				if (destFile.getName().endsWith(".zip") && unzip)
				{
					String[] tempNames = extractFolder(destFile.getAbsolutePath(), "", unzip);
					for (String name : tempNames)
						fileNames.add(name);
				}
			}
			zip.close();
		}
		catch (IOException e)
		{
		}
		return fileNames.toArray(new String[fileNames.size()]);
	}
	
	public static void deleteFile(String fileName, boolean delDirIfEmpty)
	{
		File file = new File(fileName);
		if (file.exists())
			file.delete();
		if (file.getParentFile().isDirectory() && delDirIfEmpty)
			file.getParentFile().delete();
	}
	
	public static void deleteEmptyDirs(String fileName)
	{
		File file = new File(fileName);
		if (file.exists() && file.isDirectory())
			try
			{
				Files.walkFileTree(Paths.get("", fileName), new DirDelete());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}
	
	private static class DirDelete implements FileVisitor<Path> {
		
		@Override
		public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException
		{
			arg0.toFile().delete();
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException
		{
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1) throws IOException
		{
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException
		{
			return FileVisitResult.CONTINUE;
		}
	}
	
	private static class FileNameCollecter implements FileVisitor<Path> {
		
		public final ArrayList<String> fileNames = new ArrayList<String>();
		
		@Override
		public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException
		{
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException
		{
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1) throws IOException
		{
			fileNames.add(arg0.toFile().getAbsolutePath());
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException
		{
			return FileVisitResult.CONTINUE;
		}
	}
	
	private static class FileNameShallowCollecter extends FileNameCollecter {
		
		String fileBase;
		
		public FileNameShallowCollecter(String baseName)
		{
			fileBase = baseName;
		}
		
		@Override
		public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException
		{
			if (arg0.toFile().getAbsolutePath().equals(fileBase))
				return FileVisitResult.CONTINUE;
			fileNames.add(arg0.toFile().getAbsolutePath());

			return FileVisitResult.SKIP_SUBTREE;
		}
	}
	
	public static ArrayList<String> gatherFileNamesInDir(String fileName, boolean deep)
	{
		ArrayList<String> fileNames = new ArrayList<String>();
		File file = new File(fileName);
		FileNameCollecter names = deep ? new FileNameCollecter() : new FileNameShallowCollecter(fileName);
		if (file.exists() && file.isDirectory())
			try
			{
				Files.walkFileTree(Paths.get("", fileName), names);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		for (String name : names.fileNames)
		{
			fileNames.add(name.substring(name.indexOf(fileName) + fileName.length() + 1));
		}
		return fileNames;
	}
}
