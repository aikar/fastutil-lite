/*
	* Copyright (C) 2007-2017 Sebastiano Vigna
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
package it.unimi.dsi.fastutil.doubles;
import java.util.Map;
import java.util.NoSuchElementException;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
/** A simple, brute-force implementation of a map based on two parallel backing arrays.
	*
	* <p>The main purpose of this
	* implementation is that of wrapping cleanly the brute-force approach to the storage of a very
	* small number of pairs: just put them into two parallel arrays and scan linearly to find an item.
	*/
public class Double2ObjectArrayMap <V> extends AbstractDouble2ObjectMap <V> implements java.io.Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	/** The keys (valid up to {@link #size}, excluded). */
	private transient double[] key;
	/** The values (parallel to {@link #key}). */
	private transient Object[] value;
	/** The number of valid entries in {@link #key} and {@link #value}. */
	private int size;
	/** Creates a new empty array map with given key and value backing arrays. The resulting map will have as many entries as the given arrays.
	 *
	 * <p>It is responsibility of the caller that the elements of {@code key} are distinct.
	 *
	 * @param key the key array.
	 * @param value the value array (it <em>must</em> have the same length as {@code key}).
	 */
	public Double2ObjectArrayMap(final double[] key, final Object[] value) {
	 this.key = key;
	 this.value = value;
	 size = key.length;
	 if(key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
	}
	/** Creates a new empty array map.
	 */
	public Double2ObjectArrayMap() {
	 this.key = DoubleArrays.EMPTY_ARRAY;
	 this.value = ObjectArrays.EMPTY_ARRAY;
	}
	/** Creates a new empty array map of given capacity.
	 *
	 * @param capacity the initial capacity.
	 */
	public Double2ObjectArrayMap(final int capacity) {
	 this.key = new double[capacity];
	 this.value = new Object[capacity];
	}
	/** Creates a new empty array map copying the entries of a given map.
	 *
	 * @param m a map.
	 */
	public Double2ObjectArrayMap(final Double2ObjectMap <V> m) {
	 this(m.size());
	 putAll(m);
	}
	/** Creates a new empty array map copying the entries of a given map.
	 *
	 * @param m a map.
	 */
	public Double2ObjectArrayMap(final Map<? extends Double, ? extends V> m) {
	 this(m.size());
	 putAll(m);
	}
	/** Creates a new array map with given key and value backing arrays, using the given number of elements.
	 *
	 * <p>It is responsibility of the caller that the first {@code size} elements of {@code key} are distinct.
	 *
	 * @param key the key array.
	 * @param value the value array (it <em>must</em> have the same length as {@code key}).
	 * @param size the number of valid elements in {@code key} and {@code value}.
	 */
	public Double2ObjectArrayMap(final double[] key, final Object[] value, final int size) {
	 this.key = key;
	 this.value = value;
	 this.size = size;
	 if(key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
	 if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
	}
	private final class EntrySet extends AbstractObjectSet<Double2ObjectMap.Entry <V> > implements FastEntrySet <V> {
	 @Override
	 public ObjectIterator<Double2ObjectMap.Entry <V> > iterator() {
	  return new ObjectIterator<Double2ObjectMap.Entry <V> >() {
	   int curr = -1, next = 0;
	   @Override
	   public boolean hasNext() { return next < size; }
	   @Override
	   @SuppressWarnings("unchecked")
	   public Entry <V> next() {
	    if (! hasNext()) throw new NoSuchElementException();
	    return new AbstractDouble2ObjectMap.BasicEntry <>( key[curr = next], (V) value[next++]);
	   }
	   @Override
	   public void remove() {
	    if (curr == -1) throw new IllegalStateException();
	    curr = -1;
	    final int tail = size-- - next--;
	    System.arraycopy(key, next + 1, key, next, tail);
	    System.arraycopy(value, next + 1, value, next, tail);
	    value[size] = null;
	   }
	  };
	 }
	 @Override
	 public ObjectIterator<Double2ObjectMap.Entry <V> > fastIterator() {
	  return new ObjectIterator<Double2ObjectMap.Entry <V> >() {
	   int next = 0, curr = -1;
	   final BasicEntry <V> entry = new BasicEntry <> ();
	   @Override
	   public boolean hasNext() { return next < size; }
	   @Override
	   @SuppressWarnings("unchecked")
	   public Entry <V> next() {
	    if (! hasNext()) throw new NoSuchElementException();
	    entry.key = key[curr = next];
	    entry.value = (V) value[next++];
	    return entry;
	   }
	   @Override
	   public void remove() {
	    if (curr == -1) throw new IllegalStateException();
	    curr = -1;
	    final int tail = size-- - next--;
	    System.arraycopy(key, next + 1, key, next, tail);
	    System.arraycopy(value, next + 1, value, next, tail);
	    value[size] = null;
	   }
	  };
	 }
	 @Override
	 public int size() { return size; }
	 @Override
	
	 public boolean contains(Object o) {
	  if (! (o instanceof Map.Entry)) return false;
	  final Map.Entry<?,?> e = (Map.Entry<?,?>)o;
	  if (e.getKey() == null || ! (e.getKey() instanceof Double)) return false;
	  final double k = ((Double)( e.getKey())).doubleValue();
	  return Double2ObjectArrayMap.this.containsKey(k) && java.util.Objects.equals(Double2ObjectArrayMap.this.get(k), (e.getValue()));
	 }
	 @Override
	 @SuppressWarnings("unchecked")
	 public boolean remove(final Object o) {
	  if (!(o instanceof Map.Entry)) return false;
	  final Map.Entry<?,?> e = (Map.Entry<?,?>)o;
	  if (e.getKey() == null || ! (e.getKey() instanceof Double)) return false;
	  final double k = ((Double)( e.getKey())).doubleValue();
	  final V v = ((V) e.getValue());
	  final int oldPos = Double2ObjectArrayMap.this.findKey(k);
	  if (oldPos == -1 || ! java.util.Objects.equals(v, Double2ObjectArrayMap.this.value[oldPos])) return false;
	  final int tail = size - oldPos - 1;
	  System.arraycopy(Double2ObjectArrayMap.this.key, oldPos + 1, Double2ObjectArrayMap.this.key, oldPos, tail);
	  System.arraycopy(Double2ObjectArrayMap.this.value, oldPos + 1, Double2ObjectArrayMap.this.value, oldPos, tail);
	  Double2ObjectArrayMap.this.size--;
	  Double2ObjectArrayMap.this.value[size] = null;
	  return true;
	 }
	}
	@Override
	public FastEntrySet <V> double2ObjectEntrySet() { return new EntrySet(); }
	private int findKey(final double k) {
	 final double[] key = this.key;
	 for(int i = size; i-- != 0;) if (( Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k) )) return i;
	 return -1;
	}
	@Override
	@SuppressWarnings("unchecked")
	public V get(final double k) {
	 final double[] key = this.key;
	 for(int i = size; i-- != 0;) if (( Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k) )) return (V) value[i];
	 return defRetValue;
	}
	@Override
	public int size() { return size; }
	@Override
	public void clear() {
	 for(int i = size; i-- != 0;) {
	  value[i] = null;
	 }
	 size = 0;
	}
	@Override
	public boolean containsKey(final double k) { return findKey(k) != -1; }
	@Override
	public boolean containsValue(Object v) {
	 for(int i = size; i-- != 0;) if (java.util.Objects.equals(value[i], v)) return true;
	 return false;
	}
	@Override
	public boolean isEmpty() { return size == 0; }
	@Override
	@SuppressWarnings("unchecked")
	public V put(double k, V v) {
	 final int oldKey = findKey(k);
	 if (oldKey != -1) {
	  final V oldValue = (V) value[oldKey];
	  value[oldKey] = v;
	  return oldValue;
	 }
	 if (size == key.length) {
	  final double[] newKey = new double[size == 0 ? 2 : size * 2];
	  final Object[] newValue = new Object[size == 0 ? 2 : size * 2];
	  for(int i = size; i-- != 0;) {
	   newKey[i] = key[i];
	   newValue[i] = value[i];
	  }
	  key = newKey;
	  value = newValue;
	 }
	 key[size] = k;
	 value[size] = v;
	 size++;
	 return defRetValue;
	}
	@Override
	@SuppressWarnings("unchecked")
	public V remove(final double k) {
	 final int oldPos = findKey(k);
	 if (oldPos == -1) return defRetValue;
	 final V oldValue = (V) value[oldPos];
	 final int tail = size - oldPos - 1;
	 System.arraycopy(key, oldPos + 1, key, oldPos, tail);
	 System.arraycopy(value, oldPos + 1, value, oldPos, tail);
	 size--;
	 value[size] = null;
	 return oldValue;
	}
	@Override
	public DoubleSet keySet() {
	 return new AbstractDoubleSet () {
	  @Override
	  public boolean contains(final double k) {
	   return findKey(k) != -1;
	  }
	  @Override
	  public boolean remove(final double k) {
	    final int oldPos = findKey(k);
	    if (oldPos == -1) return false;
	    final int tail = size - oldPos - 1;
	    System.arraycopy(key, oldPos + 1, key, oldPos, tail);
	    System.arraycopy(value, oldPos + 1, value, oldPos, tail);
	    size--;
	    return true;
	  }
	  @Override
	  public DoubleIterator iterator() {
	   return new DoubleIterator () {
	    int pos = 0;
	    @Override
	    public boolean hasNext() {
	     return pos < size;
	    }
	    @Override
	   
	    public double nextDouble() {
	     if (! hasNext()) throw new NoSuchElementException();
	     return key[pos++];
	    }
	    @Override
	    public void remove() {
	     if (pos == 0) throw new IllegalStateException();
	     final int tail = size - pos;
	     System.arraycopy(key, pos, key, pos - 1, tail);
	     System.arraycopy(value, pos, value, pos - 1, tail);
	     size--;
	    }
	   };
	  }
	  @Override
	  public int size() {
	   return size;
	  }
	  @Override
	  public void clear() {
	   Double2ObjectArrayMap.this.clear();
	  }
	 };
	}
	@Override
	public ObjectCollection <V> values() {
	 return new AbstractObjectCollection <V>() {
	  @Override
	  public boolean contains(final Object v) {
	   return containsValue(v);
	  }
	  @Override
	  public it.unimi.dsi.fastutil.objects.ObjectIterator <V> iterator() {
	   return new it.unimi.dsi.fastutil.objects.ObjectIterator <V>() {
	    int pos = 0;
	    @Override
	    public boolean hasNext() {
	     return pos < size;
	    }
	    @Override
	    @SuppressWarnings("unchecked")
	    public V next() {
	     if (! hasNext()) throw new NoSuchElementException();
	     return (V) value[pos++];
	    }
	    @Override
	    public void remove() {
	     if (pos == 0) throw new IllegalStateException();
	     final int tail = size - pos;
	     System.arraycopy(key, pos, key, pos - 1, tail);
	     System.arraycopy(value, pos, value, pos - 1, tail);
	     size--;
	    }
	   };
	  }
	  @Override
	  public int size() {
	   return size;
	  }
	  @Override
	  public void clear() {
	   Double2ObjectArrayMap.this.clear();
	  }
	 };
	}
	/** Returns a deep copy of this map.
	 *
	 * <p>This method performs a deep copy of this hash map; the data stored in the
	 * map, however, is not cloned. Note that this makes a difference only for object keys.
	 *
	 *  @return a deep copy of this map.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Double2ObjectArrayMap <V> clone() {
	 Double2ObjectArrayMap <V> c;
	 try {
	  c = (Double2ObjectArrayMap <V>)super.clone();
	 }
	 catch(CloneNotSupportedException cantHappen) {
	  throw new InternalError();
	 }
	 c.key = key.clone();
	 c.value = value.clone();
	 return c;
	}
	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
	 s.defaultWriteObject();
	 for(int i = 0; i < size; i++) {
	  s.writeDouble(key[i]);
	  s.writeObject(value[i]);
	 }
	}
	private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
	 s.defaultReadObject();
	 key = new double[size];
	 value = new Object[size];
	 for(int i = 0; i < size; i++) {
	  key[i] = s.readDouble();
	  value[i] = s.readObject();
	 }
	}
}
