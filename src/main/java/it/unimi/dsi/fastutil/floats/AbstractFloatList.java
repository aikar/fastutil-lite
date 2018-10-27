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
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Collection;
import java.util.NoSuchElementException;
/**  An abstract class providing basic methods for lists implementing a type-specific list interface.
	*
	* <p>As an additional bonus, this class implements on top of the list operations a type-specific stack.
	*/
public abstract class AbstractFloatList extends AbstractFloatCollection implements FloatList , FloatStack {
	protected AbstractFloatList() {}
	/** Ensures that the given index is nonnegative and not greater than the list size.
	 *
	 * @param index an index.
	 * @throws IndexOutOfBoundsException if the given index is negative or greater than the list size.
	 */
	protected void ensureIndex(final int index) {
	 if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
	 if (index > size()) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + (size()) + ")");
	}
	/** Ensures that the given index is nonnegative and smaller than the list size.
	 *
	 * @param index an index.
	 * @throws IndexOutOfBoundsException if the given index is negative or not smaller than the list size.
	 */
	protected void ensureRestrictedIndex(final int index) {
	 if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
	 if (index >= size()) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + (size()) + ")");
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation always throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public void add(final int index, final float k) {
	 throw new UnsupportedOperationException();
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific version of {@link List#add(int, Object)}.
	 */
	@Override
	public boolean add(final float k) {
	 add(size(), k);
	 return true;
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation always throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public float removeFloat(final int i) {
	 throw new UnsupportedOperationException();
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation always throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public float set(final int index, final float k) {
	 throw new UnsupportedOperationException();
	}
	/** Adds all of the elements in the specified collection to this list (optional operation). */
	@Override
	public boolean addAll(int index, final Collection<? extends Float> c) {
	 ensureIndex(index);
	 final Iterator<? extends Float> i = c.iterator();
	 final boolean retVal = i.hasNext();
	 while(i.hasNext()) add(index++, (i.next()).floatValue());
	 return retVal;
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific version of {@link List#addAll(int, Collection)}.
	 */
	@Override
	public boolean addAll(final Collection<? extends Float> c) {
	 return addAll(size(), c);
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to {@link #listIterator()}.
	 */
	@Override
	public FloatListIterator iterator() {
	 return listIterator();
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to {@link #listIterator(int) listIterator(0)}.
	 */
	@Override
	public FloatListIterator listIterator() {
	 return listIterator(0);
	}
	/** {@inheritDoc}
	 * <p>This implementation is based on the random-access methods. */
	@Override
	public FloatListIterator listIterator(final int index) {
	 ensureIndex(index);
	 return new FloatListIterator () {
	   int pos = index, last = -1;
	   @Override
	   public boolean hasNext() { return pos < AbstractFloatList.this.size(); }
	   @Override
	   public boolean hasPrevious() { return pos > 0; }
	   @Override
	   public float nextFloat() { if (! hasNext()) throw new NoSuchElementException(); return AbstractFloatList.this.getFloat(last = pos++); }
	   @Override
	   public float previousFloat() { if (! hasPrevious()) throw new NoSuchElementException(); return AbstractFloatList.this.getFloat(last = --pos); }
	   @Override
	   public int nextIndex() { return pos; }
	   @Override
	   public int previousIndex() { return pos - 1; }
	   @Override
	   public void add(final float k) {
	    AbstractFloatList.this.add(pos++, k);
	    last = -1;
	   }
	   @Override
	   public void set(final float k) {
	    if (last == -1) throw new IllegalStateException();
	    AbstractFloatList.this.set(last, k);
	   }
	   @Override
	   public void remove() {
	    if (last == -1) throw new IllegalStateException();
	    AbstractFloatList.this.removeFloat(last);
	    /* If the last operation was a next(), we are removing an element *before* us, and we must decrease pos correspondingly. */
	    if (last < pos) pos--;
	    last = -1;
	   }
	  };
	}
	/** Returns true if this list contains the specified element.
	 * <p>This implementation delegates to {@code indexOf()}.
	 * @see List#contains(Object)
	 */
	@Override
	public boolean contains(final float k) {
	 return indexOf(k) >= 0;
	}
	@Override
	public int indexOf(final float k) {
	 final FloatListIterator i = listIterator();
	 float e;
	 while(i.hasNext()) {
	  e = i.nextFloat();
	  if (( Float.floatToIntBits(k) == Float.floatToIntBits(e) )) return i.previousIndex();
	 }
	 return -1;
	}
	@Override
	public int lastIndexOf(final float k) {
	 FloatListIterator i = listIterator(size());
	 float e;
	 while(i.hasPrevious()) {
	  e = i.previousFloat();
	  if (( Float.floatToIntBits(k) == Float.floatToIntBits(e) )) return i.nextIndex();
	 }
	 return -1;
	}
	@Override
	public void size(final int size) {
	 int i = size();
	 if (size > i) while(i++ < size) add((0));
	 else while(i-- != size) removeFloat(i);
	}
	@Override
	public FloatList subList(final int from, final int to) {
	 ensureIndex(from);
	 ensureIndex(to);
	 if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
	 return new FloatSubList (this, from, to);
	}
	/** {@inheritDoc}
	 *
	 * <p>This is a trivial iterator-based implementation. It is expected that
	 * implementations will override this method with a more optimized version.
	 */
	@Override
	public void removeElements(final int from, final int to) {
	 ensureIndex(to);
	 FloatListIterator i = listIterator(from);
	 int n = to - from;
	 if (n < 0) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
	 while(n-- != 0) {
	  i.nextFloat();
	  i.remove();
	 }
	}
	/** {@inheritDoc}
	 *
	 * <p>This is a trivial iterator-based implementation. It is expected that
	 * implementations will override this method with a more optimized version.
	 */
	@Override
	public void addElements(int index, final float a[], int offset, int length) {
	 ensureIndex(index);
	 if (offset < 0) throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
	 if (offset + length > a.length) throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
	 while(length-- != 0) add(index++, a[offset++]);
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the analogous method for array fragments.
	 */
	@Override
	public void addElements(final int index, final float a[]) {
	 addElements(index, a, 0, a.length);
	}
	/** {@inheritDoc}
	 *
	 * <p>This is a trivial iterator-based implementation. It is expected that
	 * implementations will override this method with a more optimized version.
	 */
	@Override
	public void getElements(final int from, final float a[], int offset, int length) {
	 FloatListIterator i = listIterator(from);
	 if (offset < 0) throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
	 if (offset + length > a.length) throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
	 if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")");
	 while(length-- != 0) a[offset++] = i.nextFloat();
	}
	/** {@inheritDoc}
	 * <p>This implementation delegates to {@link #removeElements(int, int)}.*/
	@Override
	public void clear() {
	 removeElements(0, size());
	}
	private boolean valEquals(final Object a, final Object b) {
	 return a == null ? b == null : a.equals(b);
	}
	/** Returns the hash code for this list, which is identical to {@link java.util.List#hashCode()}.
	 *
	 * @return the hash code for this list.
	 */
	@Override
	public int hashCode() {
	 FloatIterator i = iterator();
	 int h = 1, s = size();
	 while (s-- != 0) {
	  float k = i.nextFloat();
	  h = 31 * h + it.unimi.dsi.fastutil.HashCommon.float2int(k);
	 }
	 return h;
	}
	@Override
	public boolean equals(final Object o) {
	 if (o == this) return true;
	 if (! (o instanceof List)) return false;
	 final List<?> l = (List<?>)o;
	 int s = size();
	 if (s != l.size()) return false;
	 if (l instanceof FloatList) {
	  final FloatListIterator i1 = listIterator(), i2 = ((FloatList )l).listIterator();
	  while(s-- != 0) if (i1.nextFloat() != i2.nextFloat()) return false;
	  return true;
	 }
	 final ListIterator<?> i1 = listIterator(), i2 = l.listIterator();
	 while(s-- != 0) if (! valEquals(i1.next(), i2.next())) return false;
	 return true;
	}
	/** Compares this list to another object. If the
	 * argument is a {@link java.util.List}, this method performs a lexicographical comparison; otherwise,
	 * it throws a {@code ClassCastException}.
	 *
	 * @param l a list.
	 * @return if the argument is a {@link java.util.List}, a negative integer,
	 * zero, or a positive integer as this list is lexicographically less than, equal
	 * to, or greater than the argument.
	 * @throws ClassCastException if the argument is not a list.
	 */

	@Override
	public int compareTo(final List<? extends Float> l) {
	 if (l == this) return 0;
	 if (l instanceof FloatList) {
	  final FloatListIterator i1 = listIterator(), i2 = ((FloatList )l).listIterator();
	  int r;
	  float e1, e2;
	  while(i1.hasNext() && i2.hasNext()) {
	   e1 = i1.nextFloat();
	   e2 = i2.nextFloat();
	   if ((r = ( Float.compare((e1),(e2)) )) != 0) return r;
	  }
	  return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
	 }
	 ListIterator<? extends Float> i1 = listIterator(), i2 = l.listIterator();
	 int r;
	 while(i1.hasNext() && i2.hasNext()) {
	  if ((r = ((Comparable<? super Float>)i1.next()).compareTo(i2.next())) != 0) return r;
	 }
	 return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
	}
	@Override
	public void push(final float o) {
	 add(o);
	}
	@Override
	public float popFloat() {
	 if (isEmpty()) throw new NoSuchElementException();
	 return removeFloat(size() - 1);
	}
	@Override
	public float topFloat() {
	 if (isEmpty()) throw new NoSuchElementException();
	 return getFloat(size() - 1);
	}
	@Override
	public float peekFloat(final int i) {
	 return getFloat(size() - 1 - i);
	}
	/** Removes a single instance of the specified element from this collection, if it is present (optional operation).
	 * <p>This implementation delegates to {@code indexOf()}.
	 * @see List#remove(Object)
	 */
	@Override
	public boolean rem(final float k) {
	 int index = indexOf(k);
	 if (index == -1) return false;
	 removeFloat(index);
	 return true;
	}
	@Override
	public boolean addAll(int index, final FloatCollection c) {
	 ensureIndex(index);
	 final FloatIterator i = c.iterator();
	 final boolean retVal = i.hasNext();
	 while(i.hasNext()) add(index++, i.nextFloat());
	 return retVal;
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific version of {@link List#addAll(int, Collection)}.
	 */
	@Override
	public boolean addAll(final int index, final FloatList l) {
	 return addAll(index, (FloatCollection)l);
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific version of {@link List#addAll(int, Collection)}.
	 */
	@Override
	public boolean addAll(final FloatCollection c) {
	 return addAll(size(), c);
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific list version of {@link List#addAll(int, Collection)}.
	 */
	@Override
	public boolean addAll(final FloatList l) {
	 return addAll(size(), l);
	}
	@Override
	public String toString() {
	 final StringBuilder s = new StringBuilder();
	 final FloatIterator i = iterator();
	 int n = size();
	 float k;
	 boolean first = true;
	 s.append("[");
	 while(n-- != 0) {
	  if (first) first = false;
	  else s.append(", ");
	  k = i.nextFloat();
	   s.append(String.valueOf(k));
	 }
	 s.append("]");
	 return s.toString();
	}
	/** A class implementing a sublist view. */
	public static class FloatSubList extends AbstractFloatList implements java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 /** The list this sublist restricts. */
	 protected final FloatList l;
	 /** Initial (inclusive) index of this sublist. */
	 protected final int from;
	 /** Final (exclusive) index of this sublist. */
	 protected int to;
	 public FloatSubList(final FloatList l, final int from, final int to) {
	  this.l = l;
	  this.from = from;
	  this.to = to;
	 }
	 private boolean assertRange() {
	  assert from <= l.size();
	  assert to <= l.size();
	  assert to >= from;
	  return true;
	 }
	 @Override
	 public boolean add(final float k) {
	  l.add(to, k);
	  to++;
	  assert assertRange();
	  return true;
	 }
	 @Override
	 public void add(final int index, final float k) {
	  ensureIndex(index);
	  l.add(from + index, k);
	  to++;
	  assert assertRange();
	 }
	 @Override
	 public boolean addAll(final int index, final Collection<? extends Float> c) {
	  ensureIndex(index);
	  to += c.size();
	  return l.addAll(from + index, c);
	 }
	 @Override
	 public float getFloat(final int index) {
	  ensureRestrictedIndex(index);
	  return l.getFloat(from + index);
	 }
	 @Override
	 public float removeFloat(final int index) {
	  ensureRestrictedIndex(index);
	  to--;
	  return l.removeFloat(from + index);
	 }
	 @Override
	 public float set(final int index, final float k) {
	  ensureRestrictedIndex(index);
	  return l.set(from + index, k);
	 }
	 @Override
	 public int size() {
	  return to - from;
	 }
	 @Override
	 public void getElements(final int from, final float[] a, final int offset, final int length) {
	  ensureIndex(from);
	  if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + size() + ")");
	  l.getElements(this.from + from, a, offset, length);
	 }
	 @Override
	 public void removeElements(final int from, final int to) {
	  ensureIndex(from);
	  ensureIndex(to);
	  l.removeElements(this.from + from, this.from + to);
	  this.to -= (to - from);
	  assert assertRange();
	 }
	 @Override
	 public void addElements(int index, final float a[], int offset, int length) {
	  ensureIndex(index);
	  l.addElements(this.from + index, a, offset, length);
	  this.to += length;
	  assert assertRange();
	 }
	 @Override
	 public FloatListIterator listIterator(final int index) {
	  ensureIndex(index);
	  return new FloatListIterator () {
	    int pos = index, last = -1;
	    @Override
	    public boolean hasNext() { return pos < size(); }
	    @Override
	    public boolean hasPrevious() { return pos > 0; }
	    @Override
	    public float nextFloat() { if (! hasNext()) throw new NoSuchElementException(); return l.getFloat(from + (last = pos++)); }
	    @Override
	    public float previousFloat() { if (! hasPrevious()) throw new NoSuchElementException(); return l.getFloat(from + (last = --pos)); }
	    @Override
	    public int nextIndex() { return pos; }
	    @Override
	    public int previousIndex() { return pos - 1; }
	    @Override
	    public void add(float k) {
	     if (last == -1) throw new IllegalStateException();
	     FloatSubList.this.add(pos++, k);
	     last = -1;
	     assert assertRange();
	    }
	    @Override
	    public void set(float k) {
	     if (last == -1) throw new IllegalStateException();
	     FloatSubList.this.set(last, k);
	    }
	    @Override
	    public void remove() {
	     if (last == -1) throw new IllegalStateException();
	     FloatSubList.this.removeFloat(last);
	     /* If the last operation was a next(), we are removing an element *before* us, and we must decrease pos correspondingly. */
	     if (last < pos) pos--;
	     last = -1;
	     assert assertRange();
	    }
	   };
	 }
	 @Override
	 public FloatList subList(final int from, final int to) {
	  ensureIndex(from);
	  ensureIndex(to);
	  if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
	  return new FloatSubList (this, from, to);
	 }
	 @Override
	 public boolean rem(final float k) {
	  int index = indexOf(k);
	  if (index == -1) return false;
	  to--;
	  l.removeFloat(from + index);
	  assert assertRange();
	  return true;
	 }
	 @Override
	 public boolean addAll(final int index, final FloatCollection c) {
	  ensureIndex(index);
	  return super.addAll(index, c);
	 }
	 @Override
	 public boolean addAll(final int index, final FloatList l) {
	  ensureIndex(index);
	  return super.addAll(index, l);
	 }
	}
}
