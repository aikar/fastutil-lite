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
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.NoSuchElementException;
/** A class providing static methods and objects that do useful things with type-specific sorted maps.
 *
 * @see java.util.Collections
 */
public class Long2ObjectSortedMaps {
 private Long2ObjectSortedMaps() {}
 /** Returns a comparator for entries based on a given comparator on keys.
	 *
	 * @param comparator a comparator on keys.
	 * @return the associated comparator on entries.
	 */
 public static Comparator<? super Map.Entry<Long, ?>> entryComparator( final LongComparator comparator ) {
  return new Comparator<Map.Entry<Long, ?>>() {
   public int compare( Map.Entry<Long, ?> x, Map.Entry<Long, ?> y ) {
    return comparator.compare( x.getKey(), y.getKey() );
   }
  };
 }
 /** An immutable class representing an empty type-specific sorted map. 
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */
 public static class EmptySortedMap <V> extends Long2ObjectMaps.EmptyMap <V> implements Long2ObjectSortedMap <V>, java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected EmptySortedMap() {}
  public LongComparator comparator() { return null; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Long2ObjectMap.Entry <V> > long2ObjectEntrySet() { return ObjectSortedSets.EMPTY_SET; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Map.Entry<Long, V>> entrySet() { return ObjectSortedSets.EMPTY_SET; }
 
  public LongSortedSet keySet() { return LongSortedSets.EMPTY_SET; }
  @SuppressWarnings("unchecked")
  public Long2ObjectSortedMap <V> subMap( final long from, final long to ) { return EMPTY_MAP; }
  @SuppressWarnings("unchecked")
  public Long2ObjectSortedMap <V> headMap( final long to ) { return EMPTY_MAP; }
  @SuppressWarnings("unchecked")
  public Long2ObjectSortedMap <V> tailMap( final long from ) { return EMPTY_MAP; }
  public long firstLongKey() { throw new NoSuchElementException(); }
  public long lastLongKey() { throw new NoSuchElementException(); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Long2ObjectSortedMap <V> headMap( Long oto ) { return headMap( ((oto).longValue()) ); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Long2ObjectSortedMap <V> tailMap( Long ofrom ) { return tailMap( ((ofrom).longValue()) ); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Long2ObjectSortedMap <V> subMap( Long ofrom, Long oto ) { return subMap( ((ofrom).longValue()), ((oto).longValue()) ); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Long firstKey() { return (Long.valueOf(firstLongKey())); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Long lastKey() { return (Long.valueOf(lastLongKey())); }
 }
 /** An empty sorted map (immutable). It is serializable and cloneable. 
	 */
 @SuppressWarnings("rawtypes")
 public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
 /** Return an empty sorted map (immutable). It is serializable and cloneable.
	 *
	 * <P>This method provides a typesafe access to {@link #EMPTY_MAP}.
	 * @return an empty sorted map (immutable).
	 */
 @SuppressWarnings("unchecked")
 public static <V> Long2ObjectSortedMap <V> emptyMap() {
  return EMPTY_MAP;
 }
 /** An immutable class representing a type-specific singleton sorted map. 
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */
 public static class Singleton <V> extends Long2ObjectMaps.Singleton <V> implements Long2ObjectSortedMap <V>, java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final LongComparator comparator;
  protected Singleton( final long key, final V value, LongComparator comparator ) {
   super( key, value );
   this.comparator = comparator;
  }
  protected Singleton( final long key, final V value ) {
   this( key, value, null );
  }
 
  final int compare( final long k1, final long k2 ) {
   return comparator == null ? ( Long.compare((k1),(k2)) ) : comparator.compare( k1, k2 );
  }
  public LongComparator comparator() { return comparator; }
 
  public ObjectSortedSet<Long2ObjectMap.Entry <V> > long2ObjectEntrySet() { if ( entries == null ) entries = ObjectSortedSets.singleton( (Long2ObjectMap.Entry <V>)new SingletonEntry(), (Comparator<? super Long2ObjectMap.Entry <V> >)entryComparator( comparator ) ); return (ObjectSortedSet<Long2ObjectMap.Entry <V> >)entries; }
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ObjectSortedSet<Map.Entry<Long, V>> entrySet() { return (ObjectSortedSet)long2ObjectEntrySet(); }
  public LongSortedSet keySet() { if ( keys == null ) keys = LongSortedSets.singleton( key, comparator ); return (LongSortedSet )keys; }
  @SuppressWarnings("unchecked")
  public Long2ObjectSortedMap <V> subMap( final long from, final long to ) { if ( compare( from, key ) <= 0 && compare( key, to ) < 0 ) return this; return EMPTY_MAP; }
  @SuppressWarnings("unchecked")
  public Long2ObjectSortedMap <V> headMap( final long to ) { if ( compare( key, to ) < 0 ) return this; return EMPTY_MAP; }
  @SuppressWarnings("unchecked")
  public Long2ObjectSortedMap <V> tailMap( final long from ) { if ( compare( from, key ) <= 0 ) return this; return EMPTY_MAP; }
  public long firstLongKey() { return key; }
  public long lastLongKey() { return key; }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Long2ObjectSortedMap <V> headMap( Long oto ) { return headMap( ((oto).longValue()) ); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Long2ObjectSortedMap <V> tailMap( Long ofrom ) { return tailMap( ((ofrom).longValue()) ); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Long2ObjectSortedMap <V> subMap( Long ofrom, Long oto ) { return subMap( ((ofrom).longValue()), ((oto).longValue()) ); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Long firstKey() { return (Long.valueOf(firstLongKey())); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  public Long lastKey() { return (Long.valueOf(lastLongKey())); }
 }
 /** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair <code>&lt;key,value&gt;</code>.
	 */
 public static <V> Long2ObjectSortedMap <V> singleton( final Long key, V value ) {
  return new Singleton <V>( ((key).longValue()), (value) );
 }
 /** RETURNS a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @param comparator the comparator to use in the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair <code>&lt;key,value&gt;</code>.
	 */
 public static <V> Long2ObjectSortedMap <V> singleton( final Long key, V value, LongComparator comparator ) {
  return new Singleton <V>( ((key).longValue()), (value), comparator );
 }
 /** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair <code>&lt;key,value&gt;</code>.
	 */
 public static <V> Long2ObjectSortedMap <V> singleton( final long key, final V value ) {
  return new Singleton <V>( key, value );
 }
 /** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @param comparator the comparator to use in the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair <code>&lt;key,value&gt;</code>.
	 */
 public static <V> Long2ObjectSortedMap <V> singleton( final long key, final V value, LongComparator comparator ) {
  return new Singleton <V>( key, value, comparator );
 }
  /** A synchronized wrapper class for sorted maps. */
 public static class SynchronizedSortedMap <V> extends Long2ObjectMaps.SynchronizedMap <V> implements Long2ObjectSortedMap <V>, java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final Long2ObjectSortedMap <V> sortedMap;
  protected SynchronizedSortedMap( final Long2ObjectSortedMap <V> m, final Object sync ) {
   super( m, sync );
   sortedMap = m;
  }
  protected SynchronizedSortedMap( final Long2ObjectSortedMap <V> m ) {
   super( m );
   sortedMap = m;
  }
  public LongComparator comparator() { synchronized( sync ) { return sortedMap.comparator(); } }
  public ObjectSortedSet<Long2ObjectMap.Entry <V> > long2ObjectEntrySet() { if ( entries == null ) entries = ObjectSortedSets.synchronize( sortedMap.long2ObjectEntrySet(), sync ); return (ObjectSortedSet<Long2ObjectMap.Entry <V> >)entries; }
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ObjectSortedSet<Map.Entry<Long, V>> entrySet() { return (ObjectSortedSet)long2ObjectEntrySet(); }
  public LongSortedSet keySet() { if ( keys == null ) keys = LongSortedSets.synchronize( sortedMap.keySet(), sync ); return (LongSortedSet )keys; }
  public Long2ObjectSortedMap <V> subMap( final long from, final long to ) { return new SynchronizedSortedMap <V>( sortedMap.subMap( from, to ), sync ); }
  public Long2ObjectSortedMap <V> headMap( final long to ) { return new SynchronizedSortedMap <V>( sortedMap.headMap( to ), sync ); }
  public Long2ObjectSortedMap <V> tailMap( final long from ) { return new SynchronizedSortedMap <V>( sortedMap.tailMap( from ), sync ); }
  public long firstLongKey() { synchronized( sync ) { return sortedMap.firstLongKey(); } }
  public long lastLongKey() { synchronized( sync ) { return sortedMap.lastLongKey(); } }
  public Long firstKey() { synchronized( sync ) { return sortedMap.firstKey(); } }
  public Long lastKey() { synchronized( sync ) { return sortedMap.lastKey(); } }
  public Long2ObjectSortedMap <V> subMap( final Long from, final Long to ) { return new SynchronizedSortedMap <V>( sortedMap.subMap( from, to ), sync ); }
  public Long2ObjectSortedMap <V> headMap( final Long to ) { return new SynchronizedSortedMap <V>( sortedMap.headMap( to ), sync ); }
  public Long2ObjectSortedMap <V> tailMap( final Long from ) { return new SynchronizedSortedMap <V>( sortedMap.tailMap( from ), sync ); }
 }
 /** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */
 public static <V> Long2ObjectSortedMap <V> synchronize( final Long2ObjectSortedMap <V> m ) { return new SynchronizedSortedMap <V>( m ); }
 /** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map, using an assigned object to synchronize.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @param sync an object that will be used to synchronize the access to the sorted sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */
 public static <V> Long2ObjectSortedMap <V> synchronize( final Long2ObjectSortedMap <V> m, final Object sync ) { return new SynchronizedSortedMap <V>( m, sync ); }
 /** An unmodifiable wrapper class for sorted maps. */
 public static class UnmodifiableSortedMap <V> extends Long2ObjectMaps.UnmodifiableMap <V> implements Long2ObjectSortedMap <V>, java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final Long2ObjectSortedMap <V> sortedMap;
  protected UnmodifiableSortedMap( final Long2ObjectSortedMap <V> m ) {
   super( m );
   sortedMap = m;
  }
  public LongComparator comparator() { return sortedMap.comparator(); }
  public ObjectSortedSet<Long2ObjectMap.Entry <V> > long2ObjectEntrySet() { if ( entries == null ) entries = ObjectSortedSets.unmodifiable( sortedMap.long2ObjectEntrySet() ); return (ObjectSortedSet<Long2ObjectMap.Entry <V> >)entries; }
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ObjectSortedSet<Map.Entry<Long, V>> entrySet() { return (ObjectSortedSet)long2ObjectEntrySet(); }
  public LongSortedSet keySet() { if ( keys == null ) keys = LongSortedSets.unmodifiable( sortedMap.keySet() ); return (LongSortedSet )keys; }
  public Long2ObjectSortedMap <V> subMap( final long from, final long to ) { return new UnmodifiableSortedMap <V>( sortedMap.subMap( from, to ) ); }
  public Long2ObjectSortedMap <V> headMap( final long to ) { return new UnmodifiableSortedMap <V>( sortedMap.headMap( to ) ); }
  public Long2ObjectSortedMap <V> tailMap( final long from ) { return new UnmodifiableSortedMap <V>( sortedMap.tailMap( from ) ); }
  public long firstLongKey() { return sortedMap.firstLongKey(); }
  public long lastLongKey() { return sortedMap.lastLongKey(); }
  public Long firstKey() { return sortedMap.firstKey(); }
  public Long lastKey() { return sortedMap.lastKey(); }
  public Long2ObjectSortedMap <V> subMap( final Long from, final Long to ) { return new UnmodifiableSortedMap <V>( sortedMap.subMap( from, to ) ); }
  public Long2ObjectSortedMap <V> headMap( final Long to ) { return new UnmodifiableSortedMap <V>( sortedMap.headMap( to ) ); }
  public Long2ObjectSortedMap <V> tailMap( final Long from ) { return new UnmodifiableSortedMap <V>( sortedMap.tailMap( from ) ); }
 }
 /** Returns an unmodifiable type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in an unmodifiable sorted map.
	 * @return an unmodifiable view of the specified sorted map.
	 * @see java.util.Collections#unmodifiableSortedMap(SortedMap)
	 */
 public static <V> Long2ObjectSortedMap <V> unmodifiable( final Long2ObjectSortedMap <V> m ) { return new UnmodifiableSortedMap <V>( m ); }
}
