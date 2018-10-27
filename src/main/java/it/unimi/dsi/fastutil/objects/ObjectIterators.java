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
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
/** A class providing static methods and objects that do useful things with type-specific iterators.
	*
	* @see Iterator
	*/
public final class ObjectIterators {
	private ObjectIterators() {}
	/** A class returning no elements and a type-specific iterator interface.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific iterator.
	 */
	public static class EmptyIterator <K> implements ObjectListIterator <K>, java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptyIterator() {}
	 @Override
	 public boolean hasNext() { return false; }
	 @Override
	 public boolean hasPrevious() { return false; }
	 @Override
	 public K next() { throw new NoSuchElementException(); }
	 @Override
	 public K previous() { throw new NoSuchElementException(); }
	 @Override
	 public int nextIndex() { return 0; }
	 @Override
	 public int previousIndex() { return -1; }
	 @Override
	 public int skip(int n) { return 0; };
	 @Override
	 public int back(int n) { return 0; };
	 @Override
	 public Object clone() { return EMPTY_ITERATOR; }
	 private Object readResolve() { return EMPTY_ITERATOR; }
	}
	/** An empty iterator. It is serializable and cloneable.
	 *
	 * <p>The class of this objects represent an abstract empty iterator
	 * that can iterate as a type-specific (list) iterator.
	 */
	@SuppressWarnings("rawtypes")
	public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
	/** Returns an empty iterator. It is serializable and cloneable.
	 *
	 * <p>The class of the object returned represent an abstract empty iterator
	 * that can iterate as a type-specific (list) iterator.
	 *
	 * <p>This method provides a typesafe access to {@link #EMPTY_ITERATOR}.
	 * @return an empty iterator.
	 */
	@SuppressWarnings("unchecked")
	public static <K> ObjectIterator <K> emptyIterator() { return EMPTY_ITERATOR; }
	/** An iterator returning a single element. */
	private static class SingletonIterator <K> implements ObjectListIterator <K> {
	 private final K element;
	 private int curr;
	 public SingletonIterator(final K element) {
	  this.element = element;
	 }
	 @Override
	 public boolean hasNext() { return curr == 0; }
	 @Override
	 public boolean hasPrevious() { return curr == 1; }
	 @Override
	 public K next() {
	  if (! hasNext()) throw new NoSuchElementException();
	  curr = 1;
	  return element;
	 }
	 @Override
	 public K previous() {
	  if (! hasPrevious()) throw new NoSuchElementException();
	  curr = 0;
	  return element;
	 }
	 @Override
	 public int nextIndex() {
	  return curr;
	 }
	 @Override
	 public int previousIndex() {
	  return curr - 1;
	 }
	}
	/** Returns an immutable iterator that iterates just over the given element.
	 *
	 * @param element the only element to be returned by a type-specific list iterator.
	 * @return an immutable iterator that iterates just over {@code element}.
	 */
	public static <K> ObjectListIterator <K> singleton(final K element) {
	 return new SingletonIterator <>(element);
	}
	/** A class to wrap arrays in iterators. */
	private static class ArrayIterator <K> implements ObjectListIterator <K> {
	 private final K[] array;
	 private final int offset, length;
	 private int curr;
	 public ArrayIterator(final K[] array, final int offset, final int length) {
	  this.array = array;
	  this.offset = offset;
	  this.length = length;
	 }
	 @Override
	 public boolean hasNext() { return curr < length; }
	 @Override
	 public boolean hasPrevious() { return curr > 0; }
	 @Override
	 public K next() {
	  if (! hasNext()) throw new NoSuchElementException();
	  return array[offset + curr++];
	 }
	 @Override
	 public K previous() {
	  if (! hasPrevious()) throw new NoSuchElementException();
	  return array[offset + --curr];
	 }
	 @Override
	 public int skip(int n) {
	  if (n <= length - curr) {
	   curr += n;
	   return n;
	  }
	  n = length - curr;
	  curr = length;
	  return n;
	 }
	 @Override
	 public int back(int n) {
	  if (n <= curr) {
	   curr -= n;
	   return n;
	  }
	  n = curr;
	  curr = 0;
	  return n;
	 }
	 @Override
	 public int nextIndex() {
	  return curr;
	 }
	 @Override
	 public int previousIndex() {
	  return curr - 1;
	 }
	}
	/** Wraps the given part of an array into a type-specific list iterator.
	 *
	 * <p>The type-specific list iterator returned by this method will iterate
	 * {@code length} times, returning consecutive elements of the given
	 * array starting from the one with index {@code offset}.
	 *
	 * @param array an array to wrap into a type-specific list iterator.
	 * @param offset the first element of the array to be returned.
	 * @param length the number of elements to return.
	 * @return an iterator that will return {@code length} elements of {@code array} starting at position {@code offset}.
	 */
	public static <K> ObjectListIterator <K> wrap(final K[] array, final int offset, final int length) {
	 ObjectArrays.ensureOffsetLength(array, offset, length);
	 return new ArrayIterator <>(array, offset, length);
	}
	/** Wraps the given array into a type-specific list iterator.
	 *
	 * <p>The type-specific list iterator returned by this method will return
	 * all elements of the given array.
	 *
	 * @param array an array to wrap into a type-specific list iterator.
	 * @return an iterator that will the elements of {@code array}.
	 */
	public static <K> ObjectListIterator <K> wrap(final K[] array) {
	 return new ArrayIterator <>(array, 0, array.length);
	}
	/** Unwraps an iterator into an array starting at a given offset for a given number of elements.
	 *
	 * <p>This method iterates over the given type-specific iterator and stores the elements
	 * returned, up to a maximum of {@code length}, in the given array starting at {@code offset}.
	 * The number of actually unwrapped elements is returned (it may be less than {@code max} if
	 * the iterator emits less than {@code max} elements).
	 *
	 * @param i a type-specific iterator.
	 * @param array an array to contain the output of the iterator.
	 * @param offset the first element of the array to be returned.
	 * @param max the maximum number of elements to unwrap.
	 * @return the number of elements unwrapped.
	 */
	public static <K> int unwrap(final Iterator <? extends K> i, final K array[], int offset, final int max) {
	 if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
	 if (offset < 0 || offset + max > array.length) throw new IllegalArgumentException();
	 int j = max;
	 while(j-- != 0 && i.hasNext()) array[offset++] = i.next();
	 return max - j - 1;
	}
	/** Unwraps an iterator into an array.
	 *
	 * <p>This method iterates over the given type-specific iterator and stores the
	 * elements returned in the given array. The iteration will stop when the
	 * iterator has no more elements or when the end of the array has been reached.
	 *
	 * @param i a type-specific iterator.
	 * @param array an array to contain the output of the iterator.
	 * @return the number of elements unwrapped.
	 */
	public static <K> int unwrap(final Iterator <? extends K> i, final K array[]) {
	 return unwrap(i, array, 0, array.length);
	}
	/** Unwraps an iterator, returning an array, with a limit on the number of elements.
	 *
	 * <p>This method iterates over the given type-specific iterator and returns an array
	 * containing the elements returned by the iterator. At most {@code max} elements
	 * will be returned.
	 *
	 * @param i a type-specific iterator.
	 * @param max the maximum number of elements to be unwrapped.
	 * @return an array containing the elements returned by the iterator (at most {@code max}).
	 */
	@SuppressWarnings("unchecked")
	public static <K> K[] unwrap(final Iterator <? extends K> i, int max) {
	 if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
	 K array[] = (K[]) new Object[16];
	 int j = 0;
	 while(max-- != 0 && i.hasNext()) {
	  if (j == array.length) array = ObjectArrays.grow(array, j + 1);
	  array[j++] = i.next();
	 }
	 return ObjectArrays.trim(array, j);
	}
	/** Unwraps an iterator, returning an array.
	 *
	 * <p>This method iterates over the given type-specific iterator and returns an array
	 * containing the elements returned by the iterator.
	 *
	 * @param i a type-specific iterator.
	 * @return an array containing the elements returned by the iterator.
	 */
	public static <K> K[] unwrap(final Iterator <? extends K> i) {
	 return unwrap(i, Integer.MAX_VALUE);
	}
	/** Unwraps an iterator into a type-specific collection, with a limit on the number of elements.
	 *
	 * <p>This method iterates over the given type-specific iterator and stores the elements
	 * returned, up to a maximum of {@code max}, in the given type-specific collection.
	 * The number of actually unwrapped elements is returned (it may be less than {@code max} if
	 * the iterator emits less than {@code max} elements).
	 *
	 * @param i a type-specific iterator.
	 * @param c a type-specific collection array to contain the output of the iterator.
	 * @param max the maximum number of elements to unwrap.
	 * @return the number of elements unwrapped. Note that
	 * this is the number of elements returned by the iterator, which is not necessarily the number
	 * of elements that have been added to the collection (because of duplicates).
	 */
	public static <K> int unwrap(final Iterator <K> i, final ObjectCollection <? super K> c, final int max) {
	 if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
	 int j = max;
	 while(j-- != 0 && i.hasNext()) c.add(i.next());
	 return max - j - 1;
	}
	/** Unwraps an iterator into a type-specific collection.
	 *
	 * <p>This method iterates over the given type-specific iterator and stores the
	 * elements returned in the given type-specific collection. The returned count on the number
	 * unwrapped elements is a long, so that it will work also with very large collections.
	 *
	 * @param i a type-specific iterator.
	 * @param c a type-specific collection to contain the output of the iterator.
	 * @return the number of elements unwrapped. Note that
	 * this is the number of elements returned by the iterator, which is not necessarily the number
	 * of elements that have been added to the collection (because of duplicates).
	 */
	public static <K> long unwrap(final Iterator <K> i, final ObjectCollection <? super K> c) {
	 long n = 0;
	 while(i.hasNext()) {
	  c.add(i.next());
	  n++;
	 }
	 return n;
	}
	/** Pours an iterator into a type-specific collection, with a limit on the number of elements.
	 *
	 * <p>This method iterates over the given type-specific iterator and adds
	 * the returned elements to the given collection (up to {@code max}).
	 *
	 * @param i a type-specific iterator.
	 * @param s a type-specific collection.
	 * @param max the maximum number of elements to be poured.
	 * @return the number of elements poured. Note that
	 * this is the number of elements returned by the iterator, which is not necessarily the number
	 * of elements that have been added to the collection (because of duplicates).
	 */
	public static <K> int pour(final Iterator <K> i, final ObjectCollection <? super K> s, final int max) {
	 if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
	 int j = max;
	 while(j-- != 0 && i.hasNext()) s.add(i.next());
	 return max - j - 1;
	}
	/** Pours an iterator into a type-specific collection.
	 *
	 * <p>This method iterates over the given type-specific iterator and adds
	 * the returned elements to the given collection.
	 *
	 * @param i a type-specific iterator.
	 * @param s a type-specific collection.
	 * @return the number of elements poured. Note that
	 * this is the number of elements returned by the iterator, which is not necessarily the number
	 * of elements that have been added to the collection (because of duplicates).
	 */
	public static <K> int pour(final Iterator <K> i, final ObjectCollection <? super K> s) {
	 return pour(i, s, Integer.MAX_VALUE);
	}
	/** Pours an iterator, returning a type-specific list, with a limit on the number of elements.
	 *
	 * <p>This method iterates over the given type-specific iterator and returns
	 * a type-specific list containing the returned elements (up to {@code max}). Iteration
	 * on the returned list is guaranteed to produce the elements in the same order
	 * in which they appeared in the iterator.
	 *
	 *
	 * @param i a type-specific iterator.
	 * @param max the maximum number of elements to be poured.
	 * @return a type-specific list containing the returned elements, up to {@code max}.
	 */
	public static <K> ObjectList <K> pour(final Iterator <K> i, int max) {
	 final ObjectArrayList <K> l = new ObjectArrayList <>();
	 pour(i, l, max);
	 l.trim();
	 return l;
	}
	/** Pours an iterator, returning a type-specific list.
	 *
	 * <p>This method iterates over the given type-specific iterator and returns
	 * a list containing the returned elements. Iteration
	 * on the returned list is guaranteed to produce the elements in the same order
	 * in which they appeared in the iterator.
	 *
	 * @param i a type-specific iterator.
	 * @return a type-specific list containing the returned elements.
	 */
	public static <K> ObjectList <K> pour(final Iterator <K> i) {
	 return pour(i, Integer.MAX_VALUE);
	}
	private static class IteratorWrapper <K> implements ObjectIterator <K> {
	 final Iterator<K> i;
	 public IteratorWrapper(final Iterator<K> i) {
	  this.i = i;
	 }
	 @Override
	 public boolean hasNext() { return i.hasNext(); }
	 @Override
	 public void remove() { i.remove(); }
	 @Override
	 public K next() { return (i.next()); }
	}
	/** Wraps a standard iterator into a type-specific iterator.
	 *
	 * <p>This method wraps a standard iterator into a type-specific one which will handle the
	 * type conversions for you. Of course, any attempt to wrap an iterator returning the
	 * instances of the wrong class will generate a {@link ClassCastException}. The
	 * returned iterator is backed by {@code i}: changes to one of the iterators
	 * will affect the other, too.
	 *
	 * <p>If {@code i} is already type-specific, it will returned and no new object
	 * will be generated.
	 *
	 * @param i an iterator.
	 * @return a type-specific iterator  backed by {@code i}.
	 */
	 public static <K> ObjectIterator <K> asObjectIterator(final Iterator <K> i) {
	 if (i instanceof ObjectIterator) return (ObjectIterator <K>)i;
	 return new IteratorWrapper <>(i);
	}
	private static class ListIteratorWrapper <K> implements ObjectListIterator <K> {
	 final ListIterator<K> i;
	 public ListIteratorWrapper(final ListIterator<K> i) {
	  this.i = i;
	 }
	 @Override
	 public boolean hasNext() { return i.hasNext(); }
	 @Override
	 public boolean hasPrevious() { return i.hasPrevious(); }
	 @Override
	 public int nextIndex() { return i.nextIndex(); }
	 @Override
	 public int previousIndex() { return i.previousIndex(); }
	 @Override
	 public void set(K k) { i.set((k)); }
	 @Override
	 public void add(K k) { i.add((k)); }
	 @Override
	 public void remove() { i.remove(); }
	 @Override
	 public K next() { return (i.next()); }
	 @Override
	 public K previous() { return (i.previous()); }
	}
	/** Wraps a standard list iterator into a type-specific list iterator.
	 *
	 * <p>This method wraps a standard list iterator into a type-specific one
	 * which will handle the type conversions for you. Of course, any attempt
	 * to wrap an iterator returning the instances of the wrong class will
	 * generate a {@link ClassCastException}. The
	 * returned iterator is backed by {@code i}: changes to one of the iterators
	 * will affect the other, too.
	 *
	 * <p>If {@code i} is already type-specific, it will returned and no new object
	 * will be generated.
	 *
	 * @param i a list iterator.
	 * @return a type-specific list iterator backed by {@code i}.
	 */
	 public static <K> ObjectListIterator <K> asObjectIterator(final ListIterator <K> i) {
	 if (i instanceof ObjectListIterator) return (ObjectListIterator <K>)i;
	 return new ListIteratorWrapper <>(i);
	}
	public static <K> boolean any(final ObjectIterator <K> iterator, final java.util.function.Predicate<? super K> predicate) {
	 return indexOf(iterator, predicate) != -1;
	}
	public static <K> boolean all(final ObjectIterator <K> iterator, final java.util.function.Predicate<? super K> predicate) {
	 Objects.requireNonNull(predicate);
	 do {
	  if (!iterator.hasNext()) return true;
	 } while (predicate.test(iterator.next()));
	 return false;
	}
	public static <K> int indexOf(final ObjectIterator <K> iterator, final java.util.function.Predicate<? super K> predicate) {
	 Objects.requireNonNull(predicate);
	 for (int i = 0; iterator.hasNext(); ++i) {
	  if (predicate.test(iterator.next())) return i;
	 }
	 return -1;
	}
	private static class IteratorConcatenator <K> implements ObjectIterator <K> {
	 final ObjectIterator <? extends K> a[];
	 int offset, length, lastOffset = -1;
	 public IteratorConcatenator(final ObjectIterator <? extends K> a[], int offset, int length) {
	  this.a = a;
	  this.offset = offset;
	  this.length = length;
	  advance();
	 }
	 private void advance() {
	  while(length != 0) {
	   if (a[offset].hasNext()) break;
	   length--;
	   offset++;
	  }
	  return;
	 }
	 @Override
	 public boolean hasNext() {
	  return length > 0;
	 }
	 @Override
	 public K next() {
	  if (! hasNext()) throw new NoSuchElementException();
	  K next = a[lastOffset = offset].next();
	  advance();
	  return next;
	 }
	 @Override
	 public void remove() {
	  if (lastOffset == -1) throw new IllegalStateException();
	  a[lastOffset].remove();
	 }
	 @Override
	 public int skip(int n) {
	  lastOffset = -1;
	  int skipped = 0;
	  while(skipped < n && length != 0) {
	   skipped += a[offset].skip(n - skipped);
	   if (a[offset].hasNext()) break;
	   length--;
	   offset++;
	  }
	  return skipped;
	 }
	}
	/** Concatenates all iterators contained in an array.
	 *
	 * <p>This method returns an iterator that will enumerate in order the elements returned
	 * by all iterators contained in the given array.
	 *
	 * @param a an array of iterators.
	 * @return an iterator obtained by concatenation.
	 */
	public static <K> ObjectIterator <K> concat(final ObjectIterator <? extends K> a[]) {
	 return concat(a, 0, a.length);
	}
	/** Concatenates a sequence of iterators contained in an array.
	 *
	 * <p>This method returns an iterator that will enumerate in order the elements returned
	 * by {@code a[offset]}, then those returned
	 * by {@code a[offset + 1]}, and so on up to
	 * {@code a[offset + length - 1]}.
	 *
	 * @param a an array of iterators.
	 * @param offset the index of the first iterator to concatenate.
	 * @param length the number of iterators to concatenate.
	 * @return an iterator obtained by concatenation of {@code length} elements of {@code a} starting at {@code offset}.
	 */
	public static <K> ObjectIterator <K> concat(final ObjectIterator <? extends K> a[], final int offset, final int length) {
	 return new IteratorConcatenator <>(a, offset, length);
	}
	  /** An unmodifiable wrapper class for iterators. */
	public static class UnmodifiableIterator <K> implements ObjectIterator <K> {
	 protected final ObjectIterator <K> i;
	 public UnmodifiableIterator(final ObjectIterator <K> i) {
	  this.i = i;
	 }
	 @Override
	 public boolean hasNext() { return i.hasNext(); }
	 @Override
	 public K next() { return i.next(); }
	}
	/** Returns an unmodifiable iterator backed by the specified iterator.
	 *
	 * @param i the iterator to be wrapped in an unmodifiable iterator.
	 * @return an unmodifiable view of the specified iterator.
	 */
	public static <K> ObjectIterator <K> unmodifiable(final ObjectIterator <K> i) { return new UnmodifiableIterator <>(i); }
	  /** An unmodifiable wrapper class for bidirectional iterators. */
	public static class UnmodifiableBidirectionalIterator <K> implements ObjectBidirectionalIterator <K> {
	 protected final ObjectBidirectionalIterator <K> i;
	 public UnmodifiableBidirectionalIterator(final ObjectBidirectionalIterator <K> i) {
	  this.i = i;
	 }
	 @Override
	 public boolean hasNext() { return i.hasNext(); }
	 @Override
	 public boolean hasPrevious() { return i.hasPrevious(); }
	 @Override
	 public K next() { return i.next(); }
	 @Override
	 public K previous() { return i.previous(); }
	}
	/** Returns an unmodifiable bidirectional iterator backed by the specified bidirectional iterator.
	 *
	 * @param i the bidirectional iterator to be wrapped in an unmodifiable bidirectional iterator.
	 * @return an unmodifiable view of the specified bidirectional iterator.
	 */
	public static <K> ObjectBidirectionalIterator <K> unmodifiable(final ObjectBidirectionalIterator <K> i) { return new UnmodifiableBidirectionalIterator <>(i); }
	  /** An unmodifiable wrapper class for list iterators. */
	public static class UnmodifiableListIterator <K> implements ObjectListIterator <K> {
	 protected final ObjectListIterator <K> i;
	 public UnmodifiableListIterator(final ObjectListIterator <K> i) {
	  this.i = i;
	 }
	 @Override
	 public boolean hasNext() { return i.hasNext(); }
	 @Override
	 public boolean hasPrevious() { return i.hasPrevious(); }
	 @Override
	 public K next() { return i.next(); }
	 @Override
	 public K previous() { return i.previous(); }
	 @Override
	 public int nextIndex() { return i.nextIndex(); }
	 @Override
	 public int previousIndex() { return i.previousIndex(); }
	}
	/** Returns an unmodifiable list iterator backed by the specified list iterator.
	 *
	 * @param i the list iterator to be wrapped in an unmodifiable list iterator.
	 * @return an unmodifiable view of the specified list iterator.
	 */
	public static <K> ObjectListIterator <K> unmodifiable(final ObjectListIterator <K> i) { return new UnmodifiableListIterator <>(i); }
}
