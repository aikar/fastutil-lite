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
import java.util.SortedSet;
import java.util.NoSuchElementException;
/** A class providing static methods and objects that do useful things with type-specific sorted sets.
	*
	* @see java.util.Collections
	*/
public final class DoubleSortedSets {
	private DoubleSortedSets() {}
	/** An immutable class representing the empty sorted set and implementing a type-specific set interface.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
	public static class EmptySet extends DoubleSets.EmptySet implements DoubleSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptySet() {}
	 @Override
	
	 public DoubleBidirectionalIterator iterator(double from) { return DoubleIterators.EMPTY_ITERATOR; }
	 @Override
	
	 public DoubleSortedSet subSet(double from, double to) { return EMPTY_SET; }
	 @Override
	
	 public DoubleSortedSet headSet(double from) { return EMPTY_SET; }
	 @Override
	
	 public DoubleSortedSet tailSet(double to) { return EMPTY_SET; }
	 @Override
	 public double firstDouble() { throw new NoSuchElementException(); }
	 @Override
	 public double lastDouble() { throw new NoSuchElementException(); }
	 @Override
	 public DoubleComparator comparator() { return null; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public DoubleSortedSet subSet(Double from, Double to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public DoubleSortedSet headSet(Double from) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public DoubleSortedSet tailSet(Double to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Double first() { throw new NoSuchElementException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Double last() { throw new NoSuchElementException(); }
	 @Override
	 public Object clone() { return EMPTY_SET; }
	 private Object readResolve() { return EMPTY_SET; }
	}
	/** An empty sorted set (immutable). It is serializable and cloneable.
	 *
	 */

	public static final EmptySet EMPTY_SET = new EmptySet();
	/** A class representing a singleton sorted set.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
	public static class Singleton extends DoubleSets.Singleton implements DoubleSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 final DoubleComparator comparator;
	 protected Singleton(final double element, final DoubleComparator comparator) {
	  super(element);
	  this.comparator = comparator;
	 }
	 private Singleton(final double element) {
	  this(element, null);
	 }
	
	 final int compare(final double k1, final double k2) {
	  return comparator == null ? ( Double.compare((k1),(k2)) ) : comparator.compare(k1, k2);
	 }
	 @Override
	 public DoubleBidirectionalIterator iterator(double from) {
	  DoubleBidirectionalIterator i = iterator();
	  if (compare(element, from) <= 0) i.nextDouble();
	  return i;
	 }
	 @Override
	 public DoubleComparator comparator() { return comparator; }
	 @Override
	
	 public DoubleSortedSet subSet(final double from, final double to) { if (compare(from, element) <= 0 && compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public DoubleSortedSet headSet(final double to) { if (compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public DoubleSortedSet tailSet(final double from) { if (compare(from, element) <= 0) return this; return EMPTY_SET; }
	 @Override
	 public double firstDouble() { return element; }
	 @Override
	 public double lastDouble() { return element; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public DoubleSortedSet subSet(final Double from, final Double to) { return subSet((from).doubleValue(), (to).doubleValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public DoubleSortedSet headSet(final Double to) { return headSet((to).doubleValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public DoubleSortedSet tailSet(final Double from) { return tailSet((from).doubleValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Double first() { return Double.valueOf(element); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Double last() { return Double.valueOf(element); }
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static DoubleSortedSet singleton(final double element) {
	 return new Singleton (element);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static DoubleSortedSet singleton(final double element, final DoubleComparator comparator) {
	 return new Singleton (element, comparator);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static DoubleSortedSet singleton(final Object element) {
	 return new Singleton(((Double)(element)).doubleValue());
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static DoubleSortedSet singleton(final Object element, final DoubleComparator comparator) {
	 return new Singleton(((Double)(element)).doubleValue(), comparator);
	}
	/** A synchronized wrapper class for sorted sets. */
	public static class SynchronizedSortedSet extends DoubleSets.SynchronizedSet implements DoubleSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final DoubleSortedSet sortedSet;
	 protected SynchronizedSortedSet(final DoubleSortedSet s, final Object sync) {
	  super(s, sync);
	  sortedSet = s;
	 }
	 protected SynchronizedSortedSet(final DoubleSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public DoubleComparator comparator() { synchronized(sync) { return sortedSet.comparator(); } }
	 @Override
	 public DoubleSortedSet subSet(final double from, final double to) { return new SynchronizedSortedSet (sortedSet.subSet(from, to), sync); }
	 @Override
	 public DoubleSortedSet headSet(final double to) { return new SynchronizedSortedSet (sortedSet.headSet(to), sync); }
	 @Override
	 public DoubleSortedSet tailSet(final double from) { return new SynchronizedSortedSet (sortedSet.tailSet(from), sync); }
	 @Override
	 public DoubleBidirectionalIterator iterator() { return sortedSet.iterator(); }
	 @Override
	 public DoubleBidirectionalIterator iterator(final double from) { return sortedSet.iterator(from); }
	 @Override
	 public double firstDouble() { synchronized(sync) { return sortedSet.firstDouble(); } }
	 @Override
	 public double lastDouble() { synchronized(sync) { return sortedSet.lastDouble(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Double first() { synchronized(sync) { return sortedSet.first(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Double last() { synchronized(sync) { return sortedSet.last(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public DoubleSortedSet subSet(final Double from, final Double to) { return new SynchronizedSortedSet(sortedSet.subSet(from, to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public DoubleSortedSet headSet(final Double to) { return new SynchronizedSortedSet(sortedSet.headSet(to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public DoubleSortedSet tailSet(final Double from) { return new SynchronizedSortedSet(sortedSet.tailSet(from), sync); }
	}
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static DoubleSortedSet synchronize(final DoubleSortedSet s) { return new SynchronizedSortedSet (s); }
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set, using an assigned object to synchronize.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @param sync an object that will be used to synchronize the access to the sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static DoubleSortedSet synchronize(final DoubleSortedSet s, final Object sync) { return new SynchronizedSortedSet (s, sync); }
	/** An unmodifiable wrapper class for sorted sets. */
	public static class UnmodifiableSortedSet extends DoubleSets.UnmodifiableSet implements DoubleSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final DoubleSortedSet sortedSet;
	 protected UnmodifiableSortedSet(final DoubleSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public DoubleComparator comparator() { return sortedSet.comparator(); }
	 @Override
	 public DoubleSortedSet subSet(final double from, final double to) { return new UnmodifiableSortedSet (sortedSet.subSet(from, to)); }
	 @Override
	 public DoubleSortedSet headSet(final double to) { return new UnmodifiableSortedSet (sortedSet.headSet(to)); }
	 @Override
	 public DoubleSortedSet tailSet(final double from) { return new UnmodifiableSortedSet (sortedSet.tailSet(from)); }
	 @Override
	 public DoubleBidirectionalIterator iterator() { return DoubleIterators.unmodifiable(sortedSet.iterator()); }
	 @Override
	 public DoubleBidirectionalIterator iterator(final double from) { return DoubleIterators.unmodifiable(sortedSet.iterator(from)); }
	 @Override
	 public double firstDouble() { return sortedSet.firstDouble(); }
	 @Override
	 public double lastDouble() { return sortedSet.lastDouble(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Double first() { return sortedSet.first(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Double last() { return sortedSet.last(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public DoubleSortedSet subSet(final Double from, final Double to) { return new UnmodifiableSortedSet(sortedSet.subSet(from, to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public DoubleSortedSet headSet(final Double to) { return new UnmodifiableSortedSet(sortedSet.headSet(to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public DoubleSortedSet tailSet(final Double from) { return new UnmodifiableSortedSet(sortedSet.tailSet(from)); }
	}
	/** Returns an unmodifiable type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in an unmodifiable sorted set.
	 * @return an unmodifiable view of the specified sorted set.
	 * @see java.util.Collections#unmodifiableSortedSet(SortedSet)
	 */
	public static DoubleSortedSet unmodifiable(final DoubleSortedSet s) { return new UnmodifiableSortedSet (s); }
}
