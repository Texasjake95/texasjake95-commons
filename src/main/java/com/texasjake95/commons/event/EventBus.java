package com.texasjake95.commons.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.reflect.TypeToken;

import com.texasjake95.commons.asm.ASMEventHandler;

/**
 * This class is a copy paste of the Minecraft Forge ASM Event System it just
 * works too well!
 * 
 * @see <a href=" https://github.com/MinecraftForge/MinecraftForge">
 *      https://github.com/MinecraftForge/MinecraftForge</a>
 * 
 */
public class EventBus<E extends Annotation> {
	
	private static int maxID = 0;
	private ConcurrentHashMap<Object, ArrayList<IEventListener>> listeners = new ConcurrentHashMap<Object, ArrayList<IEventListener>>();
	private final int busID = maxID++;
	final Class<E> type;
	
	public EventBus(Class<E> clazz)
	{
		ListenerList.resize(busID + 1);
		type = clazz;
	}
	
	public void register(Object target)
	{
		if (listeners.containsKey(target))
		{
			return;
		}
		Set<? extends Class<?>> supers = TypeToken.of(target.getClass()).getTypes().rawTypes();
		for (Method method : target.getClass().getMethods())
		{
			for (Class<?> cls : supers)
			{
				try
				{
					Method real = cls.getDeclaredMethod(method.getName(), method.getParameterTypes());
					if (real.isAnnotationPresent(type))
					{
						Class<?>[] parameterTypes = method.getParameterTypes();
						if (parameterTypes.length != 1)
						{
							throw new IllegalArgumentException("Method " + method + " has @SubscribeEvent annotation, but requires " + parameterTypes.length + " arguments.  Event handler methods must require a single argument.");
						}
						Class<?> eventType = parameterTypes[0];
						if (!Event.class.isAssignableFrom(eventType))
						{
							throw new IllegalArgumentException("Method " + method + " has @SubscribeEvent annotation, but takes a argument that is not an Event " + eventType);
						}
						register(eventType, target, method);
						break;
					}
				}
				catch (NoSuchMethodException e)
				{
					;
				}
			}
		}
	}
	
	private void register(Class<?> eventType, Object target, Method method)
	{
		try
		{
			Constructor<?> ctr = eventType.getConstructor();
			ctr.setAccessible(true);
			Event event = (Event) ctr.newInstance();
			ASMEventHandler listener = new ASMEventHandler(target, method);
			event.getListenerList().register(busID, listener.getPriority(), listener);
			ArrayList<IEventListener> others = listeners.get(target);
			if (others == null)
			{
				others = new ArrayList<IEventListener>();
				listeners.put(target, others);
			}
			others.add(listener);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void unregister(Object object)
	{
		ArrayList<IEventListener> list = listeners.remove(object);
		for (IEventListener listener : list)
		{
			ListenerList.unregisterAll(busID, listener);
		}
	}
	
	public boolean post(Event event)
	{
		IEventListener[] listeners = event.getListenerList().getListeners(busID);
		for (IEventListener listener : listeners)
		{
			listener.invoke(event);
		}
		return (event.isCancelable() ? event.isCanceled() : false);
	}
}