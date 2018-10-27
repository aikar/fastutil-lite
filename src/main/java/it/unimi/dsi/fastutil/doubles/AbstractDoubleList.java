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
package it.unimi.dsi.fastutil.doubles;
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Collection;
import java.util.NoSuchElementException;
/**  An abstract class providing basic methods for lists implementing a type-specific list interface.
	*
	* <p>As an additional bonus, this class implements on top of the list operations a type-specific stack.
	*/
public abstract class AbstractDoubleList extends AbstractDoubleCollection implements DoubleList , DoubleStack {
	protected AbstractDoubleList() {}
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
	public void add(final int index, final double k) {
	 throw new UnsupportedOperationException();
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific version of {@link List#add(int, Object)}.
	 */
	@Override
	public boolean add(final double k) {
	 add(size(), k);
	 return true;
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation always throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public double removeDouble(final int i) {
	 throw new UnsupportedOperationException();
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation always throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public double set(final int index, final double k) {
	 throw new UnsupportedOperationException();
	}
	/** Adds all of the elements in the specified collection to this list (optional operation). */
	@Override
	public boolean addAll(int index, final Collection<? extends Double> c) {
	 ensureIndex(index);
	 final Iterator<? extends Double> i = c.iterator();
	 final boolean retVal = i.hasNext();
	 while(i.hasNext()) add(index++, (i.next()).doubleValue());
	 return retVal;
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific version of {@link List#addAll(int, Collection)}.
	 */
	@Override
	public boolean addAll(final Collection<? extends Double> c) {
	 return addAll(size(), c);
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to {@link #listIterator()}.
	 */
	@Override
	public DoubleListIterator iterator() {
	 return listIterator();
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to {@link #listIterator(int) listIterator(0)}.
	 */
	@Override
	public DoubleListIterator listIterator() {
	 return listIterator(0);
	}
	/** {@inheritDoc}
	 * <p>This implementation is based on the random-access methods. */
	@Override
	public DoubleListIterator listIterator(final int index) {
	 ensureIndex(index);
	 return new DoubleListIterator () {
	   int pos = index, last = -1;
	   @Override
	   public boolean hasNext() { return pos < AbstractDoubleList.this.size(); }
	   @Override
	   public boolean hasPrevious() { return pos > 0; }
	   @Override
	   public double nextDouble() { if (! hasNext()) throw new NoSuchElementException(); return AbstractDoubleList.this.getDouble(last = pos++); }
	   @Override
	   public double previousDouble() { if (! hasPrevious()) throw new NoSuchElementException(); return AbstractDoubleList.this.getDouble(last = --pos); }
	   @Override
	   public int nextIndex() { return pos; }
	   @Override
	   public int previousIndex() { return pos - 1; }
	   @Override
	   public void add(final double k) {
	    AbstractDoubleList.this.add(pos++, k);
	    last = -1;
	   }
	   @Override
	   public void set(final double k) {
	    if (last == -1) throw new IllegalStateException();
	    AbstractDoubleList.this.set(last, k);
	   }
	   @Override
	   public void remove() {
	    if (last == -1) throw new IllegalStateException();
	    AbstractDoubleList.this.removeDouble(last);
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
	public boolean contains(final double k) {
	 return indexOf(k) >= 0;
	}
	@Override
	public int indexOf(final double k) {
	 final DoubleListIterator i = listIterator();
	 double e;
	 while(i.hasNext()) {
	  e = i.nextDouble();
	  if (( Double.doubleToLongBits(k) == Double.doubleToLongBits(e) )) return i.previousIndex();
	 }
	 return -1;
	}
	@Override
	public int lastIndexOf(final double k) {
	 DoubleListIterator i = listIterator(size());
	 double e;
	 while(i.hasPrevious()) {
	  e = i.previousDouble();
	  if (( Double.doubleToLongBits(k) == Double.doubleToLongBits(e) )) return i.nextIndex();
	 }
	 return -1;
	}
	@Override
	public void size(final int size) {
	 int i = size();
	 if (size > i) while(i++ < size) add((0));
	 else while(i-- != size) removeDouble(i);
	}
	@Override
	public DoubleList subList(final int from, final int to) {
	 ensureIndex(from);
	 ensureIndex(to);
	 if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
	 return new DoubleSubList (this, from, to);
	}
	/** {@inheritDoc}
	 *
	 * <p>This is a trivial iterator-based implementation. It is expected that
	 * implementations will override this method with a more optimized version.
	 */
	@Override
	public void removeElements(final int from, final int to) {
	 ensureIndex(to);
	 DoubleListIterator i = listIterator(from);
	 int n = to - from;
	 if (n < 0) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
	 while(n-- != 0) {
	  i.nextDouble();
	  i.remove();
	 }
	}
	/** {@inheritDoc}
	 *
	 * <p>This is a trivial iterator-based implementation. It is expected that
	 * implementations will override this method with a more optimized version.
	 */
	@Override
	public void addElements(int index, final double a[], int offset, int length) {
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
	public void addElements(final int index, final double a[]) {
	 addElements(index, a, 0, a.length);
	}
	/** {@inheritDoc}
	 *
	 * <p>This is a trivial iterator-based implementation. It is expected that
	 * implementations will override this method with a more optimized version.
	 */
	@Override
	public void getElements(final int from, final double a[], int offset, int length) {
	 DoubleListIterator i = listIterator(from);
	 if (offset < 0) throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
	 if (offset + length > a.length) throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
	 if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")");
	 while(length-- != 0) a[offset++] = i.nextDouble();
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
	 DoubleIterator i = iterator();
	 int h = 1, s = size();
	 while (s-- != 0) {
	  double k = i.nextDouble();
	  h = 31 * h + it.unimi.dsi.fastutil.HashCommon.double2int(k);
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
	 if (l instanceof DoubleList) {
	  final DoubleListIterator i1 = listIterator(), i2 = ((DoubleList )l).listIterator();
	  while(s-- != 0) if (i1.nextDouble() != i2.nextDouble()) return false;
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
	public int compareTo(final List<? extends Double> l) {
	 if (l == this) return 0;
	 if (l instanceof DoubleList) {
	  final DoubleListIterator i1 = listIterator(), i2 = ((DoubleList )l).listIterator();
	  int r;
	  double e1, e2;
	  while(i1.hasNext() && i2.hasNext()) {
	   e1 = i1.nextDouble();
	   e2 = i2.nextDouble();
	   if ((r = ( Double.compare((e1),(e2)) )) != 0) return r;
	  }
	  return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
	 }
	 ListIterator<? extends Double> i1 = listIterator(), i2 = l.listIterator();
	 int r;
	 while(i1.hasNext() && i2.hasNext()) {
	  if ((r = ((Comparable<? super Double>)i1.next()).compareTo(i2.next())) != 0) return r;
	 }
	 return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
	}
	@Override
	public void push(final double o) {
	 add(o);
	}
	@Override
	public double popDouble() {
	 if (isEmpty()) throw new NoSuchElementException();
	 return removeDouble(size() - 1);
	}
	@Override
	public double topDouble() {
	 if (isEmpty()) throw new NoSuchElementException();
	 return getDouble(size() - 1);
	}
	@Override
	public double peekDouble(final int i) {
	 return getDouble(size() - 1 - i);
	}
	/** Removes a single instance of the specified element from this collection, if it is present (optional operation).
	 * <p>This implementation delegates to {@code indexOf()}.
	 * @see List#remove(Object)
	 */
	@Override
	public boolean rem(final double k) {
	 int index = indexOf(k);
	 if (index == -1) return false;
	 removeDouble(index);
	 return true;
	}
	@Override
	public boolean addAll(int index, final DoubleCollection c) {
	 ensureIndex(index);
	 final DoubleIterator i = c.iterator();
	 final boolean retVal = i.hasNext();
	 while(i.hasNext()) add(index++, i.nextDouble());
	 return retVal;
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific version of {@link List#addAll(int, Collection)}.
	 */
	@Override
	public boolean addAll(final int index, final DoubleList l) {
	 return addAll(index, (DoubleCollection)l);
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific version of {@link List#addAll(int, Collection)}.
	 */
	@Override
	public boolean addAll(final DoubleCollection c) {
	 return addAll(size(), c);
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific list version of {@link List#addAll(int, Collection)}.
	 */
	@Override
	public boolean addAll(final DoubleList l) {
	 return addAll(size(), l);
	}
	@Override
	public String toString() {
	 final StringBuilder s = new StringBuilder();
	 final DoubleIterator i = iterator();
	 int n = size();
	 double k;
	 boolean first = true;
	 s.append("[");
	 while(n-- != 0) {
	  if (first) first = false;
	  else s.append(", ");
	  k = i.nextDouble();
	   s.append(String.valueOf(k));
	 }
	 s.append("]");
	 return s.toString();
	}
	/** A class implementing a sublist view. */
	public static class DoubleSubList extends AbstractDoubleList implements java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 /** The list this sublist restricts. */
	 protected final DoubleList l;
	 /** Initial (inclusive) index of this sublist. */
	 protected final int from;
	 /** Final (exclusive) index of this sublist. */
	 protected int to;
	 public DoubleSubList(final DoubleList l, final int from, final int to) {
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
	 public boolean add(final double k) {
	  l.add(to, k);
	  to++;
	  assert assertRange();
	  return true;
	 }
	 @Override
	 public void add(final int index, final double k) {
	  ensureIndex(index);
	  l.add(from + index, k);
	  to++;
	  assert assertRange();
	 }
	 @Override
	 public boolean addAll(final int index, final Collection<? extends Double> c) {
	  ensureIndex(index);
	  to += c.size();
	  return l.addAll(from + index, c);
	 }
	 @Override
	 public double getDouble(final int index) {
	  ensureRestrictedIndex(index);
	  return l.getDouble(from + index);
	 }
	 @Override
	 public double removeDouble(final int index) {
	  ensureRestrictedIndex(index);
	  to--;
	  return l.removeDouble(from + index);
	 }
	 @Override
	 public double set(final int index, final double k) {
	  ensureRestrictedIndex(index);
	  return l.set(from + index, k);
	 }
	 @Override
	 public int size() {
	  return to - from;
	 }
	 @Override
	 public void getElements(final int from, final double[] a, final int offset, final int length) {
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
	 public void addElements(int index, final double a[], int offset, int length) {
	  ensureIndex(index);
	  l.addElements(this.from + index, a, offset, length);
	  this.to += length;
	  assert assertRange();
	 }
	 @Override
	 public DoubleListIterator listIterator(final int index) {
	  ensureIndex(index);
	  return new DoubleListIterator () {
	    int pos = index, last = -1;
	    @Override
	    public boolean hasNext() { return pos < size(); }
	    @Override
	    public boolean hasPrevious() { return pos > 0; }
	    @Override
	    public double nextDouble() { if (! hasNext()) throw new NoSuchElementException(); return l.getDouble(from + (last = pos++)); }
	    @Override
	    public double previousDouble() { if (! hasPrevious()) throw new NoSuchElementException(); return l.getDouble(from + (last = --pos)); }
	    @Override
	    public int nextIndex() { return pos; }
	    @Override
	    public int previousIndex() { return pos - 1; }
	    @Override
	    public void add(double k) {
	     if (last == -1) throw new IllegalStateException();
	     DoubleSubList.this.add(pos++, k);
	     last = -1;
	     assert assertRange();
	    }
	    @Override
	    public void set(double k) {
	     if (last == -1) throw new IllegalStateException();
	     DoubleSubList.this.set(last, k);
	    }
	    @Override
	    public void remove() {
	     if (last == -1) throw new IllegalStateException();
	     DoubleSubList.this.removeDouble(last);
	     /* If the last operation was a next(), we are removing an element *before* us, and we must decrease pos correspondingly. */
	     if (last < pos) pos--;
	     last = -1;
	     assert assertRange();
	    }
	   };
	 }
	 @Override
	 public DoubleList subList(final int from, final int to) {
	  ensureIndex(from);
	  ensureIndex(to);
	  if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
	  return new DoubleSubList (this, from, to);
	 }
	 @Override
	 public boolean rem(final double k) {
	  int index = indexOf(k);
	  if (index == -1) return false;
	  to--;
	  l.removeDouble(from + index);
	  assert assertRange();
	  return true;
	 }
	 @Override
	 public boolean addAll(final int index, final DoubleCollection c) {
	  ensureIndex(index);
	  return super.addAll(index, c);
	 }
	 @Override
	 public boolean addAll(final int index, final DoubleList l) {
	  ensureIndex(index);
	  return super.addAll(index, l);
	 }
	}
}
