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
package it.unimi.dsi.fastutil.longs;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/** An abstract class providing basic methods for sorted maps implementing a type-specific interface. */
public abstract class AbstractLong2ObjectSortedMap <V> extends AbstractLong2ObjectMap <V> implements Long2ObjectSortedMap <V> {
	private static final long serialVersionUID = -1773560792952436569L;
	protected AbstractLong2ObjectSortedMap() {}
	/** {@inheritDoc}
	 *
	 * <p>The view is backed by the sorted set returned by {@link java.util.Map#entrySet()}. Note that
	 * <em>no attempt is made at caching the result of this method</em>, as this would
	 * require adding some attributes that lightweight implementations would
	 * not need. Subclasses may easily override this policy by calling
	 * this method and caching the result, but implementors are encouraged to
	 * write more efficient ad-hoc implementations.
	 *
	 * @return a sorted set view of the keys of this map; it may be safely cast to a type-specific interface.
	 */
	@Override
	public LongSortedSet keySet() {
	 return new KeySet();
	}
	/** A wrapper exhibiting the keys of a map. */
	protected class KeySet extends AbstractLongSortedSet {
	 @Override
	 public boolean contains(final long k) { return containsKey(k); }
	 @Override
	 public int size() { return AbstractLong2ObjectSortedMap.this.size(); }
	 @Override
	 public void clear() { AbstractLong2ObjectSortedMap.this.clear(); }
	 @Override
	 public LongComparator comparator() { return AbstractLong2ObjectSortedMap.this.comparator(); }
	 @Override
	 public long firstLong() { return firstLongKey(); }
	 @Override
	 public long lastLong() { return lastLongKey(); }
	 @Override
	 public LongSortedSet headSet(final long to) { return headMap(to).keySet(); }
	 @Override
	 public LongSortedSet tailSet(final long from) { return tailMap(from).keySet(); }
	 @Override
	 public LongSortedSet subSet(final long from, final long to) { return subMap(from, to).keySet(); }
	 @Override
	 public LongBidirectionalIterator iterator(final long from) { return new KeySetIterator <>(long2ObjectEntrySet().iterator(new BasicEntry <>(from, (null)))); }
	 @Override
	 public LongBidirectionalIterator iterator() { return new KeySetIterator <>(Long2ObjectSortedMaps.fastIterator(AbstractLong2ObjectSortedMap.this)); }
	}
	/** A wrapper exhibiting a map iterator as an iterator on keys.
	 *
	 * <p>To provide an iterator on keys, just create an instance of this
	 * class using the corresponding iterator on entries.
	 */
	protected static class KeySetIterator <V> implements LongBidirectionalIterator {
	 protected final ObjectBidirectionalIterator<Long2ObjectMap.Entry <V> > i;
	 public KeySetIterator(ObjectBidirectionalIterator<Long2ObjectMap.Entry <V> > i) {
	  this.i = i;
	 }
	 @Override
	 public long nextLong() { return i.next().getLongKey(); };
	 @Override
	 public long previousLong() { return i.previous().getLongKey(); };
	 @Override
	 public boolean hasNext() { return i.hasNext(); }
	 @Override
	 public boolean hasPrevious() { return i.hasPrevious(); }
	}
	/** {@inheritDoc}
	 *
	 * <p>The view is backed by the sorted set returned by {@link java.util.Map#entrySet()}. Note that
	 * <em>no attempt is made at caching the result of this method</em>, as this would
	 * require adding some attributes that lightweight implementations would
	 * not need. Subclasses may easily override this policy by calling
	 * this method and caching the result, but implementors are encouraged to
	 * write more efficient ad-hoc implementations.
	 *
	 * @return a type-specific collection view of the values contained in this map.
	 */
	@Override
	public ObjectCollection <V> values() {
	 return new ValuesCollection();
	}
	/** A wrapper exhibiting the values of a map. */
	protected class ValuesCollection extends AbstractObjectCollection <V> {
	 @Override
	 public ObjectIterator <V> iterator() { return new ValuesIterator <>(Long2ObjectSortedMaps.fastIterator(AbstractLong2ObjectSortedMap.this)); }
	 @Override
	 public boolean contains(final Object k) { return containsValue(k); }
	 @Override
	 public int size() { return AbstractLong2ObjectSortedMap.this.size(); }
	 @Override
	 public void clear() { AbstractLong2ObjectSortedMap.this.clear(); }
	}
	/** A wrapper exhibiting a map iterator as an iterator on values.
	 *
	 * <p>To provide an iterator on values, just create an instance of this
	 * class using the corresponding iterator on entries.
	 */
	protected static class ValuesIterator <V> implements ObjectIterator <V> {
	 protected final ObjectBidirectionalIterator<Long2ObjectMap.Entry <V> > i;
	 public ValuesIterator(ObjectBidirectionalIterator<Long2ObjectMap.Entry <V> > i) {
	  this.i = i;
	 }
	 @Override
	 public V next() { return i.next().getValue(); };
	 @Override
	 public boolean hasNext() { return i.hasNext(); }
	}
}
