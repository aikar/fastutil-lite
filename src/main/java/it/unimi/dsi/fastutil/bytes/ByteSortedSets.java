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
package it.unimi.dsi.fastutil.bytes;
import java.util.SortedSet;
import java.util.NoSuchElementException;
/** A class providing static methods and objects that do useful things with type-specific sorted sets.
	*
	* @see java.util.Collections
	*/
public final class ByteSortedSets {
	private ByteSortedSets() {}
	/** An immutable class representing the empty sorted set and implementing a type-specific set interface.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
	public static class EmptySet extends ByteSets.EmptySet implements ByteSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptySet() {}
	 @Override
	
	 public ByteBidirectionalIterator iterator(byte from) { return ByteIterators.EMPTY_ITERATOR; }
	 @Override
	
	 public ByteSortedSet subSet(byte from, byte to) { return EMPTY_SET; }
	 @Override
	
	 public ByteSortedSet headSet(byte from) { return EMPTY_SET; }
	 @Override
	
	 public ByteSortedSet tailSet(byte to) { return EMPTY_SET; }
	 @Override
	 public byte firstByte() { throw new NoSuchElementException(); }
	 @Override
	 public byte lastByte() { throw new NoSuchElementException(); }
	 @Override
	 public ByteComparator comparator() { return null; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ByteSortedSet subSet(Byte from, Byte to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ByteSortedSet headSet(Byte from) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ByteSortedSet tailSet(Byte to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Byte first() { throw new NoSuchElementException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Byte last() { throw new NoSuchElementException(); }
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
	public static class Singleton extends ByteSets.Singleton implements ByteSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 final ByteComparator comparator;
	 protected Singleton(final byte element, final ByteComparator comparator) {
	  super(element);
	  this.comparator = comparator;
	 }
	 private Singleton(final byte element) {
	  this(element, null);
	 }
	
	 final int compare(final byte k1, final byte k2) {
	  return comparator == null ? ( Byte.compare((k1),(k2)) ) : comparator.compare(k1, k2);
	 }
	 @Override
	 public ByteBidirectionalIterator iterator(byte from) {
	  ByteBidirectionalIterator i = iterator();
	  if (compare(element, from) <= 0) i.nextByte();
	  return i;
	 }
	 @Override
	 public ByteComparator comparator() { return comparator; }
	 @Override
	
	 public ByteSortedSet subSet(final byte from, final byte to) { if (compare(from, element) <= 0 && compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public ByteSortedSet headSet(final byte to) { if (compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public ByteSortedSet tailSet(final byte from) { if (compare(from, element) <= 0) return this; return EMPTY_SET; }
	 @Override
	 public byte firstByte() { return element; }
	 @Override
	 public byte lastByte() { return element; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ByteSortedSet subSet(final Byte from, final Byte to) { return subSet((from).byteValue(), (to).byteValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ByteSortedSet headSet(final Byte to) { return headSet((to).byteValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ByteSortedSet tailSet(final Byte from) { return tailSet((from).byteValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Byte first() { return Byte.valueOf(element); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Byte last() { return Byte.valueOf(element); }
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static ByteSortedSet singleton(final byte element) {
	 return new Singleton (element);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static ByteSortedSet singleton(final byte element, final ByteComparator comparator) {
	 return new Singleton (element, comparator);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static ByteSortedSet singleton(final Object element) {
	 return new Singleton(((Byte)(element)).byteValue());
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static ByteSortedSet singleton(final Object element, final ByteComparator comparator) {
	 return new Singleton(((Byte)(element)).byteValue(), comparator);
	}
	/** A synchronized wrapper class for sorted sets. */
	public static class SynchronizedSortedSet extends ByteSets.SynchronizedSet implements ByteSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final ByteSortedSet sortedSet;
	 protected SynchronizedSortedSet(final ByteSortedSet s, final Object sync) {
	  super(s, sync);
	  sortedSet = s;
	 }
	 protected SynchronizedSortedSet(final ByteSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public ByteComparator comparator() { synchronized(sync) { return sortedSet.comparator(); } }
	 @Override
	 public ByteSortedSet subSet(final byte from, final byte to) { return new SynchronizedSortedSet (sortedSet.subSet(from, to), sync); }
	 @Override
	 public ByteSortedSet headSet(final byte to) { return new SynchronizedSortedSet (sortedSet.headSet(to), sync); }
	 @Override
	 public ByteSortedSet tailSet(final byte from) { return new SynchronizedSortedSet (sortedSet.tailSet(from), sync); }
	 @Override
	 public ByteBidirectionalIterator iterator() { return sortedSet.iterator(); }
	 @Override
	 public ByteBidirectionalIterator iterator(final byte from) { return sortedSet.iterator(from); }
	 @Override
	 public byte firstByte() { synchronized(sync) { return sortedSet.firstByte(); } }
	 @Override
	 public byte lastByte() { synchronized(sync) { return sortedSet.lastByte(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Byte first() { synchronized(sync) { return sortedSet.first(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Byte last() { synchronized(sync) { return sortedSet.last(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ByteSortedSet subSet(final Byte from, final Byte to) { return new SynchronizedSortedSet(sortedSet.subSet(from, to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ByteSortedSet headSet(final Byte to) { return new SynchronizedSortedSet(sortedSet.headSet(to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ByteSortedSet tailSet(final Byte from) { return new SynchronizedSortedSet(sortedSet.tailSet(from), sync); }
	}
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static ByteSortedSet synchronize(final ByteSortedSet s) { return new SynchronizedSortedSet (s); }
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set, using an assigned object to synchronize.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @param sync an object that will be used to synchronize the access to the sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static ByteSortedSet synchronize(final ByteSortedSet s, final Object sync) { return new SynchronizedSortedSet (s, sync); }
	/** An unmodifiable wrapper class for sorted sets. */
	public static class UnmodifiableSortedSet extends ByteSets.UnmodifiableSet implements ByteSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final ByteSortedSet sortedSet;
	 protected UnmodifiableSortedSet(final ByteSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public ByteComparator comparator() { return sortedSet.comparator(); }
	 @Override
	 public ByteSortedSet subSet(final byte from, final byte to) { return new UnmodifiableSortedSet (sortedSet.subSet(from, to)); }
	 @Override
	 public ByteSortedSet headSet(final byte to) { return new UnmodifiableSortedSet (sortedSet.headSet(to)); }
	 @Override
	 public ByteSortedSet tailSet(final byte from) { return new UnmodifiableSortedSet (sortedSet.tailSet(from)); }
	 @Override
	 public ByteBidirectionalIterator iterator() { return ByteIterators.unmodifiable(sortedSet.iterator()); }
	 @Override
	 public ByteBidirectionalIterator iterator(final byte from) { return ByteIterators.unmodifiable(sortedSet.iterator(from)); }
	 @Override
	 public byte firstByte() { return sortedSet.firstByte(); }
	 @Override
	 public byte lastByte() { return sortedSet.lastByte(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Byte first() { return sortedSet.first(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Byte last() { return sortedSet.last(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ByteSortedSet subSet(final Byte from, final Byte to) { return new UnmodifiableSortedSet(sortedSet.subSet(from, to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ByteSortedSet headSet(final Byte to) { return new UnmodifiableSortedSet(sortedSet.headSet(to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public ByteSortedSet tailSet(final Byte from) { return new UnmodifiableSortedSet(sortedSet.tailSet(from)); }
	}
	/** Returns an unmodifiable type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in an unmodifiable sorted set.
	 * @return an unmodifiable view of the specified sorted set.
	 * @see java.util.Collections#unmodifiableSortedSet(SortedSet)
	 */
	public static ByteSortedSet unmodifiable(final ByteSortedSet s) { return new UnmodifiableSortedSet (s); }
}
