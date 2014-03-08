package com.texasjake95.commons.asm;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is a copy paste of the Minecraft's Launcher Wrapper (Which is Open
 * Sources) that was originally created by CPW (To the best of my knowledge)
 * used to allow runtime class changes, mostly for the event handlers
 * 
 * @see <a href=" https://github.com/Mojang/LegacyLauncher">
 *      https://github.com/Mojang/LegacyLauncher</a>
 * 
 */
public class ASMClassLoader extends URLClassLoader {
	
	public static final int BUFFER_SIZE = 1 << 12;
	private List<URL> sources;
	private ClassLoader parent = getClass().getClassLoader();
	private List<IClassTransformer> transformers = new ArrayList<IClassTransformer>(2);
	private Map<String, Class<?>> cachedClasses = new HashMap<String, Class<?>>(1000);
	private Set<String> invalidClasses = new HashSet<String>(1000);
	private Set<String> classLoaderExceptions = new HashSet<String>();
	private Set<String> transformerExceptions = new HashSet<String>();
	private Map<Package, Manifest> packageManifests = new HashMap<Package, Manifest>();
	private Map<String, byte[]> resourceCache = new HashMap<String, byte[]>(1000);
	private Set<String> negativeResourceCache = new HashSet<String>();
	private static final Manifest EMPTY = new Manifest();
	private final ThreadLocal<byte[]> loadBuffer = new ThreadLocal<byte[]>();
	private static final String[] RESERVED_NAMES = { "CON", "PRN", "AUX", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
	private static final boolean DEBUG = false;
	private static final boolean DEBUG_FINER = DEBUG;
	private static Logger asmLogger = Logger.getLogger("TXASMLogger");
	
	public ASMClassLoader(URL[] sources)
	{
		super(sources, null);
		this.sources = new ArrayList<URL>(Arrays.asList(sources));
		Thread.currentThread().setContextClassLoader(this);
		// classloader exclusions
		addClassLoaderExclusion("java.");
		addClassLoaderExclusion("sun.");
		// transformer exclusions
		addTransformerExclusion("javax.");
		addTransformerExclusion("argo.");
		addTransformerExclusion("org.objectweb.asm.");
		addTransformerExclusion("com.texasjake95.commons.asm.");
	}
	
	public void registerTransformer(String transformerClassName)
	{
		try
		{
			boolean isExempt = false;
			for (String exempt : classLoaderExceptions)
				if (transformerClassName.startsWith(exempt))
					isExempt = true;
			if(!isExempt)
				addClassLoaderExclusion(transformerClassName);
			IClassTransformer transformer = (IClassTransformer) loadClass(transformerClassName).newInstance();
			transformers.add(transformer);
		}
		catch (Exception e)
		{
			asmLogger.log(Level.WARNING, String.format("A critical problem occurred registering the ASM transformer class %s", transformerClassName), e);
		}
	}
	
	public void registerTransformer(IClassTransformer transformer)
	{
		try
		{
			transformers.add(transformer);
		}
		catch (Exception e)
		{
			asmLogger.log(Level.WARNING, String.format("A critical problem occurred registering the ASM transformer class %s", transformer), e);
		}
	}
	
	@Override
	public Class<?> findClass(final String name) throws ClassNotFoundException
	{
		if (invalidClasses.contains(name))
		{
			throw new ClassNotFoundException(name);
		}
		for (final String exception : classLoaderExceptions)
		{
			if (name.startsWith(exception))
			{
				return parent.loadClass(name);
			}
		}
		if (cachedClasses.containsKey(name))
		{
			return cachedClasses.get(name);
		}
		for (final String exception : transformerExceptions)
		{
			if (name.startsWith(exception))
			{
				try
				{
					final Class<?> clazz = super.findClass(name);
					cachedClasses.put(name, clazz);
					return clazz;
				}
				catch (ClassNotFoundException e)
				{
					invalidClasses.add(name);
					throw e;
				}
			}
		}
		try
		{
			if (cachedClasses.containsKey(name))
			{
				return cachedClasses.get(name);
			}
			final int lastDot = name.lastIndexOf('.');
			final String packageName = lastDot == -1 ? "" : name.substring(0, lastDot);
			final String fileName = name.replace('.', '/').concat(".class");
			URLConnection urlConnection = findCodeSourceConnectionFor(fileName);
			CodeSigner[] signers = null;
			if (lastDot > -1)
			{
				if (urlConnection instanceof JarURLConnection)
				{
					final JarURLConnection jarURLConnection = (JarURLConnection) urlConnection;
					final JarFile jarFile = jarURLConnection.getJarFile();
					if (jarFile != null && jarFile.getManifest() != null)
					{
						final Manifest manifest = jarFile.getManifest();
						final JarEntry entry = jarFile.getJarEntry(fileName);
						Package pkg = getPackage(packageName);
						getClassBytes(name);
						signers = entry.getCodeSigners();
						if (pkg == null)
						{
							pkg = definePackage(packageName, manifest, jarURLConnection.getJarFileURL());
							packageManifests.put(pkg, manifest);
						}
						else
						{
							if (pkg.isSealed() && !pkg.isSealed(jarURLConnection.getJarFileURL()))
							{
								severe("The jar file %s is trying to seal already secured path %s", jarFile.getName(), packageName);
							}
							else if (isSealed(packageName, manifest))
							{
								severe("The jar file %s has a security seal for path %s, but that path is defined and not secure", jarFile.getName(), packageName);
							}
						}
					}
				}
				else
				{
					Package pkg = getPackage(packageName);
					if (pkg == null)
					{
						pkg = definePackage(packageName, null, null, null, null, null, null, null);
						packageManifests.put(pkg, EMPTY);
					}
					else if (pkg.isSealed())
					{
						severe("The URL %s is defining elements for sealed path %s", urlConnection.getURL(), packageName);
					}
				}
			}
			final byte[] transformedClass = runTransformers(name, getClassBytes(name));
			final CodeSource codeSource = urlConnection == null ? null : new CodeSource(urlConnection.getURL(), signers);
			final Class<?> clazz = defineClass(name, transformedClass, 0, transformedClass.length, codeSource);
			cachedClasses.put(name, clazz);
			return clazz;
		}
		catch (Throwable e)
		{
			invalidClasses.add(name);
			if (DEBUG)
			{
				asmLogger.log(Level.WARNING, String.format("Exception encountered attempting classloading of %s", name), e);
			}
			throw new ClassNotFoundException(name, e);
		}
	}
	
	private boolean isSealed(final String path, final Manifest manifest)
	{
		Attributes attributes = manifest.getAttributes(path);
		String sealed = null;
		if (attributes != null)
		{
			sealed = attributes.getValue(Name.SEALED);
		}
		if (sealed == null)
		{
			attributes = manifest.getMainAttributes();
			if (attributes != null)
			{
				sealed = attributes.getValue(Name.SEALED);
			}
		}
		return "true".equalsIgnoreCase(sealed);
	}
	
	private URLConnection findCodeSourceConnectionFor(final String name)
	{
		final URL resource = findResource(name);
		if (resource != null)
		{
			try
			{
				return resource.openConnection();
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		return null;
	}
	
	private byte[] runTransformers(final String name, byte[] basicClass)
	{
		if (DEBUG_FINER)
		{
			finest("Beginning transform of {%s} Start Length: %d", name, (basicClass == null ? 0 : basicClass.length));
			for (final IClassTransformer transformer : transformers)
			{
				final String transName = transformer.getClass().getName();
				finest("Before Transformer {%s} %s: %d", name, transName, (basicClass == null ? 0 : basicClass.length));
				basicClass = transformer.transform(name, basicClass);
				finest("After  Transformer {%s} %s: %d", name, transName, (basicClass == null ? 0 : basicClass.length));
			}
			finest("Ending transform of {%s} Start Length: %d", name, (basicClass == null ? 0 : basicClass.length));
		}
		else
		{
			for (final IClassTransformer transformer : transformers)
			{
				basicClass = transformer.transform(name, basicClass);
			}
		}
		return basicClass;
	}
	
	@Override
	public void addURL(final URL url)
	{
		super.addURL(url);
		sources.add(url);
	}
	
	public List<URL> getSources()
	{
		return sources;
	}
	
	private byte[] readFully(InputStream stream)
	{
		try
		{
			byte[] buffer = getOrCreateBuffer();
			int read;
			int totalLength = 0;
			while ((read = stream.read(buffer, totalLength, buffer.length - totalLength)) != -1)
			{
				totalLength += read;
				// Extend our buffer
				if (totalLength >= buffer.length - 1)
				{
					byte[] newBuffer = new byte[buffer.length + BUFFER_SIZE];
					System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
					buffer = newBuffer;
				}
			}
			final byte[] result = new byte[totalLength];
			System.arraycopy(buffer, 0, result, 0, totalLength);
			return result;
		}
		catch (Throwable t)
		{
			asmLogger.log(Level.WARNING, "Problem loading class", t);
			return new byte[0];
		}
	}
	
	private byte[] getOrCreateBuffer()
	{
		byte[] buffer = loadBuffer.get();
		if (buffer == null)
		{
			loadBuffer.set(new byte[BUFFER_SIZE]);
			buffer = loadBuffer.get();
		}
		return buffer;
	}
	
	public List<IClassTransformer> getTransformers()
	{
		return Collections.unmodifiableList(transformers);
	}
	
	public void addClassLoaderExclusion(String toExclude)
	{
		classLoaderExceptions.add(toExclude);
	}
	
	public void addTransformerExclusion(String toExclude)
	{
		transformerExceptions.add(toExclude);
	}
	
	public byte[] getClassBytes(String name) throws IOException
	{
		if (negativeResourceCache.contains(name))
		{
			return null;
		}
		else if (resourceCache.containsKey(name))
		{
			return resourceCache.get(name);
		}
		if (name.indexOf('.') == -1)
		{
			for (final String reservedName : RESERVED_NAMES)
			{
				if (name.toUpperCase(Locale.ENGLISH).startsWith(reservedName))
				{
					final byte[] data = getClassBytes("_" + name);
					if (data != null)
					{
						resourceCache.put(name, data);
						return data;
					}
				}
			}
		}
		InputStream classStream = null;
		try
		{
			final String resourcePath = name.replace('.', '/').concat(".class");
			final URL classResource = findResource(resourcePath);
			if (classResource == null)
			{
				if (DEBUG)
					finest("Failed to find class resource %s", resourcePath);
				negativeResourceCache.add(name);
				return null;
			}
			classStream = classResource.openStream();
			if (DEBUG)
				finest("Loading class %s from resource %s", name, classResource.toString());
			final byte[] data = readFully(classStream);
			resourceCache.put(name, data);
			return data;
		}
		finally
		{
			closeSilently(classStream);
		}
	}
	
	private static void closeSilently(Closeable closeable)
	{
		if (closeable != null)
		{
			try
			{
				closeable.close();
			}
			catch (IOException ignored)
			{
			}
		}
	}
	
	private void log(Level level, String msg, Object... objects)
	{
		asmLogger.log(level, String.format(msg, objects));
	}
	
	private void finest(String msg, Object... objects)
	{
		log(Level.FINEST, msg, objects);
	}
	
	private void severe(String msg, Object... objects)
	{
		log(Level.SEVERE, msg, objects);
	}
	
	public void clearNegativeEntries(Set<String> entriesToClear)
	{
		negativeResourceCache.removeAll(entriesToClear);
	}
}
