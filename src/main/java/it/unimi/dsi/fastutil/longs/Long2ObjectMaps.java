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
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectCollections;
import java.util.Map;
/** A class providing static methods and objects that do useful things with type-specific maps.
 *
 * @see java.util.Collections
 */
public class Long2ObjectMaps {
 private Long2ObjectMaps() {}
 /** An immutable class representing an empty type-specific map.
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific map.
	 */
 public static class EmptyMap <V> extends Long2ObjectFunctions.EmptyFunction <V> implements Long2ObjectMap <V>, java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected EmptyMap() {}
  public boolean containsValue( final Object v ) { return false; }
  public void putAll( final Map<? extends Long, ? extends V> m ) { throw new UnsupportedOperationException(); }
  @SuppressWarnings("unchecked")
  public ObjectSet<Long2ObjectMap.Entry <V> > long2ObjectEntrySet() { return ObjectSets.EMPTY_SET; }
 
  public LongSet keySet() { return LongSets.EMPTY_SET; }
  @SuppressWarnings("unchecked")
  public ObjectCollection <V> values() { return ObjectSets.EMPTY_SET; }
        private Object readResolve() { return EMPTY_MAP; }
  public Object clone() { return EMPTY_MAP; }
  public boolean isEmpty() { return true; }
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ObjectSet<Map.Entry<Long, V>> entrySet() { return (ObjectSet)long2ObjectEntrySet(); }
  public int hashCode() { return 0; }
  public boolean equals( final Object o ) {
   if ( ! ( o instanceof Map ) ) return false;
   return ((Map<?,?>)o).isEmpty();
  }
  public String toString() { return "{}"; }
 }
 /** An empty type-specific map (immutable). It is serializable and cloneable. 
	 */
 @SuppressWarnings("rawtypes")
 public static final EmptyMap EMPTY_MAP = new EmptyMap();
 /** Return an empty map (immutable). It is serializable and cloneable.
	 *
	 * <P>This method provides a typesafe access to {@link #EMPTY_MAP}.
	 * @return an empty map (immutable).
	 */
 @SuppressWarnings("unchecked")
 public static <V> Long2ObjectMap <V> emptyMap() {
  return EMPTY_MAP;
 }
 /** An immutable class representing a type-specific singleton map.	 
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific map.
	 */
 public static class Singleton <V> extends Long2ObjectFunctions.Singleton <V> implements Long2ObjectMap <V>, java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected transient ObjectSet<Long2ObjectMap.Entry <V> > entries;
  protected transient LongSet keys;
  protected transient ObjectCollection <V> values;
  protected Singleton( final long key, final V value ) {
   super( key, value );
  }
  public boolean containsValue( final Object v ) { return ( (value) == null ? (v) == null : (value).equals(v) ); }
  public void putAll( final Map<? extends Long, ? extends V> m ) { throw new UnsupportedOperationException(); }
  public ObjectSet<Long2ObjectMap.Entry <V> > long2ObjectEntrySet() { if ( entries == null ) entries = ObjectSets.singleton( (Long2ObjectMap.Entry <V>)new SingletonEntry() ); return entries; }
  public LongSet keySet() { if ( keys == null ) keys = LongSets.singleton( key ); return keys; }
  public ObjectCollection <V> values() { if ( values == null ) values = ObjectSets.singleton( value ); return values; }
  protected class SingletonEntry implements Long2ObjectMap.Entry <V>, Map.Entry<Long,V> {
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
   public Long getKey() { return (Long.valueOf(Singleton.this.key)); }
   public V getValue() { return (Singleton.this.value); }
   public long getLongKey() { return Singleton.this.key; }
   public V setValue( final V value ) { throw new UnsupportedOperationException(); }
   public boolean equals( final Object o ) {
    if (!(o instanceof Map.Entry)) return false;
    Map.Entry<?,?> e = (Map.Entry<?,?>)o;
    return ( (Singleton.this.key) == (((((Long)(e.getKey())).longValue()))) ) && ( (Singleton.this.value) == null ? ((e.getValue())) == null : (Singleton.this.value).equals((e.getValue())) );
   }
   public int hashCode() { return it.unimi.dsi.fastutil.HashCommon.long2int(Singleton.this.key) ^ ( (Singleton.this.value) == null ? 0 : (Singleton.this.value).hashCode() ); }
   public String toString() { return Singleton.this.key + "->" + Singleton.this.value; }
  }
  public boolean isEmpty() { return false; }
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ObjectSet<Map.Entry<Long, V>> entrySet() { return (ObjectSet)long2ObjectEntrySet(); }
  public int hashCode() { return it.unimi.dsi.fastutil.HashCommon.long2int(key) ^ ( (value) == null ? 0 : (value).hashCode() ); }
  public boolean equals( final Object o ) {
   if ( o == this ) return true;
   if ( ! ( o instanceof Map ) ) return false;
   Map<?,?> m = (Map<?,?>)o;
   if ( m.size() != 1 ) return false;
   return entrySet().iterator().next().equals( m.entrySet().iterator().next() );
  }
  public String toString() { return "{" + key + "=>" + value + "}"; }
 }
 /** Returns a type-specific immutable map containing only the specified pair. The returned map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned map.
	 * @param value the only value of the returned map.
	 * @return a type-specific immutable map containing just the pair <code>&lt;key,value&gt;</code>.
	 */
 public static <V> Long2ObjectMap <V> singleton( final long key, V value ) {
  return new Singleton <V>( key, value );
 }
 /** Returns a type-specific immutable map containing only the specified pair. The returned map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned map.
	 * @param value the only value of the returned map.
	 * @return a type-specific immutable map containing just the pair <code>&lt;key,value&gt;</code>.
	 */
 public static <V> Long2ObjectMap <V> singleton( final Long key, final V value ) {
  return new Singleton <V>( ((key).longValue()), (value) );
 }
 /** A synchronized wrapper class for maps. */
 public static class SynchronizedMap <V> extends Long2ObjectFunctions.SynchronizedFunction <V> implements Long2ObjectMap <V>, java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final Long2ObjectMap <V> map;
  protected transient ObjectSet<Long2ObjectMap.Entry <V> > entries;
  protected transient LongSet keys;
  protected transient ObjectCollection <V> values;
  protected SynchronizedMap( final Long2ObjectMap <V> m, final Object sync ) {
   super( m, sync );
   this.map = m;
  }
  protected SynchronizedMap( final Long2ObjectMap <V> m ) {
   super( m );
   this.map = m;
  }
  public int size() { synchronized( sync ) { return map.size(); } }
  public boolean containsKey( final long k ) { synchronized( sync ) { return map.containsKey( k ); } }
  public boolean containsValue( final Object v ) { synchronized( sync ) { return map.containsValue( v ); } }
  public V defaultReturnValue() { synchronized( sync ) { return map.defaultReturnValue(); } }
  public void defaultReturnValue( final V defRetValue ) { synchronized( sync ) { map.defaultReturnValue( defRetValue ); } }
  public V put( final long k, final V v ) { synchronized( sync ) { return map.put( k, v ); } }
  //public void putAll( final MAP KEY_VALUE_EXTENDS_GENERIC c ) { synchronized( sync ) { map.putAll( c ); } }
  public void putAll( final Map<? extends Long, ? extends V> m ) { synchronized( sync ) { map.putAll( m ); } }
  public ObjectSet<Long2ObjectMap.Entry <V> > long2ObjectEntrySet() { if ( entries == null ) entries = ObjectSets.synchronize( map.long2ObjectEntrySet(), sync ); return entries; }
  public LongSet keySet() { if ( keys == null ) keys = LongSets.synchronize( map.keySet(), sync ); return keys; }
  public ObjectCollection <V> values() { if ( values == null ) return ObjectCollections.synchronize( map.values(), sync ); return values; }
  public void clear() { synchronized( sync ) { map.clear(); } }
  public String toString() { synchronized( sync ) { return map.toString(); } }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  @Override
  public V put( final Long k, final V v ) { synchronized( sync ) { return map.put( k, v ); } }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  @Override
  public V remove( final long k ) { synchronized( sync ) { return map.remove( k ); } }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  @Override
  public V get( final long k ) { synchronized( sync ) { return map.get( k ); } }
  public boolean containsKey( final Object ok ) { synchronized( sync ) { return map.containsKey( ok ); } }
  public boolean isEmpty() { synchronized( sync ) { return map.isEmpty(); } }
  public ObjectSet<Map.Entry<Long, V>> entrySet() { synchronized( sync ) { return map.entrySet(); } }
  public int hashCode() { synchronized( sync ) { return map.hashCode(); } }
  public boolean equals( final Object o ) { synchronized( sync ) { return map.equals( o ); } }
 }
 /** Returns a synchronized type-specific map backed by the given type-specific map.
	 *
	 * @param m the map to be wrapped in a synchronized map.
	 * @return a synchronized view of the specified map.
	 * @see java.util.Collections#synchronizedMap(Map)
	 */
 public static <V> Long2ObjectMap <V> synchronize( final Long2ObjectMap <V> m ) { return new SynchronizedMap <V>( m ); }
 /** Returns a synchronized type-specific map backed by the given type-specific map, using an assigned object to synchronize.
	 *
	 * @param m the map to be wrapped in a synchronized map.
	 * @param sync an object that will be used to synchronize the access to the map.
	 * @return a synchronized view of the specified map.
	 * @see java.util.Collections#synchronizedMap(Map)
	 */
 public static <V> Long2ObjectMap <V> synchronize( final Long2ObjectMap <V> m, final Object sync ) { return new SynchronizedMap <V>( m, sync ); }
 /** An unmodifiable wrapper class for maps. */
 public static class UnmodifiableMap <V> extends Long2ObjectFunctions.UnmodifiableFunction <V> implements Long2ObjectMap <V>, java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final Long2ObjectMap <V> map;
  protected transient ObjectSet<Long2ObjectMap.Entry <V> > entries;
  protected transient LongSet keys;
  protected transient ObjectCollection <V> values;
  protected UnmodifiableMap( final Long2ObjectMap <V> m ) {
   super( m );
   this.map = m;
  }
  public int size() { return map.size(); }
  public boolean containsKey( final long k ) { return map.containsKey( k ); }
  public boolean containsValue( final Object v ) { return map.containsValue( v ); }
  public V defaultReturnValue() { throw new UnsupportedOperationException(); }
  public void defaultReturnValue( final V defRetValue ) { throw new UnsupportedOperationException(); }
  public V put( final long k, final V v ) { throw new UnsupportedOperationException(); }
  //public void putAll( final MAP KEY_VALUE_EXTENDS_GENERIC c ) { throw new UnsupportedOperationException(); }
  public void putAll( final Map<? extends Long, ? extends V> m ) { throw new UnsupportedOperationException(); }
  public ObjectSet<Long2ObjectMap.Entry <V> > long2ObjectEntrySet() { if ( entries == null ) entries = ObjectSets.unmodifiable( map.long2ObjectEntrySet() ); return entries; }
  public LongSet keySet() { if ( keys == null ) keys = LongSets.unmodifiable( map.keySet() ); return keys; }
  public ObjectCollection <V> values() { if ( values == null ) return ObjectCollections.unmodifiable( map.values() ); return values; }
  public void clear() { throw new UnsupportedOperationException(); }
  public String toString() { return map.toString(); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  @Override
  public V remove( final long k ) { throw new UnsupportedOperationException(); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  @Override
  public V get( final long k ) { return map.get( k ); }
  public boolean containsKey( final Object ok ) { return map.containsKey( ok ); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  @Override
  public V remove( final Object k ) { throw new UnsupportedOperationException(); }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  @Override
  public V get( final Object k ) { return map.get( k ); }
  public boolean isEmpty() { return map.isEmpty(); }
  public ObjectSet<Map.Entry<Long, V>> entrySet() { return ObjectSets.unmodifiable( map.entrySet() ); }
 }
 /** Returns an unmodifiable type-specific map backed by the given type-specific map.
	 *
	 * @param m the map to be wrapped in an unmodifiable map.
	 * @return an unmodifiable view of the specified map.
	 * @see java.util.Collections#unmodifiableMap(Map)
	 */
 public static <V> Long2ObjectMap <V> unmodifiable( final Long2ObjectMap <V> m ) { return new UnmodifiableMap <V>( m ); }
}
