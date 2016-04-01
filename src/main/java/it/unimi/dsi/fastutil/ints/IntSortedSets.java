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
package it.unimi.dsi.fastutil.ints;
import java.util.SortedSet;
import java.util.NoSuchElementException;
/** A class providing static methods and objects that do useful things with type-specific sorted sets.
 *
 * @see java.util.Collections
 */
public class IntSortedSets {
 private IntSortedSets() {}
 /** An immutable class representing the empty sorted set and implementing a type-specific set interface.
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
 public static class EmptySet extends IntSets.EmptySet implements IntSortedSet , java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected EmptySet() {}
  public boolean remove( int ok ) { throw new UnsupportedOperationException(); }
  @Deprecated
  public IntBidirectionalIterator intIterator() { return iterator(); }
 
  public IntBidirectionalIterator iterator( int from ) { return IntIterators.EMPTY_ITERATOR; }
 
  public IntSortedSet subSet( int from, int to ) { return EMPTY_SET; }
 
  public IntSortedSet headSet( int from ) { return EMPTY_SET; }
 
  public IntSortedSet tailSet( int to ) { return EMPTY_SET; }
  public int firstInt() { throw new NoSuchElementException(); }
  public int lastInt() { throw new NoSuchElementException(); }
  public IntComparator comparator() { return null; }
  public IntSortedSet subSet( Integer from, Integer to ) { return EMPTY_SET; }
  public IntSortedSet headSet( Integer from ) { return EMPTY_SET; }
  public IntSortedSet tailSet( Integer to ) { return EMPTY_SET; }
  public Integer first() { throw new NoSuchElementException(); }
  public Integer last() { throw new NoSuchElementException(); }
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
 public static class Singleton extends IntSets.Singleton implements IntSortedSet , java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  final IntComparator comparator;
  private Singleton( final int element, final IntComparator comparator ) {
   super( element );
   this.comparator = comparator;
  }
  private Singleton( final int element ) {
   this( element, null );
  }
 
  final int compare( final int k1, final int k2 ) {
   return comparator == null ? ( Integer.compare((k1),(k2)) ) : comparator.compare( k1, k2 );
  }
  @Deprecated
  public IntBidirectionalIterator intIterator() {
   return iterator();
  }
  public IntBidirectionalIterator iterator( int from ) {
   IntBidirectionalIterator i = iterator();
   if ( compare( element, from ) <= 0 ) i.next();
   return i;
  }
  public IntComparator comparator() { return comparator; }
 
  public IntSortedSet subSet( final int from, final int to ) { if ( compare( from, element ) <= 0 && compare( element, to ) < 0 ) return this; return EMPTY_SET; }
 
  public IntSortedSet headSet( final int to ) { if ( compare( element, to ) < 0 ) return this; return EMPTY_SET; }
 
  public IntSortedSet tailSet( final int from ) { if ( compare( from, element ) <= 0 ) return this; return EMPTY_SET; }
  public int firstInt() { return element; }
  public int lastInt() { return element; }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Integer first() { return (Integer.valueOf(element)); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Integer last() { return (Integer.valueOf(element)); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public IntSortedSet subSet( final Integer from, final Integer to ) { return subSet( ((from).intValue()), ((to).intValue()) ); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public IntSortedSet headSet( final Integer to ) { return headSet( ((to).intValue()) ); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public IntSortedSet tailSet( final Integer from ) { return tailSet( ((from).intValue()) ); }
 }
 /** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just <code>element</code>.
	 */
 public static IntSortedSet singleton( final int element ) {
  return new Singleton ( element );
 }
 /** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just <code>element</code>.
	 */
 public static IntSortedSet singleton( final int element, final IntComparator comparator ) {
  return new Singleton ( element, comparator );
 }
 /** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just <code>element</code>.
	 */
 public static IntSortedSet singleton( final Object element ) {
  return new Singleton( ((((Integer)(element)).intValue())) );
 }
 /** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just <code>element</code>.
	 */
 public static IntSortedSet singleton( final Object element, final IntComparator comparator ) {
  return new Singleton( ((((Integer)(element)).intValue())), comparator );
 }
 /** A synchronized wrapper class for sorted sets. */
 public static class SynchronizedSortedSet extends IntSets.SynchronizedSet implements IntSortedSet , java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final IntSortedSet sortedSet;
  protected SynchronizedSortedSet( final IntSortedSet s, final Object sync ) {
   super( s, sync );
   sortedSet = s;
  }
  protected SynchronizedSortedSet( final IntSortedSet s ) {
   super( s );
   sortedSet = s;
  }
  public IntComparator comparator() { synchronized( sync ) { return sortedSet.comparator(); } }
  public IntSortedSet subSet( final int from, final int to ) { return new SynchronizedSortedSet ( sortedSet.subSet( from, to ), sync ); }
  public IntSortedSet headSet( final int to ) { return new SynchronizedSortedSet ( sortedSet.headSet( to ), sync ); }
  public IntSortedSet tailSet( final int from ) { return new SynchronizedSortedSet ( sortedSet.tailSet( from ), sync ); }
  public IntBidirectionalIterator iterator() { return sortedSet.iterator(); }
  public IntBidirectionalIterator iterator( final int from ) { return sortedSet.iterator( from ); }
  @Deprecated
  public IntBidirectionalIterator intIterator() { return sortedSet.iterator(); }
  public int firstInt() { synchronized( sync ) { return sortedSet.firstInt(); } }
  public int lastInt() { synchronized( sync ) { return sortedSet.lastInt(); } }
  public Integer first() { synchronized( sync ) { return sortedSet.first(); } }
  public Integer last() { synchronized( sync ) { return sortedSet.last(); } }
  public IntSortedSet subSet( final Integer from, final Integer to ) { return new SynchronizedSortedSet( sortedSet.subSet( from, to ), sync ); }
  public IntSortedSet headSet( final Integer to ) { return new SynchronizedSortedSet( sortedSet.headSet( to ), sync ); }
  public IntSortedSet tailSet( final Integer from ) { return new SynchronizedSortedSet( sortedSet.tailSet( from ), sync ); }
 }
 /** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
 public static IntSortedSet synchronize( final IntSortedSet s ) { return new SynchronizedSortedSet ( s ); }
 /** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set, using an assigned object to synchronize.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @param sync an object that will be used to synchronize the access to the sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
 public static IntSortedSet synchronize( final IntSortedSet s, final Object sync ) { return new SynchronizedSortedSet ( s, sync ); }
 /** An unmodifiable wrapper class for sorted sets. */
 public static class UnmodifiableSortedSet extends IntSets.UnmodifiableSet implements IntSortedSet , java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final IntSortedSet sortedSet;
  protected UnmodifiableSortedSet( final IntSortedSet s ) {
   super( s );
   sortedSet = s;
  }
  public IntComparator comparator() { return sortedSet.comparator(); }
  public IntSortedSet subSet( final int from, final int to ) { return new UnmodifiableSortedSet ( sortedSet.subSet( from, to ) ); }
  public IntSortedSet headSet( final int to ) { return new UnmodifiableSortedSet ( sortedSet.headSet( to ) ); }
  public IntSortedSet tailSet( final int from ) { return new UnmodifiableSortedSet ( sortedSet.tailSet( from ) ); }
  public IntBidirectionalIterator iterator() { return IntIterators.unmodifiable( sortedSet.iterator() ); }
  public IntBidirectionalIterator iterator( final int from ) { return IntIterators.unmodifiable( sortedSet.iterator( from ) ); }
  @Deprecated
  public IntBidirectionalIterator intIterator() { return iterator(); }
  public int firstInt() { return sortedSet.firstInt(); }
  public int lastInt() { return sortedSet.lastInt(); }
  public Integer first() { return sortedSet.first(); }
  public Integer last() { return sortedSet.last(); }
  public IntSortedSet subSet( final Integer from, final Integer to ) { return new UnmodifiableSortedSet( sortedSet.subSet( from, to ) ); }
  public IntSortedSet headSet( final Integer to ) { return new UnmodifiableSortedSet( sortedSet.headSet( to ) ); }
  public IntSortedSet tailSet( final Integer from ) { return new UnmodifiableSortedSet( sortedSet.tailSet( from ) ); }
 }
 /** Returns an unmodifiable type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in an unmodifiable sorted set.
	 * @return an unmodifiable view of the specified sorted set.
	 * @see java.util.Collections#unmodifiableSortedSet(SortedSet)
	 */
 public static IntSortedSet unmodifiable( final IntSortedSet s ) { return new UnmodifiableSortedSet ( s ); }
}
