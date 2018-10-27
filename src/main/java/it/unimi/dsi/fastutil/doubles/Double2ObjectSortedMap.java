/*
	* Copyright (C) 2002-2017 Sebastiano Vigna
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
package it.unimi.dsi.fastutil.doubles;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import java.util.Map;
import java.util.SortedMap;
/** A type-specific {@link SortedMap}; provides some additional methods that use polymorphism to avoid (un)boxing.
	*
	* <p>Additionally, this interface strengthens {@link #entrySet()},
	* {@link #keySet()}, {@link #values()},
	* {@link #comparator()}, {@link SortedMap#subMap(Object,Object)}, {@link SortedMap#headMap(Object)} and {@link SortedMap#tailMap(Object)}.
	*
	* @see SortedMap
	*/
public interface Double2ObjectSortedMap <V> extends Double2ObjectMap <V>, SortedMap<Double, V> {
	/** Returns a view of the portion of this sorted map whose keys range from {@code fromKey}, inclusive, to {@code toKey}, exclusive.
	 *
	 * <p>Note that this specification strengthens the one given in {@link SortedMap#subMap(Object,Object)}.
	 *
	 * @see SortedMap#subMap(Object,Object)
	 */
	Double2ObjectSortedMap <V> subMap(double fromKey, double toKey);
	/** Returns a view of the portion of this sorted map whose keys are strictly less than {@code toKey}.
	 *
	 * <p>Note that this specification strengthens the one given in {@link SortedMap#headMap(Object)}.
	 *
	 * @see SortedMap#headMap(Object)
	 */
	Double2ObjectSortedMap <V> headMap(double toKey);
	/** Returns a view of the portion of this sorted map whose keys are greater than or equal to {@code fromKey}.
	 *
	 * <p>Note that this specification strengthens the one given in {@link SortedMap#tailMap(Object)}.
	 *
	 * @see SortedMap#tailMap(Object)
	 */
	Double2ObjectSortedMap <V> tailMap(double fromKey);
	/** Returns the first (lowest) key currently in this map.
	 * @see SortedMap#firstKey()
	 */
	double firstDoubleKey();
	/** Returns the last (highest) key currently in this map.
	 * @see SortedMap#lastKey()
	 */
	double lastDoubleKey();
	/** {@inheritDoc}
	 * <p>Note that this specification strengthens the one given in {@link SortedMap#subMap(Object,Object)}.
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	default Double2ObjectSortedMap <V> subMap(final Double from, final Double to) {
	 return subMap((from).doubleValue(), (to).doubleValue());
	}
	/** {@inheritDoc}
	 * <p>Note that this specification strengthens the one given in {@link SortedMap#headMap(Object)}.
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	default Double2ObjectSortedMap <V> headMap(final Double to) {
	 return headMap((to).doubleValue());
	}
	/** {@inheritDoc}
	 * <p>Note that this specification strengthens the one given in {@link SortedMap#tailMap(Object)}.
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	default Double2ObjectSortedMap <V> tailMap(final Double from) {
	 return tailMap((from).doubleValue());
	}
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	default Double firstKey() {
	 return Double.valueOf(firstDoubleKey());
	}
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	default Double lastKey() {
	 return Double.valueOf(lastDoubleKey());
	}
	/** A sorted entry set providing fast iteration.
	 *
	 * <p>In some cases (e.g., hash-based classes) iteration over an entry set requires the creation
	 * of a large number of entry objects. Some {@code fastutil}
	 * maps might return {@linkplain #entrySet() entry set} objects of type {@code FastSortedEntrySet}: in this case, {@link #fastIterator() fastIterator()}
	 * will return an iterator that is guaranteed not to create a large number of objects, <em>possibly
	 * by returning always the same entry</em> (of course, mutated).
	 */
	interface FastSortedEntrySet <V> extends ObjectSortedSet<Double2ObjectMap.Entry <V> >, FastEntrySet <V> {
	 /** {@inheritDoc}
		 */
	 @Override
	 ObjectBidirectionalIterator<Double2ObjectMap.Entry <V> > fastIterator();
	 /** Returns a fast iterator over this entry set, starting from a given element of the domain (optional operation);
		 * the iterator might return always the same entry instance, suitably mutated.
		 *
		 * @param from an element to start from.
		 * @return a fast iterator over this sorted entry set starting at {@code from}; the iterator might return always the same entry object, suitably mutated.
		 */
	 ObjectBidirectionalIterator<Double2ObjectMap.Entry <V> > fastIterator(Double2ObjectMap.Entry <V> from);
	}
	/** Returns a sorted-set view of the mappings contained in this map.
	 *  <p>Note that this specification strengthens the one given in the
	 *  corresponding type-specific unsorted map.
	 *
	 * @return a sorted-set view of the mappings contained in this map.
	 * @see SortedMap#entrySet()
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Deprecated
	@Override
	default ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
	 return (ObjectSortedSet)double2ObjectEntrySet();
	}
	/** Returns a type-specific sorted-set view of the mappings contained in this map.
	 * <p>Note that this specification strengthens the one given in the
	 * corresponding type-specific unsorted map.
	 *
	 * @return a type-specific sorted-set view of the mappings contained in this map.
	 * @see #entrySet()
	 */
	@Override
	ObjectSortedSet<Double2ObjectMap.Entry <V> > double2ObjectEntrySet();
	/** Returns a type-specific sorted-set view of the keys contained in this map.
	 *  <p>Note that this specification strengthens the one given in the
	 *  corresponding type-specific unsorted map.
	 *
	 * @return a sorted-set view of the keys contained in this map.
	 * @see SortedMap#keySet()
	 */
	@Override
	DoubleSortedSet keySet();
	/** Returns a type-specific set view of the values contained in this map.
	 * <p>Note that this specification strengthens the one given in {@link Map#values()},
	 * which was already strengthened in the corresponding type-specific class,
	 * but was weakened by the fact that this interface extends {@link SortedMap}.
	 *
	 * @return a set view of the values contained in this map.
	 * @see SortedMap#values()
	 */
	@Override
	ObjectCollection <V> values();
	/** Returns the comparator associated with this sorted set, or null if it uses its keys' natural ordering.
	 *
	 *  <p>Note that this specification strengthens the one given in {@link SortedMap#comparator()}.
	 *
	 * @see SortedMap#comparator()
	 */
	@Override
	DoubleComparator comparator();
}
