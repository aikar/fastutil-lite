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
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import java.util.Iterator;
import java.util.Map;
/** An abstract class providing basic methods for maps implementing a type-specific interface.
	*
	* <p>Optional operations just throw an {@link
	* UnsupportedOperationException}. Generic versions of accessors delegate to
	* the corresponding type-specific counterparts following the interface rules
	* (they take care of returning {@code null} on a missing key).
	*
	* <p>As a further help, this class provides a {@link BasicEntry BasicEntry} inner class
	* that implements a type-specific version of {@link java.util.Map.Entry}; it
	* is particularly useful for those classes that do not implement their own
	* entries (e.g., most immutable maps).
	*/
public abstract class AbstractObject2DoubleMap <K> extends AbstractObject2DoubleFunction <K> implements Object2DoubleMap <K>, java.io.Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected AbstractObject2DoubleMap() {}
	@Override
	public boolean containsValue(final double v) {
	 return values().contains(v);
	}
	@Override
	public boolean containsKey(final Object k) {
	 final ObjectIterator<Object2DoubleMap.Entry <K> > i = object2DoubleEntrySet().iterator();
	 while(i.hasNext())
	  if (i.next().getKey() == k)
	   return true;
	 return false;
	}
	@Override
	public boolean isEmpty() {
	 return size() == 0;
	}
	/** This class provides a basic but complete type-specific entry class for all those maps implementations
	 * that do not have entries on their own (e.g., most immutable maps).
	 *
	 * <p>This class does not implement {@link java.util.Map.Entry#setValue(Object) setValue()}, as the modification
	 * would not be reflected in the base map.
	 */
	public static class BasicEntry <K> implements Object2DoubleMap.Entry <K> {
	 protected K key;
	 protected double value;
	 public BasicEntry() {}
	 public BasicEntry(final K key, final Double value) {
	  this.key = (key);
	  this.value = (value).doubleValue();
	 }
	 public BasicEntry(final K key, final double value) {
	  this.key = key;
	  this.value = value;
	 }
	 @Override
	 public K getKey() {
	  return key;
	 }
	 @Override
	 public double getDoubleValue() {
	  return value;
	 }
	 @Override
	 public double setValue(final double value) {
	  throw new UnsupportedOperationException();
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public boolean equals(final Object o) {
	  if (!(o instanceof Map.Entry)) return false;
	  if (o instanceof Object2DoubleMap.Entry) {
	   final Object2DoubleMap.Entry <K> e = (Object2DoubleMap.Entry <K>) o;
	   return java.util.Objects.equals(key, e.getKey()) && ( Double.doubleToLongBits(value) == Double.doubleToLongBits(e.getDoubleValue()) );
	  }
	  final Map.Entry<?,?> e = (Map.Entry<?,?>)o;
	  final Object key = e.getKey();
	  final Object value = e.getValue();
	  if (value == null || !(value instanceof Double)) return false;
	  return java.util.Objects.equals(this.key, (key)) && ( Double.doubleToLongBits(this.value) == Double.doubleToLongBits(((Double)(value)).doubleValue()) );
	 }
	 @Override
	 public int hashCode() {
	  return ( (key) == null ? 0 : (key).hashCode() ) ^ it.unimi.dsi.fastutil.HashCommon.double2int(value);
	 }
	 @Override
	 public String toString() {
	  return key + "->" + value;
	 }
	}
	/** This class provides a basic implementation for an Entry set which forwards some queries to the map.
	 */
	public abstract static class BasicEntrySet <K> extends AbstractObjectSet<Entry <K> > {
	 protected final Object2DoubleMap <K> map;
	 public BasicEntrySet(final Object2DoubleMap <K> map) {
	  this.map = map;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public boolean contains(final Object o) {
	  if (!(o instanceof Map.Entry)) return false;
	  if (o instanceof Object2DoubleMap.Entry) {
	   final Object2DoubleMap.Entry <K> e = (Object2DoubleMap.Entry <K>) o;
	   final K k = e.getKey();
	   return map.containsKey(k) && ( Double.doubleToLongBits(map.getDouble(k)) == Double.doubleToLongBits(e.getDoubleValue()) );
	  }
	  final Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
	  final Object k = e.getKey();
	  final Object value = e.getValue();
	  if (value == null || !(value instanceof Double)) return false;
	  return map.containsKey(k) && ( Double.doubleToLongBits(map.getDouble(k)) == Double.doubleToLongBits(((Double)(value)).doubleValue()) );
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public boolean remove(final Object o) {
	  if (!(o instanceof Map.Entry)) return false;
	  if (o instanceof Object2DoubleMap.Entry) {
	   final Object2DoubleMap.Entry <K> e = (Object2DoubleMap.Entry <K>) o;
	   return map.remove(e.getKey(), e.getDoubleValue());
	  }
	  Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
	  final Object k = e.getKey();
	  final Object value = e.getValue();
	  if (value == null || !(value instanceof Double)) return false;
	  final double v = ((Double)(value)).doubleValue();
	  return map.remove(k, v);
	 }
	 @Override
	 public int size() {
	  return map.size();
	 }
	}
	/** Returns a type-specific-set view of the keys of this map.
	 *
	 * <p>The view is backed by the set returned by {@link Map#entrySet()}. Note that
	 * <em>no attempt is made at caching the result of this method</em>, as this would
	 * require adding some attributes that lightweight implementations would
	 * not need. Subclasses may easily override this policy by calling
	 * this method and caching the result, but implementors are encouraged to
	 * write more efficient ad-hoc implementations.
	 *
	 * @return a set view of the keys of this map; it may be safely cast to a type-specific interface.
	 */
	@Override
	public ObjectSet <K> keySet() {
	 return new AbstractObjectSet <K>() {
	   @Override
	   public boolean contains(final Object k) { return containsKey(k); }
	   @Override
	   public int size() { return AbstractObject2DoubleMap.this.size(); }
	   @Override
	   public void clear() { AbstractObject2DoubleMap.this.clear(); }
	   @Override
	   public ObjectIterator <K> iterator() {
	    return new ObjectIterator <K>() {
	      private final ObjectIterator<Object2DoubleMap.Entry <K> > i = Object2DoubleMaps.fastIterator(AbstractObject2DoubleMap.this);
	      @Override
	      public K next() { return i.next().getKey(); };
	      @Override
	      public boolean hasNext() { return i.hasNext(); }
	      @Override
	      public void remove() { i.remove(); }
	     };
	   }
	  };
	}
	/** Returns a type-specific-set view of the values of this map.
	 *
	 * <p>The view is backed by the set returned by {@link Map#entrySet()}. Note that
	 * <em>no attempt is made at caching the result of this method</em>, as this would
	 * require adding some attributes that lightweight implementations would
	 * not need. Subclasses may easily override this policy by calling
	 * this method and caching the result, but implementors are encouraged to
	 * write more efficient ad-hoc implementations.
	 *
	 * @return a set view of the values of this map; it may be safely cast to a type-specific interface.
	 */
	@Override
	public DoubleCollection values() {
	 return new AbstractDoubleCollection () {
	   @Override
	   public boolean contains(final double k) { return containsValue(k); }
	   @Override
	   public int size() { return AbstractObject2DoubleMap.this.size(); }
	   @Override
	   public void clear() { AbstractObject2DoubleMap.this.clear(); }
	   @Override
	   public DoubleIterator iterator() {
	    return new DoubleIterator () {
	      private final ObjectIterator<Object2DoubleMap.Entry <K> > i = Object2DoubleMaps.fastIterator(AbstractObject2DoubleMap.this);
	      @Override
	      public double nextDouble() { return i.next().getDoubleValue(); }
	      @Override
	      public boolean hasNext() { return i.hasNext(); }
	     };
	   }
	  };
	}
	/** {@inheritDoc} */
	@SuppressWarnings({"unchecked","deprecation"})
	@Override
	public void putAll(final Map<? extends K,? extends Double> m) {
	 if (m instanceof Object2DoubleMap) {
	  ObjectIterator<Object2DoubleMap.Entry <K> > i = Object2DoubleMaps.fastIterator((Object2DoubleMap <K>) m);
	  while (i.hasNext()) {
	   final Object2DoubleMap.Entry <? extends K> e = i.next();
	   put(e.getKey(), e.getDoubleValue());
	  }
	 } else {
	  int n = m.size();
	  final Iterator<? extends Map.Entry<? extends K,? extends Double>> i = m.entrySet().iterator();
	  Map.Entry<? extends K,? extends Double> e;
	  while (n-- != 0) {
	   e = i.next();
	   put(e.getKey(), e.getValue());
	  }
	 }
	}
	/** Returns a hash code for this map.
	 *
	 * The hash code of a map is computed by summing the hash codes of its entries.
	 *
	 * @return a hash code for this map.
	 */
	@Override
	public int hashCode() {
	 int h = 0, n = size();
	 final ObjectIterator<Object2DoubleMap.Entry <K> > i = Object2DoubleMaps.fastIterator(this);
	 while(n-- != 0) h += i.next().hashCode();
	 return h;
	}
	@Override
	public boolean equals(Object o) {
	 if (o == this) return true;
	 if (! (o instanceof Map)) return false;
	 final Map<?,?> m = (Map<?,?>)o;
	 if (m.size() != size()) return false;
	 return object2DoubleEntrySet().containsAll(m.entrySet());
	}
	@Override
	public String toString() {
	 final StringBuilder s = new StringBuilder();
	 final ObjectIterator<Object2DoubleMap.Entry <K> > i = Object2DoubleMaps.fastIterator(this);
	 int n = size();
	 Object2DoubleMap.Entry <K> e;
	 boolean first = true;
	 s.append("{");
	 while(n-- != 0) {
	  if (first) first = false;
	  else s.append(", ");
	  e = i.next();
	  if (this == e.getKey()) s.append("(this map)"); else
	   s.append(String.valueOf(e.getKey()));
	  s.append("=>");
	   s.append(String.valueOf(e.getDoubleValue()));
	 }
	 s.append("}");
	 return s.toString();
	}
}
