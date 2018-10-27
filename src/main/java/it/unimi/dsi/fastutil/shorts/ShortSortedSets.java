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
package it.unimi.dsi.fastutil.shorts;
import java.util.SortedSet;
import java.util.NoSuchElementException;
/** A class providing static methods and objects that do useful things with type-specific sorted sets.
	*
	* @see java.util.Collections
	*/
public final class ShortSortedSets {
	private ShortSortedSets() {}
	/** An immutable class representing the empty sorted set and implementing a type-specific set interface.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
	public static class EmptySet extends ShortSets.EmptySet implements ShortSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptySet() {}
	 @Override
	
	 public ShortBidirectionalIterator iterator(short from) { return ShortIterators.EMPTY_ITERATOR; }
	 @Override
	
	 public ShortSortedSet subSet(short from, short to) { return EMPTY_SET; }
	 @Override
	
	 public ShortSortedSet headSet(short from) { return EMPTY_SET; }
	 @Override
	
	 public ShortSortedSet tailSet(short to) { return EMPTY_SET; }
	 @Override
	 public short firstShort() { throw new NoSuchElementException(); }
	 @Override
	 public short lastShort() { throw new NoSuchElementException(); }
	 @Override
	 public ShortComparator comparator() { return null; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ShortSortedSet subSet(Short from, Short to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ShortSortedSet headSet(Short from) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ShortSortedSet tailSet(Short to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Short first() { throw new NoSuchElementException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Short last() { throw new NoSuchElementException(); }
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
	public static class Singleton extends ShortSets.Singleton implements ShortSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 final ShortComparator comparator;
	 protected Singleton(final short element, final ShortComparator comparator) {
	  super(element);
	  this.comparator = comparator;
	 }
	 private Singleton(final short element) {
	  this(element, null);
	 }
	
	 final int compare(final short k1, final short k2) {
	  return comparator == null ? ( Short.compare((k1),(k2)) ) : comparator.compare(k1, k2);
	 }
	 @Override
	 public ShortBidirectionalIterator iterator(short from) {
	  ShortBidirectionalIterator i = iterator();
	  if (compare(element, from) <= 0) i.nextShort();
	  return i;
	 }
	 @Override
	 public ShortComparator comparator() { return comparator; }
	 @Override
	
	 public ShortSortedSet subSet(final short from, final short to) { if (compare(from, element) <= 0 && compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public ShortSortedSet headSet(final short to) { if (compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public ShortSortedSet tailSet(final short from) { if (compare(from, element) <= 0) return this; return EMPTY_SET; }
	 @Override
	 public short firstShort() { return element; }
	 @Override
	 public short lastShort() { return element; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ShortSortedSet subSet(final Short from, final Short to) { return subSet((from).shortValue(), (to).shortValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ShortSortedSet headSet(final Short to) { return headSet((to).shortValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ShortSortedSet tailSet(final Short from) { return tailSet((from).shortValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Short first() { return Short.valueOf(element); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Short last() { return Short.valueOf(element); }
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static ShortSortedSet singleton(final short element) {
	 return new Singleton (element);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static ShortSortedSet singleton(final short element, final ShortComparator comparator) {
	 return new Singleton (element, comparator);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static ShortSortedSet singleton(final Object element) {
	 return new Singleton(((Short)(element)).shortValue());
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static ShortSortedSet singleton(final Object element, final ShortComparator comparator) {
	 return new Singleton(((Short)(element)).shortValue(), comparator);
	}
	/** A synchronized wrapper class for sorted sets. */
	public static class SynchronizedSortedSet extends ShortSets.SynchronizedSet implements ShortSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final ShortSortedSet sortedSet;
	 protected SynchronizedSortedSet(final ShortSortedSet s, final Object sync) {
	  super(s, sync);
	  sortedSet = s;
	 }
	 protected SynchronizedSortedSet(final ShortSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public ShortComparator comparator() { synchronized(sync) { return sortedSet.comparator(); } }
	 @Override
	 public ShortSortedSet subSet(final short from, final short to) { return new SynchronizedSortedSet (sortedSet.subSet(from, to), sync); }
	 @Override
	 public ShortSortedSet headSet(final short to) { return new SynchronizedSortedSet (sortedSet.headSet(to), sync); }
	 @Override
	 public ShortSortedSet tailSet(final short from) { return new SynchronizedSortedSet (sortedSet.tailSet(from), sync); }
	 @Override
	 public ShortBidirectionalIterator iterator() { return sortedSet.iterator(); }
	 @Override
	 public ShortBidirectionalIterator iterator(final short from) { return sortedSet.iterator(from); }
	 @Override
	 public short firstShort() { synchronized(sync) { return sortedSet.firstShort(); } }
	 @Override
	 public short lastShort() { synchronized(sync) { return sortedSet.lastShort(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Short first() { synchronized(sync) { return sortedSet.first(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Short last() { synchronized(sync) { return sortedSet.last(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ShortSortedSet subSet(final Short from, final Short to) { return new SynchronizedSortedSet(sortedSet.subSet(from, to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ShortSortedSet headSet(final Short to) { return new SynchronizedSortedSet(sortedSet.headSet(to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ShortSortedSet tailSet(final Short from) { return new SynchronizedSortedSet(sortedSet.tailSet(from), sync); }
	}
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static ShortSortedSet synchronize(final ShortSortedSet s) { return new SynchronizedSortedSet (s); }
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set, using an assigned object to synchronize.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @param sync an object that will be used to synchronize the access to the sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static ShortSortedSet synchronize(final ShortSortedSet s, final Object sync) { return new SynchronizedSortedSet (s, sync); }
	/** An unmodifiable wrapper class for sorted sets. */
	public static class UnmodifiableSortedSet extends ShortSets.UnmodifiableSet implements ShortSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final ShortSortedSet sortedSet;
	 protected UnmodifiableSortedSet(final ShortSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public ShortComparator comparator() { return sortedSet.comparator(); }
	 @Override
	 public ShortSortedSet subSet(final short from, final short to) { return new UnmodifiableSortedSet (sortedSet.subSet(from, to)); }
	 @Override
	 public ShortSortedSet headSet(final short to) { return new UnmodifiableSortedSet (sortedSet.headSet(to)); }
	 @Override
	 public ShortSortedSet tailSet(final short from) { return new UnmodifiableSortedSet (sortedSet.tailSet(from)); }
	 @Override
	 public ShortBidirectionalIterator iterator() { return ShortIterators.unmodifiable(sortedSet.iterator()); }
	 @Override
	 public ShortBidirectionalIterator iterator(final short from) { return ShortIterators.unmodifiable(sortedSet.iterator(from)); }
	 @Override
	 public short firstShort() { return sortedSet.firstShort(); }
	 @Override
	 public short lastShort() { return sortedSet.lastShort(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Short first() { return sortedSet.first(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Short last() { return sortedSet.last(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ShortSortedSet subSet(final Short from, final Short to) { return new UnmodifiableSortedSet(sortedSet.subSet(from, to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ShortSortedSet headSet(final Short to) { return new UnmodifiableSortedSet(sortedSet.headSet(to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ShortSortedSet tailSet(final Short from) { return new UnmodifiableSortedSet(sortedSet.tailSet(from)); }
	}
	/** Returns an unmodifiable type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in an unmodifiable sorted set.
	 * @return an unmodifiable view of the specified sorted set.
	 * @see java.util.Collections#unmodifiableSortedSet(SortedSet)
	 */
	public static ShortSortedSet unmodifiable(final ShortSortedSet s) { return new UnmodifiableSortedSet (s); }
}
