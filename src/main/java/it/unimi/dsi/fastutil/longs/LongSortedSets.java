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
package it.unimi.dsi.fastutil.longs;
import java.util.SortedSet;
import java.util.NoSuchElementException;
/** A class providing static methods and objects that do useful things with type-specific sorted sets.
	*
	* @see java.util.Collections
	*/
public final class LongSortedSets {
	private LongSortedSets() {}
	/** An immutable class representing the empty sorted set and implementing a type-specific set interface.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
	public static class EmptySet extends LongSets.EmptySet implements LongSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptySet() {}
	 @Override
	
	 public LongBidirectionalIterator iterator(long from) { return LongIterators.EMPTY_ITERATOR; }
	 @Override
	
	 public LongSortedSet subSet(long from, long to) { return EMPTY_SET; }
	 @Override
	
	 public LongSortedSet headSet(long from) { return EMPTY_SET; }
	 @Override
	
	 public LongSortedSet tailSet(long to) { return EMPTY_SET; }
	 @Override
	 public long firstLong() { throw new NoSuchElementException(); }
	 @Override
	 public long lastLong() { throw new NoSuchElementException(); }
	 @Override
	 public LongComparator comparator() { return null; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public LongSortedSet subSet(Long from, Long to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public LongSortedSet headSet(Long from) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public LongSortedSet tailSet(Long to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Long first() { throw new NoSuchElementException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Long last() { throw new NoSuchElementException(); }
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
	public static class Singleton extends LongSets.Singleton implements LongSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 final LongComparator comparator;
	 protected Singleton(final long element, final LongComparator comparator) {
	  super(element);
	  this.comparator = comparator;
	 }
	 private Singleton(final long element) {
	  this(element, null);
	 }
	
	 final int compare(final long k1, final long k2) {
	  return comparator == null ? ( Long.compare((k1),(k2)) ) : comparator.compare(k1, k2);
	 }
	 @Override
	 public LongBidirectionalIterator iterator(long from) {
	  LongBidirectionalIterator i = iterator();
	  if (compare(element, from) <= 0) i.nextLong();
	  return i;
	 }
	 @Override
	 public LongComparator comparator() { return comparator; }
	 @Override
	
	 public LongSortedSet subSet(final long from, final long to) { if (compare(from, element) <= 0 && compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public LongSortedSet headSet(final long to) { if (compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public LongSortedSet tailSet(final long from) { if (compare(from, element) <= 0) return this; return EMPTY_SET; }
	 @Override
	 public long firstLong() { return element; }
	 @Override
	 public long lastLong() { return element; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public LongSortedSet subSet(final Long from, final Long to) { return subSet((from).longValue(), (to).longValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public LongSortedSet headSet(final Long to) { return headSet((to).longValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public LongSortedSet tailSet(final Long from) { return tailSet((from).longValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Long first() { return Long.valueOf(element); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Long last() { return Long.valueOf(element); }
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static LongSortedSet singleton(final long element) {
	 return new Singleton (element);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static LongSortedSet singleton(final long element, final LongComparator comparator) {
	 return new Singleton (element, comparator);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static LongSortedSet singleton(final Object element) {
	 return new Singleton(((Long)(element)).longValue());
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static LongSortedSet singleton(final Object element, final LongComparator comparator) {
	 return new Singleton(((Long)(element)).longValue(), comparator);
	}
	/** A synchronized wrapper class for sorted sets. */
	public static class SynchronizedSortedSet extends LongSets.SynchronizedSet implements LongSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final LongSortedSet sortedSet;
	 protected SynchronizedSortedSet(final LongSortedSet s, final Object sync) {
	  super(s, sync);
	  sortedSet = s;
	 }
	 protected SynchronizedSortedSet(final LongSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public LongComparator comparator() { synchronized(sync) { return sortedSet.comparator(); } }
	 @Override
	 public LongSortedSet subSet(final long from, final long to) { return new SynchronizedSortedSet (sortedSet.subSet(from, to), sync); }
	 @Override
	 public LongSortedSet headSet(final long to) { return new SynchronizedSortedSet (sortedSet.headSet(to), sync); }
	 @Override
	 public LongSortedSet tailSet(final long from) { return new SynchronizedSortedSet (sortedSet.tailSet(from), sync); }
	 @Override
	 public LongBidirectionalIterator iterator() { return sortedSet.iterator(); }
	 @Override
	 public LongBidirectionalIterator iterator(final long from) { return sortedSet.iterator(from); }
	 @Override
	 public long firstLong() { synchronized(sync) { return sortedSet.firstLong(); } }
	 @Override
	 public long lastLong() { synchronized(sync) { return sortedSet.lastLong(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Long first() { synchronized(sync) { return sortedSet.first(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Long last() { synchronized(sync) { return sortedSet.last(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public LongSortedSet subSet(final Long from, final Long to) { return new SynchronizedSortedSet(sortedSet.subSet(from, to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public LongSortedSet headSet(final Long to) { return new SynchronizedSortedSet(sortedSet.headSet(to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public LongSortedSet tailSet(final Long from) { return new SynchronizedSortedSet(sortedSet.tailSet(from), sync); }
	}
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static LongSortedSet synchronize(final LongSortedSet s) { return new SynchronizedSortedSet (s); }
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set, using an assigned object to synchronize.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @param sync an object that will be used to synchronize the access to the sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static LongSortedSet synchronize(final LongSortedSet s, final Object sync) { return new SynchronizedSortedSet (s, sync); }
	/** An unmodifiable wrapper class for sorted sets. */
	public static class UnmodifiableSortedSet extends LongSets.UnmodifiableSet implements LongSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final LongSortedSet sortedSet;
	 protected UnmodifiableSortedSet(final LongSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public LongComparator comparator() { return sortedSet.comparator(); }
	 @Override
	 public LongSortedSet subSet(final long from, final long to) { return new UnmodifiableSortedSet (sortedSet.subSet(from, to)); }
	 @Override
	 public LongSortedSet headSet(final long to) { return new UnmodifiableSortedSet (sortedSet.headSet(to)); }
	 @Override
	 public LongSortedSet tailSet(final long from) { return new UnmodifiableSortedSet (sortedSet.tailSet(from)); }
	 @Override
	 public LongBidirectionalIterator iterator() { return LongIterators.unmodifiable(sortedSet.iterator()); }
	 @Override
	 public LongBidirectionalIterator iterator(final long from) { return LongIterators.unmodifiable(sortedSet.iterator(from)); }
	 @Override
	 public long firstLong() { return sortedSet.firstLong(); }
	 @Override
	 public long lastLong() { return sortedSet.lastLong(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Long first() { return sortedSet.first(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Long last() { return sortedSet.last(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public LongSortedSet subSet(final Long from, final Long to) { return new UnmodifiableSortedSet(sortedSet.subSet(from, to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public LongSortedSet headSet(final Long to) { return new UnmodifiableSortedSet(sortedSet.headSet(to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public LongSortedSet tailSet(final Long from) { return new UnmodifiableSortedSet(sortedSet.tailSet(from)); }
	}
	/** Returns an unmodifiable type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in an unmodifiable sorted set.
	 * @return an unmodifiable view of the specified sorted set.
	 * @see java.util.Collections#unmodifiableSortedSet(SortedSet)
	 */
	public static LongSortedSet unmodifiable(final LongSortedSet s) { return new UnmodifiableSortedSet (s); }
}
