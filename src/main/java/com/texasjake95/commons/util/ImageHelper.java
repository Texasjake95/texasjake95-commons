package com.texasjake95.commons.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageHelper {
	
	public static BufferedImage loadImage(String loc)
	{
		String trueLoc = loc;
		if (!trueLoc.startsWith("/"))
			trueLoc = "/" + trueLoc;
		try
		{
			URL url = ImageHelper.class.getResource(trueLoc);
			return ImageIO.read(url);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage[] splitImage(String loc, int rows, int columns)
	{
		return splitImage(loadImage(loc), rows, columns);
	}
	
	public static BufferedImage[] splitImage(BufferedImage image, int rows, int columns)
	{
		int chunks = rows * columns;
		int chunkWidth = image.getWidth() / columns;
		int chunkHeight = image.getHeight() / rows;
		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks];
		for (int x = 0; x < rows; x++)
		{
			for (int y = 0; y < columns; y++)
			{
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, BufferedImage.TYPE_4BYTE_ABGR);
				Graphics2D gr = imgs[count].createGraphics();
				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				gr.dispose();
				count += 1;
			}
		}
		return imgs;
	}
	
	public static void saveImages(BufferedImage[] images, String[] names, String type)
	{
		String name = null;
		boolean nameChange = false;
		for (int i = 0; i < images.length; i++)
		{
			if (names.length > i)
			{
				name = names[i];
				nameChange = true;
			}
			try
			{
				ImageIO.write(images[i], type, new File(nameChange ? name : name.substring(0, name.lastIndexOf('.')) + i + name.substring(name.lastIndexOf('.'))));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			nameChange = false;
		}
	}
}
