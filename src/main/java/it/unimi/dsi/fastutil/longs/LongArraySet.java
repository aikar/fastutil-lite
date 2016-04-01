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
 * Copyright (C) 2007-2016 Sebastiano Vigna
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
import java.util.Collection;
import java.util.NoSuchElementException;
/** A simple, brute-force implementation of a set based on a backing array.
 *
 * <p>The main purpose of this
 * implementation is that of wrapping cleanly the brute-force approach to the storage of a very 
 * small number of items: just put them into an array and scan linearly to find an item.
 */
public class LongArraySet extends AbstractLongSet implements java.io.Serializable, Cloneable {
 private static final long serialVersionUID = 1L;
 /** The backing array (valid up to {@link #size}, excluded). */
 private transient long[] a;
 /** The number of valid entries in {@link #a}. */
 private int size;
 /** Creates a new array set using the given backing array. The resulting set will have as many elements as the array.
	 * 
	 * <p>It is responsibility of the caller that the elements of <code>a</code> are distinct.
	 * 
	 * @param a the backing array.
	 */
 public LongArraySet( final long[] a ) {
  this.a = a;
  size = a.length;
 }
 /** Creates a new empty array set.
	 */
 public LongArraySet() {
  this.a = LongArrays.EMPTY_ARRAY;
 }
 /** Creates a new empty array set of given initial capacity.
	 * 
	 * @param capacity the initial capacity.
	 */
 public LongArraySet( final int capacity ) {
  this.a = new long[ capacity ];
 }
 /** Creates a new array set copying the contents of a given collection.
	 * @param c a collection.
	 */
 public LongArraySet( LongCollection c ) {
  this( c.size () );
  addAll( c );
 }
 /** Creates a new array set copying the contents of a given set.
	 * @param c a collection.
	 */
 public LongArraySet( final Collection<? extends Long> c ) {
  this( c.size() );
  addAll( c );
 }
 /** Creates a new array set using the given backing array and the given number of elements of the array.
	 *
	 * <p>It is responsibility of the caller that the first <code>size</code> elements of <code>a</code> are distinct.
	 * 
	 * @param a the backing array.
	 * @param size the number of valid elements in <code>a</code>.
	 */
 public LongArraySet( final long[] a, final int size ) {
  this.a = a;
  this.size = size;
  if ( size > a.length ) throw new IllegalArgumentException( "The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")" );
 }
 private int findKey( final long o ) {
  for( int i = size; i-- != 0; ) if ( ( (a[ i ]) == (o) ) ) return i;
  return -1;
 }
 @Override

 public LongIterator iterator() {
  return new AbstractLongIterator () {
   int next = 0;
   public boolean hasNext() {
    return next < size;
   }
   public long nextLong() {
    if ( ! hasNext() ) throw new NoSuchElementException();
    return a[ next++ ];
   }
   public void remove() {
    final int tail = size-- - next--;
    System.arraycopy( a, next + 1, a, next, tail );
   }
  };
 }
 public boolean contains( final long k ) {
  return findKey( k ) != -1;
 }
 public int size() {
  return size;
 }
 @Override
 public boolean remove( final long k ) {
  final int pos = findKey( k );
  if ( pos == -1 ) return false;
  final int tail = size - pos - 1;
  for( int i = 0; i < tail; i++ ) a[ pos + i ] = a[ pos + i + 1 ];
  size--;
  return true;
 }
 @Override
 public boolean add( final long k ) {
  final int pos = findKey( k );
  if ( pos != -1 ) return false;
  if ( size == a.length ) {
   final long[] b = new long[ size == 0 ? 2 : size * 2 ];
   for( int i = size; i-- != 0; ) b[ i ] = a[ i ];
   a = b;
  }
  a[ size++ ] = k;
  return true;
 }
 @Override
 public void clear() {
  size = 0;
 }
 @Override
 public boolean isEmpty() {
  return size == 0;
 }
 /** Returns a deep copy of this set. 
	 *
	 * <P>This method performs a deep copy of this hash set; the data stored in the
	 * set, however, is not cloned. Note that this makes a difference only for object keys.
	 *
	 *  @return a deep copy of this set.
	 */

 public LongArraySet clone() {
  LongArraySet c;
  try {
   c = (LongArraySet )super.clone();
  }
  catch(CloneNotSupportedException cantHappen) {
   throw new InternalError();
  }
  c.a = a.clone();
  return c;
 }
 private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
  s.defaultWriteObject();
  for( int i = 0; i < size; i++ ) s.writeLong( a[ i ] );
 }
 private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
  s.defaultReadObject();
  a = new long[ size ];
  for( int i = 0; i < size; i++ ) a[ i ] = s.readLong();
 }
}
