package com.texasjake95.commons.event;

import java.util.ArrayList;

/**
 * This class is a copy paste of the Minecraft Forge ASM Event System it just works too well!
 *
 * @see <a href=" https://github.com/MinecraftForge/MinecraftForge">
 *      https://github.com/MinecraftForge/MinecraftForge</a>
 *
 */
public class ListenerList {

	private ListenerList parent;
	private ListenerListInst[] lists = new ListenerListInst[0];

	public ListenerList()
	{
		allLists.add(this);
		this.resizeLists(maxSize);
	}

	public ListenerList(ListenerList parent)
	{
		allLists.add(this);
		this.parent = parent;
		this.resizeLists(maxSize);
	}

	public IEventListener[] getListeners(int id)
	{
		return this.lists[id].getListeners();
	}

	public void register(int id, EventPriority priority, IEventListener listener)
	{
		this.lists[id].register(priority, listener);
	}

	public void resizeLists(int max)
	{
		if (this.parent != null)
			this.parent.resizeLists(max);
		if (this.lists.length >= max)
			return;
		ListenerListInst[] newList = new ListenerListInst[max];
		int x = 0;
		for (; x < this.lists.length; x++)
			newList[x] = this.lists[x];
		for (; x < max; x++)
			if (this.parent != null)
				newList[x] = new ListenerListInst(this.parent.getInstance(x));
			else
				newList[x] = new ListenerListInst();
		this.lists = newList;
	}

	public void unregister(int id, IEventListener listener)
	{
		this.lists[id].unregister(listener);
	}

	protected ListenerListInst getInstance(int id)
	{
		return this.lists[id];
	}

	private static ArrayList<ListenerList> allLists = new ArrayList<ListenerList>();
	private static int maxSize = 0;

	public static void clearBusID(int id)
	{
		for (ListenerList list : allLists)
			list.lists[id].dispose();
	}

	public static void resize(int max)
	{
		if (max <= maxSize)
			return;
		for (ListenerList list : allLists)
			list.resizeLists(max);
		maxSize = max;
	}

	public static void unregisterAll(int id, IEventListener listener)
	{
		for (ListenerList list : allLists)
			list.unregister(id, listener);
	}

	private class ListenerListInst {

		private boolean rebuild = true;
		private IEventListener[] listeners;
		private ArrayList<ArrayList<IEventListener>> priorities;
		private ListenerListInst parent;

		private ListenerListInst()
		{
			int count = EventPriority.values().length;
			this.priorities = new ArrayList<ArrayList<IEventListener>>(count);
			for (int x = 0; x < count; x++)
				this.priorities.add(new ArrayList<IEventListener>());
		}

		private ListenerListInst(ListenerListInst parent)
		{
			this();
			this.parent = parent;
		}

		public void dispose()
		{
			for (ArrayList<IEventListener> listeners : this.priorities)
				listeners.clear();
			this.priorities.clear();
			this.parent = null;
			this.listeners = null;
		}

		/**
		 * Returns a full list of all listeners for all priority levels. Including all parent
		 * listeners.
		 *
		 * List is returned in proper priority order.
		 *
		 * Automatically rebuilds the internal Array cache if its information is out of date.
		 *
		 * @return Array containing listeners
		 */
		public IEventListener[] getListeners()
		{
			if (this.shouldRebuild())
				this.buildCache();
			return this.listeners;
		}

		/**
		 * Returns a ArrayList containing all listeners for this event, and all parent events
		 * for the specified priority.
		 *
		 * The list is returned with the listeners for the children events first.
		 *
		 * @param priority
		 *            The Priority to get
		 * @return ArrayList containing listeners
		 */
		public ArrayList<IEventListener> getListeners(EventPriority priority)
		{
			ArrayList<IEventListener> ret = new ArrayList<IEventListener>(this.priorities.get(priority.ordinal()));
			if (this.parent != null)
				ret.addAll(this.parent.getListeners(priority));
			return ret;
		}

		public void register(EventPriority priority, IEventListener listener)
		{
			this.priorities.get(priority.ordinal()).add(listener);
			this.rebuild = true;
		}

		public void unregister(IEventListener listener)
		{
			for (ArrayList<IEventListener> list : this.priorities)
				if (list.remove(listener))
					this.rebuild = true;
		}

		/**
		 * Rebuild the local Array of listeners, returns early if there is no work to do.
		 */
		private void buildCache()
		{
			if (this.parent != null && this.parent.shouldRebuild())
				this.parent.buildCache();
			ArrayList<IEventListener> ret = new ArrayList<IEventListener>();
			for (EventPriority value : EventPriority.values())
				ret.addAll(this.getListeners(value));
			this.listeners = ret.toArray(new IEventListener[ret.size()]);
			this.rebuild = false;
		}

		protected boolean shouldRebuild()
		{
			return this.rebuild || this.parent != null && this.parent.shouldRebuild();
		}
	}
}
