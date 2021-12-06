// By Iacon1
// Created 12/03/2021
// A collection that allows items to be assigned many indices; They can then be retrieved as a list of values matching an index or combination of indices

package Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class IndexTable<E> implements List<E>
{
	private static class IndexWrapper<E>
	{
		private Object[] indices;
		private E value;
		
		public IndexWrapper()
		{
			value = null;
			indices = null;
		}
		public IndexWrapper(E value, Object[] indices)
		{
			this.value = value;
			this.indices = indices;
		}
		
		public void setValue(E value)
		{
			this.value = value;
		}
		public E getValue()
		{
			return value;
		}
		
		/** Returns whether all non-null parameter indices match this wrapper's given indices.
		 *  @param indices The indices to check against.
		 *  @return 1 if all given indices that are not null equal the corresponding index
		 *  in this wrapper; 0 otherwise.
		 */
		public boolean hasIndices(Object[] indices)
		{
			for (int i = 0; i < this.indices.length; ++i)
			{
				if (indices[i] == null) continue;
				else if (this.indices[i] == null) return false;
				else if (!this.indices[i].equals(indices[i])) return false;
			}
			
			return true;
		}
		
		public void setIndices(Object[] indices)
		{
			this.indices = indices;
		}
	}
	
	private Class<?>[] indexTypes;
	
	/** Returns if a set of indices has the correct classes. */
	private boolean correctClasses(Object[] indices)
	{
		if (indices.length != indexTypes.length) return false;
		
		for (int i = 0; i < indexTypes.length; ++i)
		{
			if (indices[i] == null) continue;
			if (!indexTypes[i].isAssignableFrom(indices[i].getClass())) return false;
		}
		
		return true;
	}
	private List<IndexWrapper<E>> items;
	
	private int getByValue(Object o)
	{
		for (int i = 0; i < items.size(); ++i)
			if (items.get(i).getValue().equals(o)) return i;
		return -1;
	}
	private List<Integer> getByIndices(Object[] indices) throws IndexTypesException
	{
		if (!correctClasses(indices)) throw new IndexTypesException(indexTypes, indices);
		
		List<Integer> itemIndices = new ArrayList<Integer>();
		for (int i = 0; i < items.size(); ++i)
			if (items.get(i).hasIndices(indices)) itemIndices.add(i);
		return itemIndices;
	}
	/** Returns the raw, un-indexed list of items. */
	private List<E> getItemList()
	{
		List<E> list = new ArrayList<E>();
		
		for (int i = 0; i < items.size(); ++i)
		{
			list.add(items.get(i).getValue());
		}
		
		return list;
	}
	
	public IndexTable()
	{
		indexTypes = null;
		items = new ArrayList<IndexWrapper<E>>();
	}
	public IndexTable(Class<?>... indexTypes)
	{
		this.indexTypes = indexTypes;
		items = new ArrayList<IndexWrapper<E>>();
	}
	
	// Collection overrides
	@Override public int size() {return items.size();}
	@Override public boolean isEmpty() {return items.isEmpty();}
	@Override public boolean contains(Object o) {return getByValue(o) != -1;}
	@Override public Iterator<E> iterator() {return getItemList().iterator();}
	@Override public Object[] toArray() {return getItemList().toArray();}
	@Override public <T> T[] toArray(T[] a) {return getItemList().toArray(a);}
	@Override
	public boolean add(E e)
	{
		Object[] indices = new Object[indexTypes.length];
		for (int i = 0; i < indexTypes.length; ++i) indices = null;
		return items.add(new IndexWrapper<E>(e, indices));
	}
	@Override
	public boolean remove(Object o)
	{
		if (getByValue(o) == -1) return false; 
		items.remove(getByValue(o));
		return true;
	}
	@Override public boolean containsAll(Collection<?> c) {return getItemList().containsAll(c);}
	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		boolean changed = false;
		for (E e : c) changed = changed || add(e);
		return changed;
	}
	@Override
	public boolean removeAll(Collection<?> c)
	{
		boolean changed = false;
		for (Object o : c) changed = changed || remove(o);
		return changed;
	}
	@Override public boolean retainAll(Collection<?> c)
	{
		return false; // TODO
	}
	@Override public void clear() {items.clear();}

	// List overrides
	
	@Override
	public void add(int index, E element)
	{
		Object[] indices = new Object[indexTypes.length];
		for (int i = 0; i < indexTypes.length; ++i) indices = null;
		items.add(index, new IndexWrapper<E>(element, indices));
	}
	@Override
	public E set(int index, E element)
	{
		if (index < size())
		{
			E e = items.get(index).getValue();
			items.get(index).setValue(element);
			return e;
		}
		else
		{
			add(index, element);
			return null;
		}
	}
	@Override public E remove(int index) {return items.get(index).getValue();}
	@Override public E get(int index) {return items.get(index).getValue();}
	@Override public int lastIndexOf(Object o) {return getItemList().lastIndexOf(o);}
	@Override public ListIterator<E> listIterator() {return getItemList().listIterator();}
	@Override
	public ListIterator<E> listIterator(int index) {return getItemList().listIterator(index);}
	@Override
	public List<E> subList(int fromIndex, int toIndex) {return getItemList().subList(fromIndex, toIndex);}
	@Override public boolean addAll(int index, Collection<? extends E> c)
	{
		boolean changed = false;
		int i = 0;
		for (E e : c)
		{
			changed = changed || get(index + i) != e;
			add(index + i, e);
			i += 1;
		}
		return changed;
	}
	
	// Custom methods
	public boolean add(E e, Object... indices)
	{
		return items.add(new IndexWrapper<E>(e, indices));
	}
	public List<E> get(Object... indices) throws IndexTypesException
	{
		List<E> values = new ArrayList<E>();
		List<Integer> itemIndices = getByIndices(indices);
		
		for (int i = 0; i < itemIndices.size(); ++i) values.add(items.get(i).getValue());
		
		return values;
	}
	public int indexOf(Object o)
	{
		return items.indexOf(o);
	}
	public boolean remove(int index, Object... indices) throws IndexTypesException
	{
		List<Integer> getByIndices = getByIndices(indices);
		if (getByIndices == null) return false;
		int itemIndex = getByIndices.get(index);
		if (itemIndex == -1) return false;
		return items.remove(itemIndex) != null;
	}
	public void setIndices(int index, Object... indices) throws IndexTypesException
	{
		if (!correctClasses(indices)) throw new IndexTypesException(indexTypes, indices);
		items.get(index).setIndices(indices);
	}
	

}
