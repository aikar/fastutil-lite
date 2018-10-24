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
package it.unimi.dsi.fastutil.ints;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap.FastSortedEntrySet;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.NoSuchElementException;
/** A class providing static methods and objects that do useful things with type-specific sorted maps.
	*
	* @see java.util.Collections
	*/
public final class Int2ObjectSortedMaps {
	private Int2ObjectSortedMaps() {}
	/** Returns a comparator for entries based on a given comparator on keys.
	 *
	 * @param comparator a comparator on keys.
	 * @return the associated comparator on entries.
	 */
	public static Comparator<? super Map.Entry<Integer, ?>> entryComparator(final IntComparator comparator) {
	 return (Comparator<Map.Entry<Integer, ?>>) (x, y) -> comparator.compare((x.getKey()).intValue(), (y.getKey()).intValue());
	}
	/** Returns a bidirectional iterator that will be {@linkplain FastSortedEntrySet fast}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map from which we will try to extract a (fast) bidirectional iterator on the entry set.
	 * @return a bidirectional iterator on the entry set of the given map that will be fast, if possible.
	 * @since 8.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <V> ObjectBidirectionalIterator<Int2ObjectMap.Entry <V> > fastIterator(Int2ObjectSortedMap <V> map) {
	 final ObjectSortedSet<Int2ObjectMap.Entry <V> > entries = map.int2ObjectEntrySet();
	 return entries instanceof Int2ObjectSortedMap.FastSortedEntrySet ? ((Int2ObjectSortedMap.FastSortedEntrySet <V>) entries).fastIterator() : entries.iterator();
	}
	/** Returns an iterable yielding a bidirectional iterator that will be {@linkplain FastSortedEntrySet fast}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map from which we will try to extract an iterable yielding a (fast) bidirectional iterator on the entry set.
	 * @return an iterable yielding a bidirectional iterator on the entry set of the given map that will be fast, if possible.
	 * @since 8.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <V> ObjectBidirectionalIterable<Int2ObjectMap.Entry <V> > fastIterable(Int2ObjectSortedMap <V> map) {
	 final ObjectSortedSet<Int2ObjectMap.Entry <V> > entries = map.int2ObjectEntrySet();
	 return entries instanceof Int2ObjectSortedMap.FastSortedEntrySet ? ((Int2ObjectSortedMap.FastSortedEntrySet <V>)entries)::fastIterator : entries;
	}
	/** An immutable class representing an empty type-specific sorted map.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */
	public static class EmptySortedMap <V> extends Int2ObjectMaps.EmptyMap <V> implements Int2ObjectSortedMap <V>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptySortedMap() {}
	 @Override
	 public IntComparator comparator() { return null; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public ObjectSortedSet<Int2ObjectMap.Entry <V> > int2ObjectEntrySet() { return ObjectSortedSets.EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings("unchecked")
	 public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() { return ObjectSortedSets.EMPTY_SET; }
	
	 @Override
	 public IntSortedSet keySet() { return IntSortedSets.EMPTY_SET; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Int2ObjectSortedMap <V> subMap(final int from, final int to) { return EMPTY_MAP; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Int2ObjectSortedMap <V> headMap(final int to) { return EMPTY_MAP; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Int2ObjectSortedMap <V> tailMap(final int from) { return EMPTY_MAP; }
	 @Override
	 public int firstIntKey() { throw new NoSuchElementException(); }
	 @Override
	 public int lastIntKey() { throw new NoSuchElementException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Int2ObjectSortedMap <V> headMap(Integer oto) { return headMap((oto).intValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Int2ObjectSortedMap <V> tailMap(Integer ofrom) { return tailMap((ofrom).intValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Int2ObjectSortedMap <V> subMap(Integer ofrom, Integer oto) { return subMap((ofrom).intValue(), (oto).intValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer firstKey() { return Integer.valueOf(firstIntKey()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer lastKey() { return Integer.valueOf(lastIntKey()); }
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
	public static <V> Int2ObjectSortedMap <V> emptyMap() {
	 return EMPTY_MAP;
	}
	/** An immutable class representing a type-specific singleton sorted map.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */
	public static class Singleton <V> extends Int2ObjectMaps.Singleton <V> implements Int2ObjectSortedMap <V>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final IntComparator comparator;
	 protected Singleton(final int key, final V value, IntComparator comparator) {
	  super(key, value);
	  this.comparator = comparator;
	 }
	 protected Singleton(final int key, final V value) {
	  this(key, value, null);
	 }
	
	 final int compare(final int k1, final int k2) {
	  return comparator == null ? ( Integer.compare((k1),(k2)) ) : comparator.compare(k1, k2);
	 }
	 @Override
	 public IntComparator comparator() { return comparator; }
	
	 @Override
	 public ObjectSortedSet<Int2ObjectMap.Entry <V> > int2ObjectEntrySet() { if (entries == null) entries = ObjectSortedSets.singleton(new AbstractInt2ObjectMap.BasicEntry <>(key, value), entryComparator(comparator)); return (ObjectSortedSet<Int2ObjectMap.Entry <V> >)entries; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() { return (ObjectSortedSet)int2ObjectEntrySet(); }
	 @Override
	 public IntSortedSet keySet() { if (keys == null) keys = IntSortedSets.singleton(key, comparator); return (IntSortedSet )keys; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Int2ObjectSortedMap <V> subMap(final int from, final int to) { if (compare(from, key) <= 0 && compare(key, to) < 0) return this; return EMPTY_MAP; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Int2ObjectSortedMap <V> headMap(final int to) { if (compare(key, to) < 0) return this; return EMPTY_MAP; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Int2ObjectSortedMap <V> tailMap(final int from) { if (compare(from, key) <= 0) return this; return EMPTY_MAP; }
	 @Override
	 public int firstIntKey() { return key; }
	 @Override
	 public int lastIntKey() { return key; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Int2ObjectSortedMap <V> headMap(Integer oto) { return headMap((oto).intValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Int2ObjectSortedMap <V> tailMap(Integer ofrom) { return tailMap((ofrom).intValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Int2ObjectSortedMap <V> subMap(Integer ofrom, Integer oto) { return subMap((ofrom).intValue(), (oto).intValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer firstKey() { return Integer.valueOf(firstIntKey()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer lastKey() { return Integer.valueOf(lastIntKey()); }
	}
	/** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <p>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair {@code &lt;key,value&gt;}.
	 */
	public static <V> Int2ObjectSortedMap <V> singleton(final Integer key, V value) { return new Singleton <>((key).intValue(), (value));}
	/** RETURNS a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <p>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @param comparator the comparator to use in the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair {@code &lt;key,value&gt;}.
	 */
	public static <V> Int2ObjectSortedMap <V> singleton(final Integer key, V value, IntComparator comparator) { return new Singleton <>((key).intValue(), (value), comparator); }
	/** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <p>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair {@code &lt;key,value&gt;}.
	 */
	public static <V> Int2ObjectSortedMap <V> singleton(final int key, final V value) {
	 return new Singleton <>(key, value);
	}
	/** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <p>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @param comparator the comparator to use in the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair {@code &lt;key,value&gt;}.
	 */
	public static <V> Int2ObjectSortedMap <V> singleton(final int key, final V value, IntComparator comparator) {
	 return new Singleton <>(key, value, comparator);
	}
	 /** A synchronized wrapper class for sorted maps. */
	public static class SynchronizedSortedMap <V> extends Int2ObjectMaps.SynchronizedMap <V> implements Int2ObjectSortedMap <V>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final Int2ObjectSortedMap <V> sortedMap;
	 protected SynchronizedSortedMap(final Int2ObjectSortedMap <V> m, final Object sync) {
	  super(m, sync);
	  sortedMap = m;
	 }
	 protected SynchronizedSortedMap(final Int2ObjectSortedMap <V> m) {
	  super(m);
	  sortedMap = m;
	 }
	 @Override
	 public IntComparator comparator() { synchronized(sync) { return sortedMap.comparator(); } }
	 @Override
	 public ObjectSortedSet<Int2ObjectMap.Entry <V> > int2ObjectEntrySet() { if (entries == null) entries = ObjectSortedSets.synchronize(sortedMap.int2ObjectEntrySet(), sync); return (ObjectSortedSet<Int2ObjectMap.Entry <V> >)entries; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() { return (ObjectSortedSet)int2ObjectEntrySet(); }
	 @Override
	 public IntSortedSet keySet() { if (keys == null) keys = IntSortedSets.synchronize(sortedMap.keySet(), sync); return (IntSortedSet )keys; }
	 @Override
	 public Int2ObjectSortedMap <V> subMap(final int from, final int to) { return new SynchronizedSortedMap <>(sortedMap.subMap(from, to), sync); }
	 @Override
	 public Int2ObjectSortedMap <V> headMap(final int to) { return new SynchronizedSortedMap <>(sortedMap.headMap(to), sync); }
	 @Override
	 public Int2ObjectSortedMap <V> tailMap(final int from) { return new SynchronizedSortedMap <>(sortedMap.tailMap(from), sync); }
	 @Override
	 public int firstIntKey() { synchronized(sync) { return sortedMap.firstIntKey(); } }
	 @Override
	 public int lastIntKey() { synchronized(sync) { return sortedMap.lastIntKey(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer firstKey() { synchronized(sync) { return sortedMap.firstKey(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer lastKey() { synchronized(sync) { return sortedMap.lastKey(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Int2ObjectSortedMap <V> subMap(final Integer from, final Integer to) { return new SynchronizedSortedMap <>(sortedMap.subMap(from, to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Int2ObjectSortedMap <V> headMap(final Integer to) { return new SynchronizedSortedMap <>(sortedMap.headMap(to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Int2ObjectSortedMap <V> tailMap(final Integer from) { return new SynchronizedSortedMap <>(sortedMap.tailMap(from), sync); }
	}
	/** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */
	public static <V> Int2ObjectSortedMap <V> synchronize(final Int2ObjectSortedMap <V> m) { return new SynchronizedSortedMap <>(m); }
	/** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map, using an assigned object to synchronize.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @param sync an object that will be used to synchronize the access to the sorted sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */
	public static <V> Int2ObjectSortedMap <V> synchronize(final Int2ObjectSortedMap <V> m, final Object sync) { return new SynchronizedSortedMap <>(m, sync); }
	/** An unmodifiable wrapper class for sorted maps. */
	public static class UnmodifiableSortedMap <V> extends Int2ObjectMaps.UnmodifiableMap <V> implements Int2ObjectSortedMap <V>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final Int2ObjectSortedMap <V> sortedMap;
	 protected UnmodifiableSortedMap(final Int2ObjectSortedMap <V> m) {
	  super(m);
	  sortedMap = m;
	 }
	 @Override
	 public IntComparator comparator() { return sortedMap.comparator(); }
	 @Override
	 public ObjectSortedSet<Int2ObjectMap.Entry <V> > int2ObjectEntrySet() { if (entries == null) entries = ObjectSortedSets.unmodifiable(sortedMap.int2ObjectEntrySet()); return (ObjectSortedSet<Int2ObjectMap.Entry <V> >)entries; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() { return (ObjectSortedSet)int2ObjectEntrySet(); }
	 @Override
	 public IntSortedSet keySet() { if (keys == null) keys = IntSortedSets.unmodifiable(sortedMap.keySet()); return (IntSortedSet )keys; }
	 @Override
	 public Int2ObjectSortedMap <V> subMap(final int from, final int to) { return new UnmodifiableSortedMap <>(sortedMap.subMap(from, to)); }
	 @Override
	 public Int2ObjectSortedMap <V> headMap(final int to) { return new UnmodifiableSortedMap <>(sortedMap.headMap(to)); }
	 @Override
	 public Int2ObjectSortedMap <V> tailMap(final int from) { return new UnmodifiableSortedMap <>(sortedMap.tailMap(from)); }
	 @Override
	 public int firstIntKey() { return sortedMap.firstIntKey(); }
	 @Override
	 public int lastIntKey() { return sortedMap.lastIntKey(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer firstKey() { return sortedMap.firstKey(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer lastKey() { return sortedMap.lastKey(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Int2ObjectSortedMap <V> subMap(final Integer from, final Integer to) { return new UnmodifiableSortedMap <>(sortedMap.subMap(from, to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Int2ObjectSortedMap <V> headMap(final Integer to) { return new UnmodifiableSortedMap <>(sortedMap.headMap(to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Int2ObjectSortedMap <V> tailMap(final Integer from) { return new UnmodifiableSortedMap <>(sortedMap.tailMap(from)); }
	}
	/** Returns an unmodifiable type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in an unmodifiable sorted map.
	 * @return an unmodifiable view of the specified sorted map.
	 * @see java.util.Collections#unmodifiableSortedMap(SortedMap)
	 */
	public static <V> Int2ObjectSortedMap <V> unmodifiable(final Int2ObjectSortedMap <V> m) { return new UnmodifiableSortedMap <>(m); }
}
