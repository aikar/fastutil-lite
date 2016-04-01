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
/** A class providing static methods and objects that do useful things with type-specific functions.
 *
 * @see it.unimi.dsi.fastutil.Function
 * @see java.util.Collections
 */
public class Long2ObjectFunctions {
 private Long2ObjectFunctions() {}
 /** An immutable class representing an empty type-specific function.
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific function.
	 */
 public static class EmptyFunction <V> extends AbstractLong2ObjectFunction <V> implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected EmptyFunction() {}
  public V get( final long k ) { return (null); }
  public boolean containsKey( final long k ) { return false; }
  public V defaultReturnValue() { return (null); }
  public void defaultReturnValue( final V defRetValue ) { throw new UnsupportedOperationException(); }
  @Override
  public V get( final Object k ) { return null; }
  public int size() { return 0; }
  public void clear() {}
  private Object readResolve() { return EMPTY_FUNCTION; }
  public Object clone() { return EMPTY_FUNCTION; }
 }
 /** An empty type-specific function (immutable). It is serializable and cloneable. */
 @SuppressWarnings("rawtypes")
 public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();
 /** An immutable class representing a type-specific singleton function.	 
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific function.
	 */
 public static class Singleton <V> extends AbstractLong2ObjectFunction <V> implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final long key;
  protected final V value;
  protected Singleton( final long key, final V value ) {
   this.key = key;
   this.value = value;
  }
  public boolean containsKey( final long k ) { return ( (key) == (k) ); }
  public V get( final long k ) { if ( ( (key) == (k) ) ) return value; return defRetValue; }
  public int size() { return 1; }
  public Object clone() { return this; }
 }
 /** Returns a type-specific immutable function containing only the specified pair. The returned function is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned function is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned function.
	 * @param value the only value of the returned function.
	 * @return a type-specific immutable function containing just the pair <code>&lt;key,value&gt;</code>.
	 */
 public static <V> Long2ObjectFunction <V> singleton( final long key, V value ) {
  return new Singleton <V>( key, value );
 }
 /** Returns a type-specific immutable function containing only the specified pair. The returned function is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned function is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned function.
	 * @param value the only value of the returned function.
	 * @return a type-specific immutable function containing just the pair <code>&lt;key,value&gt;</code>.
	 */
 public static <V> Long2ObjectFunction <V> singleton( final Long key, final V value ) {
  return new Singleton <V>( ((key).longValue()), (value) );
 }
 /** A synchronized wrapper class for functions. */
 public static class SynchronizedFunction <V> extends AbstractLong2ObjectFunction <V> implements java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final Long2ObjectFunction <V> function;
  protected final Object sync;
  protected SynchronizedFunction( final Long2ObjectFunction <V> f, final Object sync ) {
   if ( f == null ) throw new NullPointerException();
   this.function = f;
   this.sync = sync;
  }
  protected SynchronizedFunction( final Long2ObjectFunction <V> f ) {
   if ( f == null ) throw new NullPointerException();
   this.function = f;
   this.sync = this;
  }
  public int size() { synchronized( sync ) { return function.size(); } }
  public boolean containsKey( final long k ) { synchronized( sync ) { return function.containsKey( k ); } }
  public V defaultReturnValue() { synchronized( sync ) { return function.defaultReturnValue(); } }
  public void defaultReturnValue( final V defRetValue ) { synchronized( sync ) { function.defaultReturnValue( defRetValue ); } }
  public V put( final long k, final V v ) { synchronized( sync ) { return function.put( k, v ); } }
  public void clear() { synchronized( sync ) { function.clear(); } }
  public String toString() { synchronized( sync ) { return function.toString(); } }
  /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
  @Deprecated
  @Override
  public V put( final Long k, final V v ) { synchronized( sync ) { return function.put( k, v ); } }
  @Override
  public V get( final Object k ) { synchronized( sync ) { return function.get( k ); } }
  @Override
  public V remove( final Object k ) { synchronized( sync ) { return function.remove( k ); } }
  @Override
  public V remove( final long k ) { synchronized( sync ) { return function.remove( k ); } }
  @Override
  public V get( final long k ) { synchronized( sync ) { return function.get( k ); } }
  public boolean containsKey( final Object ok ) { synchronized( sync ) { return function.containsKey( ok ); } }
 }
 /** Returns a synchronized type-specific function backed by the given type-specific function.
	 *
	 * @param f the function to be wrapped in a synchronized function.
	 * @return a synchronized view of the specified function.
	 * @see java.util.Collections#synchronizedMap(java.util.Map)
	 */
 public static <V> Long2ObjectFunction <V> synchronize( final Long2ObjectFunction <V> f ) { return new SynchronizedFunction <V>( f ); }
 /** Returns a synchronized type-specific function backed by the given type-specific function, using an assigned object to synchronize.
	 *
	 * @param f the function to be wrapped in a synchronized function.
	 * @param sync an object that will be used to synchronize the access to the function.
	 * @return a synchronized view of the specified function.
	 * @see java.util.Collections#synchronizedMap(java.util.Map)
	 */
 public static <V> Long2ObjectFunction <V> synchronize( final Long2ObjectFunction <V> f, final Object sync ) { return new SynchronizedFunction <V>( f, sync ); }
 /** An unmodifiable wrapper class for functions. */
 public static class UnmodifiableFunction <V> extends AbstractLong2ObjectFunction <V> implements java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final Long2ObjectFunction <V> function;
  protected UnmodifiableFunction( final Long2ObjectFunction <V> f ) {
   if ( f == null ) throw new NullPointerException();
   this.function = f;
  }
  public int size() { return function.size(); }
  public boolean containsKey( final long k ) { return function.containsKey( k ); }
  public V defaultReturnValue() { return function.defaultReturnValue(); }
  public void defaultReturnValue( final V defRetValue ) { throw new UnsupportedOperationException(); }
  public V put( final long k, final V v ) { throw new UnsupportedOperationException(); }
  public void clear() { throw new UnsupportedOperationException(); }
  public String toString() { return function.toString(); }
  @Override
  public V remove( final long k ) { throw new UnsupportedOperationException(); }
  @Override
  public V get( final long k ) { return function.get( k ); }
  public boolean containsKey( final Object ok ) { return function.containsKey( ok ); }
  @Override
  public V remove( final Object k ) { throw new UnsupportedOperationException(); }
  @Override
  public V get( final Object k ) { return function.get( k ); }
 }
 /** Returns an unmodifiable type-specific function backed by the given type-specific function.
	 *
	 * @param f the function to be wrapped in an unmodifiable function.
	 * @return an unmodifiable view of the specified function.
	 * @see java.util.Collections#unmodifiableMap(java.util.Map)
	 */
 public static <V> Long2ObjectFunction <V> unmodifiable( final Long2ObjectFunction <V> f ) { return new UnmodifiableFunction <V>( f ); }
}
