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
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import java.util.Map;
import java.util.SortedMap;
/** A type-specific {@link SortedMap}; provides some additional methods that use polymorphism to avoid (un)boxing.
 *
 * <P>Additionally, this interface strengthens {@link #entrySet()},
 * {@link #keySet()}, {@link #values()},
 * {@link #comparator()}, {@link SortedMap#subMap(Object,Object)}, {@link SortedMap#headMap(Object)} and {@link SortedMap#tailMap(Object)}.
 *
 * @see SortedMap
 */
public interface Long2ObjectSortedMap <V> extends Long2ObjectMap <V>, SortedMap<Long, V> {
 /** A sorted entry set providing fast iteration.
	 *
	 * <p>In some cases (e.g., hash-based classes) iteration over an entry set requires the creation
	 * of a large number of entry objects. Some <code>fastutil</code>
	 * maps might return {@linkplain #entrySet() entry set} objects of type <code>FastSortedEntrySet</code>: in this case, {@link #fastIterator() fastIterator()}
	 * will return an iterator that is guaranteed not to create a large number of objects, <em>possibly
	 * by returning always the same entry</em> (of course, mutated).
	 */
 public interface FastSortedEntrySet <V> extends ObjectSortedSet<Long2ObjectMap.Entry <V> >, FastEntrySet <V> {
  /** Returns a fast iterator over this sorted entry set; the iterator might return always the same entry object, suitably mutated.
		 *
		 * @return a fast iterator over this sorted entry set; the iterator might return always the same entry object, suitably mutated.
		 */
  public ObjectBidirectionalIterator<Long2ObjectMap.Entry <V> > fastIterator( Long2ObjectMap.Entry <V> from );
 }
 /** Returns a sorted-set view of the mappings contained in this map.
	 *  Note that this specification strengthens the one given in the
	 *  corresponding type-specific unsorted map.
	 *
	 * @return a sorted-set view of the mappings contained in this map.
	 * @see Map#entrySet()
	 */
 ObjectSortedSet<Map.Entry<Long, V>> entrySet();
 /** Returns a type-specific sorted-set view of the mappings contained in this map.
	 * Note that this specification strengthens the one given in the
	 * corresponding type-specific unsorted map.
	 *
	 * @return a type-specific sorted-set view of the mappings contained in this map.
	 * @see #entrySet()
	 */
 ObjectSortedSet<Long2ObjectMap.Entry <V> > long2ObjectEntrySet();
 /** Returns a sorted-set view of the keys contained in this map.
	 *  Note that this specification strengthens the one given in the
	 *  corresponding type-specific unsorted map.
	 *
	 * @return a sorted-set view of the keys contained in this map.
	 * @see Map#keySet()
	 */
 LongSortedSet keySet();
 /** Returns a set view of the values contained in this map.
	 * <P>Note that this specification strengthens the one given in {@link Map#values()},
	 * which was already strengthened in the corresponding type-specific class,
	 * but was weakened by the fact that this interface extends {@link SortedMap}.
	 *
	 * @return a set view of the values contained in this map.
	 * @see Map#values()
	 */
 ObjectCollection <V> values();
 /** Returns the comparator associated with this sorted set, or null if it uses its keys' natural ordering.
	 *
	 *  <P>Note that this specification strengthens the one given in {@link SortedMap#comparator()}.
	 *
	 * @see SortedMap#comparator()
	 */
 LongComparator comparator();
 /** Returns a view of the portion of this sorted map whose keys range from <code>fromKey</code>, inclusive, to <code>toKey</code>, exclusive.
	 *
	 *  <P>Note that this specification strengthens the one given in {@link SortedMap#subMap(Object,Object)}.
	 *
	 * @see SortedMap#subMap(Object,Object)
	 */
 Long2ObjectSortedMap <V> subMap(Long fromKey, Long toKey);
 /** Returns a view of the portion of this sorted map whose keys are strictly less than <code>toKey</code>.
	 *
	 *  <P>Note that this specification strengthens the one given in {@link SortedMap#headMap(Object)}.
	 *
	 * @see SortedMap#headMap(Object)
	 */
 Long2ObjectSortedMap <V> headMap(Long toKey);
 /** Returns a view of the portion of this sorted map whose keys are greater than or equal to <code>fromKey</code>.
	 *
	 *  <P>Note that this specification strengthens the one given in {@link SortedMap#tailMap(Object)}.
	 *
	 * @see SortedMap#tailMap(Object)
	 */
 Long2ObjectSortedMap <V> tailMap(Long fromKey);
 /**  Returns a view of the portion of this sorted map whose keys range from <code>fromKey</code>, inclusive, to <code>toKey</code>, exclusive.
	 * @see SortedMap#subMap(Object,Object)
	 */
 Long2ObjectSortedMap <V> subMap(long fromKey, long toKey);
 /** Returns a view of the portion of this sorted map whose keys are strictly less than <code>toKey</code>.
	 * @see SortedMap#headMap(Object)
	 */
 Long2ObjectSortedMap <V> headMap(long toKey);
 /** Returns a view of the portion of this sorted map whose keys are greater than or equal to <code>fromKey</code>.
	 * @see SortedMap#tailMap(Object)
	 */
 Long2ObjectSortedMap <V> tailMap(long fromKey);
 /**
	 * @see SortedMap#firstKey()
	 */
 long firstLongKey();
 /**
	 * @see SortedMap#lastKey()
	 */
 long lastLongKey();
}
