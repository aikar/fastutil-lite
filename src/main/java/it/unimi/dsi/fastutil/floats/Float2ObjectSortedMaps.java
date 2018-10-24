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
package it.unimi.dsi.fastutil.floats;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import it.unimi.dsi.fastutil.floats.Float2ObjectSortedMap.FastSortedEntrySet;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.NoSuchElementException;
/** A class providing static methods and objects that do useful things with type-specific sorted maps.
	*
	* @see java.util.Collections
	*/
public final class Float2ObjectSortedMaps {
	private Float2ObjectSortedMaps() {}
	/** Returns a comparator for entries based on a given comparator on keys.
	 *
	 * @param comparator a comparator on keys.
	 * @return the associated comparator on entries.
	 */
	public static Comparator<? super Map.Entry<Float, ?>> entryComparator(final FloatComparator comparator) {
	 return (Comparator<Map.Entry<Float, ?>>) (x, y) -> comparator.compare((x.getKey()).floatValue(), (y.getKey()).floatValue());
	}
	/** Returns a bidirectional iterator that will be {@linkplain FastSortedEntrySet fast}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map from which we will try to extract a (fast) bidirectional iterator on the entry set.
	 * @return a bidirectional iterator on the entry set of the given map that will be fast, if possible.
	 * @since 8.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <V> ObjectBidirectionalIterator<Float2ObjectMap.Entry <V> > fastIterator(Float2ObjectSortedMap <V> map) {
	 final ObjectSortedSet<Float2ObjectMap.Entry <V> > entries = map.float2ObjectEntrySet();
	 return entries instanceof Float2ObjectSortedMap.FastSortedEntrySet ? ((Float2ObjectSortedMap.FastSortedEntrySet <V>) entries).fastIterator() : entries.iterator();
	}
	/** Returns an iterable yielding a bidirectional iterator that will be {@linkplain FastSortedEntrySet fast}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map from which we will try to extract an iterable yielding a (fast) bidirectional iterator on the entry set.
	 * @return an iterable yielding a bidirectional iterator on the entry set of the given map that will be fast, if possible.
	 * @since 8.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <V> ObjectBidirectionalIterable<Float2ObjectMap.Entry <V> > fastIterable(Float2ObjectSortedMap <V> map) {
	 final ObjectSortedSet<Float2ObjectMap.Entry <V> > entries = map.float2ObjectEntrySet();
	 return entries instanceof Float2ObjectSortedMap.FastSortedEntrySet ? ((Float2ObjectSortedMap.FastSortedEntrySet <V>)entries)::fastIterator : entries;
	}
	/** An immutable class representing an empty type-specific sorted map.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */
	public static class EmptySortedMap <V> extends Float2ObjectMaps.EmptyMap <V> implements Float2ObjectSortedMap <V>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptySortedMap() {}
	 @Override
	 public FloatComparator comparator() { return null; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public ObjectSortedSet<Float2ObjectMap.Entry <V> > float2ObjectEntrySet() { return ObjectSortedSets.EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings("unchecked")
	 public ObjectSortedSet<Map.Entry<Float, V>> entrySet() { return ObjectSortedSets.EMPTY_SET; }
	
	 @Override
	 public FloatSortedSet keySet() { return FloatSortedSets.EMPTY_SET; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Float2ObjectSortedMap <V> subMap(final float from, final float to) { return EMPTY_MAP; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Float2ObjectSortedMap <V> headMap(final float to) { return EMPTY_MAP; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Float2ObjectSortedMap <V> tailMap(final float from) { return EMPTY_MAP; }
	 @Override
	 public float firstFloatKey() { throw new NoSuchElementException(); }
	 @Override
	 public float lastFloatKey() { throw new NoSuchElementException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float2ObjectSortedMap <V> headMap(Float oto) { return headMap((oto).floatValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float2ObjectSortedMap <V> tailMap(Float ofrom) { return tailMap((ofrom).floatValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float2ObjectSortedMap <V> subMap(Float ofrom, Float oto) { return subMap((ofrom).floatValue(), (oto).floatValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float firstKey() { return Float.valueOf(firstFloatKey()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float lastKey() { return Float.valueOf(lastFloatKey()); }
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
	public static <V> Float2ObjectSortedMap <V> emptyMap() {
	 return EMPTY_MAP;
	}
	/** An immutable class representing a type-specific singleton sorted map.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */
	public static class Singleton <V> extends Float2ObjectMaps.Singleton <V> implements Float2ObjectSortedMap <V>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final FloatComparator comparator;
	 protected Singleton(final float key, final V value, FloatComparator comparator) {
	  super(key, value);
	  this.comparator = comparator;
	 }
	 protected Singleton(final float key, final V value) {
	  this(key, value, null);
	 }
	
	 final int compare(final float k1, final float k2) {
	  return comparator == null ? ( Float.compare((k1),(k2)) ) : comparator.compare(k1, k2);
	 }
	 @Override
	 public FloatComparator comparator() { return comparator; }
	
	 @Override
	 public ObjectSortedSet<Float2ObjectMap.Entry <V> > float2ObjectEntrySet() { if (entries == null) entries = ObjectSortedSets.singleton(new AbstractFloat2ObjectMap.BasicEntry <>(key, value), entryComparator(comparator)); return (ObjectSortedSet<Float2ObjectMap.Entry <V> >)entries; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public ObjectSortedSet<Map.Entry<Float, V>> entrySet() { return (ObjectSortedSet)float2ObjectEntrySet(); }
	 @Override
	 public FloatSortedSet keySet() { if (keys == null) keys = FloatSortedSets.singleton(key, comparator); return (FloatSortedSet )keys; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Float2ObjectSortedMap <V> subMap(final float from, final float to) { if (compare(from, key) <= 0 && compare(key, to) < 0) return this; return EMPTY_MAP; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Float2ObjectSortedMap <V> headMap(final float to) { if (compare(key, to) < 0) return this; return EMPTY_MAP; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Float2ObjectSortedMap <V> tailMap(final float from) { if (compare(from, key) <= 0) return this; return EMPTY_MAP; }
	 @Override
	 public float firstFloatKey() { return key; }
	 @Override
	 public float lastFloatKey() { return key; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float2ObjectSortedMap <V> headMap(Float oto) { return headMap((oto).floatValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float2ObjectSortedMap <V> tailMap(Float ofrom) { return tailMap((ofrom).floatValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float2ObjectSortedMap <V> subMap(Float ofrom, Float oto) { return subMap((ofrom).floatValue(), (oto).floatValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float firstKey() { return Float.valueOf(firstFloatKey()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float lastKey() { return Float.valueOf(lastFloatKey()); }
	}
	/** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <p>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair {@code &lt;key,value&gt;}.
	 */
	public static <V> Float2ObjectSortedMap <V> singleton(final Float key, V value) { return new Singleton <>((key).floatValue(), (value));}
	/** RETURNS a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <p>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @param comparator the comparator to use in the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair {@code &lt;key,value&gt;}.
	 */
	public static <V> Float2ObjectSortedMap <V> singleton(final Float key, V value, FloatComparator comparator) { return new Singleton <>((key).floatValue(), (value), comparator); }
	/** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <p>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair {@code &lt;key,value&gt;}.
	 */
	public static <V> Float2ObjectSortedMap <V> singleton(final float key, final V value) {
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
	public static <V> Float2ObjectSortedMap <V> singleton(final float key, final V value, FloatComparator comparator) {
	 return new Singleton <>(key, value, comparator);
	}
	 /** A synchronized wrapper class for sorted maps. */
	public static class SynchronizedSortedMap <V> extends Float2ObjectMaps.SynchronizedMap <V> implements Float2ObjectSortedMap <V>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final Float2ObjectSortedMap <V> sortedMap;
	 protected SynchronizedSortedMap(final Float2ObjectSortedMap <V> m, final Object sync) {
	  super(m, sync);
	  sortedMap = m;
	 }
	 protected SynchronizedSortedMap(final Float2ObjectSortedMap <V> m) {
	  super(m);
	  sortedMap = m;
	 }
	 @Override
	 public FloatComparator comparator() { synchronized(sync) { return sortedMap.comparator(); } }
	 @Override
	 public ObjectSortedSet<Float2ObjectMap.Entry <V> > float2ObjectEntrySet() { if (entries == null) entries = ObjectSortedSets.synchronize(sortedMap.float2ObjectEntrySet(), sync); return (ObjectSortedSet<Float2ObjectMap.Entry <V> >)entries; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public ObjectSortedSet<Map.Entry<Float, V>> entrySet() { return (ObjectSortedSet)float2ObjectEntrySet(); }
	 @Override
	 public FloatSortedSet keySet() { if (keys == null) keys = FloatSortedSets.synchronize(sortedMap.keySet(), sync); return (FloatSortedSet )keys; }
	 @Override
	 public Float2ObjectSortedMap <V> subMap(final float from, final float to) { return new SynchronizedSortedMap <>(sortedMap.subMap(from, to), sync); }
	 @Override
	 public Float2ObjectSortedMap <V> headMap(final float to) { return new SynchronizedSortedMap <>(sortedMap.headMap(to), sync); }
	 @Override
	 public Float2ObjectSortedMap <V> tailMap(final float from) { return new SynchronizedSortedMap <>(sortedMap.tailMap(from), sync); }
	 @Override
	 public float firstFloatKey() { synchronized(sync) { return sortedMap.firstFloatKey(); } }
	 @Override
	 public float lastFloatKey() { synchronized(sync) { return sortedMap.lastFloatKey(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float firstKey() { synchronized(sync) { return sortedMap.firstKey(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float lastKey() { synchronized(sync) { return sortedMap.lastKey(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float2ObjectSortedMap <V> subMap(final Float from, final Float to) { return new SynchronizedSortedMap <>(sortedMap.subMap(from, to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float2ObjectSortedMap <V> headMap(final Float to) { return new SynchronizedSortedMap <>(sortedMap.headMap(to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float2ObjectSortedMap <V> tailMap(final Float from) { return new SynchronizedSortedMap <>(sortedMap.tailMap(from), sync); }
	}
	/** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */
	public static <V> Float2ObjectSortedMap <V> synchronize(final Float2ObjectSortedMap <V> m) { return new SynchronizedSortedMap <>(m); }
	/** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map, using an assigned object to synchronize.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @param sync an object that will be used to synchronize the access to the sorted sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */
	public static <V> Float2ObjectSortedMap <V> synchronize(final Float2ObjectSortedMap <V> m, final Object sync) { return new SynchronizedSortedMap <>(m, sync); }
	/** An unmodifiable wrapper class for sorted maps. */
	public static class UnmodifiableSortedMap <V> extends Float2ObjectMaps.UnmodifiableMap <V> implements Float2ObjectSortedMap <V>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final Float2ObjectSortedMap <V> sortedMap;
	 protected UnmodifiableSortedMap(final Float2ObjectSortedMap <V> m) {
	  super(m);
	  sortedMap = m;
	 }
	 @Override
	 public FloatComparator comparator() { return sortedMap.comparator(); }
	 @Override
	 public ObjectSortedSet<Float2ObjectMap.Entry <V> > float2ObjectEntrySet() { if (entries == null) entries = ObjectSortedSets.unmodifiable(sortedMap.float2ObjectEntrySet()); return (ObjectSortedSet<Float2ObjectMap.Entry <V> >)entries; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public ObjectSortedSet<Map.Entry<Float, V>> entrySet() { return (ObjectSortedSet)float2ObjectEntrySet(); }
	 @Override
	 public FloatSortedSet keySet() { if (keys == null) keys = FloatSortedSets.unmodifiable(sortedMap.keySet()); return (FloatSortedSet )keys; }
	 @Override
	 public Float2ObjectSortedMap <V> subMap(final float from, final float to) { return new UnmodifiableSortedMap <>(sortedMap.subMap(from, to)); }
	 @Override
	 public Float2ObjectSortedMap <V> headMap(final float to) { return new UnmodifiableSortedMap <>(sortedMap.headMap(to)); }
	 @Override
	 public Float2ObjectSortedMap <V> tailMap(final float from) { return new UnmodifiableSortedMap <>(sortedMap.tailMap(from)); }
	 @Override
	 public float firstFloatKey() { return sortedMap.firstFloatKey(); }
	 @Override
	 public float lastFloatKey() { return sortedMap.lastFloatKey(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float firstKey() { return sortedMap.firstKey(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float lastKey() { return sortedMap.lastKey(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float2ObjectSortedMap <V> subMap(final Float from, final Float to) { return new UnmodifiableSortedMap <>(sortedMap.subMap(from, to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float2ObjectSortedMap <V> headMap(final Float to) { return new UnmodifiableSortedMap <>(sortedMap.headMap(to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float2ObjectSortedMap <V> tailMap(final Float from) { return new UnmodifiableSortedMap <>(sortedMap.tailMap(from)); }
	}
	/** Returns an unmodifiable type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in an unmodifiable sorted map.
	 * @return an unmodifiable view of the specified sorted map.
	 * @see java.util.Collections#unmodifiableSortedMap(SortedMap)
	 */
	public static <V> Float2ObjectSortedMap <V> unmodifiable(final Float2ObjectSortedMap <V> m) { return new UnmodifiableSortedMap <>(m); }
}
