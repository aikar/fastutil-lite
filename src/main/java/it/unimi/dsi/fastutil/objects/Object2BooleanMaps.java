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
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections;
import it.unimi.dsi.fastutil.booleans.BooleanSets;
import java.util.Map;
import java.util.function.Consumer;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap.FastEntrySet;
/** A class providing static methods and objects that do useful things with type-specific maps.
	*
	* @see java.util.Collections
	*/
public final class Object2BooleanMaps {
	private Object2BooleanMaps() {}
	/** Returns an iterator that will be {@linkplain FastEntrySet fast}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map from which we will try to extract a (fast) iterator on the entry set.
	 * @return an iterator on the entry set of the given map that will be fast, if possible.
	 * @since 8.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <K> ObjectIterator<Object2BooleanMap.Entry <K> > fastIterator(Object2BooleanMap <K> map) {
	 final ObjectSet<Object2BooleanMap.Entry <K> > entries = map.object2BooleanEntrySet();
	 return entries instanceof Object2BooleanMap.FastEntrySet ? ((Object2BooleanMap.FastEntrySet <K>) entries).fastIterator() : entries.iterator();
	}
	/** Iterates {@linkplain FastEntrySet#fastForEach(Consumer) quickly}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map on which we will try to iterate {@linkplain FastEntrySet#fastForEach(Consumer) quickly}.
	 * @param consumer the consumer that will be passed to  {@link FastEntrySet#fastForEach(Consumer)}, if possible, or to {@link Iterable#forEach(Consumer)}.
	 * @since 8.1.0
	 */
	@SuppressWarnings("unchecked")
	public static <K> void fastForEach(Object2BooleanMap <K> map, final Consumer<? super Object2BooleanMap.Entry <K> > consumer) {
	 final ObjectSet<Object2BooleanMap.Entry <K> > entries = map.object2BooleanEntrySet();
	 if (entries instanceof Object2BooleanMap.FastEntrySet) ((Object2BooleanMap.FastEntrySet <K>) entries).fastForEach(consumer);
	 else entries.forEach(consumer);
	}
	/** Returns an iterable yielding an iterator that will be {@linkplain FastEntrySet fast}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map from which we will try to extract an iterable yielding a (fast) iterator on the entry set.
	 * @return an iterable  yielding an iterator on the entry set of the given map that will be
	 * fast, if possible.
	 * @since 8.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <K> ObjectIterable<Object2BooleanMap.Entry <K> > fastIterable(Object2BooleanMap <K> map) {
	 final ObjectSet<Object2BooleanMap.Entry <K> > entries = map.object2BooleanEntrySet();
	 return entries instanceof Object2BooleanMap.FastEntrySet ? new ObjectIterable<Object2BooleanMap.Entry <K> >() {
	  public ObjectIterator<Object2BooleanMap.Entry <K> > iterator() { return ((Object2BooleanMap.FastEntrySet <K>)entries).fastIterator(); }
	  public void forEach(final Consumer<? super Object2BooleanMap.Entry <K> > consumer) { ((Object2BooleanMap.FastEntrySet <K>)entries).fastForEach(consumer); }
	 } : entries;
	}
	/** An immutable class representing an empty type-specific map.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific map.
	 */
	public static class EmptyMap <K> extends Object2BooleanFunctions.EmptyFunction <K> implements Object2BooleanMap <K>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptyMap() {}
	 @Override
	 public boolean containsValue(final boolean v) { return false; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean containsValue(final Object ov) { return false; }
	 @Override
	 public void putAll(final Map<? extends K, ? extends Boolean> m) { throw new UnsupportedOperationException(); }
	 @SuppressWarnings("unchecked")
	 @Override
	 public ObjectSet<Object2BooleanMap.Entry <K> > object2BooleanEntrySet() { return ObjectSets.EMPTY_SET; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public ObjectSet <K> keySet() { return ObjectSets.EMPTY_SET; }
	
	 @Override
	 public BooleanCollection values() { return BooleanSets.EMPTY_SET; }
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
	public static <K> Object2BooleanMap <K> emptyMap() {
	 return EMPTY_MAP;
	}
	/** An immutable class representing a type-specific singleton map.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific map.
	 */
	public static class Singleton <K> extends Object2BooleanFunctions.Singleton <K> implements Object2BooleanMap <K>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected transient ObjectSet<Object2BooleanMap.Entry <K> > entries;
	 protected transient ObjectSet <K> keys;
	 protected transient BooleanCollection values;
	 protected Singleton(final K key, final boolean value) {
	  super(key, value);
	 }
	 @Override
	 public boolean containsValue(final boolean v) { return ( (value) == (v) ); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean containsValue(final Object ov) { return ( (((Boolean)(ov)).booleanValue()) == (value) ); }
	 @Override
	 public void putAll(final Map<? extends K, ? extends Boolean> m) { throw new UnsupportedOperationException(); }
	 @Override
	 public ObjectSet<Object2BooleanMap.Entry <K> > object2BooleanEntrySet() { if (entries == null) entries = ObjectSets.singleton(new AbstractObject2BooleanMap.BasicEntry <>(key, value)); return entries; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public ObjectSet<Map.Entry<K, Boolean>> entrySet() { return (ObjectSet)object2BooleanEntrySet(); }
	 @Override
	 public ObjectSet <K> keySet() { if (keys == null) keys = ObjectSets.singleton(key); return keys; }
	 @Override
	 public BooleanCollection values() { if (values == null) values = BooleanSets.singleton(value); return values; }
	 @Override
	 public boolean isEmpty() { return false; }
	 @Override
	 public int hashCode() { return ( (key) == null ? 0 : (key).hashCode() ) ^ (value ? 1231 : 1237); }
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
	public static <K> Object2BooleanMap <K> singleton(final K key, boolean value) { return new Singleton <>(key, value); }
	/** Returns a type-specific immutable map containing only the specified pair. The returned map is serializable and cloneable.
	 *
	 * <p>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned map.
	 * @param value the only value of the returned map.
	 * @return a type-specific immutable map containing just the pair {@code &lt;key,value&gt;}.
	 */
	public static <K> Object2BooleanMap <K> singleton(final K key, final Boolean value) { return new Singleton <>((key), (value).booleanValue()); }
	/** A synchronized wrapper class for maps. */
	public static class SynchronizedMap <K> extends Object2BooleanFunctions.SynchronizedFunction <K> implements Object2BooleanMap <K>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final Object2BooleanMap <K> map;
	 protected transient ObjectSet<Object2BooleanMap.Entry <K> > entries;
	 protected transient ObjectSet <K> keys;
	 protected transient BooleanCollection values;
	 protected SynchronizedMap(final Object2BooleanMap <K> m, final Object sync) {
	  super(m, sync);
	  this.map = m;
	 }
	 protected SynchronizedMap(final Object2BooleanMap <K> m) {
	  super(m);
	  this.map = m;
	 }
	 @Override
	 public boolean containsValue(final boolean v) { synchronized(sync) { return map.containsValue(v); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean containsValue(final Object ov) { synchronized(sync) { return map.containsValue(ov); } }
	 @Override
	 public void putAll(final Map<? extends K, ? extends Boolean> m) { synchronized(sync) { map.putAll(m); } }
	 @Override
	 public ObjectSet<Object2BooleanMap.Entry <K> > object2BooleanEntrySet() { synchronized(sync) { if (entries == null) entries = ObjectSets.synchronize(map.object2BooleanEntrySet(), sync); return entries; } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 public ObjectSet<Map.Entry<K, Boolean>> entrySet() { return (ObjectSet)object2BooleanEntrySet(); }
	 @Override
	 public ObjectSet <K> keySet() {
	  synchronized(sync) { if (keys == null) keys = ObjectSets.synchronize(map.keySet(), sync); return keys; }
	 }
	 @Override
	 public BooleanCollection values() {
	  synchronized(sync) { if (values == null) return BooleanCollections.synchronize(map.values(), sync); return values; }
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
	 public boolean getOrDefault(final Object key, final boolean defaultValue) { synchronized(sync) { return map.getOrDefault(key, defaultValue); } }
	 @Override
	 public void forEach(final java.util.function.BiConsumer<? super K, ? super Boolean> action) { synchronized (sync) { map.forEach(action); } }
	 @Override
	 public void replaceAll(final java.util.function.BiFunction<? super K, ? super Boolean, ? extends Boolean> function) { synchronized (sync) { map.replaceAll(function); } }
	 @Override
	 public boolean putIfAbsent(final K key, final boolean value) { synchronized(sync) { return map.putIfAbsent(key, value); } }
	 @Override
	 public boolean remove(final Object key, final boolean value) { synchronized(sync) { return map.remove(key, value); } }
	 @Override
	 public boolean replace(final K key, final boolean value) { synchronized(sync) { return map.replace(key, value); } }
	 @Override
	 public boolean replace(final K key, final boolean oldValue, final boolean newValue) { synchronized(sync) { return map.replace(key, oldValue, newValue); } }
	 @Override
	 public boolean computeBooleanIfAbsent(final K key, final java.util.function.Predicate <? super K> mappingFunction) { synchronized(sync) { return map.computeBooleanIfAbsent(key, mappingFunction); } }
	 @Override
	 public boolean computeBooleanIfAbsentPartial(final K key, final Object2BooleanFunction <? super K> mappingFunction) { synchronized(sync) { return map.computeBooleanIfAbsentPartial(key, mappingFunction); } }
	 @Override
	 public boolean computeBooleanIfPresent(final K key, final java.util.function.BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
	  synchronized (sync) { return map.computeBooleanIfPresent(key, remappingFunction); }
	 }
	 @Override
	 public boolean computeBoolean(final K key, final java.util.function.BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
	  synchronized (sync) { return map.computeBoolean(key, remappingFunction); }
	 }
	 @Override
	 public boolean mergeBoolean(final K key, final boolean value, final java.util.function.BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
	  synchronized (sync) { return map.mergeBoolean(key, value, remappingFunction); }
	 }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Boolean getOrDefault(final Object key, final Boolean defaultValue) { synchronized (sync) { return map.getOrDefault(key, defaultValue); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean remove(final Object key, final Object value) { synchronized (sync) { return map.remove(key, value); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Boolean replace(final K key, final Boolean value) { synchronized (sync) { return map.replace(key, value); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean replace(final K key, final Boolean oldValue, final Boolean newValue) { synchronized (sync) { return map.replace(key, oldValue, newValue); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Boolean putIfAbsent(final K key, final Boolean value) { synchronized (sync) { return map.putIfAbsent(key, value); } }
	 @Override
	 public Boolean computeIfAbsent(final K key, final java.util.function.Function<? super K, ? extends Boolean> mappingFunction) { synchronized (sync) { return map.computeIfAbsent(key, mappingFunction); } }
	 @Override
	 public Boolean computeIfPresent(final K key, final java.util.function.BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) { synchronized (sync) { return map.computeIfPresent(key, remappingFunction); } }
	 @Override
	 public Boolean compute(final K key, final java.util.function.BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) { synchronized (sync) { return map.compute(key, remappingFunction); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Boolean merge(final K key, final Boolean value, final java.util.function.BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) { synchronized (sync) { return map.merge(key, value, remappingFunction); } }
	}
	/** Returns a synchronized type-specific map backed by the given type-specific map.
	 *
	 * @param m the map to be wrapped in a synchronized map.
	 * @return a synchronized view of the specified map.
	 * @see java.util.Collections#synchronizedMap(Map)
	 */
	public static <K> Object2BooleanMap <K> synchronize(final Object2BooleanMap <K> m) { return new SynchronizedMap <>(m); }
	/** Returns a synchronized type-specific map backed by the given type-specific map, using an assigned object to synchronize.
	 *
	 * @param m the map to be wrapped in a synchronized map.
	 * @param sync an object that will be used to synchronize the access to the map.
	 * @return a synchronized view of the specified map.
	 * @see java.util.Collections#synchronizedMap(Map)
	 */
	public static <K> Object2BooleanMap <K> synchronize(final Object2BooleanMap <K> m, final Object sync) { return new SynchronizedMap <>(m, sync); }
	/** An unmodifiable wrapper class for maps. */
	public static class UnmodifiableMap <K> extends Object2BooleanFunctions.UnmodifiableFunction <K> implements Object2BooleanMap <K>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final Object2BooleanMap <K> map;
	 protected transient ObjectSet<Object2BooleanMap.Entry <K> > entries;
	 protected transient ObjectSet <K> keys;
	 protected transient BooleanCollection values;
	 protected UnmodifiableMap(final Object2BooleanMap <K> m) {
	  super(m);
	  this.map = m;
	 }
	 @Override
	 public boolean containsValue(final boolean v) { return map.containsValue(v); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean containsValue(final Object ov) { return map.containsValue(ov); }
	 @Override
	 public void putAll(final Map<? extends K, ? extends Boolean> m) { throw new UnsupportedOperationException(); }
	 @Override
	 public ObjectSet<Object2BooleanMap.Entry <K> > object2BooleanEntrySet() { if (entries == null) entries = ObjectSets.unmodifiable(map.object2BooleanEntrySet()); return entries; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 public ObjectSet<Map.Entry<K, Boolean>> entrySet() { return (ObjectSet)object2BooleanEntrySet(); }
	 @Override
	 public ObjectSet <K> keySet() { if (keys == null) keys = ObjectSets.unmodifiable(map.keySet()); return keys; }
	 @Override
	 public BooleanCollection values() { if (values == null) return BooleanCollections.unmodifiable(map.values()); return values; }
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
	 public boolean getOrDefault(final Object key, final boolean defaultValue) { return map.getOrDefault(key, defaultValue); }
	 @Override
	 public void forEach(final java.util.function.BiConsumer<? super K, ? super Boolean> action) { map.forEach(action); }
	 @Override
	 public void replaceAll(final java.util.function.BiFunction<? super K, ? super Boolean, ? extends Boolean> function) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean putIfAbsent(final K key, final boolean value) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean remove(final Object key, final boolean value) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean replace(final K key, final boolean value) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean replace(final K key, final boolean oldValue, final boolean newValue) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean computeBooleanIfAbsent(final K key, final java.util.function.Predicate <? super K> mappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean computeBooleanIfAbsentPartial(final K key, final Object2BooleanFunction <? super K> mappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean computeBooleanIfPresent(final K key, final java.util.function.BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean computeBoolean(final K key, final java.util.function.BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean mergeBoolean(final K key, final boolean value, final java.util.function.BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Boolean getOrDefault(final Object key, final Boolean defaultValue) { return map.getOrDefault(key, defaultValue); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean remove(final Object key, final Object value) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Boolean replace(final K key, final Boolean value) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean replace(final K key, final Boolean oldValue, final Boolean newValue) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Boolean putIfAbsent(final K key, final Boolean value) { throw new UnsupportedOperationException(); }
	 @Override
	 public Boolean computeIfAbsent(final K key, final java.util.function.Function<? super K, ? extends Boolean> mappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public Boolean computeIfPresent(final K key, final java.util.function.BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public Boolean compute(final K key, final java.util.function.BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Boolean merge(final K key, final Boolean value, final java.util.function.BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) { throw new UnsupportedOperationException(); }
	}
	/** Returns an unmodifiable type-specific map backed by the given type-specific map.
	 *
	 * @param m the map to be wrapped in an unmodifiable map.
	 * @return an unmodifiable view of the specified map.
	 * @see java.util.Collections#unmodifiableMap(Map)
	 */
	public static <K> Object2BooleanMap <K> unmodifiable(final Object2BooleanMap <K> m) { return new UnmodifiableMap <>(m); }
}
