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
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntCollections;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.util.Map;
import java.util.function.Consumer;
import it.unimi.dsi.fastutil.objects.Object2IntMap.FastEntrySet;
/** A class providing static methods and objects that do useful things with type-specific maps.
	*
	* @see java.util.Collections
	*/
public final class Object2IntMaps {
	private Object2IntMaps() {}
	/** Returns an iterator that will be {@linkplain FastEntrySet fast}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map from which we will try to extract a (fast) iterator on the entry set.
	 * @return an iterator on the entry set of the given map that will be fast, if possible.
	 * @since 8.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <K> ObjectIterator<Object2IntMap.Entry <K> > fastIterator(Object2IntMap <K> map) {
	 final ObjectSet<Object2IntMap.Entry <K> > entries = map.object2IntEntrySet();
	 return entries instanceof Object2IntMap.FastEntrySet ? ((Object2IntMap.FastEntrySet <K>) entries).fastIterator() : entries.iterator();
	}
	/** Iterates {@linkplain FastEntrySet#fastForEach(Consumer) quickly}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map on which we will try to iterate {@linkplain FastEntrySet#fastForEach(Consumer) quickly}.
	 * @param consumer the consumer that will be passed to  {@link FastEntrySet#fastForEach(Consumer)}, if possible, or to {@link Iterable#forEach(Consumer)}.
	 * @since 8.1.0
	 */
	@SuppressWarnings("unchecked")
	public static <K> void fastForEach(Object2IntMap <K> map, final Consumer<? super Object2IntMap.Entry <K> > consumer) {
	 final ObjectSet<Object2IntMap.Entry <K> > entries = map.object2IntEntrySet();
	 if (entries instanceof Object2IntMap.FastEntrySet) ((Object2IntMap.FastEntrySet <K>) entries).fastForEach(consumer);
	 else entries.forEach(consumer);
	}
	/** Returns an iterable yielding an iterator that will be {@linkplain FastEntrySet fast}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map from which we will try to extract an iterable yielding a (fast) iterator on the entry set.
	 * @return an iterable  yielding an iterator on the entry set of the given map that will be
	 * fast, if possible.
	 * @since 8.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <K> ObjectIterable<Object2IntMap.Entry <K> > fastIterable(Object2IntMap <K> map) {
	 final ObjectSet<Object2IntMap.Entry <K> > entries = map.object2IntEntrySet();
	 return entries instanceof Object2IntMap.FastEntrySet ? new ObjectIterable<Object2IntMap.Entry <K> >() {
	  public ObjectIterator<Object2IntMap.Entry <K> > iterator() { return ((Object2IntMap.FastEntrySet <K>)entries).fastIterator(); }
	  public void forEach(final Consumer<? super Object2IntMap.Entry <K> > consumer) { ((Object2IntMap.FastEntrySet <K>)entries).fastForEach(consumer); }
	 } : entries;
	}
	/** An immutable class representing an empty type-specific map.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific map.
	 */
	public static class EmptyMap <K> extends Object2IntFunctions.EmptyFunction <K> implements Object2IntMap <K>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptyMap() {}
	 @Override
	 public boolean containsValue(final int v) { return false; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean containsValue(final Object ov) { return false; }
	 @Override
	 public void putAll(final Map<? extends K, ? extends Integer> m) { throw new UnsupportedOperationException(); }
	 @SuppressWarnings("unchecked")
	 @Override
	 public ObjectSet<Object2IntMap.Entry <K> > object2IntEntrySet() { return ObjectSets.EMPTY_SET; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public ObjectSet <K> keySet() { return ObjectSets.EMPTY_SET; }
	
	 @Override
	 public IntCollection values() { return IntSets.EMPTY_SET; }
	 @Override
	 public Object clone() { return EMPTY_MAP; }
	 @Override
	 public boolean isEmpty() { return true; }
	 @Override
	 public int hashCode() { return 0; }
	 @Override
	 public boolean equals(final Object o) {
	  if (! (o instanceof Map)) return false;
	  return ((Map<?,?>)o).isEmpty();
	 }
	 @Override
	 public String toString() { return "{}"; }
	}
	/** An empty type-specific map (immutable). It is serializable and cloneable.
	 */
	@SuppressWarnings("rawtypes")
	public static final EmptyMap EMPTY_MAP = new EmptyMap();
	/** Returns an empty map (immutable). It is serializable and cloneable.
	 *
	 * <p>This method provides a typesafe access to {@link #EMPTY_MAP}.
	 * @return an empty map (immutable).
	 */
	@SuppressWarnings("unchecked")
	public static <K> Object2IntMap <K> emptyMap() {
	 return EMPTY_MAP;
	}
	/** An immutable class representing a type-specific singleton map.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific map.
	 */
	public static class Singleton <K> extends Object2IntFunctions.Singleton <K> implements Object2IntMap <K>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected transient ObjectSet<Object2IntMap.Entry <K> > entries;
	 protected transient ObjectSet <K> keys;
	 protected transient IntCollection values;
	 protected Singleton(final K key, final int value) {
	  super(key, value);
	 }
	 @Override
	 public boolean containsValue(final int v) { return ( (value) == (v) ); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean containsValue(final Object ov) { return ( (((Integer)(ov)).intValue()) == (value) ); }
	 @Override
	 public void putAll(final Map<? extends K, ? extends Integer> m) { throw new UnsupportedOperationException(); }
	 @Override
	 public ObjectSet<Object2IntMap.Entry <K> > object2IntEntrySet() { if (entries == null) entries = ObjectSets.singleton(new AbstractObject2IntMap.BasicEntry <>(key, value)); return entries; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public ObjectSet<Map.Entry<K, Integer>> entrySet() { return (ObjectSet)object2IntEntrySet(); }
	 @Override
	 public ObjectSet <K> keySet() { if (keys == null) keys = ObjectSets.singleton(key); return keys; }
	 @Override
	 public IntCollection values() { if (values == null) values = IntSets.singleton(value); return values; }
	 @Override
	 public boolean isEmpty() { return false; }
	 @Override
	 public int hashCode() { return ( (key) == null ? 0 : (key).hashCode() ) ^ (value); }
	 @Override
	 public boolean equals(final Object o) {
	  if (o == this) return true;
	  if (! (o instanceof Map)) return false;
	  Map<?,?> m = (Map<?,?>)o;
	  if (m.size() != 1) return false;
	  return m.entrySet().iterator().next().equals(entrySet().iterator().next());
	 }
	 @Override
	 public String toString() { return "{" + key + "=>" + value + "}"; }
	}
	/** Returns a type-specific immutable map containing only the specified pair. The returned map is serializable and cloneable.
	 *
	 * <p>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned map.
	 * @param value the only value of the returned map.
	 * @return a type-specific immutable map containing just the pair {@code &lt;key,value&gt;}.
	 */
	public static <K> Object2IntMap <K> singleton(final K key, int value) { return new Singleton <>(key, value); }
	/** Returns a type-specific immutable map containing only the specified pair. The returned map is serializable and cloneable.
	 *
	 * <p>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned map.
	 * @param value the only value of the returned map.
	 * @return a type-specific immutable map containing just the pair {@code &lt;key,value&gt;}.
	 */
	public static <K> Object2IntMap <K> singleton(final K key, final Integer value) { return new Singleton <>((key), (value).intValue()); }
	/** A synchronized wrapper class for maps. */
	public static class SynchronizedMap <K> extends Object2IntFunctions.SynchronizedFunction <K> implements Object2IntMap <K>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final Object2IntMap <K> map;
	 protected transient ObjectSet<Object2IntMap.Entry <K> > entries;
	 protected transient ObjectSet <K> keys;
	 protected transient IntCollection values;
	 protected SynchronizedMap(final Object2IntMap <K> m, final Object sync) {
	  super(m, sync);
	  this.map = m;
	 }
	 protected SynchronizedMap(final Object2IntMap <K> m) {
	  super(m);
	  this.map = m;
	 }
	 @Override
	 public boolean containsValue(final int v) { synchronized(sync) { return map.containsValue(v); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean containsValue(final Object ov) { synchronized(sync) { return map.containsValue(ov); } }
	 @Override
	 public void putAll(final Map<? extends K, ? extends Integer> m) { synchronized(sync) { map.putAll(m); } }
	 @Override
	 public ObjectSet<Object2IntMap.Entry <K> > object2IntEntrySet() { synchronized(sync) { if (entries == null) entries = ObjectSets.synchronize(map.object2IntEntrySet(), sync); return entries; } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 public ObjectSet<Map.Entry<K, Integer>> entrySet() { return (ObjectSet)object2IntEntrySet(); }
	 @Override
	 public ObjectSet <K> keySet() {
	  synchronized(sync) { if (keys == null) keys = ObjectSets.synchronize(map.keySet(), sync); return keys; }
	 }
	 @Override
	 public IntCollection values() {
	  synchronized(sync) { if (values == null) return IntCollections.synchronize(map.values(), sync); return values; }
	 }
	 @Override
	 public boolean isEmpty() { synchronized(sync) { return map.isEmpty(); } }
	 @Override
	 public int hashCode() { synchronized(sync) { return map.hashCode(); } }
	 @Override
	 public boolean equals(final Object o) {
	  if (o == this) return true;
	  synchronized(sync) { return map.equals(o); }
	 }
	 private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
	  synchronized(sync) { s.defaultWriteObject(); }
	 }
	 // Defaultable methods
	 @Override
	 public int getOrDefault(final Object key, final int defaultValue) { synchronized(sync) { return map.getOrDefault(key, defaultValue); } }
	 @Override
	 public void forEach(final java.util.function.BiConsumer<? super K, ? super Integer> action) { synchronized (sync) { map.forEach(action); } }
	 @Override
	 public void replaceAll(final java.util.function.BiFunction<? super K, ? super Integer, ? extends Integer> function) { synchronized (sync) { map.replaceAll(function); } }
	 @Override
	 public int putIfAbsent(final K key, final int value) { synchronized(sync) { return map.putIfAbsent(key, value); } }
	 @Override
	 public boolean remove(final Object key, final int value) { synchronized(sync) { return map.remove(key, value); } }
	 @Override
	 public int replace(final K key, final int value) { synchronized(sync) { return map.replace(key, value); } }
	 @Override
	 public boolean replace(final K key, final int oldValue, final int newValue) { synchronized(sync) { return map.replace(key, oldValue, newValue); } }
	 @Override
	 public int computeIntIfAbsent(final K key, final java.util.function.ToIntFunction <? super K> mappingFunction) { synchronized(sync) { return map.computeIntIfAbsent(key, mappingFunction); } }
	 @Override
	 public int computeIntIfAbsentPartial(final K key, final Object2IntFunction <? super K> mappingFunction) { synchronized(sync) { return map.computeIntIfAbsentPartial(key, mappingFunction); } }
	 @Override
	 public int computeIntIfPresent(final K key, final java.util.function.BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
	  synchronized (sync) { return map.computeIntIfPresent(key, remappingFunction); }
	 }
	 @Override
	 public int computeInt(final K key, final java.util.function.BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
	  synchronized (sync) { return map.computeInt(key, remappingFunction); }
	 }
	 @Override
	 public int mergeInt(final K key, final int value, final java.util.function.BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
	  synchronized (sync) { return map.mergeInt(key, value, remappingFunction); }
	 }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer getOrDefault(final Object key, final Integer defaultValue) { synchronized (sync) { return map.getOrDefault(key, defaultValue); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean remove(final Object key, final Object value) { synchronized (sync) { return map.remove(key, value); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer replace(final K key, final Integer value) { synchronized (sync) { return map.replace(key, value); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean replace(final K key, final Integer oldValue, final Integer newValue) { synchronized (sync) { return map.replace(key, oldValue, newValue); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer putIfAbsent(final K key, final Integer value) { synchronized (sync) { return map.putIfAbsent(key, value); } }
	 @Override
	 public Integer computeIfAbsent(final K key, final java.util.function.Function<? super K, ? extends Integer> mappingFunction) { synchronized (sync) { return map.computeIfAbsent(key, mappingFunction); } }
	 @Override
	 public Integer computeIfPresent(final K key, final java.util.function.BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) { synchronized (sync) { return map.computeIfPresent(key, remappingFunction); } }
	 @Override
	 public Integer compute(final K key, final java.util.function.BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) { synchronized (sync) { return map.compute(key, remappingFunction); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer merge(final K key, final Integer value, final java.util.function.BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) { synchronized (sync) { return map.merge(key, value, remappingFunction); } }
	}
	/** Returns a synchronized type-specific map backed by the given type-specific map.
	 *
	 * @param m the map to be wrapped in a synchronized map.
	 * @return a synchronized view of the specified map.
	 * @see java.util.Collections#synchronizedMap(Map)
	 */
	public static <K> Object2IntMap <K> synchronize(final Object2IntMap <K> m) { return new SynchronizedMap <>(m); }
	/** Returns a synchronized type-specific map backed by the given type-specific map, using an assigned object to synchronize.
	 *
	 * @param m the map to be wrapped in a synchronized map.
	 * @param sync an object that will be used to synchronize the access to the map.
	 * @return a synchronized view of the specified map.
	 * @see java.util.Collections#synchronizedMap(Map)
	 */
	public static <K> Object2IntMap <K> synchronize(final Object2IntMap <K> m, final Object sync) { return new SynchronizedMap <>(m, sync); }
	/** An unmodifiable wrapper class for maps. */
	public static class UnmodifiableMap <K> extends Object2IntFunctions.UnmodifiableFunction <K> implements Object2IntMap <K>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final Object2IntMap <K> map;
	 protected transient ObjectSet<Object2IntMap.Entry <K> > entries;
	 protected transient ObjectSet <K> keys;
	 protected transient IntCollection values;
	 protected UnmodifiableMap(final Object2IntMap <K> m) {
	  super(m);
	  this.map = m;
	 }
	 @Override
	 public boolean containsValue(final int v) { return map.containsValue(v); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean containsValue(final Object ov) { return map.containsValue(ov); }
	 @Override
	 public void putAll(final Map<? extends K, ? extends Integer> m) { throw new UnsupportedOperationException(); }
	 @Override
	 public ObjectSet<Object2IntMap.Entry <K> > object2IntEntrySet() { if (entries == null) entries = ObjectSets.unmodifiable(map.object2IntEntrySet()); return entries; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 public ObjectSet<Map.Entry<K, Integer>> entrySet() { return (ObjectSet)object2IntEntrySet(); }
	 @Override
	 public ObjectSet <K> keySet() { if (keys == null) keys = ObjectSets.unmodifiable(map.keySet()); return keys; }
	 @Override
	 public IntCollection values() { if (values == null) return IntCollections.unmodifiable(map.values()); return values; }
	 @Override
	 public boolean isEmpty() { return map.isEmpty(); }
	 @Override
	 public int hashCode() { return map.hashCode(); }
	 @Override
	 public boolean equals(final Object o) {
	  if (o == this) return true;
	  return map.equals(o);
	 }
	 // Defaultable methods
	 @Override
	 public int getOrDefault(final Object key, final int defaultValue) { return map.getOrDefault(key, defaultValue); }
	 @Override
	 public void forEach(final java.util.function.BiConsumer<? super K, ? super Integer> action) { map.forEach(action); }
	 @Override
	 public void replaceAll(final java.util.function.BiFunction<? super K, ? super Integer, ? extends Integer> function) { throw new UnsupportedOperationException(); }
	 @Override
	 public int putIfAbsent(final K key, final int value) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean remove(final Object key, final int value) { throw new UnsupportedOperationException(); }
	 @Override
	 public int replace(final K key, final int value) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean replace(final K key, final int oldValue, final int newValue) { throw new UnsupportedOperationException(); }
	 @Override
	 public int computeIntIfAbsent(final K key, final java.util.function.ToIntFunction <? super K> mappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public int computeIntIfAbsentPartial(final K key, final Object2IntFunction <? super K> mappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public int computeIntIfPresent(final K key, final java.util.function.BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public int computeInt(final K key, final java.util.function.BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public int mergeInt(final K key, final int value, final java.util.function.BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer getOrDefault(final Object key, final Integer defaultValue) { return map.getOrDefault(key, defaultValue); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean remove(final Object key, final Object value) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer replace(final K key, final Integer value) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean replace(final K key, final Integer oldValue, final Integer newValue) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer putIfAbsent(final K key, final Integer value) { throw new UnsupportedOperationException(); }
	 @Override
	 public Integer computeIfAbsent(final K key, final java.util.function.Function<? super K, ? extends Integer> mappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public Integer computeIfPresent(final K key, final java.util.function.BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public Integer compute(final K key, final java.util.function.BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Integer merge(final K key, final Integer value, final java.util.function.BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) { throw new UnsupportedOperationException(); }
	}
	/** Returns an unmodifiable type-specific map backed by the given type-specific map.
	 *
	 * @param m the map to be wrapped in an unmodifiable map.
	 * @return an unmodifiable view of the specified map.
	 * @see java.util.Collections#unmodifiableMap(Map)
	 */
	public static <K> Object2IntMap <K> unmodifiable(final Object2IntMap <K> m) { return new UnmodifiableMap <>(m); }
}
