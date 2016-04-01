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
package it.unimi.dsi.fastutil.objects;
import java.util.SortedSet;
import java.util.NoSuchElementException;
import java.util.Comparator;
/** A class providing static methods and objects that do useful things with type-specific sorted sets.
 *
 * @see java.util.Collections
 */
public class ObjectSortedSets {
 private ObjectSortedSets() {}
 /** An immutable class representing the empty sorted set and implementing a type-specific set interface.
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
 public static class EmptySet <K> extends ObjectSets.EmptySet <K> implements ObjectSortedSet <K>, java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected EmptySet() {}
  public boolean remove( Object ok ) { throw new UnsupportedOperationException(); }
  @Deprecated
  public ObjectBidirectionalIterator <K> objectIterator() { return iterator(); }
  @SuppressWarnings("unchecked")
  public ObjectBidirectionalIterator <K> iterator( K from ) { return ObjectIterators.EMPTY_ITERATOR; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet <K> subSet( K from, K to ) { return EMPTY_SET; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet <K> headSet( K from ) { return EMPTY_SET; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet <K> tailSet( K to ) { return EMPTY_SET; }
  public K first() { throw new NoSuchElementException(); }
  public K last() { throw new NoSuchElementException(); }
  public Comparator <? super K> comparator() { return null; }
  public Object clone() { return EMPTY_SET; }
        private Object readResolve() { return EMPTY_SET; }
 }
 /** An empty sorted set (immutable). It is serializable and cloneable.
	 *
	 */
 @SuppressWarnings("rawtypes")
 public static final EmptySet EMPTY_SET = new EmptySet();
 /** Return an empty sorted set (immutable). It is serializable and cloneable.
	 *
	 * <P>This method provides a typesafe access to {@link #EMPTY_SET}.
	 * @return an empty sorted set (immutable).
	 */
 @SuppressWarnings("unchecked")
 public static <K> ObjectSet <K> emptySet() {
  return EMPTY_SET;
 }
 /** A class representing a singleton sorted set.
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
 public static class Singleton <K> extends ObjectSets.Singleton <K> implements ObjectSortedSet <K>, java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  final Comparator <? super K> comparator;
  private Singleton( final K element, final Comparator <? super K> comparator ) {
   super( element );
   this.comparator = comparator;
  }
  private Singleton( final K element ) {
   this( element, null );
  }
  @SuppressWarnings("unchecked")
  final int compare( final K k1, final K k2 ) {
   return comparator == null ? ( ((Comparable<K>)(k1)).compareTo(k2) ) : comparator.compare( k1, k2 );
  }
  @Deprecated
  public ObjectBidirectionalIterator <K> objectIterator() {
   return iterator();
  }
  public ObjectBidirectionalIterator <K> iterator( K from ) {
   ObjectBidirectionalIterator <K> i = iterator();
   if ( compare( element, from ) <= 0 ) i.next();
   return i;
  }
  public Comparator <? super K> comparator() { return comparator; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet <K> subSet( final K from, final K to ) { if ( compare( from, element ) <= 0 && compare( element, to ) < 0 ) return this; return EMPTY_SET; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet <K> headSet( final K to ) { if ( compare( element, to ) < 0 ) return this; return EMPTY_SET; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet <K> tailSet( final K from ) { if ( compare( from, element ) <= 0 ) return this; return EMPTY_SET; }
  public K first() { return element; }
  public K last() { return element; }
 }
 /** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just <code>element</code>.
	 */
 public static <K> ObjectSortedSet <K> singleton( final K element ) {
  return new Singleton <K>( element );
 }
 /** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just <code>element</code>.
	 */
 public static <K> ObjectSortedSet <K> singleton( final K element, final Comparator <? super K> comparator ) {
  return new Singleton <K>( element, comparator );
 }
 /** A synchronized wrapper class for sorted sets. */
 public static class SynchronizedSortedSet <K> extends ObjectSets.SynchronizedSet <K> implements ObjectSortedSet <K>, java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final ObjectSortedSet <K> sortedSet;
  protected SynchronizedSortedSet( final ObjectSortedSet <K> s, final Object sync ) {
   super( s, sync );
   sortedSet = s;
  }
  protected SynchronizedSortedSet( final ObjectSortedSet <K> s ) {
   super( s );
   sortedSet = s;
  }
  public Comparator <? super K> comparator() { synchronized( sync ) { return sortedSet.comparator(); } }
  public ObjectSortedSet <K> subSet( final K from, final K to ) { return new SynchronizedSortedSet <K>( sortedSet.subSet( from, to ), sync ); }
  public ObjectSortedSet <K> headSet( final K to ) { return new SynchronizedSortedSet <K>( sortedSet.headSet( to ), sync ); }
  public ObjectSortedSet <K> tailSet( final K from ) { return new SynchronizedSortedSet <K>( sortedSet.tailSet( from ), sync ); }
  public ObjectBidirectionalIterator <K> iterator() { return sortedSet.iterator(); }
  public ObjectBidirectionalIterator <K> iterator( final K from ) { return sortedSet.iterator( from ); }
  @Deprecated
  public ObjectBidirectionalIterator <K> objectIterator() { return sortedSet.iterator(); }
  public K first() { synchronized( sync ) { return sortedSet.first(); } }
  public K last() { synchronized( sync ) { return sortedSet.last(); } }
 }
 /** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
 public static <K> ObjectSortedSet <K> synchronize( final ObjectSortedSet <K> s ) { return new SynchronizedSortedSet <K>( s ); }
 /** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set, using an assigned object to synchronize.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @param sync an object that will be used to synchronize the access to the sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
 public static <K> ObjectSortedSet <K> synchronize( final ObjectSortedSet <K> s, final Object sync ) { return new SynchronizedSortedSet <K>( s, sync ); }
 /** An unmodifiable wrapper class for sorted sets. */
 public static class UnmodifiableSortedSet <K> extends ObjectSets.UnmodifiableSet <K> implements ObjectSortedSet <K>, java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final ObjectSortedSet <K> sortedSet;
  protected UnmodifiableSortedSet( final ObjectSortedSet <K> s ) {
   super( s );
   sortedSet = s;
  }
  public Comparator <? super K> comparator() { return sortedSet.comparator(); }
  public ObjectSortedSet <K> subSet( final K from, final K to ) { return new UnmodifiableSortedSet <K>( sortedSet.subSet( from, to ) ); }
  public ObjectSortedSet <K> headSet( final K to ) { return new UnmodifiableSortedSet <K>( sortedSet.headSet( to ) ); }
  public ObjectSortedSet <K> tailSet( final K from ) { return new UnmodifiableSortedSet <K>( sortedSet.tailSet( from ) ); }
  public ObjectBidirectionalIterator <K> iterator() { return ObjectIterators.unmodifiable( sortedSet.iterator() ); }
  public ObjectBidirectionalIterator <K> iterator( final K from ) { return ObjectIterators.unmodifiable( sortedSet.iterator( from ) ); }
  @Deprecated
  public ObjectBidirectionalIterator <K> objectIterator() { return iterator(); }
  public K first() { return sortedSet.first(); }
  public K last() { return sortedSet.last(); }
 }
 /** Returns an unmodifiable type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in an unmodifiable sorted set.
	 * @return an unmodifiable view of the specified sorted set.
	 * @see java.util.Collections#unmodifiableSortedSet(SortedSet)
	 */
 public static <K> ObjectSortedSet <K> unmodifiable( final ObjectSortedSet <K> s ) { return new UnmodifiableSortedSet <K>( s ); }
}
