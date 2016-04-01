/* Copyright (C) 1991-2014 Free Software Foundation, Inc.
   This file is part of the GNU C Library.

   The GNU C Library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   The GNU C Library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with the GNU C Library; if not, see
   <http://www.gnu.org/licenses/>.  */
/* This header is separate from features.h so that the compiler can
   include it implicitly at the start of every compilation.  It must
   not itself include <features.h> or any other header that includes
   <features.h> because the implicit include comes before any feature
   test macros that may be defined in a source file before it first
   explicitly includes a system header.  GCC knows the name of this
   header in order to preinclude it.  */
/* glibc's intent is to support the IEC 559 math functionality, real
   and complex.  If the GCC (4.9 and later) predefined macros
   specifying compiler intent are available, use them to determine
   whether the overall intent is to support these features; otherwise,
   presume an older compiler has intent to support these features and
   define these macros by default.  */
/* wchar_t uses ISO/IEC 10646 (2nd ed., published 2011-03-15) /
   Unicode 6.0.  */
/* We do not support C11 <threads.h>.  */
/* Generic definitions */
/* Assertions (useful to generate conditional code) */
/* Current type and class (and size, if applicable) */
/* Value methods */
/* Interfaces (keys) */
/* Interfaces (values) */
/* Abstract implementations (keys) */
/* Abstract implementations (values) */
/* Static containers (keys) */
/* Static containers (values) */
/* Implementations */
/* Synchronized wrappers */
/* Unmodifiable wrappers */
/* Other wrappers */
/* Methods (keys) */
/* Methods (values) */
/* Methods (keys/values) */
/* Methods that have special names depending on keys (but the special names depend on values) */
/* Equality */
/* Object/Reference-only definitions (keys) */
/* Primitive-type-only definitions (keys) */
/* Object/Reference-only definitions (values) */
/*		 
 * Copyright (C) 2002-2016 Sebastiano Vigna
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
public class LongSortedSets {
 private LongSortedSets() {}
 /** An immutable class representing the empty sorted set and implementing a type-specific set interface.
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
 public static class EmptySet extends LongSets.EmptySet implements LongSortedSet , java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected EmptySet() {}
  public boolean remove( long ok ) { throw new UnsupportedOperationException(); }
  @Deprecated
  public LongBidirectionalIterator longIterator() { return iterator(); }
 
  public LongBidirectionalIterator iterator( long from ) { return LongIterators.EMPTY_ITERATOR; }
 
  public LongSortedSet subSet( long from, long to ) { return EMPTY_SET; }
 
  public LongSortedSet headSet( long from ) { return EMPTY_SET; }
 
  public LongSortedSet tailSet( long to ) { return EMPTY_SET; }
  public long firstLong() { throw new NoSuchElementException(); }
  public long lastLong() { throw new NoSuchElementException(); }
  public LongComparator comparator() { return null; }
  public LongSortedSet subSet( Long from, Long to ) { return EMPTY_SET; }
  public LongSortedSet headSet( Long from ) { return EMPTY_SET; }
  public LongSortedSet tailSet( Long to ) { return EMPTY_SET; }
  public Long first() { throw new NoSuchElementException(); }
  public Long last() { throw new NoSuchElementException(); }
  public Object clone() { return EMPTY_SET; }
        private Object readResolve() { return EMPTY_SET; }
 }
 /** An empty sorted set (immutable). It is serializable and cloneable.
	 *
	 */

 public static final EmptySet EMPTY_SET = new EmptySet();
 /** A class representing a singleton sorted set.
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
 public static class Singleton extends LongSets.Singleton implements LongSortedSet , java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  final LongComparator comparator;
  private Singleton( final long element, final LongComparator comparator ) {
   super( element );
   this.comparator = comparator;
  }
  private Singleton( final long element ) {
   this( element, null );
  }
 
  final int compare( final long k1, final long k2 ) {
   return comparator == null ? ( Long.compare((k1),(k2)) ) : comparator.compare( k1, k2 );
  }
  @Deprecated
  public LongBidirectionalIterator longIterator() {
   return iterator();
  }
  public LongBidirectionalIterator iterator( long from ) {
   LongBidirectionalIterator i = iterator();
   if ( compare( element, from ) <= 0 ) i.next();
   return i;
  }
  public LongComparator comparator() { return comparator; }
 
  public LongSortedSet subSet( final long from, final long to ) { if ( compare( from, element ) <= 0 && compare( element, to ) < 0 ) return this; return EMPTY_SET; }
 
  public LongSortedSet headSet( final long to ) { if ( compare( element, to ) < 0 ) return this; return EMPTY_SET; }
 
  public LongSortedSet tailSet( final long from ) { if ( compare( from, element ) <= 0 ) return this; return EMPTY_SET; }
  public long firstLong() { return element; }
  public long lastLong() { return element; }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Long first() { return (Long.valueOf(element)); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Long last() { return (Long.valueOf(element)); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public LongSortedSet subSet( final Long from, final Long to ) { return subSet( ((from).longValue()), ((to).longValue()) ); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public LongSortedSet headSet( final Long to ) { return headSet( ((to).longValue()) ); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public LongSortedSet tailSet( final Long from ) { return tailSet( ((from).longValue()) ); }
 }
 /** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just <code>element</code>.
	 */
 public static LongSortedSet singleton( final long element ) {
  return new Singleton ( element );
 }
 /** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just <code>element</code>.
	 */
 public static LongSortedSet singleton( final long element, final LongComparator comparator ) {
  return new Singleton ( element, comparator );
 }
 /** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just <code>element</code>.
	 */
 public static LongSortedSet singleton( final Object element ) {
  return new Singleton( ((((Long)(element)).longValue())) );
 }
 /** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just <code>element</code>.
	 */
 public static LongSortedSet singleton( final Object element, final LongComparator comparator ) {
  return new Singleton( ((((Long)(element)).longValue())), comparator );
 }
 /** A synchronized wrapper class for sorted sets. */
 public static class SynchronizedSortedSet extends LongSets.SynchronizedSet implements LongSortedSet , java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final LongSortedSet sortedSet;
  protected SynchronizedSortedSet( final LongSortedSet s, final Object sync ) {
   super( s, sync );
   sortedSet = s;
  }
  protected SynchronizedSortedSet( final LongSortedSet s ) {
   super( s );
   sortedSet = s;
  }
  public LongComparator comparator() { synchronized( sync ) { return sortedSet.comparator(); } }
  public LongSortedSet subSet( final long from, final long to ) { return new SynchronizedSortedSet ( sortedSet.subSet( from, to ), sync ); }
  public LongSortedSet headSet( final long to ) { return new SynchronizedSortedSet ( sortedSet.headSet( to ), sync ); }
  public LongSortedSet tailSet( final long from ) { return new SynchronizedSortedSet ( sortedSet.tailSet( from ), sync ); }
  public LongBidirectionalIterator iterator() { return sortedSet.iterator(); }
  public LongBidirectionalIterator iterator( final long from ) { return sortedSet.iterator( from ); }
  @Deprecated
  public LongBidirectionalIterator longIterator() { return sortedSet.iterator(); }
  public long firstLong() { synchronized( sync ) { return sortedSet.firstLong(); } }
  public long lastLong() { synchronized( sync ) { return sortedSet.lastLong(); } }
  public Long first() { synchronized( sync ) { return sortedSet.first(); } }
  public Long last() { synchronized( sync ) { return sortedSet.last(); } }
  public LongSortedSet subSet( final Long from, final Long to ) { return new SynchronizedSortedSet( sortedSet.subSet( from, to ), sync ); }
  public LongSortedSet headSet( final Long to ) { return new SynchronizedSortedSet( sortedSet.headSet( to ), sync ); }
  public LongSortedSet tailSet( final Long from ) { return new SynchronizedSortedSet( sortedSet.tailSet( from ), sync ); }
 }
 /** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
 public static LongSortedSet synchronize( final LongSortedSet s ) { return new SynchronizedSortedSet ( s ); }
 /** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set, using an assigned object to synchronize.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @param sync an object that will be used to synchronize the access to the sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
 public static LongSortedSet synchronize( final LongSortedSet s, final Object sync ) { return new SynchronizedSortedSet ( s, sync ); }
 /** An unmodifiable wrapper class for sorted sets. */
 public static class UnmodifiableSortedSet extends LongSets.UnmodifiableSet implements LongSortedSet , java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final LongSortedSet sortedSet;
  protected UnmodifiableSortedSet( final LongSortedSet s ) {
   super( s );
   sortedSet = s;
  }
  public LongComparator comparator() { return sortedSet.comparator(); }
  public LongSortedSet subSet( final long from, final long to ) { return new UnmodifiableSortedSet ( sortedSet.subSet( from, to ) ); }
  public LongSortedSet headSet( final long to ) { return new UnmodifiableSortedSet ( sortedSet.headSet( to ) ); }
  public LongSortedSet tailSet( final long from ) { return new UnmodifiableSortedSet ( sortedSet.tailSet( from ) ); }
  public LongBidirectionalIterator iterator() { return LongIterators.unmodifiable( sortedSet.iterator() ); }
  public LongBidirectionalIterator iterator( final long from ) { return LongIterators.unmodifiable( sortedSet.iterator( from ) ); }
  @Deprecated
  public LongBidirectionalIterator longIterator() { return iterator(); }
  public long firstLong() { return sortedSet.firstLong(); }
  public long lastLong() { return sortedSet.lastLong(); }
  public Long first() { return sortedSet.first(); }
  public Long last() { return sortedSet.last(); }
  public LongSortedSet subSet( final Long from, final Long to ) { return new UnmodifiableSortedSet( sortedSet.subSet( from, to ) ); }
  public LongSortedSet headSet( final Long to ) { return new UnmodifiableSortedSet( sortedSet.headSet( to ) ); }
  public LongSortedSet tailSet( final Long from ) { return new UnmodifiableSortedSet( sortedSet.tailSet( from ) ); }
 }
 /** Returns an unmodifiable type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in an unmodifiable sorted set.
	 * @return an unmodifiable view of the specified sorted set.
	 * @see java.util.Collections#unmodifiableSortedSet(SortedSet)
	 */
 public static LongSortedSet unmodifiable( final LongSortedSet s ) { return new UnmodifiableSortedSet ( s ); }
}
