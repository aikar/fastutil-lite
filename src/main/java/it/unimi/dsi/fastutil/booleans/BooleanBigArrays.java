/*
	* Copyright (C) 2009-2017 Sebastiano Vigna
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
	*
	*
	*
	* Copyright (C) 1999 CERN - European Organization for Nuclear Research.
	*
	*   Permission to use, copy, modify, distribute and sell this software and
	*   its documentation for any purpose is hereby granted without fee,
	*   provided that the above copyright notice appear in all copies and that
	*   both that copyright notice and this permission notice appear in
	*   supporting documentation. CERN makes no representations about the
	*   suitability of this software for any purpose. It is provided "as is"
	*   without expressed or implied warranty.
	*/
package it.unimi.dsi.fastutil.booleans;
import java.util.Arrays;
import java.util.Random;
import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash;
import static it.unimi.dsi.fastutil.BigArrays.ensureLength;
import static it.unimi.dsi.fastutil.BigArrays.start;
import static it.unimi.dsi.fastutil.BigArrays.segment;
import static it.unimi.dsi.fastutil.BigArrays.displacement;
import static it.unimi.dsi.fastutil.BigArrays.SEGMENT_MASK;
import static it.unimi.dsi.fastutil.BigArrays.SEGMENT_SHIFT;
import static it.unimi.dsi.fastutil.BigArrays.SEGMENT_SIZE;
/** A class providing static methods and objects that do useful things with {@linkplain BigArrays big arrays}.
	*
	* <p>In particular, the {@code forceCapacity()}, {@code ensureCapacity()}, {@code grow()},
	* {@code trim()} and {@code setLength()} methods allow to handle
	* big arrays much like array lists.
	*
	* <p>Note that {@link it.unimi.dsi.fastutil.io.BinIO} and {@link it.unimi.dsi.fastutil.io.TextIO}
	* contain several methods that make it possible to load and save big arrays of primitive types as sequences
	* of elements in {@link java.io.DataInput} format (i.e., not as objects) or as sequences of lines of text.
	*
	* @see BigArrays
	*/
public final class BooleanBigArrays {
	private BooleanBigArrays() {}
	/** A static, final, empty big array. */
	public static final boolean[][] EMPTY_BIG_ARRAY = {};
	/** A static, final, empty big array to be used as default big array in allocations. An
	  * object distinct from {@link #EMPTY_BIG_ARRAY} makes it possible to have different
	  * behaviors depending on whether the user required an empty allocation, or we are
	  * just lazily delaying allocation.
	  *
	  * @see java.util.ArrayList
	  */
	public static final boolean[][] DEFAULT_EMPTY_BIG_ARRAY = {};
	/** Returns the element of the given big array of specified index.
	 *
	 * @param array a big array.
	 * @param index a position in the big array.
	 * @return the element of the big array at the specified position.
	 */
	public static boolean get(final boolean[][] array, final long index) {
	 return array[segment(index)][displacement(index)];
	}
	/** Sets the element of the given big array of specified index.
	 *
	 * @param array a big array.
	 * @param index a position in the big array.
	 * @param value the new value for the array element at the specified position.
	 */
	public static void set(final boolean[][] array, final long index, boolean value) {
	 array[segment(index)][displacement(index)] = value;
	}
	/** Swaps the element of the given big array of specified indices.
	 *
	 * @param array a big array.
	 * @param first a position in the big array.
	 * @param second a position in the big array.
	 */
	public static void swap(final boolean[][] array, final long first, final long second) {
	 final boolean t = array[segment(first)][displacement(first)];
	 array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
	 array[segment(second)][displacement(second)] = t;
	}
	/** Returns the length of the given big array.
	 *
	 * @param array a big array.
	 * @return the length of the given big array.
	 */
	public static long length(final boolean[][] array) {
	 final int length = array.length;
	 return length == 0 ? 0 : start(length - 1) + array[length - 1].length;
	}
	/** Copies a big array from the specified source big array, beginning at the specified position, to the specified position of the destination big array.
	 * Handles correctly overlapping regions of the same big array.
	 *
	 * @param srcArray the source big array.
	 * @param srcPos the starting position in the source big array.
	 * @param destArray the destination big array.
	 * @param destPos the starting position in the destination data.
	 * @param length the number of elements to be copied.
	 */
	public static void copy(final boolean[][] srcArray, final long srcPos, final boolean[][] destArray, final long destPos, long length) {
	 if (destPos <= srcPos) {
	  int srcSegment = segment(srcPos);
	  int destSegment = segment(destPos);
	  int srcDispl = displacement(srcPos);
	  int destDispl = displacement(destPos);
	  int l;
	  while(length > 0) {
	   l = (int)Math.min(length, Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
	   System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
	   if ((srcDispl += l) == SEGMENT_SIZE) {
	    srcDispl = 0;
	    srcSegment++;
	   }
	   if ((destDispl += l) == SEGMENT_SIZE) {
	    destDispl = 0;
	    destSegment++;
	   }
	   length -= l;
	  }
	 }
	 else {
	  int srcSegment = segment(srcPos + length);
	  int destSegment = segment(destPos + length);
	  int srcDispl = displacement(srcPos + length);
	  int destDispl = displacement(destPos + length);
	  int l;
	  while(length > 0) {
	   if (srcDispl == 0) {
	    srcDispl = SEGMENT_SIZE;
	    srcSegment--;
	   }
	   if (destDispl == 0) {
	    destDispl = SEGMENT_SIZE;
	    destSegment--;
	   }
	   l = (int)Math.min(length, Math.min(srcDispl, destDispl));
	   System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
	   srcDispl -= l;
	   destDispl -= l;
	   length -= l;
	  }
	 }
	}
	/** Copies a big array from the specified source big array, beginning at the specified position, to the specified position of the destination array.
	 *
	 * @param srcArray the source big array.
	 * @param srcPos the starting position in the source big array.
	 * @param destArray the destination array.
	 * @param destPos the starting position in the destination data.
	 * @param length the number of elements to be copied.
	 */
	public static void copyFromBig(final boolean[][] srcArray, final long srcPos, final boolean[] destArray, int destPos, int length) {
	 int srcSegment = segment(srcPos);
	 int srcDispl = displacement(srcPos);
	 int l;
	 while(length > 0) {
	  l = Math.min(srcArray[srcSegment].length - srcDispl, length);
	  System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
	  if ((srcDispl += l) == SEGMENT_SIZE) {
	   srcDispl = 0;
	   srcSegment++;
	  }
	  destPos += l;
	  length -= l;
	 }
	}
	/** Copies an array from the specified source array, beginning at the specified position, to the specified position of the destination big array.
	 *
	 * @param srcArray the source array.
	 * @param srcPos the starting position in the source array.
	 * @param destArray the destination big array.
	 * @param destPos the starting position in the destination data.
	 * @param length the number of elements to be copied.
	 */
	public static void copyToBig(final boolean[] srcArray, int srcPos, final boolean[][] destArray, final long destPos, long length) {
	 int destSegment = segment(destPos);
	 int destDispl = displacement(destPos);
	 int l;
	 while(length > 0) {
	  l = (int)Math.min(destArray[destSegment].length - destDispl, length);
	  System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
	  if ((destDispl += l) == SEGMENT_SIZE) {
	   destDispl = 0;
	   destSegment++;
	  }
	  srcPos += l;
	  length -= l;
	 }
	}
	/** Creates a new big array.
	 *
	 * @param length the length of the new big array.
	 * @return a new big array of given length.
	 */
	public static boolean[][] newBigArray(final long length) {
	 if (length == 0) return EMPTY_BIG_ARRAY;
	 ensureLength(length);
	 final int baseLength = (int)((length + SEGMENT_MASK) >>> SEGMENT_SHIFT);
	 boolean[][] base = new boolean[baseLength][];
	 final int residual = (int)(length & SEGMENT_MASK);
	 if (residual != 0) {
	  for(int i = 0; i < baseLength - 1; i++) base[i] = new boolean[SEGMENT_SIZE];
	  base[baseLength - 1] = new boolean[residual];
	 }
	 else for(int i = 0; i < baseLength; i++) base[i] = new boolean[SEGMENT_SIZE];
	 return base;
	}
	/** Turns a standard array into a big array.
	 *
	 * <p>Note that the returned big array might contain as a segment the original array.
	 *
	 * @param array an array.
	 * @return a new big array with the same length and content of {@code array}.
	 */
	public static boolean[][] wrap(final boolean[] array) {
	 if (array.length == 0) return EMPTY_BIG_ARRAY;
	 if (array.length <= SEGMENT_SIZE) return new boolean[][] { array };
	 final boolean[][] bigArray = newBigArray(array.length);
	 for(int i = 0; i < bigArray.length; i++) System.arraycopy(array, (int)start(i), bigArray[i], 0, bigArray[i].length);
	 return bigArray;
	}
	/** Ensures that a big array can contain the given number of entries.
	 *
	 * <p>If you cannot foresee whether this big array will need again to be
	 * enlarged, you should probably use {@code grow()} instead.
	 *
	 * <p><strong>Warning:</strong> the returned array might use part of the segments of the original
	 * array, which must be considered read-only after calling this method.
	 *
	 * @param array a big array.
	 * @param length the new minimum length for this big array.
	 * @return {@code array}, if it contains {@code length} entries or more; otherwise,
	 * a big array with {@code length} entries whose first {@code length(array)}
	 * entries are the same as those of {@code array}.
	 */
	public static boolean[][] ensureCapacity(final boolean[][] array, final long length) {
	 return ensureCapacity(array, length, length(array));
	}
	/** Forces a big array to contain the given number of entries, preserving just a part of the big array.
	 *
	 * <p><strong>Warning:</strong> the returned array might use part of the segments of the original
	 * array, which must be considered read-only after calling this method.
	 *
	 * @param array a big array.
	 * @param length the new minimum length for this big array.
	 * @param preserve the number of elements of the big array that must be preserved in case a new allocation is necessary.
	 * @return a big array with {@code length} entries whose first {@code preserve}
	 * entries are the same as those of {@code array}.
	 */
	public static boolean[][] forceCapacity(final boolean[][] array, final long length, final long preserve) {
	 ensureLength(length);
	 final int valid = array.length - (array.length == 0 || array.length > 0 && array[array.length - 1].length == SEGMENT_SIZE ? 0 : 1);
	 final int baseLength = (int)((length + SEGMENT_MASK) >>> SEGMENT_SHIFT);
	 final boolean[][] base = Arrays.copyOf(array, baseLength);
	 final int residual = (int)(length & SEGMENT_MASK);
	 if (residual != 0) {
	  for(int i = valid; i < baseLength - 1; i++) base[i] = new boolean[SEGMENT_SIZE];
	  base[baseLength - 1] = new boolean[residual];
	 }
	 else for(int i = valid; i < baseLength; i++) base[i] = new boolean[SEGMENT_SIZE];
	 if (preserve - (valid * (long)SEGMENT_SIZE) > 0) copy(array, valid * (long)SEGMENT_SIZE, base, valid * (long)SEGMENT_SIZE, preserve - (valid * (long)SEGMENT_SIZE));
	 return base;
	}
	/** Ensures that a big array can contain the given number of entries, preserving just a part of the big array.
	 *
	 * <p><strong>Warning:</strong> the returned array might use part of the segments of the original
	 * array, which must be considered read-only after calling this method.
	 *
	 * @param array a big array.
	 * @param length the new minimum length for this big array.
	 * @param preserve the number of elements of the big array that must be preserved in case a new allocation is necessary.
	 * @return {@code array}, if it can contain {@code length} entries or more; otherwise,
	 * a big array with {@code length} entries whose first {@code preserve}
	 * entries are the same as those of {@code array}.
	 */
	public static boolean[][] ensureCapacity(final boolean[][] array, final long length, final long preserve) {
	 return length > length(array) ? forceCapacity(array, length, preserve) : array;
	}
	/** Grows the given big array to the maximum between the given length and
	 * the current length increased by 50%, provided that the given
	 * length is larger than the current length.
	 *
	 * <p>If you want complete control on the big array growth, you
	 * should probably use {@code ensureCapacity()} instead.
	 *
	 * <p><strong>Warning:</strong> the returned array might use part of the segments of the original
	 * array, which must be considered read-only after calling this method.
	 *
	 * @param array a big array.
	 * @param length the new minimum length for this big array.
	 * @return {@code array}, if it can contain {@code length}
	 * entries; otherwise, a big array with
	 * max({@code length},{@code length(array)}/&phi;) entries whose first
	 * {@code length(array)} entries are the same as those of {@code array}.
	 * */
	public static boolean[][] grow(final boolean[][] array, final long length) {
	 final long oldLength = length(array);
	 return length > oldLength ? grow(array, length, oldLength) : array;
	}
	/** Grows the given big array to the maximum between the given length and
	 * the current length increased by 50%, provided that the given
	 * length is larger than the current length, preserving just a part of the big array.
	 *
	 * <p>If you want complete control on the big array growth, you
	 * should probably use {@code ensureCapacity()} instead.
	 *
	 * <p><strong>Warning:</strong> the returned array might use part of the segments of the original
	 * array, which must be considered read-only after calling this method.
	 *
	 * @param array a big array.
	 * @param length the new minimum length for this big array.
	 * @param preserve the number of elements of the big array that must be preserved in case a new allocation is necessary.
	 * @return {@code array}, if it can contain {@code length}
	 * entries; otherwise, a big array with
	 * max({@code length},{@code length(array)}/&phi;) entries whose first
	 * {@code preserve} entries are the same as those of {@code array}.
	 * */
	public static boolean[][] grow(final boolean[][] array, final long length, final long preserve) {
	 final long oldLength = length(array);
	 return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
	}
	/** Trims the given big array to the given length.
	 *
	 * <p><strong>Warning:</strong> the returned array might use part of the segments of the original
	 * array, which must be considered read-only after calling this method.
	 *
	 * @param array a big array.
	 * @param length the new maximum length for the big array.
	 * @return {@code array}, if it contains {@code length}
	 * entries or less; otherwise, a big array with
	 * {@code length} entries whose entries are the same as
	 * the first {@code length} entries of {@code array}.
	 *
	 */
	public static boolean[][] trim(final boolean[][] array, final long length) {
	 ensureLength(length);
	 final long oldLength = length(array);
	 if (length >= oldLength) return array;
	 final int baseLength = (int)((length + SEGMENT_MASK) >>> SEGMENT_SHIFT);
	 final boolean[][] base = Arrays.copyOf(array, baseLength);
	 final int residual = (int)(length & SEGMENT_MASK);
	 if (residual != 0) base[baseLength - 1] = BooleanArrays.trim(base[baseLength - 1], residual);
	 return base;
	}
	/** Sets the length of the given big array.
	 *
	 * <p><strong>Warning:</strong> the returned array might use part of the segments of the original
	 * array, which must be considered read-only after calling this method.
	 *
	 * @param array a big array.
	 * @param length the new length for the big array.
	 * @return {@code array}, if it contains exactly {@code length}
	 * entries; otherwise, if it contains <em>more</em> than
	 * {@code length} entries, a big array with {@code length} entries
	 * whose entries are the same as the first {@code length} entries of
	 * {@code array}; otherwise, a big array with {@code length} entries
	 * whose first {@code length(array)} entries are the same as those of
	 * {@code array}.
	 *
	 */
	public static boolean[][] setLength(final boolean[][] array, final long length) {
	 final long oldLength = length(array);
	 if (length == oldLength) return array;
	 if (length < oldLength) return trim(array, length);
	 return ensureCapacity(array, length);
	}
	/** Returns a copy of a portion of a big array.
	 *
	 * @param array a big array.
	 * @param offset the first element to copy.
	 * @param length the number of elements to copy.
	 * @return a new big array containing {@code length} elements of {@code array} starting at {@code offset}.
	 */
	public static boolean[][] copy(final boolean[][] array, final long offset, final long length) {
	 ensureOffsetLength(array, offset, length);
	 final boolean[][] a =
	  newBigArray(length);
	 copy(array, offset, a, 0, length);
	 return a;
	}
	/** Returns a copy of a big array.
	 *
	 * @param array a big array.
	 * @return a copy of {@code array}.
	 */
	public static boolean[][] copy(final boolean[][] array) {
	 final boolean[][] base = array.clone();
	 for(int i = base.length; i-- != 0;) base[i] = array[i].clone();
	 return base;
	}
	/** Fills the given big array with the given value.
	 *
	 * <p>This method uses a backward loop. It is significantly faster than the corresponding
	 * method in {@link java.util.Arrays}.
	 *
	 * @param array a big array.
	 * @param value the new value for all elements of the big array.
	 */
	public static void fill(final boolean[][] array, final boolean value) {
	 for(int i = array.length; i-- != 0;) Arrays.fill(array[i], value);
	}
	/** Fills a portion of the given big array with the given value.
	 *
	 * <p>If possible (i.e., {@code from} is 0) this method uses a
	 * backward loop. In this case, it is significantly faster than the
	 * corresponding method in {@link java.util.Arrays}.
	 *
	 * @param array a big array.
	 * @param from the starting index of the portion to fill.
	 * @param to the end index of the portion to fill.
	 * @param value the new value for all elements of the specified portion of the big array.
	 */
	public static void fill(final boolean[][] array, final long from, long to, final boolean value) {
	 final long length = length(array);
	 BigArrays.ensureFromTo(length, from, to);
	 if (length == 0) return; // To avoid addressing array[0]
	 int fromSegment = segment(from);
	 int toSegment = segment(to);
	 int fromDispl = displacement(from);
	 int toDispl = displacement(to);
	 if (fromSegment == toSegment) {
	  Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
	  return;
	 }
	 if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value);
	 while(--toSegment > fromSegment) Arrays.fill(array[toSegment], value);
	 Arrays.fill(array[fromSegment], fromDispl, SEGMENT_SIZE, value);
	}
	/** Returns true if the two big arrays are elementwise equal.
	 *
	 * <p>This method uses a backward loop. It is significantly faster than the corresponding
	 * method in {@link java.util.Arrays}.
	 *
	 * @param a1 a big array.
	 * @param a2 another big array.
	 * @return true if the two big arrays are of the same length, and their elements are equal.
	 */
	public static boolean equals(final boolean[][] a1, final boolean a2[][]) {
	 if (length(a1) != length(a2)) return false;
	 int i = a1.length, j;
	 boolean[] t, u;
	 while(i-- != 0) {
	  t = a1[i];
	  u = a2[i];
	  j = t.length;
	  while(j-- != 0) if (! ( (t[j]) == (u[j]) )) return false;
	 }
	 return true;
	}
	/* Returns a string representation of the contents of the specified big array.
	 *
	 * The string representation consists of a list of the big array's elements, enclosed in square brackets ("[]"). Adjacent elements are separated by the characters ", " (a comma followed by a space). Returns "null" if {@code a} is null.
	 * @param a the big array whose string representation to return.
	 * @return the string representation of {@code a}.
	 */
	public static String toString(final boolean[][] a) {
	 if (a == null) return "null";
	 final long last = length(a) - 1;
	 if (last == - 1) return "[]";
	 final StringBuilder b = new StringBuilder();
	 b.append('[');
	 for (long i = 0; ; i++) {
	  b.append(String.valueOf(get(a, i)));
	  if (i == last) return b.append(']').toString();
	  b.append(", ");
	 }
	}
	/** Ensures that a range given by its first (inclusive) and last (exclusive) elements fits a big array.
	 *
	 * <p>This method may be used whenever a big array range check is needed.
	 *
	 * @param a a big array.
	 * @param from a start index (inclusive).
	 * @param to an end index (inclusive).
	 * @throws IllegalArgumentException if {@code from} is greater than {@code to}.
	 * @throws ArrayIndexOutOfBoundsException if {@code from} or {@code to} are greater than the big array length or negative.
	 */
	public static void ensureFromTo(final boolean[][] a, final long from, final long to) {
	 BigArrays.ensureFromTo(length(a), from, to);
	}
	/** Ensures that a range given by an offset and a length fits a big array.
	 *
	 * <p>This method may be used whenever a big array range check is needed.
	 *
	 * @param a a big array.
	 * @param offset a start index.
	 * @param length a length (the number of elements in the range).
	 * @throws IllegalArgumentException if {@code length} is negative.
	 * @throws ArrayIndexOutOfBoundsException if {@code offset} is negative or {@code offset}+{@code length} is greater than the big array length.
	 */
	public static void ensureOffsetLength(final boolean[][] a, final long offset, final long length) {
	 BigArrays.ensureOffsetLength(length(a), offset, length);
	}
	/** A type-specific content-based hash strategy for big arrays. */
	private static final class BigArrayHashStrategy implements Hash.Strategy<boolean[][]>, java.io.Serializable {
	 private static final long serialVersionUID = -7046029254386353129L;
	 @Override
	 public int hashCode(final boolean[][] o) { return java.util.Arrays.deepHashCode(o); }
	 @Override
	 public boolean equals(final boolean[][] a, final boolean[][] b) { return BooleanBigArrays.equals(a, b); }
	}
	/** A type-specific content-based hash strategy for big arrays.
	 *
	 * <p>This hash strategy may be used in custom hash collections whenever keys are
	 * big arrays, and they must be considered equal by content. This strategy
	 * will handle {@code null} correctly, and it is serializable.
	 */
	@SuppressWarnings({"rawtypes"})
	public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy();
	private static final int SMALL = 7;
	private static final int MEDIUM = 40;
	private static void vecSwap(final boolean[][] x, long a, long b, final long n) {
	 for(int i = 0; i < n; i++, a++, b++) swap(x, a, b);
	}
	private static long med3(final boolean x[][], final long a, final long b, final long c, BooleanComparator comp) {
	 int ab = comp.compare(get(x, a), get(x, b));
	 int ac = comp.compare(get(x, a), get(x, c));
	 int bc = comp.compare(get(x, b), get(x, c));
	 return (ab < 0 ?
	  (bc < 0 ? b : ac < 0 ? c : a) :
	  (bc > 0 ? b : ac > 0 ? c : a));
	}
	private static void selectionSort(final boolean[][] a, final long from, final long to, final BooleanComparator comp) {
	 for(long i = from; i < to - 1; i++) {
	  long m = i;
	  for(long j = i + 1; j < to; j++) if (comp.compare(BooleanBigArrays.get(a, j), BooleanBigArrays.get(a, m)) < 0) m = j;
	  if (m != i) swap(a, i, m);
	 }
	}
	/** Sorts the specified range of elements according to the order induced by the specified
	 * comparator using quicksort.
	 *
	 * <p>The sorting algorithm is a tuned quicksort adapted from Jon L. Bentley and M. Douglas
	 * McIlroy, &ldquo;Engineering a Sort Function&rdquo;, <i>Software: Practice and Experience</i>, 23(11), pages
	 * 1249&minus;1265, 1993.
	 *
	 * @param x the big array to be sorted.
	 * @param from the index of the first element (inclusive) to be sorted.
	 * @param to the index of the last element (exclusive) to be sorted.
	 * @param comp the comparator to determine the sorting order.
	 */
	public static void quickSort(final boolean[][] x, final long from, final long to, final BooleanComparator comp) {
	 final long len = to - from;
	 // Selection sort on smallest arrays
	 if (len < SMALL) {
	  selectionSort(x, from, to, comp);
	  return;
	 }
	 // Choose a partition element, v
	 long m = from + len / 2; // Small arrays, middle element
	 if (len > SMALL) {
	  long l = from;
	  long n = to - 1;
	  if (len > MEDIUM) { // Big arrays, pseudomedian of 9
	   long s = len / 8;
	   l = med3(x, l, l + s, l + 2 * s, comp);
	   m = med3(x, m - s, m, m + s, comp);
	   n = med3(x, n - 2 * s, n - s, n, comp);
	  }
	  m = med3(x, l, m, n, comp); // Mid-size, med of 3
	 }
	 final boolean v = get(x, m);
	 // Establish Invariant: v* (<v)* (>v)* v*
	 long a = from, b = a, c = to - 1, d = c;
	 while(true) {
	  int comparison;
	  while (b <= c && (comparison = comp.compare(get(x, b), v)) <= 0) {
	   if (comparison == 0) swap(x, a++, b);
	   b++;
	  }
	  while (c >= b && (comparison = comp.compare(get(x, c), v)) >=0) {
	   if (comparison == 0) swap(x, c, d--);
	   c--;
	  }
	  if (b > c) break;
	  swap(x, b++, c--);
	 }
	 // Swap partition elements back to middle
	 long s, n = to;
	 s = Math.min(a - from, b - a);
	 vecSwap(x, from, b - s, s);
	 s = Math.min(d - c, n - d- 1);
	 vecSwap(x, b, n - s, s);
	 // Recursively sort non-partition-elements
	 if ((s = b - a) > 1) quickSort(x, from, from + s, comp);
	 if ((s = d - c) > 1) quickSort(x, n - s, n, comp);
	}

	private static long med3(final boolean x[][], final long a, final long b, final long c) {
	 int ab = ( Boolean.compare((get(x, a)),(get(x, b))) );
	 int ac = ( Boolean.compare((get(x, a)),(get(x, c))) );
	 int bc = ( Boolean.compare((get(x, b)),(get(x, c))) );
	 return (ab < 0 ?
	  (bc < 0 ? b : ac < 0 ? c : a) :
	  (bc > 0 ? b : ac > 0 ? c : a));
	}

	private static void selectionSort(final boolean[][] a, final long from, final long to) {
	 for(long i = from; i < to - 1; i++) {
	  long m = i;
	  for(long j = i + 1; j < to; j++) if (( !(BooleanBigArrays.get(a, j)) && (BooleanBigArrays.get(a, m)) )) m = j;
	  if (m != i) swap(a, i, m);
	 }
	}
	/** Sorts the specified big array according to the order induced by the specified
	 * comparator using quicksort.
	 *
	 * <p>The sorting algorithm is a tuned quicksort adapted from Jon L. Bentley and M. Douglas
	 * McIlroy, &ldquo;Engineering a Sort Function&rdquo;, <i>Software: Practice and Experience</i>, 23(11), pages
	 * 1249&minus;1265, 1993.
	 *
	 * @param x the big array to be sorted.
	 * @param comp the comparator to determine the sorting order.
	 *
	 */
	public static void quickSort(final boolean[][] x, final BooleanComparator comp) {
	 quickSort(x, 0, BooleanBigArrays.length(x), comp);
	}
	/** Sorts the specified range of elements according to the natural ascending order using quicksort.
	 *
	 * <p>The sorting algorithm is a tuned quicksort adapted from Jon L. Bentley and M. Douglas
	 * McIlroy, &ldquo;Engineering a Sort Function&rdquo;, <i>Software: Practice and Experience</i>, 23(11), pages
	 * 1249&minus;1265, 1993.
	 *
	 * @param x the big array to be sorted.
	 * @param from the index of the first element (inclusive) to be sorted.
	 * @param to the index of the last element (exclusive) to be sorted.
	 */

	public static void quickSort(final boolean[][] x, final long from, final long to) {
	 final long len = to - from;
	 // Selection sort on smallest arrays
	 if (len < SMALL) {
	  selectionSort(x, from, to);
	  return;
	 }
	 // Choose a partition element, v
	 long m = from + len / 2; // Small arrays, middle element
	 if (len > SMALL) {
	  long l = from;
	  long n = to - 1;
	  if (len > MEDIUM) { // Big arrays, pseudomedian of 9
	   long s = len / 8;
	   l = med3(x, l, l + s, l + 2 * s);
	   m = med3(x, m - s, m, m + s);
	   n = med3(x, n - 2 * s, n - s, n);
	  }
	  m = med3(x, l, m, n); // Mid-size, med of 3
	 }
	 final boolean v = get(x, m);
	 // Establish Invariant: v* (<v)* (>v)* v*
	 long a = from, b = a, c = to - 1, d = c;
	 while(true) {
	  int comparison;
	  while (b <= c && (comparison = ( Boolean.compare((get(x, b)),(v)) )) <= 0) {
	   if (comparison == 0) swap(x, a++, b);
	   b++;
	  }
	  while (c >= b && (comparison = ( Boolean.compare((get(x, c)),(v)) )) >=0) {
	   if (comparison == 0) swap(x, c, d--);
	   c--;
	  }
	  if (b > c) break;
	  swap(x, b++, c--);
	 }
	 // Swap partition elements back to middle
	 long s, n = to;
	 s = Math.min(a - from, b - a);
	 vecSwap(x, from, b - s, s);
	 s = Math.min(d - c, n - d- 1);
	 vecSwap(x, b, n - s, s);
	 // Recursively sort non-partition-elements
	 if ((s = b - a) > 1) quickSort(x, from, from + s);
	 if ((s = d - c) > 1) quickSort(x, n - s, n);
	}
	/** Sorts the specified big array according to the natural ascending order using quicksort.
	 *
	 * <p>The sorting algorithm is a tuned quicksort adapted from Jon L. Bentley and M. Douglas
	 * McIlroy, &ldquo;Engineering a Sort Function&rdquo;, <i>Software: Practice and Experience</i>, 23(11), pages
	 * 1249&minus;1265, 1993.
	 *
	 * @param x the big array to be sorted.
	 */
	public static void quickSort(final boolean[][] x) {
	 quickSort(x, 0, BooleanBigArrays.length(x));
	}
	/** Shuffles the specified big array fragment using the specified pseudorandom number generator.
	 *
	 * @param a the big array to be shuffled.
	 * @param from the index of the first element (inclusive) to be shuffled.
	 * @param to the index of the last element (exclusive) to be shuffled.
	 * @param random a pseudorandom number generator.
	 * @return {@code a}.
	 */
	public static boolean[][] shuffle(final boolean[][] a, final long from, final long to, final Random random) {
	 for(long i = to - from; i-- != 0;) {
	  final long p = (random.nextLong() & 0x7FFFFFFFFFFFFFFFL) % (i + 1);
	  final boolean t = get(a, from + i);
	  set(a, from + i, get(a, from + p));
	  set(a, from + p, t);
	 }
	 return a;
	}
	/** Shuffles the specified big array using the specified pseudorandom number generator.
	 *
	 * @param a the big array to be shuffled.
	 * @param random a pseudorandom number generator.
	 * @return {@code a}.
	 */
	public static boolean[][] shuffle(final boolean[][] a, final Random random) {
	 for(long i = length(a); i-- != 0;) {
	  final long p = (random.nextLong() & 0x7FFFFFFFFFFFFFFFL) % (i + 1);
	  final boolean t = get(a, i);
	  set(a, i, get(a, p));
	  set(a, p, t);
	 }
	 return a;
	}
}
