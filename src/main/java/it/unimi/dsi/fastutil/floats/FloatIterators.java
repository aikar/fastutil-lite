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
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
/** A class providing static methods and objects that do useful things with type-specific iterators.
	*
	* @see Iterator
	*/
public final class FloatIterators {
	private FloatIterators() {}
	/** A class returning no elements and a type-specific iterator interface.
	 *
	 * <p>This class may be useful to implement your own in case you subclass
	 * a type-specific iterator.
	 */
	public static class EmptyIterator implements FloatListIterator , java.io.Serializable, Cloneable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 protected EmptyIterator() {}
	 @Override
	 public boolean hasNext() { return false; }
	 @Override
	 public boolean hasPrevious() { return false; }
	 @Override
	 public float nextFloat() { throw new NoSuchElementException(); }
	 @Override
	 public float previousFloat() { throw new NoSuchElementException(); }
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

	public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
	/** An iterator returning a single element. */
	private static class SingletonIterator implements FloatListIterator {
	 private final float element;
	 private int curr;
	 public SingletonIterator(final float element) {
	  this.element = element;
	 }
	 @Override
	 public boolean hasNext() { return curr == 0; }
	 @Override
	 public boolean hasPrevious() { return curr == 1; }
	 @Override
	 public float nextFloat() {
	  if (! hasNext()) throw new NoSuchElementException();
	  curr = 1;
	  return element;
	 }
	 @Override
	 public float previousFloat() {
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
	public static FloatListIterator singleton(final float element) {
	 return new SingletonIterator (element);
	}
	/** A class to wrap arrays in iterators. */
	private static class ArrayIterator implements FloatListIterator {
	 private final float[] array;
	 private final int offset, length;
	 private int curr;
	 public ArrayIterator(final float[] array, final int offset, final int length) {
	  this.array = array;
	  this.offset = offset;
	  this.length = length;
	 }
	 @Override
	 public boolean hasNext() { return curr < length; }
	 @Override
	 public boolean hasPrevious() { return curr > 0; }
	 @Override
	 public float nextFloat() {
	  if (! hasNext()) throw new NoSuchElementException();
	  return array[offset + curr++];
	 }
	 @Override
	 public float previousFloat() {
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
	public static FloatListIterator wrap(final float[] array, final int offset, final int length) {
	 FloatArrays.ensureOffsetLength(array, offset, length);
	 return new ArrayIterator (array, offset, length);
	}
	/** Wraps the given array into a type-specific list iterator.
	 *
	 * <p>The type-specific list iterator returned by this method will return
	 * all elements of the given array.
	 *
	 * @param array an array to wrap into a type-specific list iterator.
	 * @return an iterator that will the elements of {@code array}.
	 */
	public static FloatListIterator wrap(final float[] array) {
	 return new ArrayIterator (array, 0, array.length);
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
	public static int unwrap(final FloatIterator i, final float array[], int offset, final int max) {
	 if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
	 if (offset < 0 || offset + max > array.length) throw new IllegalArgumentException();
	 int j = max;
	 while(j-- != 0 && i.hasNext()) array[offset++] = i.nextFloat();
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
	public static int unwrap(final FloatIterator i, final float array[]) {
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

	public static float[] unwrap(final FloatIterator i, int max) {
	 if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
	 float array[] = new float[16];
	 int j = 0;
	 while(max-- != 0 && i.hasNext()) {
	  if (j == array.length) array = FloatArrays.grow(array, j + 1);
	  array[j++] = i.nextFloat();
	 }
	 return FloatArrays.trim(array, j);
	}
	/** Unwraps an iterator, returning an array.
	 *
	 * <p>This method iterates over the given type-specific iterator and returns an array
	 * containing the elements returned by the iterator.
	 *
	 * @param i a type-specific iterator.
	 * @return an array containing the elements returned by the iterator.
	 */
	public static float[] unwrap(final FloatIterator i) {
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
	public static int unwrap(final FloatIterator i, final FloatCollection c, final int max) {
	 if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
	 int j = max;
	 while(j-- != 0 && i.hasNext()) c.add(i.nextFloat());
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
	public static long unwrap(final FloatIterator i, final FloatCollection c) {
	 long n = 0;
	 while(i.hasNext()) {
	  c.add(i.nextFloat());
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
	public static int pour(final FloatIterator i, final FloatCollection s, final int max) {
	 if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
	 int j = max;
	 while(j-- != 0 && i.hasNext()) s.add(i.nextFloat());
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
	public static int pour(final FloatIterator i, final FloatCollection s) {
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
	public static FloatList pour(final FloatIterator i, int max) {
	 final FloatArrayList l = new FloatArrayList ();
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
	public static FloatList pour(final FloatIterator i) {
	 return pour(i, Integer.MAX_VALUE);
	}
	private static class IteratorWrapper implements FloatIterator {
	 final Iterator<Float> i;
	 public IteratorWrapper(final Iterator<Float> i) {
	  this.i = i;
	 }
	 @Override
	 public boolean hasNext() { return i.hasNext(); }
	 @Override
	 public void remove() { i.remove(); }
	 @Override
	 public float nextFloat() { return (i.next()).floatValue(); }
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
	@SuppressWarnings({"unchecked","rawtypes"})
	 public static FloatIterator asFloatIterator(final Iterator i) {
	 if (i instanceof FloatIterator) return (FloatIterator )i;
	 return new IteratorWrapper (i);
	}
	private static class ListIteratorWrapper implements FloatListIterator {
	 final ListIterator<Float> i;
	 public ListIteratorWrapper(final ListIterator<Float> i) {
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
	 public void set(float k) { i.set(Float.valueOf(k)); }
	 @Override
	 public void add(float k) { i.add(Float.valueOf(k)); }
	 @Override
	 public void remove() { i.remove(); }
	 @Override
	 public float nextFloat() { return (i.next()).floatValue(); }
	 @Override
	 public float previousFloat() { return (i.previous()).floatValue(); }
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
	@SuppressWarnings({"unchecked","rawtypes"})
	 public static FloatListIterator asFloatIterator(final ListIterator i) {
	 if (i instanceof FloatListIterator) return (FloatListIterator )i;
	 return new ListIteratorWrapper (i);
	}
	public static boolean any(final FloatIterator iterator, final java.util.function.DoublePredicate predicate) {
	 return indexOf(iterator, predicate) != -1;
	}
	public static boolean all(final FloatIterator iterator, final java.util.function.DoublePredicate predicate) {
	 Objects.requireNonNull(predicate);
	 do {
	  if (!iterator.hasNext()) return true;
	 } while (predicate.test(iterator.nextFloat()));
	 return false;
	}
	public static int indexOf(final FloatIterator iterator, final java.util.function.DoublePredicate predicate) {
	 Objects.requireNonNull(predicate);
	 for (int i = 0; iterator.hasNext(); ++i) {
	  if (predicate.test(iterator.nextFloat())) return i;
	 }
	 return -1;
	}
	private static class IteratorConcatenator implements FloatIterator {
	 final FloatIterator a[];
	 int offset, length, lastOffset = -1;
	 public IteratorConcatenator(final FloatIterator a[], int offset, int length) {
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
	 public float nextFloat() {
	  if (! hasNext()) throw new NoSuchElementException();
	  float next = a[lastOffset = offset].nextFloat();
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
	public static FloatIterator concat(final FloatIterator a[]) {
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
	public static FloatIterator concat(final FloatIterator a[], final int offset, final int length) {
	 return new IteratorConcatenator (a, offset, length);
	}
	  /** An unmodifiable wrapper class for iterators. */
	public static class UnmodifiableIterator implements FloatIterator {
	 protected final FloatIterator i;
	 public UnmodifiableIterator(final FloatIterator i) {
	  this.i = i;
	 }
	 @Override
	 public boolean hasNext() { return i.hasNext(); }
	 @Override
	 public float nextFloat() { return i.nextFloat(); }
	}
	/** Returns an unmodifiable iterator backed by the specified iterator.
	 *
	 * @param i the iterator to be wrapped in an unmodifiable iterator.
	 * @return an unmodifiable view of the specified iterator.
	 */
	public static FloatIterator unmodifiable(final FloatIterator i) { return new UnmodifiableIterator (i); }
	  /** An unmodifiable wrapper class for bidirectional iterators. */
	public static class UnmodifiableBidirectionalIterator implements FloatBidirectionalIterator {
	 protected final FloatBidirectionalIterator i;
	 public UnmodifiableBidirectionalIterator(final FloatBidirectionalIterator i) {
	  this.i = i;
	 }
	 @Override
	 public boolean hasNext() { return i.hasNext(); }
	 @Override
	 public boolean hasPrevious() { return i.hasPrevious(); }
	 @Override
	 public float nextFloat() { return i.nextFloat(); }
	 @Override
	 public float previousFloat() { return i.previousFloat(); }
	}
	/** Returns an unmodifiable bidirectional iterator backed by the specified bidirectional iterator.
	 *
	 * @param i the bidirectional iterator to be wrapped in an unmodifiable bidirectional iterator.
	 * @return an unmodifiable view of the specified bidirectional iterator.
	 */
	public static FloatBidirectionalIterator unmodifiable(final FloatBidirectionalIterator i) { return new UnmodifiableBidirectionalIterator (i); }
	  /** An unmodifiable wrapper class for list iterators. */
	public static class UnmodifiableListIterator implements FloatListIterator {
	 protected final FloatListIterator i;
	 public UnmodifiableListIterator(final FloatListIterator i) {
	  this.i = i;
	 }
	 @Override
	 public boolean hasNext() { return i.hasNext(); }
	 @Override
	 public boolean hasPrevious() { return i.hasPrevious(); }
	 @Override
	 public float nextFloat() { return i.nextFloat(); }
	 @Override
	 public float previousFloat() { return i.previousFloat(); }
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
	public static FloatListIterator unmodifiable(final FloatListIterator i) { return new UnmodifiableListIterator (i); }
	  /** A wrapper promoting the results of a ByteIterator. */
	protected static class ByteIteratorWrapper implements FloatIterator {
	 final it.unimi.dsi.fastutil.bytes.ByteIterator iterator;
	 public ByteIteratorWrapper(final it.unimi.dsi.fastutil.bytes.ByteIterator iterator) {
	  this.iterator = iterator;
	 }
	 @Override
	 public boolean hasNext() { return iterator.hasNext(); }
	 @Deprecated
	 @Override
	 public Float next() { return Float.valueOf(iterator.nextByte()); }
	 @Override
	 public float nextFloat() { return iterator.nextByte(); }
	 @Override
	 public void remove() { iterator.remove(); }
	 @Override
	 public int skip(final int n) { return iterator.skip(n); }
	}
	/** Returns an iterator backed by the specified byte iterator.
	 * @param iterator a byte iterator.
	 * @return an iterator backed by the specified byte iterator.
	 */
	public static FloatIterator wrap(final it.unimi.dsi.fastutil.bytes.ByteIterator iterator) {
	 return new ByteIteratorWrapper(iterator);
	}
	/** A wrapper promoting the results of a ShortIterator. */
	protected static class ShortIteratorWrapper implements FloatIterator {
	 final it.unimi.dsi.fastutil.shorts.ShortIterator iterator;
	 public ShortIteratorWrapper(final it.unimi.dsi.fastutil.shorts.ShortIterator iterator) {
	  this.iterator = iterator;
	 }
	 @Override
	 public boolean hasNext() { return iterator.hasNext(); }
	 @Deprecated
	 @Override
	 public Float next() { return Float.valueOf(iterator.nextShort()); }
	 @Override
	 public float nextFloat() { return iterator.nextShort(); }
	 @Override
	 public void remove() { iterator.remove(); }
	 @Override
	 public int skip(final int n) { return iterator.skip(n); }
	}
	/** Returns an iterator backed by the specified short iterator.
	 * @param iterator a short iterator.
	 * @return an iterator backed by the specified short iterator.
	 */
	public static FloatIterator wrap(final it.unimi.dsi.fastutil.shorts.ShortIterator iterator) {
	 return new ShortIteratorWrapper(iterator);
	}
}
