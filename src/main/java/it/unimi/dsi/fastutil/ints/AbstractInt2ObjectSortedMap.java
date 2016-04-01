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
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
/** An abstract class providing basic methods for sorted maps implementing a type-specific interface. */
public abstract class AbstractInt2ObjectSortedMap <V> extends AbstractInt2ObjectMap <V> implements Int2ObjectSortedMap <V> {
 private static final long serialVersionUID = -1773560792952436569L;
 protected AbstractInt2ObjectSortedMap() {}
 /** Delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead. */
 @Deprecated
 public Int2ObjectSortedMap <V> headMap( final Integer to ) {
  return headMap( ((to).intValue()) );
 }
 /** Delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead. */
 @Deprecated
 public Int2ObjectSortedMap <V> tailMap( final Integer from ) {
  return tailMap( ((from).intValue()) );
 }
 /** Delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead. */
 @Deprecated
 public Int2ObjectSortedMap <V> subMap( final Integer from, final Integer to ) {
  return subMap( ((from).intValue()), ((to).intValue()) );
 }
 /** Delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead. */
 @Deprecated
 public Integer firstKey() {
  return (Integer.valueOf(firstIntKey()));
 }
 /** Delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead. */
 @Deprecated
 public Integer lastKey() {
  return (Integer.valueOf(lastIntKey()));
 }
 /** Returns a type-specific-sorted-set view of the keys of this map.
	 *
	 * <P>The view is backed by the sorted set returned by {@link #entrySet()}. Note that
	 * <em>no attempt is made at caching the result of this method</em>, as this would
	 * require adding some attributes that lightweight implementations would
	 * not need. Subclasses may easily override this policy by calling
	 * this method and caching the result, but implementors are encouraged to
	 * write more efficient ad-hoc implementations.
	 *
	 * @return a sorted set view of the keys of this map; it may be safely cast to a type-specific interface.
	 */
 public IntSortedSet keySet() {
  return new KeySet();
 }
 /** A wrapper exhibiting the keys of a map. */
 protected class KeySet extends AbstractIntSortedSet {
  public boolean contains( final int k ) { return containsKey( k ); }
  public int size() { return AbstractInt2ObjectSortedMap.this.size(); }
  public void clear() { AbstractInt2ObjectSortedMap.this.clear(); }
  public IntComparator comparator() { return AbstractInt2ObjectSortedMap.this.comparator(); }
  public int firstInt() { return firstIntKey(); }
  public int lastInt() { return lastIntKey(); }
  public IntSortedSet headSet( final int to ) { return headMap( to ).keySet(); }
  public IntSortedSet tailSet( final int from ) { return tailMap( from ).keySet(); }
  public IntSortedSet subSet( final int from, final int to ) { return subMap( from, to ).keySet(); }
  public IntBidirectionalIterator iterator( final int from ) { return new KeySetIterator <V>( entrySet().iterator( new BasicEntry <V>( from, (null) ) ) ); }
  public IntBidirectionalIterator iterator() { return new KeySetIterator <V>( entrySet().iterator() ); }
 }
 /** A wrapper exhibiting a map iterator as an iterator on keys.
	 *
	 * <P>To provide an iterator on keys, just create an instance of this
	 * class using the corresponding iterator on entries.
	 */
 protected static class KeySetIterator <V> extends AbstractIntBidirectionalIterator {
  protected final ObjectBidirectionalIterator<Map.Entry <Integer, V>> i;
  public KeySetIterator( ObjectBidirectionalIterator<Map.Entry <Integer, V>> i ) {
   this.i = i;
  }
  public int nextInt() { return ((i.next().getKey()).intValue()); };
  public int previousInt() { return ((i.previous().getKey()).intValue()); };
  public boolean hasNext() { return i.hasNext(); }
  public boolean hasPrevious() { return i.hasPrevious(); }
 }
 /** Returns a type-specific collection view of the values contained in this map.
	 *
	 * <P>The view is backed by the sorted set returned by {@link #entrySet()}. Note that
	 * <em>no attempt is made at caching the result of this method</em>, as this would
	 * require adding some attributes that lightweight implementations would
	 * not need. Subclasses may easily override this policy by calling
	 * this method and caching the result, but implementors are encouraged to
	 * write more efficient ad-hoc implementations.
	 *
	 * @return a type-specific collection view of the values contained in this map.
	 */
 public ObjectCollection <V> values() {
  return new ValuesCollection();
 }
 /** A wrapper exhibiting the values of a map. */
 protected class ValuesCollection extends AbstractObjectCollection <V> {
  public ObjectIterator <V> iterator() { return new ValuesIterator <V>( entrySet().iterator() ); }
  public boolean contains( final Object k ) { return containsValue( k ); }
  public int size() { return AbstractInt2ObjectSortedMap.this.size(); }
  public void clear() { AbstractInt2ObjectSortedMap.this.clear(); }
 }
 /** A wrapper exhibiting a map iterator as an iterator on values.
	 *
	 * <P>To provide an iterator on values, just create an instance of this
	 * class using the corresponding iterator on entries.
	 */
 protected static class ValuesIterator <V> extends AbstractObjectIterator <V> {
  protected final ObjectBidirectionalIterator<Map.Entry <Integer, V>> i;
  public ValuesIterator( ObjectBidirectionalIterator<Map.Entry <Integer, V>> i ) {
   this.i = i;
  }
  public V next() { return (i.next().getValue()); };
  public boolean hasNext() { return i.hasNext(); }
 }
 @SuppressWarnings({ "unchecked", "rawtypes" })
 public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
  return (ObjectSortedSet)int2ObjectEntrySet();
 }
}
