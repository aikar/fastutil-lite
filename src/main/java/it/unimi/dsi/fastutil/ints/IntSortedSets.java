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
package it.unimi.dsi.fastutil.ints;
import java.util.SortedSet;
import java.util.NoSuchElementException;
/** A class providing static methods and objects that do useful things with type-specific sorted sets.
	*
	* @see java.util.Collections
	*/
public final class IntSortedSets {
	private IntSortedSets() {}
	/** An immutable class representing the empty sorted set and implementing a type-specific set interface.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
	public static class EmptySet extends IntSets.EmptySet implements IntSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptySet() {}
	 @Override
	
	 public IntBidirectionalIterator iterator(int from) { return IntIterators.EMPTY_ITERATOR; }
	 @Override
	
	 public IntSortedSet subSet(int from, int to) { return EMPTY_SET; }
	 @Override
	
	 public IntSortedSet headSet(int from) { return EMPTY_SET; }
	 @Override
	
	 public IntSortedSet tailSet(int to) { return EMPTY_SET; }
	 @Override
	 public int firstInt() { throw new NoSuchElementException(); }
	 @Override
	 public int lastInt() { throw new NoSuchElementException(); }
	 @Override
	 public IntComparator comparator() { return null; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public IntSortedSet subSet(Integer from, Integer to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public IntSortedSet headSet(Integer from) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public IntSortedSet tailSet(Integer to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer first() { throw new NoSuchElementException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer last() { throw new NoSuchElementException(); }
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
	public static class Singleton extends IntSets.Singleton implements IntSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 final IntComparator comparator;
	 protected Singleton(final int element, final IntComparator comparator) {
	  super(element);
	  this.comparator = comparator;
	 }
	 private Singleton(final int element) {
	  this(element, null);
	 }
	
	 final int compare(final int k1, final int k2) {
	  return comparator == null ? ( Integer.compare((k1),(k2)) ) : comparator.compare(k1, k2);
	 }
	 @Override
	 public IntBidirectionalIterator iterator(int from) {
	  IntBidirectionalIterator i = iterator();
	  if (compare(element, from) <= 0) i.nextInt();
	  return i;
	 }
	 @Override
	 public IntComparator comparator() { return comparator; }
	 @Override
	
	 public IntSortedSet subSet(final int from, final int to) { if (compare(from, element) <= 0 && compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public IntSortedSet headSet(final int to) { if (compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public IntSortedSet tailSet(final int from) { if (compare(from, element) <= 0) return this; return EMPTY_SET; }
	 @Override
	 public int firstInt() { return element; }
	 @Override
	 public int lastInt() { return element; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public IntSortedSet subSet(final Integer from, final Integer to) { return subSet((from).intValue(), (to).intValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public IntSortedSet headSet(final Integer to) { return headSet((to).intValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public IntSortedSet tailSet(final Integer from) { return tailSet((from).intValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer first() { return Integer.valueOf(element); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer last() { return Integer.valueOf(element); }
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static IntSortedSet singleton(final int element) {
	 return new Singleton (element);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static IntSortedSet singleton(final int element, final IntComparator comparator) {
	 return new Singleton (element, comparator);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static IntSortedSet singleton(final Object element) {
	 return new Singleton(((Integer)(element)).intValue());
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static IntSortedSet singleton(final Object element, final IntComparator comparator) {
	 return new Singleton(((Integer)(element)).intValue(), comparator);
	}
	/** A synchronized wrapper class for sorted sets. */
	public static class SynchronizedSortedSet extends IntSets.SynchronizedSet implements IntSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final IntSortedSet sortedSet;
	 protected SynchronizedSortedSet(final IntSortedSet s, final Object sync) {
	  super(s, sync);
	  sortedSet = s;
	 }
	 protected SynchronizedSortedSet(final IntSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public IntComparator comparator() { synchronized(sync) { return sortedSet.comparator(); } }
	 @Override
	 public IntSortedSet subSet(final int from, final int to) { return new SynchronizedSortedSet (sortedSet.subSet(from, to), sync); }
	 @Override
	 public IntSortedSet headSet(final int to) { return new SynchronizedSortedSet (sortedSet.headSet(to), sync); }
	 @Override
	 public IntSortedSet tailSet(final int from) { return new SynchronizedSortedSet (sortedSet.tailSet(from), sync); }
	 @Override
	 public IntBidirectionalIterator iterator() { return sortedSet.iterator(); }
	 @Override
	 public IntBidirectionalIterator iterator(final int from) { return sortedSet.iterator(from); }
	 @Override
	 public int firstInt() { synchronized(sync) { return sortedSet.firstInt(); } }
	 @Override
	 public int lastInt() { synchronized(sync) { return sortedSet.lastInt(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer first() { synchronized(sync) { return sortedSet.first(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer last() { synchronized(sync) { return sortedSet.last(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public IntSortedSet subSet(final Integer from, final Integer to) { return new SynchronizedSortedSet(sortedSet.subSet(from, to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public IntSortedSet headSet(final Integer to) { return new SynchronizedSortedSet(sortedSet.headSet(to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public IntSortedSet tailSet(final Integer from) { return new SynchronizedSortedSet(sortedSet.tailSet(from), sync); }
	}
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static IntSortedSet synchronize(final IntSortedSet s) { return new SynchronizedSortedSet (s); }
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set, using an assigned object to synchronize.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @param sync an object that will be used to synchronize the access to the sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static IntSortedSet synchronize(final IntSortedSet s, final Object sync) { return new SynchronizedSortedSet (s, sync); }
	/** An unmodifiable wrapper class for sorted sets. */
	public static class UnmodifiableSortedSet extends IntSets.UnmodifiableSet implements IntSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final IntSortedSet sortedSet;
	 protected UnmodifiableSortedSet(final IntSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public IntComparator comparator() { return sortedSet.comparator(); }
	 @Override
	 public IntSortedSet subSet(final int from, final int to) { return new UnmodifiableSortedSet (sortedSet.subSet(from, to)); }
	 @Override
	 public IntSortedSet headSet(final int to) { return new UnmodifiableSortedSet (sortedSet.headSet(to)); }
	 @Override
	 public IntSortedSet tailSet(final int from) { return new UnmodifiableSortedSet (sortedSet.tailSet(from)); }
	 @Override
	 public IntBidirectionalIterator iterator() { return IntIterators.unmodifiable(sortedSet.iterator()); }
	 @Override
	 public IntBidirectionalIterator iterator(final int from) { return IntIterators.unmodifiable(sortedSet.iterator(from)); }
	 @Override
	 public int firstInt() { return sortedSet.firstInt(); }
	 @Override
	 public int lastInt() { return sortedSet.lastInt(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer first() { return sortedSet.first(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer last() { return sortedSet.last(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public IntSortedSet subSet(final Integer from, final Integer to) { return new UnmodifiableSortedSet(sortedSet.subSet(from, to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public IntSortedSet headSet(final Integer to) { return new UnmodifiableSortedSet(sortedSet.headSet(to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public IntSortedSet tailSet(final Integer from) { return new UnmodifiableSortedSet(sortedSet.tailSet(from)); }
	}
	/** Returns an unmodifiable type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in an unmodifiable sorted set.
	 * @return an unmodifiable view of the specified sorted set.
	 * @see java.util.Collections#unmodifiableSortedSet(SortedSet)
	 */
	public static IntSortedSet unmodifiable(final IntSortedSet s) { return new UnmodifiableSortedSet (s); }
}
