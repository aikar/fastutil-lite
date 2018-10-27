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
package it.unimi.dsi.fastutil.chars;
import java.util.SortedSet;
import java.util.NoSuchElementException;
/** A class providing static methods and objects that do useful things with type-specific sorted sets.
	*
	* @see java.util.Collections
	*/
public final class CharSortedSets {
	private CharSortedSets() {}
	/** An immutable class representing the empty sorted set and implementing a type-specific set interface.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
	public static class EmptySet extends CharSets.EmptySet implements CharSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptySet() {}
	 @Override
	
	 public CharBidirectionalIterator iterator(char from) { return CharIterators.EMPTY_ITERATOR; }
	 @Override
	
	 public CharSortedSet subSet(char from, char to) { return EMPTY_SET; }
	 @Override
	
	 public CharSortedSet headSet(char from) { return EMPTY_SET; }
	 @Override
	
	 public CharSortedSet tailSet(char to) { return EMPTY_SET; }
	 @Override
	 public char firstChar() { throw new NoSuchElementException(); }
	 @Override
	 public char lastChar() { throw new NoSuchElementException(); }
	 @Override
	 public CharComparator comparator() { return null; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public CharSortedSet subSet(Character from, Character to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public CharSortedSet headSet(Character from) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public CharSortedSet tailSet(Character to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character first() { throw new NoSuchElementException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character last() { throw new NoSuchElementException(); }
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
	public static class Singleton extends CharSets.Singleton implements CharSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 final CharComparator comparator;
	 protected Singleton(final char element, final CharComparator comparator) {
	  super(element);
	  this.comparator = comparator;
	 }
	 private Singleton(final char element) {
	  this(element, null);
	 }
	
	 final int compare(final char k1, final char k2) {
	  return comparator == null ? ( Character.compare((k1),(k2)) ) : comparator.compare(k1, k2);
	 }
	 @Override
	 public CharBidirectionalIterator iterator(char from) {
	  CharBidirectionalIterator i = iterator();
	  if (compare(element, from) <= 0) i.nextChar();
	  return i;
	 }
	 @Override
	 public CharComparator comparator() { return comparator; }
	 @Override
	
	 public CharSortedSet subSet(final char from, final char to) { if (compare(from, element) <= 0 && compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public CharSortedSet headSet(final char to) { if (compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public CharSortedSet tailSet(final char from) { if (compare(from, element) <= 0) return this; return EMPTY_SET; }
	 @Override
	 public char firstChar() { return element; }
	 @Override
	 public char lastChar() { return element; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public CharSortedSet subSet(final Character from, final Character to) { return subSet((from).charValue(), (to).charValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public CharSortedSet headSet(final Character to) { return headSet((to).charValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public CharSortedSet tailSet(final Character from) { return tailSet((from).charValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character first() { return Character.valueOf(element); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character last() { return Character.valueOf(element); }
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static CharSortedSet singleton(final char element) {
	 return new Singleton (element);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static CharSortedSet singleton(final char element, final CharComparator comparator) {
	 return new Singleton (element, comparator);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static CharSortedSet singleton(final Object element) {
	 return new Singleton(((Character)(element)).charValue());
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static CharSortedSet singleton(final Object element, final CharComparator comparator) {
	 return new Singleton(((Character)(element)).charValue(), comparator);
	}
	/** A synchronized wrapper class for sorted sets. */
	public static class SynchronizedSortedSet extends CharSets.SynchronizedSet implements CharSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final CharSortedSet sortedSet;
	 protected SynchronizedSortedSet(final CharSortedSet s, final Object sync) {
	  super(s, sync);
	  sortedSet = s;
	 }
	 protected SynchronizedSortedSet(final CharSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public CharComparator comparator() { synchronized(sync) { return sortedSet.comparator(); } }
	 @Override
	 public CharSortedSet subSet(final char from, final char to) { return new SynchronizedSortedSet (sortedSet.subSet(from, to), sync); }
	 @Override
	 public CharSortedSet headSet(final char to) { return new SynchronizedSortedSet (sortedSet.headSet(to), sync); }
	 @Override
	 public CharSortedSet tailSet(final char from) { return new SynchronizedSortedSet (sortedSet.tailSet(from), sync); }
	 @Override
	 public CharBidirectionalIterator iterator() { return sortedSet.iterator(); }
	 @Override
	 public CharBidirectionalIterator iterator(final char from) { return sortedSet.iterator(from); }
	 @Override
	 public char firstChar() { synchronized(sync) { return sortedSet.firstChar(); } }
	 @Override
	 public char lastChar() { synchronized(sync) { return sortedSet.lastChar(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character first() { synchronized(sync) { return sortedSet.first(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character last() { synchronized(sync) { return sortedSet.last(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public CharSortedSet subSet(final Character from, final Character to) { return new SynchronizedSortedSet(sortedSet.subSet(from, to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public CharSortedSet headSet(final Character to) { return new SynchronizedSortedSet(sortedSet.headSet(to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public CharSortedSet tailSet(final Character from) { return new SynchronizedSortedSet(sortedSet.tailSet(from), sync); }
	}
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static CharSortedSet synchronize(final CharSortedSet s) { return new SynchronizedSortedSet (s); }
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set, using an assigned object to synchronize.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @param sync an object that will be used to synchronize the access to the sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static CharSortedSet synchronize(final CharSortedSet s, final Object sync) { return new SynchronizedSortedSet (s, sync); }
	/** An unmodifiable wrapper class for sorted sets. */
	public static class UnmodifiableSortedSet extends CharSets.UnmodifiableSet implements CharSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final CharSortedSet sortedSet;
	 protected UnmodifiableSortedSet(final CharSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public CharComparator comparator() { return sortedSet.comparator(); }
	 @Override
	 public CharSortedSet subSet(final char from, final char to) { return new UnmodifiableSortedSet (sortedSet.subSet(from, to)); }
	 @Override
	 public CharSortedSet headSet(final char to) { return new UnmodifiableSortedSet (sortedSet.headSet(to)); }
	 @Override
	 public CharSortedSet tailSet(final char from) { return new UnmodifiableSortedSet (sortedSet.tailSet(from)); }
	 @Override
	 public CharBidirectionalIterator iterator() { return CharIterators.unmodifiable(sortedSet.iterator()); }
	 @Override
	 public CharBidirectionalIterator iterator(final char from) { return CharIterators.unmodifiable(sortedSet.iterator(from)); }
	 @Override
	 public char firstChar() { return sortedSet.firstChar(); }
	 @Override
	 public char lastChar() { return sortedSet.lastChar(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character first() { return sortedSet.first(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character last() { return sortedSet.last(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public CharSortedSet subSet(final Character from, final Character to) { return new UnmodifiableSortedSet(sortedSet.subSet(from, to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public CharSortedSet headSet(final Character to) { return new UnmodifiableSortedSet(sortedSet.headSet(to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public CharSortedSet tailSet(final Character from) { return new UnmodifiableSortedSet(sortedSet.tailSet(from)); }
	}
	/** Returns an unmodifiable type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in an unmodifiable sorted set.
	 * @return an unmodifiable view of the specified sorted set.
	 * @see java.util.Collections#unmodifiableSortedSet(SortedSet)
	 */
	public static CharSortedSet unmodifiable(final CharSortedSet s) { return new UnmodifiableSortedSet (s); }
}
