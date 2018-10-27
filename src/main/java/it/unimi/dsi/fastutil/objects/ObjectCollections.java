/*
	* Copyright (C) 2002-2017 Sebastiano Vigna
	*
	* Licensed under the Apache License, Version 2.0 (the "License");
	* you may not use this file except in compliance with the License.
	* You may obtain a copy of the License at
	*
	*     http://www.apache.org/licenses/LICENSE-2.0
	*
	* Unless required by applicable law or agreed to in writing, software
	* distributed under the License is distributed on an "AS IS" BASIS,
	* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	* See the License for the specific language governing permissions and
	* limitations under the License.
	*/
package it.unimi.dsi.fastutil.objects;
import java.util.Collection;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
/** A class providing static methods and objects that do useful things with type-specific collections.
	*
	* @see java.util.Collections
	*/
public final class ObjectCollections {
	private ObjectCollections() {}
	/** An immutable class representing an empty type-specific collection.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific collection.
	 */
	public abstract static class EmptyCollection <K> extends AbstractObjectCollection <K> {
	 protected EmptyCollection() {}
	 @Override
	 public boolean contains(Object k) { return false; }
	 @Override
	 public Object[] toArray() { return ObjectArrays.EMPTY_ARRAY; }
	 @Override
	 @SuppressWarnings("unchecked")
	 public ObjectBidirectionalIterator <K> iterator() { return ObjectIterators.EMPTY_ITERATOR; }
	 @Override
	 public int size() { return 0; }
	 @Override
	 public void clear() {}
	 @Override
	 public int hashCode() { return 0; }
	 @Override
	 public boolean equals(Object o) {
	  if (o == this) return true;
	  if (! (o instanceof Collection)) return false;
	  return ((Collection<?>)o).isEmpty();
	 }
	 @Override
	 public boolean addAll(final Collection<? extends K> c) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean removeAll(final Collection<?> c) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean retainAll(final Collection<?> c) { throw new UnsupportedOperationException(); }
	}
	/** A synchronized wrapper class for collections. */
	public static class SynchronizedCollection <K> implements ObjectCollection <K>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final ObjectCollection <K> collection;
	 protected final Object sync;
	 protected SynchronizedCollection(final ObjectCollection <K> c, final Object sync) {
	  if (c == null) throw new NullPointerException();
	  this.collection = c;
	  this.sync = sync;
	 }
	 protected SynchronizedCollection(final ObjectCollection <K> c) {
	  if (c == null) throw new NullPointerException();
	  this.collection = c;
	  this.sync = this;
	 }
	 @Override
	 public boolean add(final K k) { synchronized(sync) { return collection.add(k); } }
	 @Override
	 public boolean contains(final Object k) { synchronized(sync) { return collection.contains(k); } }
	 @Override
	 public boolean remove(final Object k) { synchronized(sync) { return collection.remove(k); } }
	 @Override
	 public int size() { synchronized(sync) { return collection.size(); } }
	 @Override
	 public boolean isEmpty() { synchronized(sync) { return collection.isEmpty(); } }
	 @Override
	 public Object[] toArray() { synchronized(sync) { return collection.toArray(); } }
	 @Override
	 public <T> T[] toArray(final T[] a) { synchronized(sync) { return collection.toArray(a); } }
	 @Override
	 public ObjectIterator <K> iterator() { return collection.iterator(); }
	 @Override
	 public boolean addAll(final Collection<? extends K> c) { synchronized(sync) { return collection.addAll(c); } }
	 @Override
	 public boolean containsAll(final Collection<?> c) { synchronized(sync) { return collection.containsAll(c); } }
	 @Override
	 public boolean removeAll(final Collection<?> c) { synchronized(sync) { return collection.removeAll(c); } }
	 @Override
	 public boolean retainAll(final Collection<?> c) { synchronized(sync) { return collection.retainAll(c); } }
	 @Override
	 public void clear() { synchronized(sync) { collection.clear(); } }
	 @Override
	 public String toString() { synchronized(sync) { return collection.toString(); } }
	 @Override
	 public int hashCode() { synchronized(sync) { return collection.hashCode(); } }
	 @Override
	 public boolean equals(final Object o) { if (o == this) return true; synchronized(sync) { return collection.equals(o); } }
	 private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
	  synchronized(sync) { s.defaultWriteObject(); }
	 }
	}
	/** Returns a synchronized collection backed by the specified collection.
	 *
	 * @param c the collection to be wrapped in a synchronized collection.
	 * @return a synchronized view of the specified collection.
	 * @see java.util.Collections#synchronizedCollection(Collection)
	 */
	public static <K> ObjectCollection <K> synchronize(final ObjectCollection <K> c) { return new SynchronizedCollection <>(c); }
	/** Returns a synchronized collection backed by the specified collection, using an assigned object to synchronize.
	 *
	 * @param c the collection to be wrapped in a synchronized collection.
	 * @param sync an object that will be used to synchronize the list access.
	 * @return a synchronized view of the specified collection.
	 * @see java.util.Collections#synchronizedCollection(Collection)
	 */
	public static <K> ObjectCollection <K> synchronize(final ObjectCollection <K> c, final Object sync) { return new SynchronizedCollection <>(c, sync); }
	/** An unmodifiable wrapper class for collections. */
	public static class UnmodifiableCollection <K> implements ObjectCollection <K>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final ObjectCollection <K> collection;
	 protected UnmodifiableCollection(final ObjectCollection <K> c) {
	  if (c == null) throw new NullPointerException();
	  this.collection = c;
	 }
	 @Override
	 public boolean add(final K k) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean remove(final Object k) { throw new UnsupportedOperationException(); }
	 @Override
	 public int size() { return collection.size(); }
	 @Override
	 public boolean isEmpty() { return collection.isEmpty(); }
	 @Override
	 public boolean contains(final Object o) { return collection.contains(o); }
	 @Override
	 public ObjectIterator <K> iterator() { return ObjectIterators.unmodifiable(collection.iterator()); }
	 @Override
	 public void clear() { throw new UnsupportedOperationException(); }
	 @Override
	 public <T> T[] toArray(final T[] a) { return collection.toArray(a); }
	 @Override
	 public Object[] toArray() { return collection.toArray(); }
	 @Override
	 public boolean containsAll(Collection<?> c) { return collection.containsAll(c); }
	 @Override
	 public boolean addAll(Collection<? extends K> c) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
	 @Override
	 public String toString() { return collection.toString(); }
	 @Override
	 public int hashCode() { return collection.hashCode(); }
	 @Override
	 public boolean equals(final Object o) { if (o == this) return true; return collection.equals(o); }
	}
	/** Returns an unmodifiable collection backed by the specified collection.
	 *
	 * @param c the collection to be wrapped in an unmodifiable collection.
	 * @return an unmodifiable view of the specified collection.
	 * @see java.util.Collections#unmodifiableCollection(Collection)
	 */
	public static <K> ObjectCollection <K> unmodifiable(final ObjectCollection <K> c) { return new UnmodifiableCollection <>(c); }
	/** A collection wrapper class for iterables. */
	public static class IterableCollection <K> extends AbstractObjectCollection <K> implements java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final ObjectIterable <K> iterable;
	 protected IterableCollection(final ObjectIterable <K> iterable) {
	  if (iterable == null) throw new NullPointerException();
	  this.iterable = iterable;
	 }
	 @Override
	 public int size() {
	  int c = 0;
	  final ObjectIterator <K> iterator = iterator();
	  while(iterator.hasNext()) {
	   iterator.next();
	   c++;
	  }
	  return c;
	 }
	 @Override
	 public boolean isEmpty() { return ! iterable.iterator().hasNext(); }
	 @Override
	 public ObjectIterator <K> iterator() { return iterable.iterator(); }
	}
	/** Returns an unmodifiable collection backed by the specified iterable.
	 *
	 * @param iterable the iterable object to be wrapped in an unmodifiable collection.
	 * @return an unmodifiable collection view of the specified iterable.
	 */
	public static <K> ObjectCollection <K> asCollection(final ObjectIterable <K> iterable) {
	 if (iterable instanceof ObjectCollection) return (ObjectCollection <K>)iterable;
	 return new IterableCollection <>(iterable);
	}
}
