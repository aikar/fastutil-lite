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
package it.unimi.dsi.fastutil.objects;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap.FastSortedEntrySet;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.NoSuchElementException;
/** A class providing static methods and objects that do useful things with type-specific sorted maps.
	*
	* @see java.util.Collections
	*/
public final class Object2ObjectSortedMaps {
	private Object2ObjectSortedMaps() {}
	/** Returns a comparator for entries based on a given comparator on keys.
	 *
	 * @param comparator a comparator on keys.
	 * @return the associated comparator on entries.
	 */
	public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(final Comparator <? super K> comparator) {
	 return (Comparator<Map.Entry<K, ?>>) (x, y) -> comparator.compare((x.getKey()), (y.getKey()));
	}
	/** Returns a bidirectional iterator that will be {@linkplain FastSortedEntrySet fast}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map from which we will try to extract a (fast) bidirectional iterator on the entry set.
	 * @return a bidirectional iterator on the entry set of the given map that will be fast, if possible.
	 * @since 8.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <K,V> ObjectBidirectionalIterator<Object2ObjectMap.Entry <K,V> > fastIterator(Object2ObjectSortedMap <K,V> map) {
	 final ObjectSortedSet<Object2ObjectMap.Entry <K,V> > entries = map.object2ObjectEntrySet();
	 return entries instanceof Object2ObjectSortedMap.FastSortedEntrySet ? ((Object2ObjectSortedMap.FastSortedEntrySet <K,V>) entries).fastIterator() : entries.iterator();
	}
	/** Returns an iterable yielding a bidirectional iterator that will be {@linkplain FastSortedEntrySet fast}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map from which we will try to extract an iterable yielding a (fast) bidirectional iterator on the entry set.
	 * @return an iterable yielding a bidirectional iterator on the entry set of the given map that will be fast, if possible.
	 * @since 8.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <K,V> ObjectBidirectionalIterable<Object2ObjectMap.Entry <K,V> > fastIterable(Object2ObjectSortedMap <K,V> map) {
	 final ObjectSortedSet<Object2ObjectMap.Entry <K,V> > entries = map.object2ObjectEntrySet();
	 return entries instanceof Object2ObjectSortedMap.FastSortedEntrySet ? ((Object2ObjectSortedMap.FastSortedEntrySet <K,V>)entries)::fastIterator : entries;
	}
	/** An immutable class representing an empty type-specific sorted map.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */
	public static class EmptySortedMap <K,V> extends Object2ObjectMaps.EmptyMap <K,V> implements Object2ObjectSortedMap <K,V>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptySortedMap() {}
	 @Override
	 public Comparator <? super K> comparator() { return null; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public ObjectSortedSet<Object2ObjectMap.Entry <K,V> > object2ObjectEntrySet() { return ObjectSortedSets.EMPTY_SET; }
	 /** {@inheritDoc} */
	 @Override
	 @SuppressWarnings("unchecked")
	 public ObjectSortedSet<Map.Entry<K, V>> entrySet() { return ObjectSortedSets.EMPTY_SET; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public ObjectSortedSet <K> keySet() { return ObjectSortedSets.EMPTY_SET; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Object2ObjectSortedMap <K,V> subMap(final K from, final K to) { return EMPTY_MAP; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Object2ObjectSortedMap <K,V> headMap(final K to) { return EMPTY_MAP; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Object2ObjectSortedMap <K,V> tailMap(final K from) { return EMPTY_MAP; }
	 @Override
	 public K firstKey() { throw new NoSuchElementException(); }
	 @Override
	 public K lastKey() { throw new NoSuchElementException(); }
	}
	/** An empty sorted map (immutable). It is serializable and cloneable.
	 */
	@SuppressWarnings("rawtypes")
	public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
	/** Returns an empty sorted map (immutable). It is serializable and cloneable.
	 *
	 * <p>This method provides a typesafe access to {@link #EMPTY_MAP}.
	 * @return an empty sorted map (immutable).
	 */
	@SuppressWarnings("unchecked")
	public static <K,V> Object2ObjectSortedMap <K,V> emptyMap() {
	 return EMPTY_MAP;
	}
	/** An immutable class representing a type-specific singleton sorted map.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */
	public static class Singleton <K,V> extends Object2ObjectMaps.Singleton <K,V> implements Object2ObjectSortedMap <K,V>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final Comparator <? super K> comparator;
	 protected Singleton(final K key, final V value, Comparator <? super K> comparator) {
	  super(key, value);
	  this.comparator = comparator;
	 }
	 protected Singleton(final K key, final V value) {
	  this(key, value, null);
	 }
	 @SuppressWarnings("unchecked")
	 final int compare(final K k1, final K k2) {
	  return comparator == null ? ( ((Comparable<K>)(k1)).compareTo(k2) ) : comparator.compare(k1, k2);
	 }
	 @Override
	 public Comparator <? super K> comparator() { return comparator; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public ObjectSortedSet<Object2ObjectMap.Entry <K,V> > object2ObjectEntrySet() { if (entries == null) entries = ObjectSortedSets.singleton(new AbstractObject2ObjectMap.BasicEntry <>(key, value), entryComparator(comparator)); return (ObjectSortedSet<Object2ObjectMap.Entry <K,V> >)entries; }
	 /** {@inheritDoc} */
	 @Override
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public ObjectSortedSet<Map.Entry<K, V>> entrySet() { return (ObjectSortedSet)object2ObjectEntrySet(); }
	 @Override
	 public ObjectSortedSet <K> keySet() { if (keys == null) keys = ObjectSortedSets.singleton(key, comparator); return (ObjectSortedSet <K>)keys; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Object2ObjectSortedMap <K,V> subMap(final K from, final K to) { if (compare(from, key) <= 0 && compare(key, to) < 0) return this; return EMPTY_MAP; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Object2ObjectSortedMap <K,V> headMap(final K to) { if (compare(key, to) < 0) return this; return EMPTY_MAP; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Object2ObjectSortedMap <K,V> tailMap(final K from) { if (compare(from, key) <= 0) return this; return EMPTY_MAP; }
	 @Override
	 public K firstKey() { return key; }
	 @Override
	 public K lastKey() { return key; }
	}
	/** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <p>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair {@code &lt;key,value&gt;}.
	 */
	public static <K,V> Object2ObjectSortedMap <K,V> singleton(final K key, V value) { return new Singleton <>((key), (value));}
	/** RETURNS a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <p>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @param comparator the comparator to use in the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair {@code &lt;key,value&gt;}.
	 */
	public static <K,V> Object2ObjectSortedMap <K,V> singleton(final K key, V value, Comparator <? super K> comparator) { return new Singleton <>((key), (value), comparator); }
	 /** A synchronized wrapper class for sorted maps. */
	public static class SynchronizedSortedMap <K,V> extends Object2ObjectMaps.SynchronizedMap <K,V> implements Object2ObjectSortedMap <K,V>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final Object2ObjectSortedMap <K,V> sortedMap;
	 protected SynchronizedSortedMap(final Object2ObjectSortedMap <K,V> m, final Object sync) {
	  super(m, sync);
	  sortedMap = m;
	 }
	 protected SynchronizedSortedMap(final Object2ObjectSortedMap <K,V> m) {
	  super(m);
	  sortedMap = m;
	 }
	 @Override
	 public Comparator <? super K> comparator() { synchronized(sync) { return sortedMap.comparator(); } }
	 @Override
	 public ObjectSortedSet<Object2ObjectMap.Entry <K,V> > object2ObjectEntrySet() { if (entries == null) entries = ObjectSortedSets.synchronize(sortedMap.object2ObjectEntrySet(), sync); return (ObjectSortedSet<Object2ObjectMap.Entry <K,V> >)entries; }
	 /** {@inheritDoc} */
	 @Override
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public ObjectSortedSet<Map.Entry<K, V>> entrySet() { return (ObjectSortedSet)object2ObjectEntrySet(); }
	 @Override
	 public ObjectSortedSet <K> keySet() { if (keys == null) keys = ObjectSortedSets.synchronize(sortedMap.keySet(), sync); return (ObjectSortedSet <K>)keys; }
	 @Override
	 public Object2ObjectSortedMap <K,V> subMap(final K from, final K to) { return new SynchronizedSortedMap <>(sortedMap.subMap(from, to), sync); }
	 @Override
	 public Object2ObjectSortedMap <K,V> headMap(final K to) { return new SynchronizedSortedMap <>(sortedMap.headMap(to), sync); }
	 @Override
	 public Object2ObjectSortedMap <K,V> tailMap(final K from) { return new SynchronizedSortedMap <>(sortedMap.tailMap(from), sync); }
	 @Override
	 public K firstKey() { synchronized(sync) { return sortedMap.firstKey(); } }
	 @Override
	 public K lastKey() { synchronized(sync) { return sortedMap.lastKey(); } }
	}
	/** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */
	public static <K,V> Object2ObjectSortedMap <K,V> synchronize(final Object2ObjectSortedMap <K,V> m) { return new SynchronizedSortedMap <>(m); }
	/** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map, using an assigned object to synchronize.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @param sync an object that will be used to synchronize the access to the sorted sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */
	public static <K,V> Object2ObjectSortedMap <K,V> synchronize(final Object2ObjectSortedMap <K,V> m, final Object sync) { return new SynchronizedSortedMap <>(m, sync); }
	/** An unmodifiable wrapper class for sorted maps. */
	public static class UnmodifiableSortedMap <K,V> extends Object2ObjectMaps.UnmodifiableMap <K,V> implements Object2ObjectSortedMap <K,V>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final Object2ObjectSortedMap <K,V> sortedMap;
	 protected UnmodifiableSortedMap(final Object2ObjectSortedMap <K,V> m) {
	  super(m);
	  sortedMap = m;
	 }
	 @Override
	 public Comparator <? super K> comparator() { return sortedMap.comparator(); }
	 @Override
	 public ObjectSortedSet<Object2ObjectMap.Entry <K,V> > object2ObjectEntrySet() { if (entries == null) entries = ObjectSortedSets.unmodifiable(sortedMap.object2ObjectEntrySet()); return (ObjectSortedSet<Object2ObjectMap.Entry <K,V> >)entries; }
	 /** {@inheritDoc} */
	 @Override
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public ObjectSortedSet<Map.Entry<K, V>> entrySet() { return (ObjectSortedSet)object2ObjectEntrySet(); }
	 @Override
	 public ObjectSortedSet <K> keySet() { if (keys == null) keys = ObjectSortedSets.unmodifiable(sortedMap.keySet()); return (ObjectSortedSet <K>)keys; }
	 @Override
	 public Object2ObjectSortedMap <K,V> subMap(final K from, final K to) { return new UnmodifiableSortedMap <>(sortedMap.subMap(from, to)); }
	 @Override
	 public Object2ObjectSortedMap <K,V> headMap(final K to) { return new UnmodifiableSortedMap <>(sortedMap.headMap(to)); }
	 @Override
	 public Object2ObjectSortedMap <K,V> tailMap(final K from) { return new UnmodifiableSortedMap <>(sortedMap.tailMap(from)); }
	 @Override
	 public K firstKey() { return sortedMap.firstKey(); }
	 @Override
	 public K lastKey() { return sortedMap.lastKey(); }
	}
	/** Returns an unmodifiable type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in an unmodifiable sorted map.
	 * @return an unmodifiable view of the specified sorted map.
	 * @see java.util.Collections#unmodifiableSortedMap(SortedMap)
	 */
	public static <K,V> Object2ObjectSortedMap <K,V> unmodifiable(final Object2ObjectSortedMap <K,V> m) { return new UnmodifiableSortedMap <>(m); }
}
