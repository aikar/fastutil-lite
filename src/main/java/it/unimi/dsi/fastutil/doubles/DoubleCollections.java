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
package it.unimi.dsi.fastutil.doubles;
import java.util.Collection;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
/** A class providing static methods and objects that do useful things with type-specific collections.
	*
	* @see java.util.Collections
	*/
public final class DoubleCollections {
	private DoubleCollections() {}
	/** An immutable class representing an empty type-specific collection.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific collection.
	 */
	public abstract static class EmptyCollection extends AbstractDoubleCollection {
	 protected EmptyCollection() {}
	 @Override
	 public boolean contains(double k) { return false; }
	 @Override
	 public Object[] toArray() { return ObjectArrays.EMPTY_ARRAY; }
	 @Override
	
	 public DoubleBidirectionalIterator iterator() { return DoubleIterators.EMPTY_ITERATOR; }
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
	 public boolean addAll(final Collection<? extends Double> c) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean removeAll(final Collection<?> c) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean retainAll(final Collection<?> c) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean addAll(final DoubleCollection c) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean removeAll(final DoubleCollection c) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean retainAll(final DoubleCollection c) { throw new UnsupportedOperationException(); }
	}
	/** A synchronized wrapper class for collections. */
	public static class SynchronizedCollection implements DoubleCollection , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final DoubleCollection collection;
	 protected final Object sync;
	 protected SynchronizedCollection(final DoubleCollection c, final Object sync) {
	  if (c == null) throw new NullPointerException();
	  this.collection = c;
	  this.sync = sync;
	 }
	 protected SynchronizedCollection(final DoubleCollection c) {
	  if (c == null) throw new NullPointerException();
	  this.collection = c;
	  this.sync = this;
	 }
	 @Override
	 public boolean add(final double k) { synchronized(sync) { return collection.add(k); } }
	 @Override
	 public boolean contains(final double k) { synchronized(sync) { return collection.contains(k); } }
	 @Override
	 public boolean rem(final double k) { synchronized(sync) { return collection.rem(k); } }
	 @Override
	 public int size() { synchronized(sync) { return collection.size(); } }
	 @Override
	 public boolean isEmpty() { synchronized(sync) { return collection.isEmpty(); } }
	 @Override
	 public double[] toDoubleArray() { synchronized(sync) { return collection.toDoubleArray(); } }
	 @Override
	 public Object[] toArray() { synchronized(sync) { return collection.toArray(); } }
	 /* {@inheritDoc}
		 * @deprecated Please use {@code toArray()} instead&mdash;this method is redundant and will be removed in the future.
		 */
	 @Deprecated
	 @Override
	 public double[] toDoubleArray(final double[] a) { return toArray(a); }
	 @Override
	 public double[] toArray(final double[] a) { synchronized(sync) { return collection.toArray(a); } }
	 @Override
	 public boolean addAll(final DoubleCollection c) { synchronized(sync) { return collection.addAll(c); } }
	 @Override
	 public boolean containsAll(final DoubleCollection c) { synchronized(sync) { return collection.containsAll(c); } }
	 @Override
	 public boolean removeAll(final DoubleCollection c) { synchronized(sync) { return collection.removeAll(c); } }
	 @Override
	 public boolean removeIf(final java.util.function.DoublePredicate filter) { synchronized(sync) { return collection.removeIf(filter); } }
	 @Override
	 public boolean retainAll(final DoubleCollection c) { synchronized(sync) { return collection.retainAll(c); } }
	 @Override
	 @Deprecated
	 public boolean add(final Double k) { synchronized(sync) { return collection.add(k); } }
	 @Override
	 @Deprecated
	 public boolean contains(final Object k) { synchronized(sync) { return collection.contains(k); } }
	 @Override
	 @Deprecated
	 public boolean remove(final Object k) { synchronized(sync) { return collection.remove(k); } }
	 @Override
	 public <T> T[] toArray(final T[] a) { synchronized(sync) { return collection.toArray(a); } }
	 @Override
	 public DoubleIterator iterator() { return collection.iterator(); }
	 @Override
	 public boolean addAll(final Collection<? extends Double> c) { synchronized(sync) { return collection.addAll(c); } }
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
	public static DoubleCollection synchronize(final DoubleCollection c) { return new SynchronizedCollection (c); }
	/** Returns a synchronized collection backed by the specified collection, using an assigned object to synchronize.
	 *
	 * @param c the collection to be wrapped in a synchronized collection.
	 * @param sync an object that will be used to synchronize the list access.
	 * @return a synchronized view of the specified collection.
	 * @see java.util.Collections#synchronizedCollection(Collection)
	 */
	public static DoubleCollection synchronize(final DoubleCollection c, final Object sync) { return new SynchronizedCollection (c, sync); }
	/** An unmodifiable wrapper class for collections. */
	public static class UnmodifiableCollection implements DoubleCollection , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final DoubleCollection collection;
	 protected UnmodifiableCollection(final DoubleCollection c) {
	  if (c == null) throw new NullPointerException();
	  this.collection = c;
	 }
	 @Override
	 public boolean add(final double k) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean rem(final double k) { throw new UnsupportedOperationException(); }
	 @Override
	 public int size() { return collection.size(); }
	 @Override
	 public boolean isEmpty() { return collection.isEmpty(); }
	 @Override
	 public boolean contains(final double o) { return collection.contains(o); }
	 @Override
	 public DoubleIterator iterator() { return DoubleIterators.unmodifiable(collection.iterator()); }
	 @Override
	 public void clear() { throw new UnsupportedOperationException(); }
	 @Override
	 public <T> T[] toArray(final T[] a) { return collection.toArray(a); }
	 @Override
	 public Object[] toArray() { return collection.toArray(); }
	 @Override
	 public boolean containsAll(Collection<?> c) { return collection.containsAll(c); }
	 @Override
	 public boolean addAll(Collection<? extends Double> c) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
	 @Override
	 @Deprecated
	 public boolean add(final Double k) { throw new UnsupportedOperationException(); }
	 @Override
	 @Deprecated
	 public boolean contains(final Object k) { return collection.contains(k); }
	 @Override
	 @Deprecated
	 public boolean remove(final Object k) { throw new UnsupportedOperationException(); }
	 @Override
	 public double[] toDoubleArray() { return collection.toDoubleArray(); }
	 /* {@inheritDoc}
		 * @deprecated Please use {@code toArray()} instead&mdash;this method is redundant.
		 */
	 @Deprecated
	 @Override
	 public double[] toDoubleArray(final double[] a) { return toArray(a); }
	 @Override
	 public double[] toArray(final double[] a) { return collection.toArray(a); }
	 @Override
	 public boolean containsAll(DoubleCollection c) { return collection.containsAll(c); }
	 @Override
	 public boolean addAll(DoubleCollection c) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean removeAll(DoubleCollection c) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean retainAll(DoubleCollection c) { throw new UnsupportedOperationException(); }
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
	public static DoubleCollection unmodifiable(final DoubleCollection c) { return new UnmodifiableCollection (c); }
	/** A collection wrapper class for iterables. */
	public static class IterableCollection extends AbstractDoubleCollection implements java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final DoubleIterable iterable;
	 protected IterableCollection(final DoubleIterable iterable) {
	  if (iterable == null) throw new NullPointerException();
	  this.iterable = iterable;
	 }
	 @Override
	 public int size() {
	  int c = 0;
	  final DoubleIterator iterator = iterator();
	  while(iterator.hasNext()) {
	   iterator.nextDouble();
	   c++;
	  }
	  return c;
	 }
	 @Override
	 public boolean isEmpty() { return ! iterable.iterator().hasNext(); }
	 @Override
	 public DoubleIterator iterator() { return iterable.iterator(); }
	}
	/** Returns an unmodifiable collection backed by the specified iterable.
	 *
	 * @param iterable the iterable object to be wrapped in an unmodifiable collection.
	 * @return an unmodifiable collection view of the specified iterable.
	 */
	public static DoubleCollection asCollection(final DoubleIterable iterable) {
	 if (iterable instanceof DoubleCollection) return (DoubleCollection )iterable;
	 return new IterableCollection (iterable);
	}
}
