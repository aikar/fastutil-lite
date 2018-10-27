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
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import java.util.function.Consumer;
import java.util.Map;
/** A type-specific {@link Map}; provides some additional methods that use polymorphism to avoid (un)boxing, and handling of a default return value.
	*
	* <p>Besides extending the corresponding type-specific {@linkplain it.unimi.dsi.fastutil.Function function}, this interface strengthens {@link Map#entrySet()},
	* {@link #keySet()} and {@link #values()}. Moreover, a number of methods, such as {@link #size()}, {@link #defaultReturnValue()}, etc., are un-defaulted
	* as their function default do not make sense for a map.
	* Maps returning entry sets of type {@link FastEntrySet} support also fast iteration.
	*
	* <p>A submap or subset may or may not have an
	* independent default return value (which however must be initialized to the
	* default return value of the originator).
	*
	* @see Map
	*/
public interface Object2ObjectMap <K,V> extends Object2ObjectFunction <K,V>, Map<K,V> {
	/** An entry set providing fast iteration.
	 *
	 * <p>In some cases (e.g., hash-based classes) iteration over an entry set requires the creation
	 * of a large number of {@link java.util.Map.Entry} objects. Some {@code fastutil}
	 * maps might return {@linkplain Map#entrySet() entry set} objects of type {@code FastEntrySet}: in this case, {@link #fastIterator() fastIterator()}
	 * will return an iterator that is guaranteed not to create a large number of objects, <em>possibly
	 * by returning always the same entry</em> (of course, mutated), and {@link #fastForEach(Consumer)} will apply
	 * the provided consumer to all elements of the entry set, <em>which might be represented
	 * always by the same entry</em> (of course, mutated).
	 */
	interface FastEntrySet <K,V> extends ObjectSet<Object2ObjectMap.Entry <K,V> > {
	 /** Returns a fast iterator over this entry set; the iterator might return always the same entry instance, suitably mutated.
		 *
		 * @return a fast iterator over this entry set; the iterator might return always the same {@link java.util.Map.Entry} instance, suitably mutated.
		 */
	 ObjectIterator<Object2ObjectMap.Entry <K,V> > fastIterator();
	 /** Iterates quickly over this entry set; the iteration might happen always on the same entry instance, suitably mutated.
		 *
		 * <p>This default implementation just delegates to {@link #forEach(Consumer)}.
		 *
		 * @param consumer a consumer that will by applied to the entries of this set; the entries might be represented
		 * by the same entry instance, suitably mutated.
		 * @since 8.1.0
		 */
	 default void fastForEach(final Consumer<? super Object2ObjectMap.Entry <K,V> > consumer) {
	  forEach(consumer);
	 }
	}
	/**
	 * Returns the number of key/value mappings in this map.  If the
	 * map contains more than {@link Integer#MAX_VALUE} elements, returns {@link Integer#MAX_VALUE}.
	 *
	 * @return the number of key-value mappings in this map.
	 * @see it.unimi.dsi.fastutil.Size64
	 */
	@Override
	int size();
	/** Removes all of the mappings from this map (optional operation).
	 * The map will be empty after this call returns.
	 *
	 * @throws UnsupportedOperationException if the <tt>clear</tt> operation is not supported by this map
	 */
	@Override
	default void clear() { throw new UnsupportedOperationException(); }
	/** Sets the default return value (optional operation).
	 *
	 * This value must be returned by type-specific versions of {@code get()},
	 * {@code put()} and {@code remove()} to denote that the map does not contain
	 * the specified key. It must be {@code null} by default.
	 *
	 * <p><strong>Warning</strong>: Changing this to a non-null value can have
	 * unforeseen consequences. Especially, it breaks compatibility with the
	 * specifications of Java's {@link java.util.Map} interface. It has to be
	 * used with great care and thorough study of all method comments is
	 * recommended.
	 *
	 * @param rv the new default return value.
	 * @see #defaultReturnValue()
	 */
	@Override
	void defaultReturnValue(V rv);
	/** Gets the default return value.
	 *
	 * @return the current default return value.
	 */
	@Override
	V defaultReturnValue();
	/** Returns a type-specific set view of the mappings contained in this map.
	 *
	 * <p>This method is necessary because there is no inheritance along
	 * type parameters: it is thus impossible to strengthen {@link Map#entrySet()}
	 * so that it returns an {@link it.unimi.dsi.fastutil.objects.ObjectSet}
	 * of type-specific entries (the latter makes it possible to
	 * access keys and values with type-specific methods).
	 *
	 * @return a type-specific set view of the mappings contained in this map.
	 * @see Map#entrySet()
	 */
	ObjectSet<Object2ObjectMap.Entry <K,V> > object2ObjectEntrySet();
	/** Returns a set view of the mappings contained in this map.
	 *  <p>Note that this specification strengthens the one given in {@link Map#entrySet()}.
	 *
	 * @return a set view of the mappings contained in this map.
	 * @see Map#entrySet()
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	default ObjectSet<Map.Entry<K, V>> entrySet() {
	 return (ObjectSet)object2ObjectEntrySet();
	}
	/** {@inheritDoc}
	 * <p>This default implementation just delegates to the corresponding function method.
	 */
	@Override
	default V put(final K key, final V value) {
	 return Object2ObjectFunction.super.put(key, value);
	}
	/** {@inheritDoc}
	 * <p>This default implementation just delegates to the corresponding type-specific&ndash;{@linkplain it.unimi.dsi.fastutil.Function function} method.
	 */
	@Override
	default V remove(final Object key) {
	 return Object2ObjectFunction.super.remove(key);
	}
	/** {@inheritDoc}
	 * <p>Note that this specification strengthens the one given in {@link Map#keySet()}.
	 * @return a set view of the keys contained in this map.
	 * @see Map#keySet()
	 */
	@Override
	ObjectSet <K> keySet();
	/** {@inheritDoc}
	 *  <p>Note that this specification strengthens the one given in {@link Map#values()}.
	 * @return a set view of the values contained in this map.
	 * @see Map#values()
	 */
	@Override
	ObjectCollection <V> values();
	/** Returns true if this function contains a mapping for the specified key.
	 *
	 * @param key the key.
	 * @return true if this function associates a value to {@code key}.
	 * @see Map#containsKey(Object)
	 */
	@Override
	boolean containsKey(Object key);
	// Defaultable methods
	/** A type-specific {@link java.util.Map.Entry}; provides some additional methods
	 *  that use polymorphism to avoid (un)boxing.
	 *
	 * @see java.util.Map.Entry
	 */
	interface Entry <K,V> extends Map.Entry <K,V> {
	}
}
