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
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
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
public abstract class AbstractObject2ByteMap <K> extends AbstractObject2ByteFunction <K> implements Object2ByteMap <K>, java.io.Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected AbstractObject2ByteMap() {}
	@Override
	public boolean containsValue(final byte v) {
	 return values().contains(v);
	}
	@Override
	public boolean containsKey(final Object k) {
	 final ObjectIterator<Object2ByteMap.Entry <K> > i = object2ByteEntrySet().iterator();
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
	public static class BasicEntry <K> implements Object2ByteMap.Entry <K> {
	 protected K key;
	 protected byte value;
	 public BasicEntry() {}
	 public BasicEntry(final K key, final Byte value) {
	  this.key = (key);
	  this.value = (value).byteValue();
	 }
	 public BasicEntry(final K key, final byte value) {
	  this.key = key;
	  this.value = value;
	 }
	 @Override
	 public K getKey() {
	  return key;
	 }
	 @Override
	 public byte getByteValue() {
	  return value;
	 }
	 @Override
	 public byte setValue(final byte value) {
	  throw new UnsupportedOperationException();
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public boolean equals(final Object o) {
	  if (!(o instanceof Map.Entry)) return false;
	  if (o instanceof Object2ByteMap.Entry) {
	   final Object2ByteMap.Entry <K> e = (Object2ByteMap.Entry <K>) o;
	   return java.util.Objects.equals(key, e.getKey()) && ( (value) == (e.getByteValue()) );
	  }
	  final Map.Entry<?,?> e = (Map.Entry<?,?>)o;
	  final Object key = e.getKey();
	  final Object value = e.getValue();
	  if (value == null || !(value instanceof Byte)) return false;
	  return java.util.Objects.equals(this.key, (key)) && ( (this.value) == (((Byte)(value)).byteValue()) );
	 }
	 @Override
	 public int hashCode() {
	  return ( (key) == null ? 0 : (key).hashCode() ) ^ (value);
	 }
	 @Override
	 public String toString() {
	  return key + "->" + value;
	 }
	}
	/** This class provides a basic implementation for an Entry set which forwards some queries to the map.
	 */
	public abstract static class BasicEntrySet <K> extends AbstractObjectSet<Entry <K> > {
	 protected final Object2ByteMap <K> map;
	 public BasicEntrySet(final Object2ByteMap <K> map) {
	  this.map = map;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public boolean contains(final Object o) {
	  if (!(o instanceof Map.Entry)) return false;
	  if (o instanceof Object2ByteMap.Entry) {
	   final Object2ByteMap.Entry <K> e = (Object2ByteMap.Entry <K>) o;
	   final K k = e.getKey();
	   return map.containsKey(k) && ( (map.getByte(k)) == (e.getByteValue()) );
	  }
	  final Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
	  final Object k = e.getKey();
	  final Object value = e.getValue();
	  if (value == null || !(value instanceof Byte)) return false;
	  return map.containsKey(k) && ( (map.getByte(k)) == (((Byte)(value)).byteValue()) );
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public boolean remove(final Object o) {
	  if (!(o instanceof Map.Entry)) return false;
	  if (o instanceof Object2ByteMap.Entry) {
	   final Object2ByteMap.Entry <K> e = (Object2ByteMap.Entry <K>) o;
	   return map.remove(e.getKey(), e.getByteValue());
	  }
	  Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
	  final Object k = e.getKey();
	  final Object value = e.getValue();
	  if (value == null || !(value instanceof Byte)) return false;
	  final byte v = ((Byte)(value)).byteValue();
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
	   public int size() { return AbstractObject2ByteMap.this.size(); }
	   @Override
	   public void clear() { AbstractObject2ByteMap.this.clear(); }
	   @Override
	   public ObjectIterator <K> iterator() {
	    return new ObjectIterator <K>() {
	      private final ObjectIterator<Object2ByteMap.Entry <K> > i = Object2ByteMaps.fastIterator(AbstractObject2ByteMap.this);
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
	public ByteCollection values() {
	 return new AbstractByteCollection () {
	   @Override
	   public boolean contains(final byte k) { return containsValue(k); }
	   @Override
	   public int size() { return AbstractObject2ByteMap.this.size(); }
	   @Override
	   public void clear() { AbstractObject2ByteMap.this.clear(); }
	   @Override
	   public ByteIterator iterator() {
	    return new ByteIterator () {
	      private final ObjectIterator<Object2ByteMap.Entry <K> > i = Object2ByteMaps.fastIterator(AbstractObject2ByteMap.this);
	      @Override
	      public byte nextByte() { return i.next().getByteValue(); }
	      @Override
	      public boolean hasNext() { return i.hasNext(); }
	     };
	   }
	  };
	}
	/** {@inheritDoc} */
	@SuppressWarnings({"unchecked","deprecation"})
	@Override
	public void putAll(final Map<? extends K,? extends Byte> m) {
	 if (m instanceof Object2ByteMap) {
	  ObjectIterator<Object2ByteMap.Entry <K> > i = Object2ByteMaps.fastIterator((Object2ByteMap <K>) m);
	  while (i.hasNext()) {
	   final Object2ByteMap.Entry <? extends K> e = i.next();
	   put(e.getKey(), e.getByteValue());
	  }
	 } else {
	  int n = m.size();
	  final Iterator<? extends Map.Entry<? extends K,? extends Byte>> i = m.entrySet().iterator();
	  Map.Entry<? extends K,? extends Byte> e;
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
	 final ObjectIterator<Object2ByteMap.Entry <K> > i = Object2ByteMaps.fastIterator(this);
	 while(n-- != 0) h += i.next().hashCode();
	 return h;
	}
	@Override
	public boolean equals(Object o) {
	 if (o == this) return true;
	 if (! (o instanceof Map)) return false;
	 final Map<?,?> m = (Map<?,?>)o;
	 if (m.size() != size()) return false;
	 return object2ByteEntrySet().containsAll(m.entrySet());
	}
	@Override
	public String toString() {
	 final StringBuilder s = new StringBuilder();
	 final ObjectIterator<Object2ByteMap.Entry <K> > i = Object2ByteMaps.fastIterator(this);
	 int n = size();
	 Object2ByteMap.Entry <K> e;
	 boolean first = true;
	 s.append("{");
	 while(n-- != 0) {
	  if (first) first = false;
	  else s.append(", ");
	  e = i.next();
	  if (this == e.getKey()) s.append("(this map)"); else
	   s.append(String.valueOf(e.getKey()));
	  s.append("=>");
	   s.append(String.valueOf(e.getByteValue()));
	 }
	 s.append("}");
	 return s.toString();
	}
}
