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
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharCollections;
import it.unimi.dsi.fastutil.chars.CharSets;
import java.util.Map;
import java.util.function.Consumer;
import it.unimi.dsi.fastutil.objects.Object2CharMap.FastEntrySet;
/** A class providing static methods and objects that do useful things with type-specific maps.
	*
	* @see java.util.Collections
	*/
public final class Object2CharMaps {
	private Object2CharMaps() {}
	/** Returns an iterator that will be {@linkplain FastEntrySet fast}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map from which we will try to extract a (fast) iterator on the entry set.
	 * @return an iterator on the entry set of the given map that will be fast, if possible.
	 * @since 8.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <K> ObjectIterator<Object2CharMap.Entry <K> > fastIterator(Object2CharMap <K> map) {
	 final ObjectSet<Object2CharMap.Entry <K> > entries = map.object2CharEntrySet();
	 return entries instanceof Object2CharMap.FastEntrySet ? ((Object2CharMap.FastEntrySet <K>) entries).fastIterator() : entries.iterator();
	}
	/** Iterates {@linkplain FastEntrySet#fastForEach(Consumer) quickly}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map on which we will try to iterate {@linkplain FastEntrySet#fastForEach(Consumer) quickly}.
	 * @param consumer the consumer that will be passed to  {@link FastEntrySet#fastForEach(Consumer)}, if possible, or to {@link Iterable#forEach(Consumer)}.
	 * @since 8.1.0
	 */
	@SuppressWarnings("unchecked")
	public static <K> void fastForEach(Object2CharMap <K> map, final Consumer<? super Object2CharMap.Entry <K> > consumer) {
	 final ObjectSet<Object2CharMap.Entry <K> > entries = map.object2CharEntrySet();
	 if (entries instanceof Object2CharMap.FastEntrySet) ((Object2CharMap.FastEntrySet <K>) entries).fastForEach(consumer);
	 else entries.forEach(consumer);
	}
	/** Returns an iterable yielding an iterator that will be {@linkplain FastEntrySet fast}, if possible, on the {@linkplain Map#entrySet() entry set} of the provided {@code map}.
	 * @param map a map from which we will try to extract an iterable yielding a (fast) iterator on the entry set.
	 * @return an iterable  yielding an iterator on the entry set of the given map that will be
	 * fast, if possible.
	 * @since 8.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <K> ObjectIterable<Object2CharMap.Entry <K> > fastIterable(Object2CharMap <K> map) {
	 final ObjectSet<Object2CharMap.Entry <K> > entries = map.object2CharEntrySet();
	 return entries instanceof Object2CharMap.FastEntrySet ? new ObjectIterable<Object2CharMap.Entry <K> >() {
	  public ObjectIterator<Object2CharMap.Entry <K> > iterator() { return ((Object2CharMap.FastEntrySet <K>)entries).fastIterator(); }
	  public void forEach(final Consumer<? super Object2CharMap.Entry <K> > consumer) { ((Object2CharMap.FastEntrySet <K>)entries).fastForEach(consumer); }
	 } : entries;
	}
	/** An immutable class representing an empty type-specific map.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific map.
	 */
	public static class EmptyMap <K> extends Object2CharFunctions.EmptyFunction <K> implements Object2CharMap <K>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptyMap() {}
	 @Override
	 public boolean containsValue(final char v) { return false; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean containsValue(final Object ov) { return false; }
	 @Override
	 public void putAll(final Map<? extends K, ? extends Character> m) { throw new UnsupportedOperationException(); }
	 @SuppressWarnings("unchecked")
	 @Override
	 public ObjectSet<Object2CharMap.Entry <K> > object2CharEntrySet() { return ObjectSets.EMPTY_SET; }
	 @SuppressWarnings("unchecked")
	 @Override
	 public ObjectSet <K> keySet() { return ObjectSets.EMPTY_SET; }
	
	 @Override
	 public CharCollection values() { return CharSets.EMPTY_SET; }
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
	public static <K> Object2CharMap <K> emptyMap() {
	 return EMPTY_MAP;
	}
	/** An immutable class representing a type-specific singleton map.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific map.
	 */
	public static class Singleton <K> extends Object2CharFunctions.Singleton <K> implements Object2CharMap <K>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected transient ObjectSet<Object2CharMap.Entry <K> > entries;
	 protected transient ObjectSet <K> keys;
	 protected transient CharCollection values;
	 protected Singleton(final K key, final char value) {
	  super(key, value);
	 }
	 @Override
	 public boolean containsValue(final char v) { return ( (value) == (v) ); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean containsValue(final Object ov) { return ( (((Character)(ov)).charValue()) == (value) ); }
	 @Override
	 public void putAll(final Map<? extends K, ? extends Character> m) { throw new UnsupportedOperationException(); }
	 @Override
	 public ObjectSet<Object2CharMap.Entry <K> > object2CharEntrySet() { if (entries == null) entries = ObjectSets.singleton(new AbstractObject2CharMap.BasicEntry <>(key, value)); return entries; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public ObjectSet<Map.Entry<K, Character>> entrySet() { return (ObjectSet)object2CharEntrySet(); }
	 @Override
	 public ObjectSet <K> keySet() { if (keys == null) keys = ObjectSets.singleton(key); return keys; }
	 @Override
	 public CharCollection values() { if (values == null) values = CharSets.singleton(value); return values; }
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
	public static <K> Object2CharMap <K> singleton(final K key, char value) { return new Singleton <>(key, value); }
	/** Returns a type-specific immutable map containing only the specified pair. The returned map is serializable and cloneable.
	 *
	 * <p>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned map.
	 * @param value the only value of the returned map.
	 * @return a type-specific immutable map containing just the pair {@code &lt;key,value&gt;}.
	 */
	public static <K> Object2CharMap <K> singleton(final K key, final Character value) { return new Singleton <>((key), (value).charValue()); }
	/** A synchronized wrapper class for maps. */
	public static class SynchronizedMap <K> extends Object2CharFunctions.SynchronizedFunction <K> implements Object2CharMap <K>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final Object2CharMap <K> map;
	 protected transient ObjectSet<Object2CharMap.Entry <K> > entries;
	 protected transient ObjectSet <K> keys;
	 protected transient CharCollection values;
	 protected SynchronizedMap(final Object2CharMap <K> m, final Object sync) {
	  super(m, sync);
	  this.map = m;
	 }
	 protected SynchronizedMap(final Object2CharMap <K> m) {
	  super(m);
	  this.map = m;
	 }
	 @Override
	 public boolean containsValue(final char v) { synchronized(sync) { return map.containsValue(v); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean containsValue(final Object ov) { synchronized(sync) { return map.containsValue(ov); } }
	 @Override
	 public void putAll(final Map<? extends K, ? extends Character> m) { synchronized(sync) { map.putAll(m); } }
	 @Override
	 public ObjectSet<Object2CharMap.Entry <K> > object2CharEntrySet() { synchronized(sync) { if (entries == null) entries = ObjectSets.synchronize(map.object2CharEntrySet(), sync); return entries; } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 public ObjectSet<Map.Entry<K, Character>> entrySet() { return (ObjectSet)object2CharEntrySet(); }
	 @Override
	 public ObjectSet <K> keySet() {
	  synchronized(sync) { if (keys == null) keys = ObjectSets.synchronize(map.keySet(), sync); return keys; }
	 }
	 @Override
	 public CharCollection values() {
	  synchronized(sync) { if (values == null) return CharCollections.synchronize(map.values(), sync); return values; }
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
	 public char getOrDefault(final Object key, final char defaultValue) { synchronized(sync) { return map.getOrDefault(key, defaultValue); } }
	 @Override
	 public void forEach(final java.util.function.BiConsumer<? super K, ? super Character> action) { synchronized (sync) { map.forEach(action); } }
	 @Override
	 public void replaceAll(final java.util.function.BiFunction<? super K, ? super Character, ? extends Character> function) { synchronized (sync) { map.replaceAll(function); } }
	 @Override
	 public char putIfAbsent(final K key, final char value) { synchronized(sync) { return map.putIfAbsent(key, value); } }
	 @Override
	 public boolean remove(final Object key, final char value) { synchronized(sync) { return map.remove(key, value); } }
	 @Override
	 public char replace(final K key, final char value) { synchronized(sync) { return map.replace(key, value); } }
	 @Override
	 public boolean replace(final K key, final char oldValue, final char newValue) { synchronized(sync) { return map.replace(key, oldValue, newValue); } }
	 @Override
	 public char computeCharIfAbsent(final K key, final java.util.function.ToIntFunction <? super K> mappingFunction) { synchronized(sync) { return map.computeCharIfAbsent(key, mappingFunction); } }
	 @Override
	 public char computeCharIfAbsentPartial(final K key, final Object2CharFunction <? super K> mappingFunction) { synchronized(sync) { return map.computeCharIfAbsentPartial(key, mappingFunction); } }
	 @Override
	 public char computeCharIfPresent(final K key, final java.util.function.BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
	  synchronized (sync) { return map.computeCharIfPresent(key, remappingFunction); }
	 }
	 @Override
	 public char computeChar(final K key, final java.util.function.BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
	  synchronized (sync) { return map.computeChar(key, remappingFunction); }
	 }
	 @Override
	 public char mergeChar(final K key, final char value, final java.util.function.BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
	  synchronized (sync) { return map.mergeChar(key, value, remappingFunction); }
	 }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character getOrDefault(final Object key, final Character defaultValue) { synchronized (sync) { return map.getOrDefault(key, defaultValue); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean remove(final Object key, final Object value) { synchronized (sync) { return map.remove(key, value); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character replace(final K key, final Character value) { synchronized (sync) { return map.replace(key, value); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean replace(final K key, final Character oldValue, final Character newValue) { synchronized (sync) { return map.replace(key, oldValue, newValue); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character putIfAbsent(final K key, final Character value) { synchronized (sync) { return map.putIfAbsent(key, value); } }
	 @Override
	 public Character computeIfAbsent(final K key, final java.util.function.Function<? super K, ? extends Character> mappingFunction) { synchronized (sync) { return map.computeIfAbsent(key, mappingFunction); } }
	 @Override
	 public Character computeIfPresent(final K key, final java.util.function.BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) { synchronized (sync) { return map.computeIfPresent(key, remappingFunction); } }
	 @Override
	 public Character compute(final K key, final java.util.function.BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) { synchronized (sync) { return map.compute(key, remappingFunction); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character merge(final K key, final Character value, final java.util.function.BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) { synchronized (sync) { return map.merge(key, value, remappingFunction); } }
	}
	/** Returns a synchronized type-specific map backed by the given type-specific map.
	 *
	 * @param m the map to be wrapped in a synchronized map.
	 * @return a synchronized view of the specified map.
	 * @see java.util.Collections#synchronizedMap(Map)
	 */
	public static <K> Object2CharMap <K> synchronize(final Object2CharMap <K> m) { return new SynchronizedMap <>(m); }
	/** Returns a synchronized type-specific map backed by the given type-specific map, using an assigned object to synchronize.
	 *
	 * @param m the map to be wrapped in a synchronized map.
	 * @param sync an object that will be used to synchronize the access to the map.
	 * @return a synchronized view of the specified map.
	 * @see java.util.Collections#synchronizedMap(Map)
	 */
	public static <K> Object2CharMap <K> synchronize(final Object2CharMap <K> m, final Object sync) { return new SynchronizedMap <>(m, sync); }
	/** An unmodifiable wrapper class for maps. */
	public static class UnmodifiableMap <K> extends Object2CharFunctions.UnmodifiableFunction <K> implements Object2CharMap <K>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final Object2CharMap <K> map;
	 protected transient ObjectSet<Object2CharMap.Entry <K> > entries;
	 protected transient ObjectSet <K> keys;
	 protected transient CharCollection values;
	 protected UnmodifiableMap(final Object2CharMap <K> m) {
	  super(m);
	  this.map = m;
	 }
	 @Override
	 public boolean containsValue(final char v) { return map.containsValue(v); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean containsValue(final Object ov) { return map.containsValue(ov); }
	 @Override
	 public void putAll(final Map<? extends K, ? extends Character> m) { throw new UnsupportedOperationException(); }
	 @Override
	 public ObjectSet<Object2CharMap.Entry <K> > object2CharEntrySet() { if (entries == null) entries = ObjectSets.unmodifiable(map.object2CharEntrySet()); return entries; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 public ObjectSet<Map.Entry<K, Character>> entrySet() { return (ObjectSet)object2CharEntrySet(); }
	 @Override
	 public ObjectSet <K> keySet() { if (keys == null) keys = ObjectSets.unmodifiable(map.keySet()); return keys; }
	 @Override
	 public CharCollection values() { if (values == null) return CharCollections.unmodifiable(map.values()); return values; }
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
	 public char getOrDefault(final Object key, final char defaultValue) { return map.getOrDefault(key, defaultValue); }
	 @Override
	 public void forEach(final java.util.function.BiConsumer<? super K, ? super Character> action) { map.forEach(action); }
	 @Override
	 public void replaceAll(final java.util.function.BiFunction<? super K, ? super Character, ? extends Character> function) { throw new UnsupportedOperationException(); }
	 @Override
	 public char putIfAbsent(final K key, final char value) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean remove(final Object key, final char value) { throw new UnsupportedOperationException(); }
	 @Override
	 public char replace(final K key, final char value) { throw new UnsupportedOperationException(); }
	 @Override
	 public boolean replace(final K key, final char oldValue, final char newValue) { throw new UnsupportedOperationException(); }
	 @Override
	 public char computeCharIfAbsent(final K key, final java.util.function.ToIntFunction <? super K> mappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public char computeCharIfAbsentPartial(final K key, final Object2CharFunction <? super K> mappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public char computeCharIfPresent(final K key, final java.util.function.BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public char computeChar(final K key, final java.util.function.BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public char mergeChar(final K key, final char value, final java.util.function.BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character getOrDefault(final Object key, final Character defaultValue) { return map.getOrDefault(key, defaultValue); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean remove(final Object key, final Object value) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character replace(final K key, final Character value) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public boolean replace(final K key, final Character oldValue, final Character newValue) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character putIfAbsent(final K key, final Character value) { throw new UnsupportedOperationException(); }
	 @Override
	 public Character computeIfAbsent(final K key, final java.util.function.Function<? super K, ? extends Character> mappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public Character computeIfPresent(final K key, final java.util.function.BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) { throw new UnsupportedOperationException(); }
	 @Override
	 public Character compute(final K key, final java.util.function.BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) { throw new UnsupportedOperationException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Character merge(final K key, final Character value, final java.util.function.BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) { throw new UnsupportedOperationException(); }
	}
	/** Returns an unmodifiable type-specific map backed by the given type-specific map.
	 *
	 * @param m the map to be wrapped in an unmodifiable map.
	 * @return an unmodifiable view of the specified map.
	 * @see java.util.Collections#unmodifiableMap(Map)
	 */
	public static <K> Object2CharMap <K> unmodifiable(final Object2CharMap <K> m) { return new UnmodifiableMap <>(m); }
}
