/*
	* Copyright (C) 2005-2017 Sebastiano Vigna
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
package it.unimi.dsi.fastutil.io;
import static it.unimi.dsi.fastutil.BigArrays.SEGMENT_MASK;
import static it.unimi.dsi.fastutil.BigArrays.segment;
import static it.unimi.dsi.fastutil.BigArrays.start;
import java.io.*;
import java.util.*;
import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.longs.*;
import it.unimi.dsi.fastutil.doubles.*;
import it.unimi.dsi.fastutil.booleans.*;
import it.unimi.dsi.fastutil.bytes.*;
import it.unimi.dsi.fastutil.shorts.*;
import it.unimi.dsi.fastutil.floats.*;
/** Provides static methods to perform easily textual I/O.
	*
	* <p>This class fills a gap in the Java API: a natural operation on sequences
	* of primitive elements is to load or store them in textual form. This format
	* makes files humanly readable.
	*
	* <p>For each primitive type, this class provides methods that read elements
	* from a {@link BufferedReader} or from a filename (which will be opened
	* using a buffer of {@link #BUFFER_SIZE} bytes) into an array. Analogously,
	* there are methods that store the content of an array (fragment) or the
	* elements returned by an iterator to a {@link PrintStream} or to a given
	* filename.
	*
	* <p>Finally, there are useful wrapper methods that {@linkplain #asIntIterator(CharSequence)
	* exhibit a file as a type-specific iterator}.
	*
	* <p>Note that, contrarily to the binary case, there is no way to
	* {@linkplain BinIO#loadInts(CharSequence) load from a file without providing an array}. You can
	* easily work around the problem as follows:
	* <pre>
	* array = IntIterators.unwrap(TextIO.asIntIterator("foo"));
	* </pre>
	*
	* @since 4.4
	*/
public class TextIO {
	private TextIO() {}
	/** The size of the buffer used for all I/O on files. */
	public static final int BUFFER_SIZE = 8 * 1024;
/* Generic definitions */
/* Assertions (useful to generate conditional code) */
/* Current type and class (and size, if applicable) */
/* Value methods */
/* Interfaces (keys) */
/* Interfaces (values) */
/* Abstract implementations (keys) */
/* Abstract implementations (values) */
/* Static containers (keys) */
/* Static containers (values) */
/* Implementations */
/* Synchronized wrappers */
/* Unmodifiable wrappers */
/* Other wrappers */
/* Methods (keys) */
/* Methods (values) */
/* Methods (keys/values) */
/* Methods that have special names depending on keys (but the special names depend on values) */
/* Equality */
/* Object/Reference-only definitions (keys) */
/* Primitive-type-only definitions (keys) */
/* Object/Reference-only definitions (values) */
/* START_OF_JAVA_SOURCE */
/*
	* Copyright (C) 2004-2017 Sebastiano Vigna
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
/** Loads elements from a given fast buffered reader, storing them in a given array fragment.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static int loadInts(final BufferedReader reader, final int[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.ints.IntArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	String s;
	try {
	 for(i = 0; i < length; i++)
	  if ((s = reader.readLine()) != null) array[i + offset] = Integer.parseInt(s.trim());
	  else break;
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static int loadInts(final BufferedReader reader, final int[] array) throws IOException {
	return loadInts(reader, array, 0, array.length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array fragment.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadInts(final File file, final int[] array, final int offset, final int length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final int result = loadInts(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadInts(final CharSequence filename, final int[] array, final int offset, final int length) throws IOException {
	return loadInts(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadInts(final File file, final int[] array) throws IOException {
	return loadInts(file, array, 0, array.length);
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadInts(final CharSequence filename, final int[] array) throws IOException {
	return loadInts(filename, array, 0, array.length);
}
/** Stores an array fragment to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeInts(final int array[], final int offset, final int length, final PrintStream stream) {
	it.unimi.dsi.fastutil.ints.IntArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) stream.println(array[offset + i]);
}
/** Stores an array to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeInts(final int array[], final PrintStream stream) {
	storeInts(array, 0, array.length, stream);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeInts(final int array[], final int offset, final int length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeInts(array, offset, length, stream);
	stream.close();
}
/** Stores an array fragment to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeInts(final int array[], final int offset, final int length, final CharSequence filename) throws IOException {
	storeInts(array, offset, length, new File(filename.toString()));
}
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeInts(final int array[], final File file) throws IOException {
	storeInts(array, 0, array.length, file);
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeInts(final int array[], final CharSequence filename) throws IOException {
	storeInts(array, 0, array.length, filename);
}
/** Stores the element returned by an iterator to a given print stream.
	*
	* @param i an iterator whose output will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeInts(final IntIterator i, final PrintStream stream) {
	while(i.hasNext()) stream.println(i.nextInt());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeInts(final IntIterator i, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeInts(i, stream);
	stream.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeInts(final IntIterator i, final CharSequence filename) throws IOException {
	storeInts(i, new File(filename.toString()));
}
/** Loads elements from a given fast buffered reader, storing them in a given big-array fragment.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static long loadInts(final BufferedReader reader, final int[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.ints.IntBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	String s;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final int[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   if ((s = reader.readLine()) != null) t[d] = Integer.parseInt(s.trim());
	   else return c;
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static long loadInts(final BufferedReader reader, final int[][] array) throws IOException {
	return loadInts(reader, array, 0, it.unimi.dsi.fastutil.ints.IntBigArrays.length(array));
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big-array fragment.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadInts(final File file, final int[][] array, final long offset, final long length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final long result = loadInts(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadInts(final CharSequence filename, final int[][] array, final long offset, final long length) throws IOException {
	return loadInts(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadInts(final File file, final int[][] array) throws IOException {
	return loadInts(file, array, 0, it.unimi.dsi.fastutil.ints.IntBigArrays.length(array));
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadInts(final CharSequence filename, final int[][] array) throws IOException {
	return loadInts(filename, array, 0, it.unimi.dsi.fastutil.ints.IntBigArrays.length(array));
}
/** Stores a big-array fragment to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeInts(final int array[][], final long offset, final long length, final PrintStream stream) {
	it.unimi.dsi.fastutil.ints.IntBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final int[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) stream.println(t[d]);
	}
}
/** Stores a big array to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeInts(final int array[][], final PrintStream stream) {
	storeInts(array, 0, it.unimi.dsi.fastutil.ints.IntBigArrays.length(array), stream);
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeInts(final int array[][], final long offset, final long length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeInts(array, offset, length, stream);
	stream.close();
}
/** Stores a big-array fragment to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeInts(final int array[][], final long offset, final long length, final CharSequence filename) throws IOException {
	storeInts(array, offset, length, new File(filename.toString()));
}
/** Stores a big array to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeInts(final int array[][], final File file) throws IOException {
	storeInts(array, 0, it.unimi.dsi.fastutil.ints.IntBigArrays.length(array), file);
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeInts(final int array[][], final CharSequence filename) throws IOException {
	storeInts(array, 0, it.unimi.dsi.fastutil.ints.IntBigArrays.length(array), filename);
}
/** A wrapper that exhibits the content of a reader as a type-specific iterator. */
private static final class IntReaderWrapper implements IntIterator {
	private final BufferedReader reader;
	private boolean toAdvance = true;
	private String s;
	private int next;
	public IntReaderWrapper(final BufferedReader reader) {
	 this.reader = reader;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return s != null;
	 toAdvance = false;
	 try {
	  s = reader.readLine();
	 }
	 catch(EOFException itsOk) {}
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 if (s == null) return false;
	 next = Integer.parseInt(s.trim());
	 return true;
	}
	@Override
	public int nextInt() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given buffered reader into an iterator.
	*
	* @param reader a buffered reader.
	*/
public static IntIterator asIntIterator(final BufferedReader reader) {
	return new IntReaderWrapper(reader);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static IntIterator asIntIterator(final File file) throws IOException {
	return new IntReaderWrapper(new BufferedReader(new FileReader(file)));
}
/** Wraps a file given by a pathname into an iterator.
	*
	* @param filename a filename.
	*/
public static IntIterator asIntIterator(final CharSequence filename) throws IOException {
	return asIntIterator(new File(filename.toString()));
}
/** Wraps a file given by a {@link File} object into an iterable object.
	*
	* @param file a file.
	*/
public static IntIterable asIntIterable(final File file) {
	return () -> {
	 try { return asIntIterator(file); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/** Wraps a file given by a pathname into an iterable object.
	*
	* @param filename a filename.
	*/
public static IntIterable asIntIterable(final CharSequence filename) {
	return () -> {
	 try { return asIntIterator(filename); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/* Generic definitions */
/* Assertions (useful to generate conditional code) */
/* Current type and class (and size, if applicable) */
/* Value methods */
/* Interfaces (keys) */
/* Interfaces (values) */
/* Abstract implementations (keys) */
/* Abstract implementations (values) */
/* Static containers (keys) */
/* Static containers (values) */
/* Implementations */
/* Synchronized wrappers */
/* Unmodifiable wrappers */
/* Other wrappers */
/* Methods (keys) */
/* Methods (values) */
/* Methods (keys/values) */
/* Methods that have special names depending on keys (but the special names depend on values) */
/* Equality */
/* Object/Reference-only definitions (keys) */
/* Primitive-type-only definitions (keys) */
/* Object/Reference-only definitions (values) */
/* START_OF_JAVA_SOURCE */
/*
	* Copyright (C) 2004-2017 Sebastiano Vigna
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
/** Loads elements from a given fast buffered reader, storing them in a given array fragment.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static int loadLongs(final BufferedReader reader, final long[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.longs.LongArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	String s;
	try {
	 for(i = 0; i < length; i++)
	  if ((s = reader.readLine()) != null) array[i + offset] = Long.parseLong(s.trim());
	  else break;
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static int loadLongs(final BufferedReader reader, final long[] array) throws IOException {
	return loadLongs(reader, array, 0, array.length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array fragment.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadLongs(final File file, final long[] array, final int offset, final int length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final int result = loadLongs(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadLongs(final CharSequence filename, final long[] array, final int offset, final int length) throws IOException {
	return loadLongs(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadLongs(final File file, final long[] array) throws IOException {
	return loadLongs(file, array, 0, array.length);
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadLongs(final CharSequence filename, final long[] array) throws IOException {
	return loadLongs(filename, array, 0, array.length);
}
/** Stores an array fragment to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeLongs(final long array[], final int offset, final int length, final PrintStream stream) {
	it.unimi.dsi.fastutil.longs.LongArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) stream.println(array[offset + i]);
}
/** Stores an array to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeLongs(final long array[], final PrintStream stream) {
	storeLongs(array, 0, array.length, stream);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeLongs(final long array[], final int offset, final int length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeLongs(array, offset, length, stream);
	stream.close();
}
/** Stores an array fragment to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeLongs(final long array[], final int offset, final int length, final CharSequence filename) throws IOException {
	storeLongs(array, offset, length, new File(filename.toString()));
}
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeLongs(final long array[], final File file) throws IOException {
	storeLongs(array, 0, array.length, file);
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeLongs(final long array[], final CharSequence filename) throws IOException {
	storeLongs(array, 0, array.length, filename);
}
/** Stores the element returned by an iterator to a given print stream.
	*
	* @param i an iterator whose output will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeLongs(final LongIterator i, final PrintStream stream) {
	while(i.hasNext()) stream.println(i.nextLong());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeLongs(final LongIterator i, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeLongs(i, stream);
	stream.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeLongs(final LongIterator i, final CharSequence filename) throws IOException {
	storeLongs(i, new File(filename.toString()));
}
/** Loads elements from a given fast buffered reader, storing them in a given big-array fragment.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static long loadLongs(final BufferedReader reader, final long[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.longs.LongBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	String s;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final long[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   if ((s = reader.readLine()) != null) t[d] = Long.parseLong(s.trim());
	   else return c;
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static long loadLongs(final BufferedReader reader, final long[][] array) throws IOException {
	return loadLongs(reader, array, 0, it.unimi.dsi.fastutil.longs.LongBigArrays.length(array));
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big-array fragment.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadLongs(final File file, final long[][] array, final long offset, final long length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final long result = loadLongs(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadLongs(final CharSequence filename, final long[][] array, final long offset, final long length) throws IOException {
	return loadLongs(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadLongs(final File file, final long[][] array) throws IOException {
	return loadLongs(file, array, 0, it.unimi.dsi.fastutil.longs.LongBigArrays.length(array));
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadLongs(final CharSequence filename, final long[][] array) throws IOException {
	return loadLongs(filename, array, 0, it.unimi.dsi.fastutil.longs.LongBigArrays.length(array));
}
/** Stores a big-array fragment to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeLongs(final long array[][], final long offset, final long length, final PrintStream stream) {
	it.unimi.dsi.fastutil.longs.LongBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final long[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) stream.println(t[d]);
	}
}
/** Stores a big array to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeLongs(final long array[][], final PrintStream stream) {
	storeLongs(array, 0, it.unimi.dsi.fastutil.longs.LongBigArrays.length(array), stream);
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeLongs(final long array[][], final long offset, final long length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeLongs(array, offset, length, stream);
	stream.close();
}
/** Stores a big-array fragment to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeLongs(final long array[][], final long offset, final long length, final CharSequence filename) throws IOException {
	storeLongs(array, offset, length, new File(filename.toString()));
}
/** Stores a big array to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeLongs(final long array[][], final File file) throws IOException {
	storeLongs(array, 0, it.unimi.dsi.fastutil.longs.LongBigArrays.length(array), file);
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeLongs(final long array[][], final CharSequence filename) throws IOException {
	storeLongs(array, 0, it.unimi.dsi.fastutil.longs.LongBigArrays.length(array), filename);
}
/** A wrapper that exhibits the content of a reader as a type-specific iterator. */
private static final class LongReaderWrapper implements LongIterator {
	private final BufferedReader reader;
	private boolean toAdvance = true;
	private String s;
	private long next;
	public LongReaderWrapper(final BufferedReader reader) {
	 this.reader = reader;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return s != null;
	 toAdvance = false;
	 try {
	  s = reader.readLine();
	 }
	 catch(EOFException itsOk) {}
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 if (s == null) return false;
	 next = Long.parseLong(s.trim());
	 return true;
	}
	@Override
	public long nextLong() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given buffered reader into an iterator.
	*
	* @param reader a buffered reader.
	*/
public static LongIterator asLongIterator(final BufferedReader reader) {
	return new LongReaderWrapper(reader);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static LongIterator asLongIterator(final File file) throws IOException {
	return new LongReaderWrapper(new BufferedReader(new FileReader(file)));
}
/** Wraps a file given by a pathname into an iterator.
	*
	* @param filename a filename.
	*/
public static LongIterator asLongIterator(final CharSequence filename) throws IOException {
	return asLongIterator(new File(filename.toString()));
}
/** Wraps a file given by a {@link File} object into an iterable object.
	*
	* @param file a file.
	*/
public static LongIterable asLongIterable(final File file) {
	return () -> {
	 try { return asLongIterator(file); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/** Wraps a file given by a pathname into an iterable object.
	*
	* @param filename a filename.
	*/
public static LongIterable asLongIterable(final CharSequence filename) {
	return () -> {
	 try { return asLongIterator(filename); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/* Generic definitions */
/* Assertions (useful to generate conditional code) */
/* Current type and class (and size, if applicable) */
/* Value methods */
/* Interfaces (keys) */
/* Interfaces (values) */
/* Abstract implementations (keys) */
/* Abstract implementations (values) */
/* Static containers (keys) */
/* Static containers (values) */
/* Implementations */
/* Synchronized wrappers */
/* Unmodifiable wrappers */
/* Other wrappers */
/* Methods (keys) */
/* Methods (values) */
/* Methods (keys/values) */
/* Methods that have special names depending on keys (but the special names depend on values) */
/* Equality */
/* Object/Reference-only definitions (keys) */
/* Primitive-type-only definitions (keys) */
/* Object/Reference-only definitions (values) */
/* START_OF_JAVA_SOURCE */
/*
	* Copyright (C) 2004-2017 Sebastiano Vigna
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
/** Loads elements from a given fast buffered reader, storing them in a given array fragment.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static int loadDoubles(final BufferedReader reader, final double[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.doubles.DoubleArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	String s;
	try {
	 for(i = 0; i < length; i++)
	  if ((s = reader.readLine()) != null) array[i + offset] = Double.parseDouble(s.trim());
	  else break;
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static int loadDoubles(final BufferedReader reader, final double[] array) throws IOException {
	return loadDoubles(reader, array, 0, array.length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array fragment.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadDoubles(final File file, final double[] array, final int offset, final int length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final int result = loadDoubles(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadDoubles(final CharSequence filename, final double[] array, final int offset, final int length) throws IOException {
	return loadDoubles(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadDoubles(final File file, final double[] array) throws IOException {
	return loadDoubles(file, array, 0, array.length);
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadDoubles(final CharSequence filename, final double[] array) throws IOException {
	return loadDoubles(filename, array, 0, array.length);
}
/** Stores an array fragment to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeDoubles(final double array[], final int offset, final int length, final PrintStream stream) {
	it.unimi.dsi.fastutil.doubles.DoubleArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) stream.println(array[offset + i]);
}
/** Stores an array to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeDoubles(final double array[], final PrintStream stream) {
	storeDoubles(array, 0, array.length, stream);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeDoubles(final double array[], final int offset, final int length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeDoubles(array, offset, length, stream);
	stream.close();
}
/** Stores an array fragment to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeDoubles(final double array[], final int offset, final int length, final CharSequence filename) throws IOException {
	storeDoubles(array, offset, length, new File(filename.toString()));
}
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeDoubles(final double array[], final File file) throws IOException {
	storeDoubles(array, 0, array.length, file);
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeDoubles(final double array[], final CharSequence filename) throws IOException {
	storeDoubles(array, 0, array.length, filename);
}
/** Stores the element returned by an iterator to a given print stream.
	*
	* @param i an iterator whose output will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeDoubles(final DoubleIterator i, final PrintStream stream) {
	while(i.hasNext()) stream.println(i.nextDouble());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeDoubles(final DoubleIterator i, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeDoubles(i, stream);
	stream.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeDoubles(final DoubleIterator i, final CharSequence filename) throws IOException {
	storeDoubles(i, new File(filename.toString()));
}
/** Loads elements from a given fast buffered reader, storing them in a given big-array fragment.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static long loadDoubles(final BufferedReader reader, final double[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.doubles.DoubleBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	String s;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final double[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   if ((s = reader.readLine()) != null) t[d] = Double.parseDouble(s.trim());
	   else return c;
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static long loadDoubles(final BufferedReader reader, final double[][] array) throws IOException {
	return loadDoubles(reader, array, 0, it.unimi.dsi.fastutil.doubles.DoubleBigArrays.length(array));
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big-array fragment.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadDoubles(final File file, final double[][] array, final long offset, final long length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final long result = loadDoubles(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadDoubles(final CharSequence filename, final double[][] array, final long offset, final long length) throws IOException {
	return loadDoubles(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadDoubles(final File file, final double[][] array) throws IOException {
	return loadDoubles(file, array, 0, it.unimi.dsi.fastutil.doubles.DoubleBigArrays.length(array));
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadDoubles(final CharSequence filename, final double[][] array) throws IOException {
	return loadDoubles(filename, array, 0, it.unimi.dsi.fastutil.doubles.DoubleBigArrays.length(array));
}
/** Stores a big-array fragment to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeDoubles(final double array[][], final long offset, final long length, final PrintStream stream) {
	it.unimi.dsi.fastutil.doubles.DoubleBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final double[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) stream.println(t[d]);
	}
}
/** Stores a big array to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeDoubles(final double array[][], final PrintStream stream) {
	storeDoubles(array, 0, it.unimi.dsi.fastutil.doubles.DoubleBigArrays.length(array), stream);
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeDoubles(final double array[][], final long offset, final long length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeDoubles(array, offset, length, stream);
	stream.close();
}
/** Stores a big-array fragment to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeDoubles(final double array[][], final long offset, final long length, final CharSequence filename) throws IOException {
	storeDoubles(array, offset, length, new File(filename.toString()));
}
/** Stores a big array to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeDoubles(final double array[][], final File file) throws IOException {
	storeDoubles(array, 0, it.unimi.dsi.fastutil.doubles.DoubleBigArrays.length(array), file);
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeDoubles(final double array[][], final CharSequence filename) throws IOException {
	storeDoubles(array, 0, it.unimi.dsi.fastutil.doubles.DoubleBigArrays.length(array), filename);
}
/** A wrapper that exhibits the content of a reader as a type-specific iterator. */
private static final class DoubleReaderWrapper implements DoubleIterator {
	private final BufferedReader reader;
	private boolean toAdvance = true;
	private String s;
	private double next;
	public DoubleReaderWrapper(final BufferedReader reader) {
	 this.reader = reader;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return s != null;
	 toAdvance = false;
	 try {
	  s = reader.readLine();
	 }
	 catch(EOFException itsOk) {}
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 if (s == null) return false;
	 next = Double.parseDouble(s.trim());
	 return true;
	}
	@Override
	public double nextDouble() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given buffered reader into an iterator.
	*
	* @param reader a buffered reader.
	*/
public static DoubleIterator asDoubleIterator(final BufferedReader reader) {
	return new DoubleReaderWrapper(reader);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static DoubleIterator asDoubleIterator(final File file) throws IOException {
	return new DoubleReaderWrapper(new BufferedReader(new FileReader(file)));
}
/** Wraps a file given by a pathname into an iterator.
	*
	* @param filename a filename.
	*/
public static DoubleIterator asDoubleIterator(final CharSequence filename) throws IOException {
	return asDoubleIterator(new File(filename.toString()));
}
/** Wraps a file given by a {@link File} object into an iterable object.
	*
	* @param file a file.
	*/
public static DoubleIterable asDoubleIterable(final File file) {
	return () -> {
	 try { return asDoubleIterator(file); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/** Wraps a file given by a pathname into an iterable object.
	*
	* @param filename a filename.
	*/
public static DoubleIterable asDoubleIterable(final CharSequence filename) {
	return () -> {
	 try { return asDoubleIterator(filename); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/* Generic definitions */
/* Assertions (useful to generate conditional code) */
/* Current type and class (and size, if applicable) */
/* Value methods */
/* Interfaces (keys) */
/* Interfaces (values) */
/* Abstract implementations (keys) */
/* Abstract implementations (values) */
/* Static containers (keys) */
/* Static containers (values) */
/* Implementations */
/* Synchronized wrappers */
/* Unmodifiable wrappers */
/* Other wrappers */
/* Methods (keys) */
/* Methods (values) */
/* Methods (keys/values) */
/* Methods that have special names depending on keys (but the special names depend on values) */
/* Equality */
/* Object/Reference-only definitions (keys) */
/* Primitive-type-only definitions (keys) */
/* Object/Reference-only definitions (values) */
/* START_OF_JAVA_SOURCE */
/*
	* Copyright (C) 2004-2017 Sebastiano Vigna
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
/** Loads elements from a given fast buffered reader, storing them in a given array fragment.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static int loadBooleans(final BufferedReader reader, final boolean[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.booleans.BooleanArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	String s;
	try {
	 for(i = 0; i < length; i++)
	  if ((s = reader.readLine()) != null) array[i + offset] = Boolean.parseBoolean(s.trim());
	  else break;
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static int loadBooleans(final BufferedReader reader, final boolean[] array) throws IOException {
	return loadBooleans(reader, array, 0, array.length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array fragment.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadBooleans(final File file, final boolean[] array, final int offset, final int length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final int result = loadBooleans(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadBooleans(final CharSequence filename, final boolean[] array, final int offset, final int length) throws IOException {
	return loadBooleans(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadBooleans(final File file, final boolean[] array) throws IOException {
	return loadBooleans(file, array, 0, array.length);
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadBooleans(final CharSequence filename, final boolean[] array) throws IOException {
	return loadBooleans(filename, array, 0, array.length);
}
/** Stores an array fragment to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeBooleans(final boolean array[], final int offset, final int length, final PrintStream stream) {
	it.unimi.dsi.fastutil.booleans.BooleanArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) stream.println(array[offset + i]);
}
/** Stores an array to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeBooleans(final boolean array[], final PrintStream stream) {
	storeBooleans(array, 0, array.length, stream);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeBooleans(final boolean array[], final int offset, final int length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeBooleans(array, offset, length, stream);
	stream.close();
}
/** Stores an array fragment to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeBooleans(final boolean array[], final int offset, final int length, final CharSequence filename) throws IOException {
	storeBooleans(array, offset, length, new File(filename.toString()));
}
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeBooleans(final boolean array[], final File file) throws IOException {
	storeBooleans(array, 0, array.length, file);
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeBooleans(final boolean array[], final CharSequence filename) throws IOException {
	storeBooleans(array, 0, array.length, filename);
}
/** Stores the element returned by an iterator to a given print stream.
	*
	* @param i an iterator whose output will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeBooleans(final BooleanIterator i, final PrintStream stream) {
	while(i.hasNext()) stream.println(i.nextBoolean());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeBooleans(final BooleanIterator i, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeBooleans(i, stream);
	stream.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeBooleans(final BooleanIterator i, final CharSequence filename) throws IOException {
	storeBooleans(i, new File(filename.toString()));
}
/** Loads elements from a given fast buffered reader, storing them in a given big-array fragment.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static long loadBooleans(final BufferedReader reader, final boolean[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.booleans.BooleanBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	String s;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final boolean[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   if ((s = reader.readLine()) != null) t[d] = Boolean.parseBoolean(s.trim());
	   else return c;
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static long loadBooleans(final BufferedReader reader, final boolean[][] array) throws IOException {
	return loadBooleans(reader, array, 0, it.unimi.dsi.fastutil.booleans.BooleanBigArrays.length(array));
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big-array fragment.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadBooleans(final File file, final boolean[][] array, final long offset, final long length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final long result = loadBooleans(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadBooleans(final CharSequence filename, final boolean[][] array, final long offset, final long length) throws IOException {
	return loadBooleans(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadBooleans(final File file, final boolean[][] array) throws IOException {
	return loadBooleans(file, array, 0, it.unimi.dsi.fastutil.booleans.BooleanBigArrays.length(array));
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadBooleans(final CharSequence filename, final boolean[][] array) throws IOException {
	return loadBooleans(filename, array, 0, it.unimi.dsi.fastutil.booleans.BooleanBigArrays.length(array));
}
/** Stores a big-array fragment to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeBooleans(final boolean array[][], final long offset, final long length, final PrintStream stream) {
	it.unimi.dsi.fastutil.booleans.BooleanBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final boolean[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) stream.println(t[d]);
	}
}
/** Stores a big array to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeBooleans(final boolean array[][], final PrintStream stream) {
	storeBooleans(array, 0, it.unimi.dsi.fastutil.booleans.BooleanBigArrays.length(array), stream);
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeBooleans(final boolean array[][], final long offset, final long length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeBooleans(array, offset, length, stream);
	stream.close();
}
/** Stores a big-array fragment to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeBooleans(final boolean array[][], final long offset, final long length, final CharSequence filename) throws IOException {
	storeBooleans(array, offset, length, new File(filename.toString()));
}
/** Stores a big array to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeBooleans(final boolean array[][], final File file) throws IOException {
	storeBooleans(array, 0, it.unimi.dsi.fastutil.booleans.BooleanBigArrays.length(array), file);
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeBooleans(final boolean array[][], final CharSequence filename) throws IOException {
	storeBooleans(array, 0, it.unimi.dsi.fastutil.booleans.BooleanBigArrays.length(array), filename);
}
/** A wrapper that exhibits the content of a reader as a type-specific iterator. */
private static final class BooleanReaderWrapper implements BooleanIterator {
	private final BufferedReader reader;
	private boolean toAdvance = true;
	private String s;
	private boolean next;
	public BooleanReaderWrapper(final BufferedReader reader) {
	 this.reader = reader;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return s != null;
	 toAdvance = false;
	 try {
	  s = reader.readLine();
	 }
	 catch(EOFException itsOk) {}
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 if (s == null) return false;
	 next = Boolean.parseBoolean(s.trim());
	 return true;
	}
	@Override
	public boolean nextBoolean() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given buffered reader into an iterator.
	*
	* @param reader a buffered reader.
	*/
public static BooleanIterator asBooleanIterator(final BufferedReader reader) {
	return new BooleanReaderWrapper(reader);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static BooleanIterator asBooleanIterator(final File file) throws IOException {
	return new BooleanReaderWrapper(new BufferedReader(new FileReader(file)));
}
/** Wraps a file given by a pathname into an iterator.
	*
	* @param filename a filename.
	*/
public static BooleanIterator asBooleanIterator(final CharSequence filename) throws IOException {
	return asBooleanIterator(new File(filename.toString()));
}
/** Wraps a file given by a {@link File} object into an iterable object.
	*
	* @param file a file.
	*/
public static BooleanIterable asBooleanIterable(final File file) {
	return () -> {
	 try { return asBooleanIterator(file); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/** Wraps a file given by a pathname into an iterable object.
	*
	* @param filename a filename.
	*/
public static BooleanIterable asBooleanIterable(final CharSequence filename) {
	return () -> {
	 try { return asBooleanIterator(filename); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/* Generic definitions */
/* Assertions (useful to generate conditional code) */
/* Current type and class (and size, if applicable) */
/* Value methods */
/* Interfaces (keys) */
/* Interfaces (values) */
/* Abstract implementations (keys) */
/* Abstract implementations (values) */
/* Static containers (keys) */
/* Static containers (values) */
/* Implementations */
/* Synchronized wrappers */
/* Unmodifiable wrappers */
/* Other wrappers */
/* Methods (keys) */
/* Methods (values) */
/* Methods (keys/values) */
/* Methods that have special names depending on keys (but the special names depend on values) */
/* Equality */
/* Object/Reference-only definitions (keys) */
/* Primitive-type-only definitions (keys) */
/* Object/Reference-only definitions (values) */
/* START_OF_JAVA_SOURCE */
/*
	* Copyright (C) 2004-2017 Sebastiano Vigna
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
/** Loads elements from a given fast buffered reader, storing them in a given array fragment.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static int loadBytes(final BufferedReader reader, final byte[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.bytes.ByteArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	String s;
	try {
	 for(i = 0; i < length; i++)
	  if ((s = reader.readLine()) != null) array[i + offset] = Byte.parseByte(s.trim());
	  else break;
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static int loadBytes(final BufferedReader reader, final byte[] array) throws IOException {
	return loadBytes(reader, array, 0, array.length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array fragment.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadBytes(final File file, final byte[] array, final int offset, final int length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final int result = loadBytes(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadBytes(final CharSequence filename, final byte[] array, final int offset, final int length) throws IOException {
	return loadBytes(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadBytes(final File file, final byte[] array) throws IOException {
	return loadBytes(file, array, 0, array.length);
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadBytes(final CharSequence filename, final byte[] array) throws IOException {
	return loadBytes(filename, array, 0, array.length);
}
/** Stores an array fragment to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeBytes(final byte array[], final int offset, final int length, final PrintStream stream) {
	it.unimi.dsi.fastutil.bytes.ByteArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) stream.println(array[offset + i]);
}
/** Stores an array to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeBytes(final byte array[], final PrintStream stream) {
	storeBytes(array, 0, array.length, stream);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeBytes(final byte array[], final int offset, final int length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeBytes(array, offset, length, stream);
	stream.close();
}
/** Stores an array fragment to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeBytes(final byte array[], final int offset, final int length, final CharSequence filename) throws IOException {
	storeBytes(array, offset, length, new File(filename.toString()));
}
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeBytes(final byte array[], final File file) throws IOException {
	storeBytes(array, 0, array.length, file);
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeBytes(final byte array[], final CharSequence filename) throws IOException {
	storeBytes(array, 0, array.length, filename);
}
/** Stores the element returned by an iterator to a given print stream.
	*
	* @param i an iterator whose output will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeBytes(final ByteIterator i, final PrintStream stream) {
	while(i.hasNext()) stream.println(i.nextByte());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeBytes(final ByteIterator i, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeBytes(i, stream);
	stream.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeBytes(final ByteIterator i, final CharSequence filename) throws IOException {
	storeBytes(i, new File(filename.toString()));
}
/** Loads elements from a given fast buffered reader, storing them in a given big-array fragment.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static long loadBytes(final BufferedReader reader, final byte[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.bytes.ByteBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	String s;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final byte[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   if ((s = reader.readLine()) != null) t[d] = Byte.parseByte(s.trim());
	   else return c;
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static long loadBytes(final BufferedReader reader, final byte[][] array) throws IOException {
	return loadBytes(reader, array, 0, it.unimi.dsi.fastutil.bytes.ByteBigArrays.length(array));
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big-array fragment.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadBytes(final File file, final byte[][] array, final long offset, final long length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final long result = loadBytes(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadBytes(final CharSequence filename, final byte[][] array, final long offset, final long length) throws IOException {
	return loadBytes(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadBytes(final File file, final byte[][] array) throws IOException {
	return loadBytes(file, array, 0, it.unimi.dsi.fastutil.bytes.ByteBigArrays.length(array));
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadBytes(final CharSequence filename, final byte[][] array) throws IOException {
	return loadBytes(filename, array, 0, it.unimi.dsi.fastutil.bytes.ByteBigArrays.length(array));
}
/** Stores a big-array fragment to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeBytes(final byte array[][], final long offset, final long length, final PrintStream stream) {
	it.unimi.dsi.fastutil.bytes.ByteBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final byte[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) stream.println(t[d]);
	}
}
/** Stores a big array to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeBytes(final byte array[][], final PrintStream stream) {
	storeBytes(array, 0, it.unimi.dsi.fastutil.bytes.ByteBigArrays.length(array), stream);
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeBytes(final byte array[][], final long offset, final long length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeBytes(array, offset, length, stream);
	stream.close();
}
/** Stores a big-array fragment to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeBytes(final byte array[][], final long offset, final long length, final CharSequence filename) throws IOException {
	storeBytes(array, offset, length, new File(filename.toString()));
}
/** Stores a big array to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeBytes(final byte array[][], final File file) throws IOException {
	storeBytes(array, 0, it.unimi.dsi.fastutil.bytes.ByteBigArrays.length(array), file);
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeBytes(final byte array[][], final CharSequence filename) throws IOException {
	storeBytes(array, 0, it.unimi.dsi.fastutil.bytes.ByteBigArrays.length(array), filename);
}
/** A wrapper that exhibits the content of a reader as a type-specific iterator. */
private static final class ByteReaderWrapper implements ByteIterator {
	private final BufferedReader reader;
	private boolean toAdvance = true;
	private String s;
	private byte next;
	public ByteReaderWrapper(final BufferedReader reader) {
	 this.reader = reader;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return s != null;
	 toAdvance = false;
	 try {
	  s = reader.readLine();
	 }
	 catch(EOFException itsOk) {}
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 if (s == null) return false;
	 next = Byte.parseByte(s.trim());
	 return true;
	}
	@Override
	public byte nextByte() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given buffered reader into an iterator.
	*
	* @param reader a buffered reader.
	*/
public static ByteIterator asByteIterator(final BufferedReader reader) {
	return new ByteReaderWrapper(reader);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static ByteIterator asByteIterator(final File file) throws IOException {
	return new ByteReaderWrapper(new BufferedReader(new FileReader(file)));
}
/** Wraps a file given by a pathname into an iterator.
	*
	* @param filename a filename.
	*/
public static ByteIterator asByteIterator(final CharSequence filename) throws IOException {
	return asByteIterator(new File(filename.toString()));
}
/** Wraps a file given by a {@link File} object into an iterable object.
	*
	* @param file a file.
	*/
public static ByteIterable asByteIterable(final File file) {
	return () -> {
	 try { return asByteIterator(file); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/** Wraps a file given by a pathname into an iterable object.
	*
	* @param filename a filename.
	*/
public static ByteIterable asByteIterable(final CharSequence filename) {
	return () -> {
	 try { return asByteIterator(filename); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/* Generic definitions */
/* Assertions (useful to generate conditional code) */
/* Current type and class (and size, if applicable) */
/* Value methods */
/* Interfaces (keys) */
/* Interfaces (values) */
/* Abstract implementations (keys) */
/* Abstract implementations (values) */
/* Static containers (keys) */
/* Static containers (values) */
/* Implementations */
/* Synchronized wrappers */
/* Unmodifiable wrappers */
/* Other wrappers */
/* Methods (keys) */
/* Methods (values) */
/* Methods (keys/values) */
/* Methods that have special names depending on keys (but the special names depend on values) */
/* Equality */
/* Object/Reference-only definitions (keys) */
/* Primitive-type-only definitions (keys) */
/* Object/Reference-only definitions (values) */
/* START_OF_JAVA_SOURCE */
/*
	* Copyright (C) 2004-2017 Sebastiano Vigna
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
/** Loads elements from a given fast buffered reader, storing them in a given array fragment.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static int loadShorts(final BufferedReader reader, final short[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.shorts.ShortArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	String s;
	try {
	 for(i = 0; i < length; i++)
	  if ((s = reader.readLine()) != null) array[i + offset] = Short.parseShort(s.trim());
	  else break;
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static int loadShorts(final BufferedReader reader, final short[] array) throws IOException {
	return loadShorts(reader, array, 0, array.length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array fragment.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadShorts(final File file, final short[] array, final int offset, final int length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final int result = loadShorts(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadShorts(final CharSequence filename, final short[] array, final int offset, final int length) throws IOException {
	return loadShorts(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadShorts(final File file, final short[] array) throws IOException {
	return loadShorts(file, array, 0, array.length);
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadShorts(final CharSequence filename, final short[] array) throws IOException {
	return loadShorts(filename, array, 0, array.length);
}
/** Stores an array fragment to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeShorts(final short array[], final int offset, final int length, final PrintStream stream) {
	it.unimi.dsi.fastutil.shorts.ShortArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) stream.println(array[offset + i]);
}
/** Stores an array to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeShorts(final short array[], final PrintStream stream) {
	storeShorts(array, 0, array.length, stream);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeShorts(final short array[], final int offset, final int length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeShorts(array, offset, length, stream);
	stream.close();
}
/** Stores an array fragment to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeShorts(final short array[], final int offset, final int length, final CharSequence filename) throws IOException {
	storeShorts(array, offset, length, new File(filename.toString()));
}
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeShorts(final short array[], final File file) throws IOException {
	storeShorts(array, 0, array.length, file);
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeShorts(final short array[], final CharSequence filename) throws IOException {
	storeShorts(array, 0, array.length, filename);
}
/** Stores the element returned by an iterator to a given print stream.
	*
	* @param i an iterator whose output will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeShorts(final ShortIterator i, final PrintStream stream) {
	while(i.hasNext()) stream.println(i.nextShort());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeShorts(final ShortIterator i, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeShorts(i, stream);
	stream.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeShorts(final ShortIterator i, final CharSequence filename) throws IOException {
	storeShorts(i, new File(filename.toString()));
}
/** Loads elements from a given fast buffered reader, storing them in a given big-array fragment.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static long loadShorts(final BufferedReader reader, final short[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.shorts.ShortBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	String s;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final short[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   if ((s = reader.readLine()) != null) t[d] = Short.parseShort(s.trim());
	   else return c;
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static long loadShorts(final BufferedReader reader, final short[][] array) throws IOException {
	return loadShorts(reader, array, 0, it.unimi.dsi.fastutil.shorts.ShortBigArrays.length(array));
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big-array fragment.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadShorts(final File file, final short[][] array, final long offset, final long length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final long result = loadShorts(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadShorts(final CharSequence filename, final short[][] array, final long offset, final long length) throws IOException {
	return loadShorts(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadShorts(final File file, final short[][] array) throws IOException {
	return loadShorts(file, array, 0, it.unimi.dsi.fastutil.shorts.ShortBigArrays.length(array));
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadShorts(final CharSequence filename, final short[][] array) throws IOException {
	return loadShorts(filename, array, 0, it.unimi.dsi.fastutil.shorts.ShortBigArrays.length(array));
}
/** Stores a big-array fragment to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeShorts(final short array[][], final long offset, final long length, final PrintStream stream) {
	it.unimi.dsi.fastutil.shorts.ShortBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final short[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) stream.println(t[d]);
	}
}
/** Stores a big array to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeShorts(final short array[][], final PrintStream stream) {
	storeShorts(array, 0, it.unimi.dsi.fastutil.shorts.ShortBigArrays.length(array), stream);
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeShorts(final short array[][], final long offset, final long length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeShorts(array, offset, length, stream);
	stream.close();
}
/** Stores a big-array fragment to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeShorts(final short array[][], final long offset, final long length, final CharSequence filename) throws IOException {
	storeShorts(array, offset, length, new File(filename.toString()));
}
/** Stores a big array to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeShorts(final short array[][], final File file) throws IOException {
	storeShorts(array, 0, it.unimi.dsi.fastutil.shorts.ShortBigArrays.length(array), file);
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeShorts(final short array[][], final CharSequence filename) throws IOException {
	storeShorts(array, 0, it.unimi.dsi.fastutil.shorts.ShortBigArrays.length(array), filename);
}
/** A wrapper that exhibits the content of a reader as a type-specific iterator. */
private static final class ShortReaderWrapper implements ShortIterator {
	private final BufferedReader reader;
	private boolean toAdvance = true;
	private String s;
	private short next;
	public ShortReaderWrapper(final BufferedReader reader) {
	 this.reader = reader;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return s != null;
	 toAdvance = false;
	 try {
	  s = reader.readLine();
	 }
	 catch(EOFException itsOk) {}
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 if (s == null) return false;
	 next = Short.parseShort(s.trim());
	 return true;
	}
	@Override
	public short nextShort() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given buffered reader into an iterator.
	*
	* @param reader a buffered reader.
	*/
public static ShortIterator asShortIterator(final BufferedReader reader) {
	return new ShortReaderWrapper(reader);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static ShortIterator asShortIterator(final File file) throws IOException {
	return new ShortReaderWrapper(new BufferedReader(new FileReader(file)));
}
/** Wraps a file given by a pathname into an iterator.
	*
	* @param filename a filename.
	*/
public static ShortIterator asShortIterator(final CharSequence filename) throws IOException {
	return asShortIterator(new File(filename.toString()));
}
/** Wraps a file given by a {@link File} object into an iterable object.
	*
	* @param file a file.
	*/
public static ShortIterable asShortIterable(final File file) {
	return () -> {
	 try { return asShortIterator(file); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/** Wraps a file given by a pathname into an iterable object.
	*
	* @param filename a filename.
	*/
public static ShortIterable asShortIterable(final CharSequence filename) {
	return () -> {
	 try { return asShortIterator(filename); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/* Generic definitions */
/* Assertions (useful to generate conditional code) */
/* Current type and class (and size, if applicable) */
/* Value methods */
/* Interfaces (keys) */
/* Interfaces (values) */
/* Abstract implementations (keys) */
/* Abstract implementations (values) */
/* Static containers (keys) */
/* Static containers (values) */
/* Implementations */
/* Synchronized wrappers */
/* Unmodifiable wrappers */
/* Other wrappers */
/* Methods (keys) */
/* Methods (values) */
/* Methods (keys/values) */
/* Methods that have special names depending on keys (but the special names depend on values) */
/* Equality */
/* Object/Reference-only definitions (keys) */
/* Primitive-type-only definitions (keys) */
/* Object/Reference-only definitions (values) */
/* START_OF_JAVA_SOURCE */
/*
	* Copyright (C) 2004-2017 Sebastiano Vigna
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
/** Loads elements from a given fast buffered reader, storing them in a given array fragment.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static int loadFloats(final BufferedReader reader, final float[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.floats.FloatArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	String s;
	try {
	 for(i = 0; i < length; i++)
	  if ((s = reader.readLine()) != null) array[i + offset] = Float.parseFloat(s.trim());
	  else break;
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array an array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static int loadFloats(final BufferedReader reader, final float[] array) throws IOException {
	return loadFloats(reader, array, 0, array.length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array fragment.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadFloats(final File file, final float[] array, final int offset, final int length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final int result = loadFloats(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadFloats(final CharSequence filename, final float[] array, final int offset, final int length) throws IOException {
	return loadFloats(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadFloats(final File file, final float[] array) throws IOException {
	return loadFloats(file, array, 0, array.length);
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadFloats(final CharSequence filename, final float[] array) throws IOException {
	return loadFloats(filename, array, 0, array.length);
}
/** Stores an array fragment to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeFloats(final float array[], final int offset, final int length, final PrintStream stream) {
	it.unimi.dsi.fastutil.floats.FloatArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) stream.println(array[offset + i]);
}
/** Stores an array to a given print stream.
	*
	* @param array an array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeFloats(final float array[], final PrintStream stream) {
	storeFloats(array, 0, array.length, stream);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeFloats(final float array[], final int offset, final int length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeFloats(array, offset, length, stream);
	stream.close();
}
/** Stores an array fragment to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeFloats(final float array[], final int offset, final int length, final CharSequence filename) throws IOException {
	storeFloats(array, offset, length, new File(filename.toString()));
}
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeFloats(final float array[], final File file) throws IOException {
	storeFloats(array, 0, array.length, file);
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeFloats(final float array[], final CharSequence filename) throws IOException {
	storeFloats(array, 0, array.length, filename);
}
/** Stores the element returned by an iterator to a given print stream.
	*
	* @param i an iterator whose output will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeFloats(final FloatIterator i, final PrintStream stream) {
	while(i.hasNext()) stream.println(i.nextFloat());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeFloats(final FloatIterator i, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeFloats(i, stream);
	stream.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeFloats(final FloatIterator i, final CharSequence filename) throws IOException {
	storeFloats(i, new File(filename.toString()));
}
/** Loads elements from a given fast buffered reader, storing them in a given big-array fragment.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code reader} (it might be less than {@code length} if {@code reader} ends).
	*/
public static long loadFloats(final BufferedReader reader, final float[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.floats.FloatBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	String s;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final float[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   if ((s = reader.readLine()) != null) t[d] = Float.parseFloat(s.trim());
	   else return c;
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given buffered reader, storing them in a given array.
	*
	* @param reader a buffered reader.
	* @param array a big array which will be filled with data from {@code reader}.
	* @return the number of elements actually read from {@code reader} (it might be less than the array length if {@code reader} ends).
	*/
public static long loadFloats(final BufferedReader reader, final float[][] array) throws IOException {
	return loadFloats(reader, array, 0, it.unimi.dsi.fastutil.floats.FloatBigArrays.length(array));
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big-array fragment.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadFloats(final File file, final float[][] array, final long offset, final long length) throws IOException {
	final BufferedReader reader = new BufferedReader(new FileReader(file));
	final long result = loadFloats(reader, array, offset, length);
	reader.close();
	return result;
}
/** Loads elements from a file given by a filename, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadFloats(final CharSequence filename, final float[][] array, final long offset, final long length) throws IOException {
	return loadFloats(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadFloats(final File file, final float[][] array) throws IOException {
	return loadFloats(file, array, 0, it.unimi.dsi.fastutil.floats.FloatBigArrays.length(array));
}
/** Loads elements from a file given by a filename, storing them in a given array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadFloats(final CharSequence filename, final float[][] array) throws IOException {
	return loadFloats(filename, array, 0, it.unimi.dsi.fastutil.floats.FloatBigArrays.length(array));
}
/** Stores a big-array fragment to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param stream a print stream.
	*/
public static void storeFloats(final float array[][], final long offset, final long length, final PrintStream stream) {
	it.unimi.dsi.fastutil.floats.FloatBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final float[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) stream.println(t[d]);
	}
}
/** Stores a big array to a given print stream.
	*
	* @param array a big array whose elements will be written to {@code stream}.
	* @param stream a print stream.
	*/
public static void storeFloats(final float array[][], final PrintStream stream) {
	storeFloats(array, 0, it.unimi.dsi.fastutil.floats.FloatBigArrays.length(array), stream);
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeFloats(final float array[][], final long offset, final long length, final File file) throws IOException {
	final PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	storeFloats(array, offset, length, stream);
	stream.close();
}
/** Stores a big-array fragment to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeFloats(final float array[][], final long offset, final long length, final CharSequence filename) throws IOException {
	storeFloats(array, offset, length, new File(filename.toString()));
}
/** Stores a big array to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeFloats(final float array[][], final File file) throws IOException {
	storeFloats(array, 0, it.unimi.dsi.fastutil.floats.FloatBigArrays.length(array), file);
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeFloats(final float array[][], final CharSequence filename) throws IOException {
	storeFloats(array, 0, it.unimi.dsi.fastutil.floats.FloatBigArrays.length(array), filename);
}
/** A wrapper that exhibits the content of a reader as a type-specific iterator. */
private static final class FloatReaderWrapper implements FloatIterator {
	private final BufferedReader reader;
	private boolean toAdvance = true;
	private String s;
	private float next;
	public FloatReaderWrapper(final BufferedReader reader) {
	 this.reader = reader;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return s != null;
	 toAdvance = false;
	 try {
	  s = reader.readLine();
	 }
	 catch(EOFException itsOk) {}
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 if (s == null) return false;
	 next = Float.parseFloat(s.trim());
	 return true;
	}
	@Override
	public float nextFloat() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given buffered reader into an iterator.
	*
	* @param reader a buffered reader.
	*/
public static FloatIterator asFloatIterator(final BufferedReader reader) {
	return new FloatReaderWrapper(reader);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static FloatIterator asFloatIterator(final File file) throws IOException {
	return new FloatReaderWrapper(new BufferedReader(new FileReader(file)));
}
/** Wraps a file given by a pathname into an iterator.
	*
	* @param filename a filename.
	*/
public static FloatIterator asFloatIterator(final CharSequence filename) throws IOException {
	return asFloatIterator(new File(filename.toString()));
}
/** Wraps a file given by a {@link File} object into an iterable object.
	*
	* @param file a file.
	*/
public static FloatIterable asFloatIterable(final File file) {
	return () -> {
	 try { return asFloatIterator(file); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/** Wraps a file given by a pathname into an iterable object.
	*
	* @param filename a filename.
	*/
public static FloatIterable asFloatIterable(final CharSequence filename) {
	return () -> {
	 try { return asFloatIterator(filename); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
}
