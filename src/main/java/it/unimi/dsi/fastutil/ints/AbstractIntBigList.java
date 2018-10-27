/*
	* Copyright (C) 2010-2017 Sebastiano Vigna
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
package it.unimi.dsi.fastutil.ints;
import java.util.Iterator;
import java.util.Collection;
import java.util.NoSuchElementException;
import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
/**  An abstract class providing basic methods for big lists implementing a type-specific big list interface. */
public abstract class AbstractIntBigList extends AbstractIntCollection implements IntBigList , IntStack {
	protected AbstractIntBigList() {}
	/** Ensures that the given index is nonnegative and not greater than this big-list size.
	 *
	 * @param index an index.
	 * @throws IndexOutOfBoundsException if the given index is negative or greater than this big-list size.
	 */
	protected void ensureIndex(final long index) {
	 if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
	 if (index > size64()) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + (size64()) + ")");
	}
	/** Ensures that the given index is nonnegative and smaller than this big-list size.
	 *
	 * @param index an index.
	 * @throws IndexOutOfBoundsException if the given index is negative or not smaller than this big-list size.
	 */
	protected void ensureRestrictedIndex(final long index) {
	 if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
	 if (index >= size64()) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + (size64()) + ")");
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation always throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public void add(final long index, final int k) {
	 throw new UnsupportedOperationException();
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific version of {@link BigList#add(long, Object)}.
	 */
	@Override
	public boolean add(final int k) {
	 add(size64(), k);
	 return true;
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation always throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public int removeInt(long i) {
	 throw new UnsupportedOperationException();
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation always throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public int set(final long index, final int k) {
	 throw new UnsupportedOperationException();
	}
	/** Adds all of the elements in the specified collection to this list (optional operation). */
	@Override
	public boolean addAll(long index, final Collection<? extends Integer> c) {
	 ensureIndex(index);
	 final Iterator<? extends Integer> i = c.iterator();
	 final boolean retVal = i.hasNext();
	 while(i.hasNext()) add(index++, i.next());
	 return retVal;
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific version of {@link BigList#addAll(long, Collection)}.
	 */
	@Override
	public boolean addAll(final Collection<? extends Integer> c) {
	 return addAll(size64(), c);
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to {@link #listIterator()}.
	 */
	@Override
	public IntBigListIterator iterator() {
	 return listIterator();
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to {@link BigList#listIterator(long) listIterator(0)}.
	 */
	@Override
	public IntBigListIterator listIterator() {
	 return listIterator(0L);
	}
	/** {@inheritDoc}
	 * <p>This implementation is based on the random-access methods. */
	@Override
	public IntBigListIterator listIterator(final long index) {
	 ensureIndex(index);
	 return new IntBigListIterator () {
	   long pos = index, last = -1;
	   @Override
	   public boolean hasNext() { return pos < AbstractIntBigList.this.size64(); }
	   @Override
	   public boolean hasPrevious() { return pos > 0; }
	   @Override
	   public int nextInt() { if (! hasNext()) throw new NoSuchElementException(); return AbstractIntBigList.this.getInt(last = pos++); }
	   @Override
	   public int previousInt() { if (! hasPrevious()) throw new NoSuchElementException(); return AbstractIntBigList.this.getInt(last = --pos); }
	   @Override
	   public long nextIndex() { return pos; }
	   @Override
	   public long previousIndex() { return pos - 1; }
	   @Override
	   public void add(int k) {
	    AbstractIntBigList.this.add(pos++, k);
	    last = -1;
	   }
	   @Override
	   public void set(int k) {
	    if (last == -1) throw new IllegalStateException();
	    AbstractIntBigList.this.set(last, k);
	   }
	   @Override
	   public void remove() {
	    if (last == -1) throw new IllegalStateException();
	    AbstractIntBigList.this.removeInt(last);
	    /* If the last operation was a next(), we are removing an element *before* us, and we must decrease pos correspondingly. */
	    if (last < pos) pos--;
	    last = -1;
	   }
	  };
	}
	/** Returns true if this list contains the specified element.
	 * <p>This implementation delegates to {@code indexOf()}.
	 * @see BigList#contains(Object)
	 */
	@Override
	public boolean contains(final int k) { return indexOf(k) >= 0; }
	@Override
	public long indexOf(final int k) {
	 final IntBigListIterator i = listIterator();
	 int e;
	 while(i.hasNext()) {
	  e = i.nextInt();
	  if (( (k) == (e) )) return i.previousIndex();
	 }
	 return -1;
	}
	@Override
	public long lastIndexOf(final int k) {
	 IntBigListIterator i = listIterator(size64());
	 int e;
	 while(i.hasPrevious()) {
	  e = i.previousInt();
	  if (( (k) == (e) )) return i.nextIndex();
	 }
	 return -1;
	}
	@Override
	public void size(final long size) {
	 long i = size64();
	 if (size > i) while(i++ < size) add((0));
	 else while(i-- != size) remove(i);
	}
	@Override
	public IntBigList subList(final long from, final long to) {
	 ensureIndex(from);
	 ensureIndex(to);
	 if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
	 return new IntSubList (this, from, to);
	}
	/** {@inheritDoc}
	 *
	 * <p>This is a trivial iterator-based implementation. It is expected that
	 * implementations will override this method with a more optimized version.
	 */
	@Override
	public void removeElements(final long from, final long to) {
	 ensureIndex(to);
	 IntBigListIterator i = listIterator(from);
	 long n = to - from;
	 if (n < 0) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
	 while(n-- != 0) {
	  i.nextInt();
	  i.remove();
	 }
	}
	/** {@inheritDoc}
	 *
	 * <p>This is a trivial iterator-based implementation. It is expected that
	 * implementations will override this method with a more optimized version.
	 */
	@Override
	public void addElements(long index, final int a[][], long offset, long length) {
	 ensureIndex(index);
	 IntBigArrays.ensureOffsetLength(a, offset, length);
	 while(length-- != 0) add(index++, IntBigArrays.get(a, offset++));
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the analogous method for big-array fragments.
	 */
	@Override
	public void addElements(final long index, final int a[][]) {
	 addElements(index, a, 0, IntBigArrays.length(a));
	}
	/** {@inheritDoc}
	 *
	 * <p>This is a trivial iterator-based implementation. It is expected that
	 * implementations will override this method with a more optimized version.
	 */
	@Override
	public void getElements(final long from, final int a[][], long offset, long length) {
	 IntBigListIterator i = listIterator(from);
	 IntBigArrays.ensureOffsetLength(a, offset, length);
	 if (from + length > size64()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size64() + ")");
	 while(length-- != 0) IntBigArrays.set(a, offset++, i.nextInt());
	}
	/** {@inheritDoc}
	 * <p>This implementation delegates to {@link #removeElements(long, long)}.*/
	@Override
	public void clear() {
	 removeElements(0, size64());
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to {@link #size64()}.
	 * @deprecated Please use {@link #size64()} instead. */
	@Override
	@Deprecated
	public int size() {
	 return (int)Math.min(Integer.MAX_VALUE, size64());
	}
	private boolean valEquals(final Object a, final Object b) { return a == null ? b == null : a.equals(b); }
	/** Returns the hash code for this big list, which is identical to {@link java.util.List#hashCode()}.
	 *
	 * @return the hash code for this big list.
	 */
	@Override
	public int hashCode() {
	 IntIterator i = iterator();
	 int h = 1;
	 long s = size64();
	 while (s-- != 0) {
	  int k = i.nextInt();
	  h = 31 * h + (k);
	 }
	 return h;
	}
	@Override
	public boolean equals(final Object o) {
	 if (o == this) return true;
	 if (! (o instanceof BigList)) return false;
	 final BigList<?> l = (BigList<?>)o;
	 long s = size64();
	 if (s != l.size64()) return false;
	 if (l instanceof IntBigList) {
	  final IntBigListIterator i1 = listIterator(), i2 = ((IntBigList )l).listIterator();
	  while(s-- != 0) if (i1.nextInt() != i2.nextInt()) return false;
	  return true;
	 }
	 final BigListIterator<?> i1 = listIterator(), i2 = l.listIterator();
	 while(s-- != 0) if (! valEquals(i1.next(), i2.next())) return false;
	 return true;
	}
	/** Compares this big list to another object. If the
	 * argument is a {@link BigList}, this method performs a lexicographical comparison; otherwise,
	 * it throws a {@code ClassCastException}.
	 *
	 * @param l a big list.
	 * @return if the argument is a {@link BigList}, a negative integer,
	 * zero, or a positive integer as this list is lexicographically less than, equal
	 * to, or greater than the argument.
	 * @throws ClassCastException if the argument is not a big list.
	 */

	@Override
	public int compareTo(final BigList<? extends Integer> l) {
	 if (l == this) return 0;
	 if (l instanceof IntBigList) {
	  final IntBigListIterator i1 = listIterator(), i2 = ((IntBigList )l).listIterator();
	  int r;
	  int e1, e2;
	  while(i1.hasNext() && i2.hasNext()) {
	   e1 = i1.nextInt();
	   e2 = i2.nextInt();
	   if ((r = ( Integer.compare((e1),(e2)) )) != 0) return r;
	  }
	  return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
	 }
	 BigListIterator<? extends Integer> i1 = listIterator(), i2 = l.listIterator();
	 int r;
	 while(i1.hasNext() && i2.hasNext()) {
	  if ((r = ((Comparable<? super Integer>)i1.next()).compareTo(i2.next())) != 0) return r;
	 }
	 return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
	}
	@Override
	public void push(int o) {
	 add(o);
	}
	@Override
	public int popInt() {
	 if (isEmpty()) throw new NoSuchElementException();
	 return removeInt(size64() - 1);
	}
	@Override
	public int topInt() {
	 if (isEmpty()) throw new NoSuchElementException();
	 return getInt(size64() - 1);
	}
	@Override
	public int peekInt(int i) {
	 return getInt(size64() - 1 - i);
	}
	/** Removes a single instance of the specified element from this collection, if it is present (optional operation).
	 * <p>This implementation delegates to {@code indexOf()}.
	 * @see BigList#remove(Object)
	 */
	@Override
	public boolean rem(int k) {
	 long index = indexOf(k);
	 if (index == -1) return false;
	 removeInt(index);
	 return true;
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific version of {@link #addAll(long, Collection)}.
	 */
	@Override
	public boolean addAll(final long index, final IntCollection c) { return addAll(index, (Collection<? extends Integer>)c); }
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific version of {@link #addAll(long, Collection)}.
	 */
	@Override
	public boolean addAll(final long index, final IntBigList l) { return addAll(index, (IntCollection)l); }
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific version of {@link #addAll(long, Collection)}.
	 */
	@Override
	public boolean addAll(final IntCollection c) { return addAll(size64(), c); }
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the type-specific list version of {@link #addAll(long, Collection)}.
	 */
	@Override
	public boolean addAll(final IntBigList l) { return addAll(size64(), l); }
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	public void add(final long index, final Integer ok) { add(index, ok.intValue()); }
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	public Integer set(final long index, final Integer ok) { return Integer.valueOf(set(index, ok.intValue())); }
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	public Integer get(final long index) { return Integer.valueOf(getInt(index)); }
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	public long indexOf(final Object ok) { return indexOf(((Integer)(ok)).intValue()); }
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	public long lastIndexOf(final Object ok) { return lastIndexOf(((Integer)(ok)).intValue()); }
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	public Integer remove(final long index) { return Integer.valueOf(removeInt(index)); }
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	public void push(Integer o) { push(o.intValue()); }
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	public Integer pop() { return Integer.valueOf(popInt()); }
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	public Integer top() { return Integer.valueOf(topInt()); }
	/** {@inheritDoc}
	 *
	 * <p>This implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	public Integer peek(int i) { return Integer.valueOf(peekInt(i)); }
	@Override
	public String toString() {
	 final StringBuilder s = new StringBuilder();
	 final IntIterator i = iterator();
	 long n = size64();
	 int k;
	 boolean first = true;
	 s.append("[");
	 while(n-- != 0) {
	  if (first) first = false;
	  else s.append(", ");
	  k = i.nextInt();
	   s.append(String.valueOf(k));
	 }
	 s.append("]");
	 return s.toString();
	}
	/** A class implementing a sublist view. */
	public static class IntSubList extends AbstractIntBigList implements java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 /** The list this sublist restricts. */
	 protected final IntBigList l;
	 /** Initial (inclusive) index of this sublist. */
	 protected final long from;
	 /** Final (exclusive) index of this sublist. */
	 protected long to;
	 public IntSubList(final IntBigList l, final long from, final long to) {
	  this.l = l;
	  this.from = from;
	  this.to = to;
	 }
	 private boolean assertRange() {
	  assert from <= l.size64();
	  assert to <= l.size64();
	  assert to >= from;
	  return true;
	 }
	 @Override
	 public boolean add(final int k) {
	  l.add(to, k);
	  to++;
	  assert assertRange();
	  return true;
	 }
	 @Override
	 public void add(final long index, final int k) {
	  ensureIndex(index);
	  l.add(from + index, k);
	  to++;
	  assert assertRange();
	 }
	 @Override
	 public boolean addAll(final long index, final Collection<? extends Integer> c) {
	  ensureIndex(index);
	  to += c.size();
	  return l.addAll(from + index, c);
	 }
	 @Override
	 public int getInt(long index) {
	  ensureRestrictedIndex(index);
	  return l.getInt(from + index);
	 }
	 @Override
	 public int removeInt(long index) {
	  ensureRestrictedIndex(index);
	  to--;
	  return l.removeInt(from + index);
	 }
	 @Override
	 public int set(long index, int k) {
	  ensureRestrictedIndex(index);
	  return l.set(from + index, k);
	 }
	 @Override
	 public long size64() { return to - from; }
	 @Override
	 public void getElements(final long from, final int[][] a, final long offset, final long length) {
	  ensureIndex(from);
	  if (from + length > size64()) throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + size64() + ")");
	  l.getElements(this.from + from, a, offset, length);
	 }
	 @Override
	 public void removeElements(final long from, final long to) {
	  ensureIndex(from);
	  ensureIndex(to);
	  l.removeElements(this.from + from, this.from + to);
	  this.to -= (to - from);
	  assert assertRange();
	 }
	 @Override
	 public void addElements(final long index, final int a[][], long offset, long length) {
	  ensureIndex(index);
	  l.addElements(this.from + index, a, offset, length);
	  this.to += length;
	  assert assertRange();
	 }
	 @Override
	 public IntBigListIterator listIterator(final long index) {
	  ensureIndex(index);
	  return new IntBigListIterator () {
	   long pos = index, last = -1;
	   @Override
	   public boolean hasNext() { return pos < size64(); }
	   @Override
	   public boolean hasPrevious() { return pos > 0; }
	   @Override
	   public int nextInt() { if (! hasNext()) throw new NoSuchElementException(); return l.getInt(from + (last = pos++)); }
	   @Override
	   public int previousInt() { if (! hasPrevious()) throw new NoSuchElementException(); return l.getInt(from + (last = --pos)); }
	   @Override
	   public long nextIndex() { return pos; }
	   @Override
	   public long previousIndex() { return pos - 1; }
	   @Override
	   public void add(int k) {
	    if (last == -1) throw new IllegalStateException();
	    IntSubList.this.add(pos++, k);
	    last = -1;
	    assert assertRange();
	   }
	   @Override
	   public void set(int k) {
	    if (last == -1) throw new IllegalStateException();
	    IntSubList.this.set(last, k);
	   }
	   @Override
	   public void remove() {
	    if (last == -1) throw new IllegalStateException();
	    IntSubList.this.removeInt(last);
	    /* If the last operation was a next(), we are removing an element *before* us, and we must decrease pos correspondingly. */
	    if (last < pos) pos--;
	    last = -1;
	    assert assertRange();
	   }
	  };
	 }
	 @Override
	 public IntBigList subList(final long from, final long to) {
	  ensureIndex(from);
	  ensureIndex(to);
	  if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
	  return new IntSubList (this, from, to);
	 }
	 @Override
	 public boolean rem(int k) {
	  long index = indexOf(k);
	  if (index == -1) return false;
	  to--;
	  l.removeInt(from + index);
	  assert assertRange();
	  return true;
	 }
	 @Override
	 public boolean addAll(final long index, final IntCollection c) {
	  ensureIndex(index);
	  return super.addAll(index, c);
	 }
	 @Override
	 public boolean addAll(final long index, final IntBigList l) {
	  ensureIndex(index);
	  return super.addAll(index, l);
	 }
	}
}
