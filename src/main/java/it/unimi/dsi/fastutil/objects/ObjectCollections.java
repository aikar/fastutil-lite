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
import java.util.Collection;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
/** A class providing static methods and objects that do useful things with type-specific collections.
 *
 * @see java.util.Collections
 */
public class ObjectCollections {
 private ObjectCollections() {}
 /** An immutable class representing an empty type-specific collection.
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific collection.
	 */
 public abstract static class EmptyCollection <K> extends AbstractObjectCollection <K> {
  protected EmptyCollection() {}
  public boolean add( K k ) { throw new UnsupportedOperationException(); }
  public boolean contains( Object k ) { return false; }
  public Object[] toArray() { return ObjectArrays.EMPTY_ARRAY; }
  public boolean remove( final Object k ) { throw new UnsupportedOperationException(); }
  public <T> T[] toArray( T[] a ) { return a; }
  @SuppressWarnings("unchecked")
  public ObjectBidirectionalIterator <K> iterator() { return ObjectIterators.EMPTY_ITERATOR; }
  public int size() { return 0; }
  public void clear() {}
  public int hashCode() { return 0; }
  public boolean equals( Object o ) {
   if ( o == this ) return true;
   if ( ! ( o instanceof Collection ) ) return false;
   return ((Collection<?>)o).isEmpty();
  }
 }
 /** A synchronized wrapper class for collections. */
 public static class SynchronizedCollection <K> implements ObjectCollection <K>, java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final ObjectCollection <K> collection;
  protected final Object sync;
  protected SynchronizedCollection( final ObjectCollection <K> c, final Object sync ) {
   if ( c == null ) throw new NullPointerException();
   this.collection = c;
   this.sync = sync;
  }
  protected SynchronizedCollection( final ObjectCollection <K> c ) {
   if ( c == null ) throw new NullPointerException();
   this.collection = c;
   this.sync = this;
  }
  public int size() { synchronized( sync ) { return collection.size(); } }
  public boolean isEmpty() { synchronized( sync ) { return collection.isEmpty(); } }
  public boolean contains( final Object o ) { synchronized( sync ) { return collection.contains( o ); } }
  public Object[] toArray() { synchronized( sync ) { return collection.toArray(); } }
  public <T> T[] toArray( final T[] a ) { synchronized( sync ) { return collection.toArray( a ); } }
  public ObjectIterator <K> iterator() { return collection.iterator(); }
  @Deprecated
  public ObjectIterator <K> objectIterator() { return iterator(); }
  public boolean add( final K k ) { synchronized( sync ) { return collection.add( k ); } }
  public boolean rem( final Object k ) { synchronized( sync ) { return collection.remove( k ); } }
  public boolean remove( final Object ok ) { synchronized( sync ) { return collection.remove( ok ); } }
  public boolean addAll( final Collection<? extends K> c ) { synchronized( sync ) { return collection.addAll( c ); } }
  public boolean containsAll( final Collection<?> c ) { synchronized( sync ) { return collection.containsAll( c ); } }
  public boolean removeAll( final Collection<?> c ) { synchronized( sync ) { return collection.removeAll( c ); } }
  public boolean retainAll( final Collection<?> c ) { synchronized( sync ) { return collection.retainAll( c ); } }
  public void clear() { synchronized( sync ) { collection.clear(); } }
  public String toString() { synchronized( sync ) { return collection.toString(); } }
 }
 /** Returns a synchronized collection backed by the specified collection.
	 *
	 * @param c the collection to be wrapped in a synchronized collection.
	 * @return a synchronized view of the specified collection.
	 * @see java.util.Collections#synchronizedCollection(Collection)
	 */
 public static <K> ObjectCollection <K> synchronize( final ObjectCollection <K> c ) { return new SynchronizedCollection <K>( c ); }
 /** Returns a synchronized collection backed by the specified collection, using an assigned object to synchronize.
	 *
	 * @param c the collection to be wrapped in a synchronized collection.
	 * @param sync an object that will be used to synchronize the list access.
	 * @return a synchronized view of the specified collection.
	 * @see java.util.Collections#synchronizedCollection(Collection)
	 */
 public static <K> ObjectCollection <K> synchronize( final ObjectCollection <K> c, final Object sync ) { return new SynchronizedCollection <K>( c, sync ); }
 /** An unmodifiable wrapper class for collections. */
 public static class UnmodifiableCollection <K> implements ObjectCollection <K>, java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final ObjectCollection <K> collection;
  protected UnmodifiableCollection( final ObjectCollection <K> c ) {
   if ( c == null ) throw new NullPointerException();
   this.collection = c;
  }
  public int size() { return collection.size(); }
  public boolean isEmpty() { return collection.isEmpty(); }
  public boolean contains( final Object o ) { return collection.contains( o ); }
  public ObjectIterator <K> iterator() { return ObjectIterators.unmodifiable( collection.iterator() ); }
  @Deprecated
  public ObjectIterator <K> objectIterator() { return iterator(); }
  public boolean add( final K k ) { throw new UnsupportedOperationException(); }
  public boolean remove( final Object ok ) { throw new UnsupportedOperationException(); }
  public boolean addAll( final Collection<? extends K> c ) { throw new UnsupportedOperationException(); }
  public boolean containsAll( final Collection<?> c ) { return collection.containsAll( c ); }
  public boolean removeAll( final Collection<?> c ) { throw new UnsupportedOperationException(); }
  public boolean retainAll( final Collection<?> c ) { throw new UnsupportedOperationException(); }
  public void clear() { throw new UnsupportedOperationException(); }
  public String toString() { return collection.toString(); }
  public <T> T[] toArray( final T[] a ) { return collection.toArray( a ); }
  public Object[] toArray() { return collection.toArray(); }
 }
 /** Returns an unmodifiable collection backed by the specified collection.
	 *
	 * @param c the collection to be wrapped in an unmodifiable collection.
	 * @return an unmodifiable view of the specified collection.
	 * @see java.util.Collections#unmodifiableCollection(Collection)
	 */
 public static <K> ObjectCollection <K> unmodifiable( final ObjectCollection <K> c ) { return new UnmodifiableCollection <K>( c ); }
 /** A collection wrapper class for iterables. */
 public static class IterableCollection <K> extends AbstractObjectCollection <K> implements java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final ObjectIterable <K> iterable;
  protected IterableCollection( final ObjectIterable <K> iterable ) {
   if ( iterable == null ) throw new NullPointerException();
   this.iterable = iterable;
  }
  public int size() {
   int c = 0;
   final ObjectIterator <K> iterator = iterator();
   while( iterator.hasNext() ) {
    iterator.next();
    c++;
   }
   return c;
  }
  public boolean isEmpty() { return iterable.iterator().hasNext(); }
  public ObjectIterator <K> iterator() { return iterable.iterator(); }
  @Deprecated
  public ObjectIterator <K> objectIterator() { return iterator(); }
 }
 /** Returns an unmodifiable collection backed by the specified iterable.
	 *
	 * @param iterable the iterable object to be wrapped in an unmodifiable collection.
	 * @return an unmodifiable collection view of the specified iterable.
	 */
 public static <K> ObjectCollection <K> asCollection( final ObjectIterable <K> iterable ) {
  if ( iterable instanceof ObjectCollection ) return (ObjectCollection <K>)iterable;
  return new IterableCollection <K>( iterable );
 }
}
