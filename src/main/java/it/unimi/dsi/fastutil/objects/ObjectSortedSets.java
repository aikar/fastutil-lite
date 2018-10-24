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
import java.util.SortedSet;
import java.util.NoSuchElementException;
import java.util.Comparator;
/** A class providing static methods and objects that do useful things with type-specific sorted sets.
	*
	* @see java.util.Collections
	*/
public final class ObjectSortedSets {
	private ObjectSortedSets() {}
	/** An immutable class representing the empty sorted set and implementing a type-specific set interface.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
	public static class EmptySet <K> extends ObjectSets.EmptySet <K> implements ObjectSortedSet <K>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptySet() {}
	 @Override
	 @SuppressWarnings("unchecked")
	 public ObjectBidirectionalIterator <K> iterator(K from) { return ObjectIterators.EMPTY_ITERATOR; }
	 @Override
	 @SuppressWarnings("unchecked")
	 public ObjectSortedSet <K> subSet(K from, K to) { return EMPTY_SET; }
	 @Override
	 @SuppressWarnings("unchecked")
	 public ObjectSortedSet <K> headSet(K from) { return EMPTY_SET; }
	 @Override
	 @SuppressWarnings("unchecked")
	 public ObjectSortedSet <K> tailSet(K to) { return EMPTY_SET; }
	 @Override
	 public K first() { throw new NoSuchElementException(); }
	 @Override
	 public K last() { throw new NoSuchElementException(); }
	 @Override
	 public Comparator <? super K> comparator() { return null; }
	 @Override
	 public Object clone() { return EMPTY_SET; }
	 private Object readResolve() { return EMPTY_SET; }
	}
	/** An empty sorted set (immutable). It is serializable and cloneable.
	 *
	 */
	@SuppressWarnings("rawtypes")
	public static final EmptySet EMPTY_SET = new EmptySet();
	/** Returns an empty sorted set (immutable). It is serializable and cloneable.
	 *
	 * <p>This method provides a typesafe access to {@link #EMPTY_SET}.
	 * @return an empty sorted set (immutable).
	 */
	@SuppressWarnings("unchecked")
	public static <K> ObjectSet <K> emptySet() {
	 return EMPTY_SET;
	}
	/** A class representing a singleton sorted set.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
	public static class Singleton <K> extends ObjectSets.Singleton <K> implements ObjectSortedSet <K>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 final Comparator <? super K> comparator;
	 protected Singleton(final K element, final Comparator <? super K> comparator) {
	  super(element);
	  this.comparator = comparator;
	 }
	 private Singleton(final K element) {
	  this(element, null);
	 }
	 @SuppressWarnings("unchecked")
	 final int compare(final K k1, final K k2) {
	  return comparator == null ? ( ((Comparable<K>)(k1)).compareTo(k2) ) : comparator.compare(k1, k2);
	 }
	 @Override
	 public ObjectBidirectionalIterator <K> iterator(K from) {
	  ObjectBidirectionalIterator <K> i = iterator();
	  if (compare(element, from) <= 0) i.next();
	  return i;
	 }
	 @Override
	 public Comparator <? super K> comparator() { return comparator; }
	 @Override
	 @SuppressWarnings("unchecked")
	 public ObjectSortedSet <K> subSet(final K from, final K to) { if (compare(from, element) <= 0 && compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	 @SuppressWarnings("unchecked")
	 public ObjectSortedSet <K> headSet(final K to) { if (compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	 @SuppressWarnings("unchecked")
	 public ObjectSortedSet <K> tailSet(final K from) { if (compare(from, element) <= 0) return this; return EMPTY_SET; }
	 @Override
	 public K first() { return element; }
	 @Override
	 public K last() { return element; }
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static <K> ObjectSortedSet <K> singleton(final K element) {
	 return new Singleton <>(element);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static <K> ObjectSortedSet <K> singleton(final K element, final Comparator <? super K> comparator) {
	 return new Singleton <>(element, comparator);
	}
	/** A synchronized wrapper class for sorted sets. */
	public static class SynchronizedSortedSet <K> extends ObjectSets.SynchronizedSet <K> implements ObjectSortedSet <K>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final ObjectSortedSet <K> sortedSet;
	 protected SynchronizedSortedSet(final ObjectSortedSet <K> s, final Object sync) {
	  super(s, sync);
	  sortedSet = s;
	 }
	 protected SynchronizedSortedSet(final ObjectSortedSet <K> s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public Comparator <? super K> comparator() { synchronized(sync) { return sortedSet.comparator(); } }
	 @Override
	 public ObjectSortedSet <K> subSet(final K from, final K to) { return new SynchronizedSortedSet <>(sortedSet.subSet(from, to), sync); }
	 @Override
	 public ObjectSortedSet <K> headSet(final K to) { return new SynchronizedSortedSet <>(sortedSet.headSet(to), sync); }
	 @Override
	 public ObjectSortedSet <K> tailSet(final K from) { return new SynchronizedSortedSet <>(sortedSet.tailSet(from), sync); }
	 @Override
	 public ObjectBidirectionalIterator <K> iterator() { return sortedSet.iterator(); }
	 @Override
	 public ObjectBidirectionalIterator <K> iterator(final K from) { return sortedSet.iterator(from); }
	 @Override
	 public K first() { synchronized(sync) { return sortedSet.first(); } }
	 @Override
	 public K last() { synchronized(sync) { return sortedSet.last(); } }
	}
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static <K> ObjectSortedSet <K> synchronize(final ObjectSortedSet <K> s) { return new SynchronizedSortedSet <>(s); }
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set, using an assigned object to synchronize.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @param sync an object that will be used to synchronize the access to the sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static <K> ObjectSortedSet <K> synchronize(final ObjectSortedSet <K> s, final Object sync) { return new SynchronizedSortedSet <>(s, sync); }
	/** An unmodifiable wrapper class for sorted sets. */
	public static class UnmodifiableSortedSet <K> extends ObjectSets.UnmodifiableSet <K> implements ObjectSortedSet <K>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final ObjectSortedSet <K> sortedSet;
	 protected UnmodifiableSortedSet(final ObjectSortedSet <K> s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public Comparator <? super K> comparator() { return sortedSet.comparator(); }
	 @Override
	 public ObjectSortedSet <K> subSet(final K from, final K to) { return new UnmodifiableSortedSet <>(sortedSet.subSet(from, to)); }
	 @Override
	 public ObjectSortedSet <K> headSet(final K to) { return new UnmodifiableSortedSet <>(sortedSet.headSet(to)); }
	 @Override
	 public ObjectSortedSet <K> tailSet(final K from) { return new UnmodifiableSortedSet <>(sortedSet.tailSet(from)); }
	 @Override
	 public ObjectBidirectionalIterator <K> iterator() { return ObjectIterators.unmodifiable(sortedSet.iterator()); }
	 @Override
	 public ObjectBidirectionalIterator <K> iterator(final K from) { return ObjectIterators.unmodifiable(sortedSet.iterator(from)); }
	 @Override
	 public K first() { return sortedSet.first(); }
	 @Override
	 public K last() { return sortedSet.last(); }
	}
	/** Returns an unmodifiable type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in an unmodifiable sorted set.
	 * @return an unmodifiable view of the specified sorted set.
	 * @see java.util.Collections#unmodifiableSortedSet(SortedSet)
	 */
	public static <K> ObjectSortedSet <K> unmodifiable(final ObjectSortedSet <K> s) { return new UnmodifiableSortedSet <>(s); }
}
