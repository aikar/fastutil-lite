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
import java.util.SortedSet;
import java.util.NoSuchElementException;
/** A class providing static methods and objects that do useful things with type-specific sorted sets.
	*
	* @see java.util.Collections
	*/
public final class FloatSortedSets {
	private FloatSortedSets() {}
	/** An immutable class representing the empty sorted set and implementing a type-specific set interface.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
	public static class EmptySet extends FloatSets.EmptySet implements FloatSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptySet() {}
	 @Override
	
	 public FloatBidirectionalIterator iterator(float from) { return FloatIterators.EMPTY_ITERATOR; }
	 @Override
	
	 public FloatSortedSet subSet(float from, float to) { return EMPTY_SET; }
	 @Override
	
	 public FloatSortedSet headSet(float from) { return EMPTY_SET; }
	 @Override
	
	 public FloatSortedSet tailSet(float to) { return EMPTY_SET; }
	 @Override
	 public float firstFloat() { throw new NoSuchElementException(); }
	 @Override
	 public float lastFloat() { throw new NoSuchElementException(); }
	 @Override
	 public FloatComparator comparator() { return null; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public FloatSortedSet subSet(Float from, Float to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public FloatSortedSet headSet(Float from) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public FloatSortedSet tailSet(Float to) { return EMPTY_SET; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float first() { throw new NoSuchElementException(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float last() { throw new NoSuchElementException(); }
	 @Override
	 public Object clone() { return EMPTY_SET; }
	 private Object readResolve() { return EMPTY_SET; }
	}
	/** An empty sorted set (immutable). It is serializable and cloneable.
	 *
	 */

	public static final EmptySet EMPTY_SET = new EmptySet();
	/** A class representing a singleton sorted set.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted set.
	 */
	public static class Singleton extends FloatSets.Singleton implements FloatSortedSet , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 final FloatComparator comparator;
	 protected Singleton(final float element, final FloatComparator comparator) {
	  super(element);
	  this.comparator = comparator;
	 }
	 private Singleton(final float element) {
	  this(element, null);
	 }
	
	 final int compare(final float k1, final float k2) {
	  return comparator == null ? ( Float.compare((k1),(k2)) ) : comparator.compare(k1, k2);
	 }
	 @Override
	 public FloatBidirectionalIterator iterator(float from) {
	  FloatBidirectionalIterator i = iterator();
	  if (compare(element, from) <= 0) i.nextFloat();
	  return i;
	 }
	 @Override
	 public FloatComparator comparator() { return comparator; }
	 @Override
	
	 public FloatSortedSet subSet(final float from, final float to) { if (compare(from, element) <= 0 && compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public FloatSortedSet headSet(final float to) { if (compare(element, to) < 0) return this; return EMPTY_SET; }
	 @Override
	
	 public FloatSortedSet tailSet(final float from) { if (compare(from, element) <= 0) return this; return EMPTY_SET; }
	 @Override
	 public float firstFloat() { return element; }
	 @Override
	 public float lastFloat() { return element; }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public FloatSortedSet subSet(final Float from, final Float to) { return subSet((from).floatValue(), (to).floatValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public FloatSortedSet headSet(final Float to) { return headSet((to).floatValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public FloatSortedSet tailSet(final Float from) { return tailSet((from).floatValue()); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float first() { return Float.valueOf(element); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float last() { return Float.valueOf(element); }
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static FloatSortedSet singleton(final float element) {
	 return new Singleton (element);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static FloatSortedSet singleton(final float element, final FloatComparator comparator) {
	 return new Singleton (element, comparator);
	}
	/** Returns a type-specific immutable sorted set containing only the specified element. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static FloatSortedSet singleton(final Object element) {
	 return new Singleton(((Float)(element)).floatValue());
	}
	/** Returns a type-specific immutable sorted set containing only the specified element, and using a specified comparator. The returned sorted set is serializable and cloneable.
	 *
	 * @param element the only element of the returned sorted set.
	 * @param comparator the comparator to use in the returned sorted set.
	 * @return a type-specific immutable sorted set containing just {@code element}.
	 */
	public static FloatSortedSet singleton(final Object element, final FloatComparator comparator) {
	 return new Singleton(((Float)(element)).floatValue(), comparator);
	}
	/** A synchronized wrapper class for sorted sets. */
	public static class SynchronizedSortedSet extends FloatSets.SynchronizedSet implements FloatSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final FloatSortedSet sortedSet;
	 protected SynchronizedSortedSet(final FloatSortedSet s, final Object sync) {
	  super(s, sync);
	  sortedSet = s;
	 }
	 protected SynchronizedSortedSet(final FloatSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public FloatComparator comparator() { synchronized(sync) { return sortedSet.comparator(); } }
	 @Override
	 public FloatSortedSet subSet(final float from, final float to) { return new SynchronizedSortedSet (sortedSet.subSet(from, to), sync); }
	 @Override
	 public FloatSortedSet headSet(final float to) { return new SynchronizedSortedSet (sortedSet.headSet(to), sync); }
	 @Override
	 public FloatSortedSet tailSet(final float from) { return new SynchronizedSortedSet (sortedSet.tailSet(from), sync); }
	 @Override
	 public FloatBidirectionalIterator iterator() { return sortedSet.iterator(); }
	 @Override
	 public FloatBidirectionalIterator iterator(final float from) { return sortedSet.iterator(from); }
	 @Override
	 public float firstFloat() { synchronized(sync) { return sortedSet.firstFloat(); } }
	 @Override
	 public float lastFloat() { synchronized(sync) { return sortedSet.lastFloat(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float first() { synchronized(sync) { return sortedSet.first(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float last() { synchronized(sync) { return sortedSet.last(); } }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public FloatSortedSet subSet(final Float from, final Float to) { return new SynchronizedSortedSet(sortedSet.subSet(from, to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public FloatSortedSet headSet(final Float to) { return new SynchronizedSortedSet(sortedSet.headSet(to), sync); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public FloatSortedSet tailSet(final Float from) { return new SynchronizedSortedSet(sortedSet.tailSet(from), sync); }
	}
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static FloatSortedSet synchronize(final FloatSortedSet s) { return new SynchronizedSortedSet (s); }
	/** Returns a synchronized type-specific sorted set backed by the given type-specific sorted set, using an assigned object to synchronize.
	 *
	 * @param s the sorted set to be wrapped in a synchronized sorted set.
	 * @param sync an object that will be used to synchronize the access to the sorted set.
	 * @return a synchronized view of the specified sorted set.
	 * @see java.util.Collections#synchronizedSortedSet(SortedSet)
	 */
	public static FloatSortedSet synchronize(final FloatSortedSet s, final Object sync) { return new SynchronizedSortedSet (s, sync); }
	/** An unmodifiable wrapper class for sorted sets. */
	public static class UnmodifiableSortedSet extends FloatSets.UnmodifiableSet implements FloatSortedSet , java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected final FloatSortedSet sortedSet;
	 protected UnmodifiableSortedSet(final FloatSortedSet s) {
	  super(s);
	  sortedSet = s;
	 }
	 @Override
	 public FloatComparator comparator() { return sortedSet.comparator(); }
	 @Override
	 public FloatSortedSet subSet(final float from, final float to) { return new UnmodifiableSortedSet (sortedSet.subSet(from, to)); }
	 @Override
	 public FloatSortedSet headSet(final float to) { return new UnmodifiableSortedSet (sortedSet.headSet(to)); }
	 @Override
	 public FloatSortedSet tailSet(final float from) { return new UnmodifiableSortedSet (sortedSet.tailSet(from)); }
	 @Override
	 public FloatBidirectionalIterator iterator() { return FloatIterators.unmodifiable(sortedSet.iterator()); }
	 @Override
	 public FloatBidirectionalIterator iterator(final float from) { return FloatIterators.unmodifiable(sortedSet.iterator(from)); }
	 @Override
	 public float firstFloat() { return sortedSet.firstFloat(); }
	 @Override
	 public float lastFloat() { return sortedSet.lastFloat(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float first() { return sortedSet.first(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public Float last() { return sortedSet.last(); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public FloatSortedSet subSet(final Float from, final Float to) { return new UnmodifiableSortedSet(sortedSet.subSet(from, to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public FloatSortedSet headSet(final Float to) { return new UnmodifiableSortedSet(sortedSet.headSet(to)); }
	 /** {@inheritDoc}
		 * @deprecated Please use the corresponding type-specific method instead. */
	 @Deprecated
	 @Override
	 public FloatSortedSet tailSet(final Float from) { return new UnmodifiableSortedSet(sortedSet.tailSet(from)); }
	}
	/** Returns an unmodifiable type-specific sorted set backed by the given type-specific sorted set.
	 *
	 * @param s the sorted set to be wrapped in an unmodifiable sorted set.
	 * @return an unmodifiable view of the specified sorted set.
	 * @see java.util.Collections#unmodifiableSortedSet(SortedSet)
	 */
	public static FloatSortedSet unmodifiable(final FloatSortedSet s) { return new UnmodifiableSortedSet (s); }
}
