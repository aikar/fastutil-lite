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
import java.util.List;
import java.util.Collection;
import java.util.Random;
/** A class providing static methods and objects that do useful things with type-specific lists.
 *
 * @see java.util.Collections
 */
public class LongLists {
 private LongLists() {}
 /** Shuffles the specified list using the specified pseudorandom number generator.
	 * 
	 * @param l the list to be shuffled.
	 * @param random a pseudorandom number generator (please use a <a href="http://dsiutils.dsi.unimi.it/docs/it/unimi/dsi/util/XorShiftStarRandom.html">XorShift*</a> generator).
	 * @return <code>l</code>.
	 */
 public static LongList shuffle( final LongList l, final Random random ) {
  for( int i = l.size(); i-- != 0; ) {
   final int p = random.nextInt( i + 1 );
   final long t = l.getLong( i );
   l.set( i, l.getLong( p ) );
   l.set( p, t );
  }
  return l;
 }
 /** An immutable class representing an empty type-specific list.
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific list.
	 */
 public static class EmptyList extends LongCollections.EmptyCollection implements LongList , java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected EmptyList() {}
  public void add( final int index, final long k ) { throw new UnsupportedOperationException(); }
  public boolean add( final long k ) { throw new UnsupportedOperationException(); }
  public long removeLong( int i ) { throw new UnsupportedOperationException(); }
  public long set( final int index, final long k ) { throw new UnsupportedOperationException(); }
  public int indexOf( long k ) { return -1; }
  public int lastIndexOf( long k ) { return -1; }
  public boolean addAll( Collection<? extends Long> c ) { throw new UnsupportedOperationException(); }
  public boolean addAll( int i, Collection<? extends Long> c ) { throw new UnsupportedOperationException(); }
  public boolean removeAll( Collection<?> c ) { throw new UnsupportedOperationException(); }
  public Long get( int i ) { throw new IndexOutOfBoundsException(); }
  public boolean addAll( LongCollection c ) { throw new UnsupportedOperationException(); }
  public boolean addAll( LongList c ) { throw new UnsupportedOperationException(); }
  public boolean addAll( int i, LongCollection c ) { throw new UnsupportedOperationException(); }
  public boolean addAll( int i, LongList c ) { throw new UnsupportedOperationException(); }
  public void add( final int index, final Long k ) { throw new UnsupportedOperationException(); }
  public boolean add( final Long k ) { throw new UnsupportedOperationException(); }
  public Long set( final int index, final Long k ) { throw new UnsupportedOperationException(); }
  public long getLong( int i ) { throw new IndexOutOfBoundsException(); }
  public Long remove( int k ) { throw new UnsupportedOperationException(); }
  public int indexOf( Object k ) { return -1; }
  public int lastIndexOf( Object k ) { return -1; }
  //SUPPRESS_WARNINGS_KEY_UNCHECKED
  //public KEY_ITERATOR KEY_GENERIC iterator( int i ) { if ( i == 0 ) return ITERATORS.EMPTY_ITERATOR; throw new IndexOutOfBoundsException( String.valueOf( i ) ); }
  @Deprecated
 
  public LongIterator longIterator() { return LongIterators.EMPTY_ITERATOR; }
 
  public LongListIterator listIterator() { return LongIterators.EMPTY_ITERATOR; }
 
  public LongListIterator iterator() { return LongIterators.EMPTY_ITERATOR; }
 
  public LongListIterator listIterator( int i ) { if ( i == 0 ) return LongIterators.EMPTY_ITERATOR; throw new IndexOutOfBoundsException( String.valueOf( i ) ); }
  @Deprecated
  public LongListIterator longListIterator() { return listIterator(); }
  @Deprecated
  public LongListIterator longListIterator( int i ) { return listIterator( i ); }
  public LongList subList( int from, int to ) { if ( from == 0 && to == 0 ) return this; throw new IndexOutOfBoundsException(); }
  @Deprecated
  public LongList longSubList( int from, int to ) { return subList( from, to ); }
  public void getElements( int from, long[] a, int offset, int length ) { if ( from == 0 && length == 0 && offset >= 0 && offset <= a.length ) return; throw new IndexOutOfBoundsException(); }
  public void removeElements( int from, int to ) { throw new UnsupportedOperationException(); }
  public void addElements( int index, final long a[], int offset, int length ) { throw new UnsupportedOperationException(); }
  public void addElements( int index, final long a[] ) { throw new UnsupportedOperationException(); }
  public void size( int s ) { throw new UnsupportedOperationException(); }
  public int compareTo( final List<? extends Long> o ) {
   if ( o == this ) return 0;
   return ((List<?>)o).isEmpty() ? 0 : -1;
  }
  private Object readResolve() { return EMPTY_LIST; }
  public Object clone() { return EMPTY_LIST; }
  public int hashCode() { return 1; }
  @SuppressWarnings("rawtypes")
  public boolean equals( Object o ) { return o instanceof List && ((List)o).isEmpty(); }
  public String toString() { return "[]"; }
 }
 /** An empty list (immutable). It is serializable and cloneable. 
	 */

 public static final EmptyList EMPTY_LIST = new EmptyList();
 /** An immutable class representing a type-specific singleton list. 
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific list.
	 */
 public static class Singleton extends AbstractLongList implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  private final long element;
  private Singleton( final long element ) {
   this.element = element;
  }
  public long getLong( final int i ) { if ( i == 0 ) return element; throw new IndexOutOfBoundsException(); }
  public long removeLong( final int i ) { throw new UnsupportedOperationException(); }
  public boolean contains( final long k ) { return ( (k) == (element) ); }
  public boolean addAll( final Collection<? extends Long> c ) { throw new UnsupportedOperationException(); }
  public boolean addAll( final int i, final Collection <? extends Long> c ) { throw new UnsupportedOperationException(); }
  public boolean removeAll( final Collection<?> c ) { throw new UnsupportedOperationException(); }
  public boolean retainAll( final Collection<?> c ) { throw new UnsupportedOperationException(); }
  /* Slightly optimized w.r.t. the one in ABSTRACT_SET. */
  public long[] toLongArray() {
   long a[] = new long[ 1 ];
   a[ 0 ] = element;
   return a;
  }
  public LongListIterator listIterator() { return LongIterators.singleton( element ); }
  public LongListIterator iterator() { return listIterator(); }
  public LongListIterator listIterator( int i ) {
   if ( i > 1 || i < 0 ) throw new IndexOutOfBoundsException();
   LongListIterator l = listIterator();
   if ( i == 1 ) l.next();
   return l;
  }
 
  public LongList subList( final int from, final int to ) {
   ensureIndex( from );
   ensureIndex( to );
   if ( from > to ) throw new IndexOutOfBoundsException( "Start index (" + from + ") is greater than end index (" + to + ")" );
   if ( from != 0 || to != 1 ) return EMPTY_LIST;
   return this;
  }
  public int size() { return 1; }
  public void size( final int size ) { throw new UnsupportedOperationException(); }
  public void clear() { throw new UnsupportedOperationException(); }
  public Object clone() { return this; }
  public boolean rem( final long k ) { throw new UnsupportedOperationException(); }
  public boolean addAll( final LongCollection c ) { throw new UnsupportedOperationException(); }
  public boolean addAll( final int i, final LongCollection c ) { throw new UnsupportedOperationException(); }
 }
 /** Returns a type-specific immutable list containing only the specified element. The returned list is serializable and cloneable.
	 *
	 * @param element the only element of the returned list.
	 * @return a type-specific immutable list containing just <code>element</code>.
	 */
 public static LongList singleton( final long element ) { return new Singleton ( element ); }
 /** Returns a type-specific immutable list containing only the specified element. The returned list is serializable and cloneable.
	 *
	 * @param element the only element of the returned list.
	 * @return a type-specific immutable list containing just <code>element</code>.
	 */
 public static LongList singleton( final Object element ) { return new Singleton ( ((((Long)(element)).longValue())) ); }
 /** A synchronized wrapper class for lists. */
 public static class SynchronizedList extends LongCollections.SynchronizedCollection implements LongList , java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final LongList list; // Due to the large number of methods that are not in COLLECTION, this is worth caching.
  protected SynchronizedList( final LongList l, final Object sync ) {
   super( l, sync );
   this.list = l;
  }
  protected SynchronizedList( final LongList l ) {
   super( l );
   this.list = l;
  }
  public long getLong( final int i ) { synchronized( sync ) { return list.getLong( i ); } }
  public long set( final int i, final long k ) { synchronized( sync ) { return list.set( i, k ); } }
  public void add( final int i, final long k ) { synchronized( sync ) { list.add( i, k ); } }
  public long removeLong( final int i ) { synchronized( sync ) { return list.removeLong( i ); } }
  public int indexOf( final long k ) { synchronized( sync ) { return list.indexOf( k ); } }
  public int lastIndexOf( final long k ) { synchronized( sync ) { return list.lastIndexOf( k ); } }
  public boolean addAll( final int index, final Collection<? extends Long> c ) { synchronized( sync ) { return list.addAll( index, c ); } }
  public void getElements( final int from, final long a[], final int offset, final int length ) { synchronized( sync ) { list.getElements( from, a, offset, length ); } }
  public void removeElements( final int from, final int to ) { synchronized( sync ) { list.removeElements( from, to ); } }
  public void addElements( int index, final long a[], int offset, int length ) { synchronized( sync ) { list.addElements( index, a, offset, length ); } }
  public void addElements( int index, final long a[] ) { synchronized( sync ) { list.addElements( index, a ); } }
  public void size( final int size ) { synchronized( sync ) { list.size( size ); } }
  public LongListIterator iterator() { return list.listIterator(); }
  public LongListIterator listIterator() { return list.listIterator(); }
  public LongListIterator listIterator( final int i ) { return list.listIterator( i ); }
  @Deprecated
  public LongListIterator longListIterator() { return listIterator(); }
  @Deprecated
  public LongListIterator longListIterator( final int i ) { return listIterator( i ); }
  public LongList subList( final int from, final int to ) { synchronized( sync ) { return synchronize( list.subList( from, to ), sync ); } }
  @Deprecated
  public LongList longSubList( final int from, final int to ) { return subList( from, to ); }
  public boolean equals( final Object o ) { synchronized( sync ) { return collection.equals( o ); } }
  public int hashCode() { synchronized( sync ) { return collection.hashCode(); } }
  public int compareTo( final List<? extends Long> o ) { synchronized( sync ) { return list.compareTo( o ); } }
  public boolean addAll( final int index, final LongCollection c ) { synchronized( sync ) { return list.addAll( index, c ); } }
  public boolean addAll( final int index, LongList l ) { synchronized( sync ) { return list.addAll( index, l ); } }
  public boolean addAll( LongList l ) { synchronized( sync ) { return list.addAll( l ); } }
  public Long get( final int i ) { synchronized( sync ) { return list.get( i ); } }
  public void add( final int i, Long k ) { synchronized( sync ) { list.add( i, k ); } }
  public Long set( final int index, Long k ) { synchronized( sync ) { return list.set( index, k ); } }
  public Long remove( final int i ) { synchronized( sync ) { return list.remove( i ); } }
  public int indexOf( final Object o ) { synchronized( sync ) { return list.indexOf( o ); } }
  public int lastIndexOf( final Object o ) { synchronized( sync ) { return list.lastIndexOf( o ); } }
 }
 /** Returns a synchronized type-specific list backed by the given type-specific list.
	 *
	 * @param l the list to be wrapped in a synchronized list.
	 * @return a synchronized view of the specified list.
	 * @see java.util.Collections#synchronizedList(List)
	 */
 public static LongList synchronize( final LongList l ) { return new SynchronizedList ( l ); }
 /** Returns a synchronized type-specific list backed by the given type-specific list, using an assigned object to synchronize.
	 *
	 * @param l the list to be wrapped in a synchronized list.
	 * @param sync an object that will be used to synchronize the access to the list.
	 * @return a synchronized view of the specified list.
	 * @see java.util.Collections#synchronizedList(List)
	 */
 public static LongList synchronize( final LongList l, final Object sync ) { return new SynchronizedList ( l, sync ); }
 /** An unmodifiable wrapper class for lists. */
 public static class UnmodifiableList extends LongCollections.UnmodifiableCollection implements LongList , java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final LongList list; // Due to the large number of methods that are not in COLLECTION, this is worth caching.
  protected UnmodifiableList( final LongList l ) {
   super( l );
   this.list = l;
  }
  public long getLong( final int i ) { return list.getLong( i ); }
  public long set( final int i, final long k ) { throw new UnsupportedOperationException(); }
  public void add( final int i, final long k ) { throw new UnsupportedOperationException(); }
  public long removeLong( final int i ) { throw new UnsupportedOperationException(); }
  public int indexOf( final long k ) { return list.indexOf( k ); }
  public int lastIndexOf( final long k ) { return list.lastIndexOf( k ); }
  public boolean addAll( final int index, final Collection<? extends Long> c ) { throw new UnsupportedOperationException(); }
  public void getElements( final int from, final long a[], final int offset, final int length ) { list.getElements( from, a, offset, length ); }
  public void removeElements( final int from, final int to ) { throw new UnsupportedOperationException(); }
  public void addElements( int index, final long a[], int offset, int length ) { throw new UnsupportedOperationException(); }
  public void addElements( int index, final long a[] ) { throw new UnsupportedOperationException(); }
  public void size( final int size ) { list.size( size ); }
  public LongListIterator iterator() { return listIterator(); }
  public LongListIterator listIterator() { return LongIterators.unmodifiable( list.listIterator() ); }
  public LongListIterator listIterator( final int i ) { return LongIterators.unmodifiable( list.listIterator( i ) ); }
  @Deprecated
  public LongListIterator longListIterator() { return listIterator(); }
  @Deprecated
  public LongListIterator longListIterator( final int i ) { return listIterator( i ); }
  public LongList subList( final int from, final int to ) { return unmodifiable( list.subList( from, to ) ); }
  @Deprecated
  public LongList longSubList( final int from, final int to ) { return subList( from, to ); }
  public boolean equals( final Object o ) { return collection.equals( o ); }
  public int hashCode() { return collection.hashCode(); }
  public int compareTo( final List<? extends Long> o ) { return list.compareTo( o ); }
  public boolean addAll( final int index, final LongCollection c ) { throw new UnsupportedOperationException(); }
  public boolean addAll( final LongList l ) { throw new UnsupportedOperationException(); }
  public boolean addAll( final int index, final LongList l ) { throw new UnsupportedOperationException(); }
  public Long get( final int i ) { return list.get( i ); }
  public void add( final int i, Long k ) { throw new UnsupportedOperationException(); }
  public Long set( final int index, Long k ) { throw new UnsupportedOperationException(); }
  public Long remove( final int i ) { throw new UnsupportedOperationException(); }
  public int indexOf( final Object o ) { return list.indexOf( o ); }
  public int lastIndexOf( final Object o ) { return list.lastIndexOf( o ); }
 }
 /** Returns an unmodifiable type-specific list backed by the given type-specific list.
	 *
	 * @param l the list to be wrapped in an unmodifiable list.
	 * @return an unmodifiable view of the specified list.
	 * @see java.util.Collections#unmodifiableList(List)
	 */
 public static LongList unmodifiable( final LongList l ) { return new UnmodifiableList ( l ); }
}
