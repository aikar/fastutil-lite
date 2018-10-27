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
package it.unimi.dsi.fastutil.chars;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
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
public abstract class AbstractChar2ObjectMap <V> extends AbstractChar2ObjectFunction <V> implements Char2ObjectMap <V>, java.io.Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected AbstractChar2ObjectMap() {}
	@Override
	public boolean containsValue(final Object v) {
	 return values().contains(v);
	}
	@Override
	public boolean containsKey(final char k) {
	 final ObjectIterator<Char2ObjectMap.Entry <V> > i = char2ObjectEntrySet().iterator();
	 while(i.hasNext())
	  if (i.next().getCharKey() == k)
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
	public static class BasicEntry <V> implements Char2ObjectMap.Entry <V> {
	 protected char key;
	 protected V value;
	 public BasicEntry() {}
	 public BasicEntry(final Character key, final V value) {
	  this.key = (key).charValue();
	  this.value = (value);
	 }
	 public BasicEntry(final char key, final V value) {
	  this.key = key;
	  this.value = value;
	 }
	 @Override
	 public char getCharKey() {
	  return key;
	 }
	 @Override
	 public V getValue() {
	  return value;
	 }
	 @Override
	 public V setValue(final V value) {
	  throw new UnsupportedOperationException();
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public boolean equals(final Object o) {
	  if (!(o instanceof Map.Entry)) return false;
	  if (o instanceof Char2ObjectMap.Entry) {
	   final Char2ObjectMap.Entry <V> e = (Char2ObjectMap.Entry <V>) o;
	   return ( (key) == (e.getCharKey()) ) && java.util.Objects.equals(value, e.getValue());
	  }
	  final Map.Entry<?,?> e = (Map.Entry<?,?>)o;
	  final Object key = e.getKey();
	  if (key == null || !(key instanceof Character)) return false;
	  final Object value = e.getValue();
	  return ( (this.key) == (((Character)(key)).charValue()) ) && java.util.Objects.equals(this.value, (value));
	 }
	 @Override
	 public int hashCode() {
	  return (key) ^ ( (value) == null ? 0 : (value).hashCode() );
	 }
	 @Override
	 public String toString() {
	  return key + "->" + value;
	 }
	}
	/** This class provides a basic implementation for an Entry set which forwards some queries to the map.
	 */
	public abstract static class BasicEntrySet <V> extends AbstractObjectSet<Entry <V> > {
	 protected final Char2ObjectMap <V> map;
	 public BasicEntrySet(final Char2ObjectMap <V> map) {
	  this.map = map;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public boolean contains(final Object o) {
	  if (!(o instanceof Map.Entry)) return false;
	  if (o instanceof Char2ObjectMap.Entry) {
	   final Char2ObjectMap.Entry <V> e = (Char2ObjectMap.Entry <V>) o;
	   final char k = e.getCharKey();
	   return map.containsKey(k) && java.util.Objects.equals(map.get(k), e.getValue());
	  }
	  final Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
	  final Object key = e.getKey();
	  if (key == null || !(key instanceof Character)) return false;
	  final char k = ((Character)(key)).charValue();
	  final Object value = e.getValue();
	  return map.containsKey(k) && java.util.Objects.equals(map.get(k), (value));
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public boolean remove(final Object o) {
	  if (!(o instanceof Map.Entry)) return false;
	  if (o instanceof Char2ObjectMap.Entry) {
	   final Char2ObjectMap.Entry <V> e = (Char2ObjectMap.Entry <V>) o;
	   return map.remove(e.getCharKey(), e.getValue());
	  }
	  Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
	  final Object key = e.getKey();
	  if (key == null || !(key instanceof Character)) return false;
	  final char k = ((Character)(key)).charValue();
	  final Object v = e.getValue();
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
	public CharSet keySet() {
	 return new AbstractCharSet () {
	   @Override
	   public boolean contains(final char k) { return containsKey(k); }
	   @Override
	   public int size() { return AbstractChar2ObjectMap.this.size(); }
	   @Override
	   public void clear() { AbstractChar2ObjectMap.this.clear(); }
	   @Override
	   public CharIterator iterator() {
	    return new CharIterator () {
	      private final ObjectIterator<Char2ObjectMap.Entry <V> > i = Char2ObjectMaps.fastIterator(AbstractChar2ObjectMap.this);
	      @Override
	      public char nextChar() { return i.next().getCharKey(); };
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
	public ObjectCollection <V> values() {
	 return new AbstractObjectCollection <V>() {
	   @Override
	   public boolean contains(final Object k) { return containsValue(k); }
	   @Override
	   public int size() { return AbstractChar2ObjectMap.this.size(); }
	   @Override
	   public void clear() { AbstractChar2ObjectMap.this.clear(); }
	   @Override
	   public ObjectIterator <V> iterator() {
	    return new ObjectIterator <V>() {
	      private final ObjectIterator<Char2ObjectMap.Entry <V> > i = Char2ObjectMaps.fastIterator(AbstractChar2ObjectMap.this);
	      @Override
	      public V next() { return i.next().getValue(); }
	      @Override
	      public boolean hasNext() { return i.hasNext(); }
	     };
	   }
	  };
	}
	/** {@inheritDoc} */
	@SuppressWarnings({"unchecked","deprecation"})
	@Override
	public void putAll(final Map<? extends Character,? extends V> m) {
	 if (m instanceof Char2ObjectMap) {
	  ObjectIterator<Char2ObjectMap.Entry <V> > i = Char2ObjectMaps.fastIterator((Char2ObjectMap <V>) m);
	  while (i.hasNext()) {
	   final Char2ObjectMap.Entry <? extends V> e = i.next();
	   put(e.getCharKey(), e.getValue());
	  }
	 } else {
	  int n = m.size();
	  final Iterator<? extends Map.Entry<? extends Character,? extends V>> i = m.entrySet().iterator();
	  Map.Entry<? extends Character,? extends V> e;
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
	 final ObjectIterator<Char2ObjectMap.Entry <V> > i = Char2ObjectMaps.fastIterator(this);
	 while(n-- != 0) h += i.next().hashCode();
	 return h;
	}
	@Override
	public boolean equals(Object o) {
	 if (o == this) return true;
	 if (! (o instanceof Map)) return false;
	 final Map<?,?> m = (Map<?,?>)o;
	 if (m.size() != size()) return false;
	 return char2ObjectEntrySet().containsAll(m.entrySet());
	}
	@Override
	public String toString() {
	 final StringBuilder s = new StringBuilder();
	 final ObjectIterator<Char2ObjectMap.Entry <V> > i = Char2ObjectMaps.fastIterator(this);
	 int n = size();
	 Char2ObjectMap.Entry <V> e;
	 boolean first = true;
	 s.append("{");
	 while(n-- != 0) {
	  if (first) first = false;
	  else s.append(", ");
	  e = i.next();
	   s.append(String.valueOf(e.getCharKey()));
	  s.append("=>");
	  if (this == e.getValue()) s.append("(this map)"); else
	   s.append(String.valueOf(e.getValue()));
	 }
	 s.append("}");
	 return s.toString();
	}
}
