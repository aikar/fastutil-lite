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
import java.util.Collection;
import java.util.Set;
/** A class providing static methods and objects that do useful things with type-specific sets.
 *
 * @see java.util.Collections
 */
public class IntSets {
 private IntSets() {}
 /** An immutable class representing the empty set and implementing a type-specific set interface.
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific set.
	 */
 public static class EmptySet extends IntCollections.EmptyCollection implements IntSet , java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected EmptySet() {}
  public boolean remove( int ok ) { throw new UnsupportedOperationException(); }
  public Object clone() { return EMPTY_SET; }
  @SuppressWarnings("rawtypes")
  public boolean equals( final Object o ) { return o instanceof Set && ((Set)o).isEmpty(); }
        private Object readResolve() { return EMPTY_SET; }
 }
 /** An empty set (immutable). It is serializable and cloneable.
	 */

 public static final EmptySet EMPTY_SET = new EmptySet();
 /** An immutable class representing a type-specific singleton set.
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific set.	 */
 public static class Singleton extends AbstractIntSet implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected final int element;
  protected Singleton( final int element ) {
   this.element = element;
  }
  public boolean add( final int k ) { throw new UnsupportedOperationException(); }
  public boolean contains( final int k ) { return ( (k) == (element) ); }
  public boolean addAll( final Collection<? extends Integer> c ) { throw new UnsupportedOperationException(); }
  public boolean removeAll( final Collection<?> c ) { throw new UnsupportedOperationException(); }
  public boolean retainAll( final Collection<?> c ) { throw new UnsupportedOperationException(); }
  /* Slightly optimized w.r.t. the one in ABSTRACT_SET. */
  public int[] toIntArray() {
   int a[] = new int[ 1 ];
   a[ 0 ] = element;
   return a;
  }
  public boolean addAll( final IntCollection c ) { throw new UnsupportedOperationException(); }
  public boolean removeAll( final IntCollection c ) { throw new UnsupportedOperationException(); }
  public boolean retainAll( final IntCollection c ) { throw new UnsupportedOperationException(); }
  public IntListIterator iterator() { return IntIterators.singleton( element ); }
  public int size() { return 1; }
  public Object clone() { return this; }
 }
 /** Returns a type-specific immutable set containing only the specified element. The returned set is serializable and cloneable.
	 *
	 * @param element the only element of the returned set.
	 * @return a type-specific immutable set containing just <code>element</code>.
	 */
 public static IntSet singleton( final int element ) {
  return new Singleton ( element );
 }
 /** Returns a type-specific immutable set containing only the specified element. The returned set is serializable and cloneable.
	 *
	 * @param element the only element of the returned set.
	 * @return a type-specific immutable set containing just <code>element</code>.
	 */
 public static IntSet singleton( final Integer element ) {
  return new Singleton ( ((element).intValue()) );
 }
 /** A synchronized wrapper class for sets. */
 public static class SynchronizedSet extends IntCollections.SynchronizedCollection implements IntSet , java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected SynchronizedSet( final IntSet s, final Object sync ) {
   super( s, sync );
  }
  protected SynchronizedSet( final IntSet s ) {
   super( s );
  }
  public boolean remove( final int k ) { synchronized( sync ) { return collection.remove( (Integer.valueOf(k)) ); } }
  public boolean equals( final Object o ) { synchronized( sync ) { return collection.equals( o ); } }
  public int hashCode() { synchronized( sync ) { return collection.hashCode(); } }
 }
 /** Returns a synchronized type-specific set backed by the given type-specific set.
	 *
	 * @param s the set to be wrapped in a synchronized set.
	 * @return a synchronized view of the specified set.
	 * @see java.util.Collections#synchronizedSet(Set)
	 */
 public static IntSet synchronize( final IntSet s ) { return new SynchronizedSet ( s ); }
 /** Returns a synchronized type-specific set backed by the given type-specific set, using an assigned object to synchronize.
	 *
	 * @param s the set to be wrapped in a synchronized set.
	 * @param sync an object that will be used to synchronize the access to the set.
	 * @return a synchronized view of the specified set.
	 * @see java.util.Collections#synchronizedSet(Set)
	 */
 public static IntSet synchronize( final IntSet s, final Object sync ) { return new SynchronizedSet ( s, sync ); }
 /** An unmodifiable wrapper class for sets. */
 public static class UnmodifiableSet extends IntCollections.UnmodifiableCollection implements IntSet , java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  protected UnmodifiableSet( final IntSet s ) {
   super( s );
  }
  public boolean remove( final int k ) { throw new UnsupportedOperationException(); }
  public boolean equals( final Object o ) { return collection.equals( o ); }
  public int hashCode() { return collection.hashCode(); }
 }
 /** Returns an unmodifiable type-specific set backed by the given type-specific set.
	 *
	 * @param s the set to be wrapped in an unmodifiable set.
	 * @return an unmodifiable view of the specified set.
	 * @see java.util.Collections#unmodifiableSet(Set)
	 */
 public static IntSet unmodifiable( final IntSet s ) { return new UnmodifiableSet ( s ); }
}
