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
package it.unimi.dsi.fastutil.ints;
import java.util.Map;
import java.util.NoSuchElementException;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectCollections;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
/** A simple, brute-force implementation of a map based on two parallel backing arrays. 
 * 
 * <p>The main purpose of this
 * implementation is that of wrapping cleanly the brute-force approach to the storage of a very 
 * small number of pairs: just put them into two parallel arrays and scan linearly to find an item.
 */
public class Int2ObjectArrayMap <V> extends AbstractInt2ObjectMap <V> implements java.io.Serializable, Cloneable {
 private static final long serialVersionUID = 1L;
 /** The keys (valid up to {@link #size}, excluded). */
 private transient int[] key;
 /** The values (parallel to {@link #key}). */
 private transient Object[] value;
 /** The number of valid entries in {@link #key} and {@link #value}. */
 private int size;
 /** Creates a new empty array map with given key and value backing arrays. The resulting map will have as many entries as the given arrays.
	 * 
	 * <p>It is responsibility of the caller that the elements of <code>key</code> are distinct.
	 * 
	 * @param key the key array.
	 * @param value the value array (it <em>must</em> have the same length as <code>key</code>).
	 */
 public Int2ObjectArrayMap( final int[] key, final Object[] value ) {
  this.key = key;
  this.value = value;
  size = key.length;
  if( key.length != value.length ) throw new IllegalArgumentException( "Keys and values have different lengths (" + key.length + ", " + value.length + ")" );
 }
 /** Creates a new empty array map.
	 */
 public Int2ObjectArrayMap() {
  this.key = IntArrays.EMPTY_ARRAY;
  this.value = ObjectArrays.EMPTY_ARRAY;
 }
 /** Creates a new empty array map of given capacity.
	 *
	 * @param capacity the initial capacity.
	 */
 public Int2ObjectArrayMap( final int capacity ) {
  this.key = new int[ capacity ];
  this.value = new Object[ capacity ];
 }
 /** Creates a new empty array map copying the entries of a given map.
	 *
	 * @param m a map.
	 */
 public Int2ObjectArrayMap( final Int2ObjectMap <V> m ) {
  this( m.size() );
  putAll( m );
 }
 /** Creates a new empty array map copying the entries of a given map.
	 *
	 * @param m a map.
	 */
 public Int2ObjectArrayMap( final Map<? extends Integer, ? extends V> m ) {
  this( m.size() );
  putAll( m );
 }
 /** Creates a new array map with given key and value backing arrays, using the given number of elements.
	 * 
	 * <p>It is responsibility of the caller that the first <code>size</code> elements of <code>key</code> are distinct.
	 * 
	 * @param key the key array.
	 * @param value the value array (it <em>must</em> have the same length as <code>key</code>).
	 * @param size the number of valid elements in <code>key</code> and <code>value</code>.
	 */
 public Int2ObjectArrayMap( final int[] key, final Object[] value, final int size ) {
  this.key = key;
  this.value = value;
  this.size = size;
  if( key.length != value.length ) throw new IllegalArgumentException( "Keys and values have different lengths (" + key.length + ", " + value.length + ")" );
  if ( size > key.length ) throw new IllegalArgumentException( "The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")" );
 }
 private final class EntrySet extends AbstractObjectSet<Int2ObjectMap.Entry <V> > implements FastEntrySet <V> {
  @Override
  public ObjectIterator<Int2ObjectMap.Entry <V> > iterator() {
   return new AbstractObjectIterator<Int2ObjectMap.Entry <V> >() {
    int curr = -1, next = 0;
    public boolean hasNext() {
     return next < size;
    }
    @SuppressWarnings("unchecked")
    public Entry <V> next() {
     if ( ! hasNext() ) throw new NoSuchElementException();
     return new AbstractInt2ObjectMap.BasicEntry <V>( key[ curr = next ], (V) value[ next++ ] );
    }
    public void remove() {
     if ( curr == -1 ) throw new IllegalStateException();
     curr = -1;
     final int tail = size-- - next--;
     System.arraycopy( key, next + 1, key, next, tail );
     System.arraycopy( value, next + 1, value, next, tail );
     value[ size ] = null;
    }
   };
  }
  public ObjectIterator<Int2ObjectMap.Entry <V> > fastIterator() {
   return new AbstractObjectIterator<Int2ObjectMap.Entry <V> >() {
    int next = 0, curr = -1;
    final BasicEntry <V> entry = new BasicEntry <V> ( (0), (null) );
    public boolean hasNext() {
     return next < size;
    }
    @SuppressWarnings("unchecked")
    public Entry <V> next() {
     if ( ! hasNext() ) throw new NoSuchElementException();
     entry.key = key[ curr = next ];
     entry.value = (V) value[ next++ ];
     return entry;
    }
    public void remove() {
     if ( curr == -1 ) throw new IllegalStateException();
     curr = -1;
     final int tail = size-- - next--;
     System.arraycopy( key, next + 1, key, next, tail );
     System.arraycopy( value, next + 1, value, next, tail );
     value[ size ] = null;
    }
   };
  }
  public int size() {
   return size;
  }
  @SuppressWarnings("unchecked")
  public boolean contains( Object o ) {
   if ( ! ( o instanceof Map.Entry ) ) return false;
   final Map.Entry<Integer, V> e = (Map.Entry<Integer, V>)o;
   if ( e.getKey() == null ) return false;
   final int k = ((e.getKey()).intValue());
   return Int2ObjectArrayMap.this.containsKey( k ) && ( (Int2ObjectArrayMap.this.get( k )) == null ? ((e.getValue())) == null : (Int2ObjectArrayMap.this.get( k )).equals((e.getValue())) );
  }
  @SuppressWarnings("unchecked")
  @Override
  public boolean remove( final Object o ) {
   if ( !( o instanceof Map.Entry ) ) return false;
   final Map.Entry<Integer, V> e = (Map.Entry<Integer, V>)o;
   if ( e.getKey() == null ) return false;
   final int k = ((e.getKey()).intValue());
   final V v = (e.getValue());
   final int oldPos = Int2ObjectArrayMap.this.findKey( k );
   if ( oldPos == -1 || ! ( (v) == null ? (Int2ObjectArrayMap.this.value[ oldPos ]) == null : (v).equals(Int2ObjectArrayMap.this.value[ oldPos ]) ) ) return false;
   final int tail = size - oldPos - 1;
   System.arraycopy( Int2ObjectArrayMap.this.key, oldPos + 1, Int2ObjectArrayMap.this.key, oldPos, tail );
   System.arraycopy( Int2ObjectArrayMap.this.value, oldPos + 1, Int2ObjectArrayMap.this.value, oldPos, tail );
   Int2ObjectArrayMap.this.size--;
   Int2ObjectArrayMap.this.value[ size ] = null;
   return true;
  }
 }
 public FastEntrySet <V> int2ObjectEntrySet() {
  return new EntrySet();
 }
 private int findKey( final int k ) {
  final int[] key = this.key;
  for( int i = size; i-- != 0; ) if ( ( (key[ i ]) == (k) ) ) return i;
  return -1;
 }
 @SuppressWarnings("unchecked")
 public V get( final int k ) {
  final int[] key = this.key;
  for( int i = size; i-- != 0; ) if ( ( (key[ i ]) == (k) ) ) return (V) value[ i ];
  return defRetValue;
 }
 public int size() {
  return size;
 }
 @Override
 public void clear() {
  for( int i = size; i-- != 0; ) {
   value[ i ] = null;
  }
  size = 0;
 }
 @Override
 public boolean containsKey( final int k ) {
  return findKey( k ) != -1;
 }
 @Override
 public boolean containsValue( Object v ) {
  for( int i = size; i-- != 0; ) if ( ( (value[ i ]) == null ? (v) == null : (value[ i ]).equals(v) ) ) return true;
  return false;
 }
 @Override
 public boolean isEmpty() {
  return size == 0;
 }
 @Override
 @SuppressWarnings("unchecked")
 public V put( int k, V v ) {
  final int oldKey = findKey( k );
  if ( oldKey != -1 ) {
   final V oldValue = (V) value[ oldKey ];
   value[ oldKey ] = v;
   return oldValue;
  }
  if ( size == key.length ) {
   final int[] newKey = new int[ size == 0 ? 2 : size * 2 ];
   final Object[] newValue = new Object[ size == 0 ? 2 : size * 2 ];
   for( int i = size; i-- != 0; ) {
    newKey[ i ] = key[ i ];
    newValue[ i ] = value[ i ];
   }
   key = newKey;
   value = newValue;
  }
  key[ size ] = k;
  value[ size ] = v;
  size++;
  return defRetValue;
 }
 @Override
 @SuppressWarnings("unchecked")
 public V remove( final int k ) {
  final int oldPos = findKey( k );
  if ( oldPos == -1 ) return defRetValue;
  final V oldValue = (V) value[ oldPos ];
  final int tail = size - oldPos - 1;
  System.arraycopy( key, oldPos + 1, key, oldPos, tail );
  System.arraycopy( value, oldPos + 1, value, oldPos, tail );
  size--;
  value[ size ] = null;
  return oldValue;
 }
 @Override
 public IntSet keySet() {
  return new IntArraySet ( key, size );
 }
 @Override
 public ObjectCollection <V> values() {
  return ObjectCollections.unmodifiable( new ObjectArraySet <V>( value, size ) );
 }
 /** Returns a deep copy of this map. 
	 *
	 * <P>This method performs a deep copy of this hash map; the data stored in the
	 * map, however, is not cloned. Note that this makes a difference only for object keys.
	 *
	 *  @return a deep copy of this map.
	 */
 @SuppressWarnings("unchecked")
 public Int2ObjectArrayMap <V> clone() {
  Int2ObjectArrayMap <V> c;
  try {
   c = (Int2ObjectArrayMap <V>)super.clone();
  }
  catch(CloneNotSupportedException cantHappen) {
   throw new InternalError();
  }
  c.key = key.clone();
  c.value = value.clone();
  return c;
 }
 private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
  s.defaultWriteObject();
  for( int i = 0; i < size; i++ ) {
   s.writeInt( key[ i ] );
   s.writeObject( value[ i ] );
  }
 }
 private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
  s.defaultReadObject();
  key = new int[ size ];
  value = new Object[ size ];
  for( int i = 0; i < size; i++ ) {
   key[ i ] = s.readInt();
   value[ i ] = s.readObject();
  }
 }
}
