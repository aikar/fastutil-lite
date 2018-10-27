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
import static it.unimi.dsi.fastutil.BigArrays.start;
import static it.unimi.dsi.fastutil.BigArrays.segment;
import static it.unimi.dsi.fastutil.BigArrays.displacement;
import java.io.*;
import java.util.*;
import it.unimi.dsi.fastutil.bytes.*;
import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.longs.*;
import it.unimi.dsi.fastutil.doubles.*;
import it.unimi.dsi.fastutil.booleans.*;
import it.unimi.dsi.fastutil.chars.*;
import it.unimi.dsi.fastutil.shorts.*;
import it.unimi.dsi.fastutil.floats.*;
/** Provides static methods to perform easily binary I/O.
	*
	* <p>This class fills some gaps in the Java API. First of all, you have two
	* buffered, easy-to-use methods to {@linkplain #storeObject(Object,CharSequence) store an object to a file}
	* or {@linkplain #loadObject(CharSequence) load an object from a file},
	* and two
	* buffered, easy-to-use methods to {@linkplain #storeObject(Object,OutputStream) store an object to an output stream}
	* or to {@linkplain #loadObject(InputStream) load an object from an input stream}.
	*
	* <p>Second, a natural operation on sequences of primitive elements is to load or
	* store them in binary form using the {@link DataInput} conventions.  This
	* method is much more flexible than storing arrays as objects, as it allows
	* for partial load, partial store, and makes it easy to read the
	* resulting files from other languages.
	*
	* <p>For each primitive type, this class provides methods that read elements
	* from a {@link DataInput} or from a filename into an array. Analogously, there are
	* methods that store the content of an array (fragment) or the elements
	* returned by an iterator to a {@link DataOutput} or to a given filename. Files
	* are buffered using {@link FastBufferedInputStream} and {@link FastBufferedOutputStream}.
	*
	* <p>Since bytes can be read from or written to any stream, additional methods
	* makes it possible to {@linkplain #loadBytes(InputStream,byte[]) load bytes from} and
	* {@linkplain #storeBytes(byte[],OutputStream) store bytes to} a stream. Such methods
	* use the bulk-read methods of {@link InputStream} and {@link OutputStream}, but they
	* also include a workaround for <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6478546">bug #6478546</a>.
	*
	* <p>Finally, there are useful wrapper methods that {@linkplain #asIntIterator(CharSequence)
	* exhibit a file as a type-specific iterator}.
	*
	* @since 4.4
	*/
public class BinIO {
	private BinIO() {}
	/** Stores an object in a file given by a {@link File} object.
	 *
	 * @param o an object.
	 * @param file a file.
	 * @see #loadObject(File)
	 */
	public static void storeObject(final Object o, final File file) throws IOException {
	 final ObjectOutputStream oos = new ObjectOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	 oos.writeObject(o);
	 oos.close();
	}
	/** Stores an object in a file given by a pathname.
	 *
	 * @param o an object.
	 * @param filename a filename.
	 * @see #loadObject(CharSequence)
	 */
	public static void storeObject(final Object o, final CharSequence filename) throws IOException {
	 storeObject(o, new File(filename.toString()));
	}
	/** Loads an object from a file given by a {@link File} object.
	 *
	 * @param file a file.
	 * @return the object stored under the given file.
	 * @see #storeObject(Object, File)
	 */
	public static Object loadObject(final File file) throws IOException, ClassNotFoundException {
	 final ObjectInputStream ois = new ObjectInputStream(new FastBufferedInputStream(new FileInputStream(file)));
	 final Object result = ois.readObject();
	 ois.close();
	 return result;
	}
	/** Loads an object from a file given by a pathname.
	 *
	 * @param filename a filename.
	 * @return the object stored under the given filename.
	 * @see #storeObject(Object, CharSequence)
	 */
	public static Object loadObject(final CharSequence filename) throws IOException, ClassNotFoundException {
	 return loadObject(new File(filename.toString()));
	}
	/** Stores an object in a given output stream.
	 *
	 * This methods buffers {@code s}, and flushes all wrappers after
	 * calling {@code writeObject()}, but does not close {@code s}.
	 *
	 * @param o an object.
	 * @param s an output stream.
	 * @see #loadObject(InputStream)
	 */
	public static void storeObject(final Object o, final OutputStream s) throws IOException {
	 @SuppressWarnings("resource")
	 final ObjectOutputStream oos = new ObjectOutputStream(new FastBufferedOutputStream(s));
	 oos.writeObject(o);
	 oos.flush();
	}
	/** Loads an object from a given input stream.
	 *
	 * <p><STRONG>Warning</STRONG>: this method buffers the input stream. As a consequence,
	 * subsequent reads from the same stream may not give the desired results, as bytes
	 * may have been read by the internal buffer, but not used by {@code readObject()}.
	 * This is a feature, as this method is targeted at one-shot reading from streams,
	 * e.g., reading exactly one object from {@link System#in}.
	 *
	 * @param s an input stream.
	 * @return the object read from the given input stream.
	 * @see #storeObject(Object, OutputStream)
	 */
	public static Object loadObject(final InputStream s) throws IOException, ClassNotFoundException {
	 @SuppressWarnings("resource")
	 final ObjectInputStream ois = new ObjectInputStream(new FastBufferedInputStream(s));
	 final Object result = ois.readObject();
	 return result;
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
// HORRIBLE kluges to work around bug #6478546
private static final int MAX_IO_LENGTH = 1024 * 1024;
private static int read(final InputStream is, final byte a[], final int offset, final int length) throws IOException {
	if (length == 0) return 0;
	int read = 0, result;
	do {
	 result = is.read(a, offset + read, Math.min(length - read, MAX_IO_LENGTH));
	 if (result < 0) return read;
	 read += result;
	} while(read < length);
	return read;
}
private static void write(final OutputStream outputStream, final byte a[], final int offset, final int length) throws IOException {
	int written = 0;
	while(written < length) {
	 outputStream.write(a, offset + written, Math.min(length - written, MAX_IO_LENGTH));
	 written += Math.min(length - written, MAX_IO_LENGTH);
	}
}
private static void write(final DataOutput dataOutput, final byte a[], final int offset, final int length) throws IOException {
	int written = 0;
	while(written < length) {
	 dataOutput.write(a, offset + written, Math.min(length - written, MAX_IO_LENGTH));
	 written += Math.min(length - written, MAX_IO_LENGTH);
	}
}
// Additional read/write methods to work around the DataInput/DataOutput schizophrenia.
/** Loads bytes from a given input stream, storing them in a given array fragment.
	*
	* <p>Note that this method is going to be significantly faster than {@link #loadBytes(DataInput,byte[],int,int)}
	* as it uses {@link InputStream}'s bulk-read methods.
	*
	* @param inputStream an input stream.
	* @param array an array which will be filled with data from {@code inputStream}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code inputStream} (it might be less than {@code length} if {@code inputStream} ends).
	*/
public static int loadBytes(final InputStream inputStream, final byte[] array, final int offset, final int length) throws IOException {
	return read(inputStream, array, offset, length);
}
/** Loads bytes from a given input stream, storing them in a given array.
	*
	* <p>Note that this method is going to be significantly faster than {@link #loadBytes(DataInput,byte[])}
	* as it uses {@link InputStream}'s bulk-read methods.
	*
	* @param inputStream an input stream.
	* @param array an array which will be filled with data from {@code inputStream}.
	* @return the number of elements actually read from {@code inputStream} (it might be less than the array length if {@code inputStream} ends).
	*/
public static int loadBytes(final InputStream inputStream, final byte[] array) throws IOException {
	return read(inputStream, array, 0, array.length);
}
/** Stores an array fragment to a given output stream.
	*
	* <p>Note that this method is going to be significantly faster than {@link #storeBytes(byte[],int,int,DataOutput)}
	* as it uses {@link OutputStream}'s bulk-read methods.
	*
	* @param array an array whose elements will be written to {@code outputStream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param outputStream an output stream.
	*/
public static void storeBytes(final byte array[], final int offset, final int length, final OutputStream outputStream) throws IOException {
	write(outputStream, array, offset, length);
}
/** Stores an array to a given output stream.
	*
	* <p>Note that this method is going to be significantly faster than {@link #storeBytes(byte[],DataOutput)}
	* as it uses {@link OutputStream}'s bulk-read methods.
	*
	* @param array an array whose elements will be written to {@code outputStream}.
	* @param outputStream an output stream.
	*/
public static void storeBytes(final byte array[], final OutputStream outputStream) throws IOException {
	write(outputStream, array, 0, array.length);
}
private static long read(final InputStream is, final byte a[][], final long offset, final long length) throws IOException {
	if (length == 0) return 0;
	long read = 0;
	int segment = segment(offset);
	int displacement = displacement(offset);
	int result;
	do {
	 result = is.read(a[segment], displacement, (int)Math.min(a[segment].length - displacement, Math.min(length - read, MAX_IO_LENGTH)));
	 if (result < 0) return read;
	 read += result;
	 displacement += result;
	 if (displacement == a[segment].length) {
	  segment++;
	  displacement = 0;
	 }
	} while(read < length);
	return read;
}
private static void write(final OutputStream outputStream, final byte a[][], final long offset, final long length) throws IOException {
	if (length == 0) return;
	long written = 0;
	int toWrite;
	int segment = segment(offset);
	int displacement = displacement(offset);
	do {
	 toWrite = (int)Math.min(a[segment].length - displacement, Math.min(length - written, MAX_IO_LENGTH));
	 outputStream.write(a[segment], displacement, toWrite);
	 written += toWrite;
	 displacement += toWrite;
	 if (displacement == a[segment].length) {
	  segment++;
	  displacement = 0;
	 }
	} while(written < length);
}
private static void write(final DataOutput dataOutput, final byte a[][], final long offset, final long length) throws IOException {
	if (length == 0) return;
	long written = 0;
	int toWrite;
	int segment = segment(offset);
	int displacement = displacement(offset);
	do {
	 toWrite = (int)Math.min(a[segment].length - displacement, Math.min(length - written, MAX_IO_LENGTH));
	 dataOutput.write(a[segment], displacement, toWrite);
	 written += toWrite;
	 displacement += toWrite;
	 if (displacement == a[segment].length) {
	  segment++;
	  displacement = 0;
	 }
	} while(written < length);
}
// Additional read/write methods to work around the DataInput/DataOutput schizophrenia.
/** Loads bytes from a given input stream, storing them in a given big-array fragment.
	*
	* <p>Note that this method is going to be significantly faster than {@link #loadBytes(DataInput,byte[][],long,long)}
	* as it uses {@link InputStream}'s bulk-read methods.
	*
	* @param inputStream an input stream.
	* @param array a big array which will be filled with data from {@code inputStream}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code inputStream} (it might be less than {@code length} if {@code inputStream} ends).
	*/
public static long loadBytes(final InputStream inputStream, final byte[][] array, final long offset, final long length) throws IOException {
	return read(inputStream, array, offset, length);
}
/** Loads bytes from a given input stream, storing them in a given big array.
	*
	* <p>Note that this method is going to be significantly faster than {@link #loadBytes(DataInput,byte[][])}
	* as it uses {@link InputStream}'s bulk-read methods.
	*
	* @param inputStream an input stream.
	* @param array a big array which will be filled with data from {@code inputStream}.
	* @return the number of elements actually read from {@code inputStream} (it might be less than the array length if {@code inputStream} ends).
	*/
public static long loadBytes(final InputStream inputStream, final byte[][] array) throws IOException {
	return read(inputStream, array, 0, ByteBigArrays.length(array));
}
/** Stores a big-array fragment to a given output stream.
	*
	* <p>Note that this method is going to be significantly faster than {@link #storeBytes(byte[][],long,long,DataOutput)}
	* as it uses {@link OutputStream}'s bulk-read methods.
	*
	* @param array a big array whose elements will be written to {@code outputStream}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param outputStream an output stream.
	*/
public static void storeBytes(final byte array[][], final long offset, final long length, final OutputStream outputStream) throws IOException {
	write(outputStream, array, offset, length);
}
/** Stores a big array to a given output stream.
	*
	* <p>Note that this method is going to be significantly faster than {@link #storeBytes(byte[][],DataOutput)}
	* as it uses {@link OutputStream}'s bulk-read methods.
	*
	* @param array a big array whose elements will be written to {@code outputStream}.
	* @param outputStream an output stream.
	*/
public static void storeBytes(final byte array[][], final OutputStream outputStream) throws IOException {
	write(outputStream, array, 0, ByteBigArrays.length(array));
}
/** Loads elements from a given data input, storing them in a given array fragment.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static int loadBytes(final DataInput dataInput, final byte[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.bytes.ByteArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dataInput.readByte();
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given data input, storing them in a given array.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static int loadBytes(final DataInput dataInput, final byte[] array) throws IOException {
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dataInput.readByte();
	}
	catch(EOFException itsOk) {}
	return i;
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
	it.unimi.dsi.fastutil.bytes.ByteArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final int result = read(fis, array, offset, length);
	fis.close();
	return result;
}
/** Loads elements from a file given by a pathname, storing them in a given array fragment.
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
	final FileInputStream fis = new FileInputStream(file);
	final int result = read(fis, array, 0, array.length);
	fis.close();
	return result;
}
/** Loads elements from a file given by a pathname, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadBytes(final CharSequence filename, final byte[] array) throws IOException {
	return loadBytes(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return an array filled with the content of the specified file.
	*/
public static byte[] loadBytes(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Byte.SIZE / 8);
	if (length > Integer.MAX_VALUE) {
	 fis.close();
	 throw new IllegalArgumentException("File too long: " + fis.getChannel().size()+ " bytes (" + length + " elements)");
	}
	final byte[] array = new byte[(int)length];
	if (read(fis, array, 0, (int)length) < length) throw new EOFException();
	fis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return an array filled with the content of the specified file.
	*/
public static byte[] loadBytes(final CharSequence filename) throws IOException {
	return loadBytes(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeBytes(final byte array[], final int offset, final int length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.bytes.ByteArrays.ensureOffsetLength(array, offset, length);
	write(dataOutput, array, offset, length);
}
/** Stores an array to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeBytes(final byte array[], final DataOutput dataOutput) throws IOException {
	write(dataOutput, array, 0, array.length);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeBytes(final byte array[], final int offset, final int length, final File file) throws IOException {
	it.unimi.dsi.fastutil.bytes.ByteArrays.ensureOffsetLength(array, offset, length);
	final OutputStream os = new FastBufferedOutputStream(new FileOutputStream(file));
	write(os, array, offset, length);
	os.close();
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
	final OutputStream os = new FastBufferedOutputStream(new FileOutputStream(file));
	write(os, array, 0, array.length);
	os.close();
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeBytes(final byte array[], final CharSequence filename) throws IOException {
	storeBytes(array, new File(filename.toString()));
}
/** Loads elements from a given data input, storing them in a given big-array fragment.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code bigArray} to be filled.
	* @param length the number of elements of {@code bigArray} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static long loadBytes(final DataInput dataInput, final byte[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.bytes.ByteBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final byte[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dataInput.readByte();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given data input, storing them in a given big array.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static long loadBytes(final DataInput dataInput, final byte[][] array) throws IOException {
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final byte[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dataInput.readByte();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
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
	it.unimi.dsi.fastutil.bytes.ByteBigArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final long result = read(fis, array, offset, length);
	fis.close();
	return result;
}
/** Loads elements from a file given by a pathname, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadBytes(final CharSequence filename, final byte[][] array, final long offset, final long length) throws IOException {
	return loadBytes(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadBytes(final File file, final byte[][] array) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long result = read(fis, array, 0, ByteBigArrays.length(array));
	fis.close();
	return result;
}
/** Loads elements from a file given by a pathname, storing them in a given big array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadBytes(final CharSequence filename, final byte[][] array) throws IOException {
	return loadBytes(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return a big array filled with the content of the specified file.
	*/
public static byte[][] loadBytesBig(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Byte.SIZE / 8);
	final byte[][] array = ByteBigArrays.newBigArray(length);
	if (read(fis, array, 0, length) < length) throw new EOFException();
	fis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return a big array filled with the content of the specified file.
	*/
public static byte[][] loadBytesBig(final CharSequence filename) throws IOException {
	return loadBytesBig(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeBytes(final byte array[][], final long offset, final long length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.bytes.ByteBigArrays.ensureOffsetLength(array, offset, length);
	write(dataOutput, array, offset, length);
}
/** Stores a big array to a given data output.
	*
	* @param array a big array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeBytes(final byte array[][], final DataOutput dataOutput) throws IOException {
	write(dataOutput, array, 0, ByteBigArrays.length(array));
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeBytes(final byte array[][], final long offset, final long length, final File file) throws IOException {
	it.unimi.dsi.fastutil.bytes.ByteBigArrays.ensureOffsetLength(array, offset, length);
	final OutputStream os = new FastBufferedOutputStream(new FileOutputStream(file));
	write(os, array, offset, length);
	os.close();
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
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeBytes(final byte array[][], final File file) throws IOException {
	final OutputStream os = new FastBufferedOutputStream(new FileOutputStream(file));
	write(os, array, 0, ByteBigArrays.length(array));
	os.close();
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeBytes(final byte array[][], final CharSequence filename) throws IOException {
	storeBytes(array, new File(filename.toString()));
}
/** Stores the element returned by an iterator to a given data output.
	*
	* @param i an iterator whose output will be written to {@code dataOutput}.
	* @param dataOutput a filename.
	*/
public static void storeBytes(final ByteIterator i, final DataOutput dataOutput) throws IOException {
	while(i.hasNext()) dataOutput.writeByte(i.nextByte());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeBytes(final ByteIterator i, final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	while(i.hasNext()) dos.writeByte(i.nextByte());
	dos.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeBytes(final ByteIterator i, final CharSequence filename) throws IOException {
	storeBytes(i, new File(filename.toString()));
}
/** A wrapper that exhibits the content of a data input stream as a type-specific iterator. */
private static final class ByteDataInputWrapper implements ByteIterator {
	private final DataInput dataInput;
	private boolean toAdvance = true;
	private boolean endOfProcess = false;
	private byte next;
	public ByteDataInputWrapper(final DataInput dataInput) {
	 this.dataInput = dataInput;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return ! endOfProcess;
	 toAdvance = false;
	 try { next = dataInput.readByte(); }
	 catch(EOFException eof) { endOfProcess = true; }
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 return ! endOfProcess;
	}
	@Override
	public byte nextByte() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given data input stream into an iterator.
	*
	* @param dataInput a data input.
	*/
public static ByteIterator asByteIterator(final DataInput dataInput) {
	return new ByteDataInputWrapper(dataInput);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static ByteIterator asByteIterator(final File file) throws IOException {
	return new ByteDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
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
/** Loads elements from a given data input, storing them in a given array fragment.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static int loadInts(final DataInput dataInput, final int[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.ints.IntArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dataInput.readInt();
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given data input, storing them in a given array.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static int loadInts(final DataInput dataInput, final int[] array) throws IOException {
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dataInput.readInt();
	}
	catch(EOFException itsOk) {}
	return i;
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
	it.unimi.dsi.fastutil.ints.IntArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dis.readInt();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array fragment.
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
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dis.readInt();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadInts(final CharSequence filename, final int[] array) throws IOException {
	return loadInts(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return an array filled with the content of the specified file.
	*/
public static int[] loadInts(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Integer.SIZE / 8);
	if (length > Integer.MAX_VALUE) {
	 fis.close();
	 throw new IllegalArgumentException("File too long: " + fis.getChannel().size()+ " bytes (" + length + " elements)");
	}
	final int[] array = new int[(int)length];
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < length; i++) array[i] = dis.readInt();
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return an array filled with the content of the specified file.
	*/
public static int[] loadInts(final CharSequence filename) throws IOException {
	return loadInts(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeInts(final int array[], final int offset, final int length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.ints.IntArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) dataOutput.writeInt(array[offset + i]);
}
/** Stores an array to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeInts(final int array[], final DataOutput dataOutput) throws IOException {
	final int length = array.length;
	for(int i = 0; i < length; i++) dataOutput.writeInt(array[i]);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeInts(final int array[], final int offset, final int length, final File file) throws IOException {
	it.unimi.dsi.fastutil.ints.IntArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeInt(array[offset + i]);
	dos.close();
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
	final int length = array.length;
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeInt(array[i]);
	dos.close();
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeInts(final int array[], final CharSequence filename) throws IOException {
	storeInts(array, new File(filename.toString()));
}
/** Loads elements from a given data input, storing them in a given big-array fragment.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code bigArray} to be filled.
	* @param length the number of elements of {@code bigArray} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static long loadInts(final DataInput dataInput, final int[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.ints.IntBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final int[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dataInput.readInt();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given data input, storing them in a given big array.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static long loadInts(final DataInput dataInput, final int[][] array) throws IOException {
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final int[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dataInput.readInt();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
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
	it.unimi.dsi.fastutil.ints.IntBigArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final int[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dis.readInt();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadInts(final CharSequence filename, final int[][] array, final long offset, final long length) throws IOException {
	return loadInts(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadInts(final File file, final int[][] array) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final int[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dis.readInt();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadInts(final CharSequence filename, final int[][] array) throws IOException {
	return loadInts(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return a big array filled with the content of the specified file.
	*/
public static int[][] loadIntsBig(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Integer.SIZE / 8);
	final int[][] array = IntBigArrays.newBigArray(length);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < array.length; i++) {
	 final int[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) t[d] = dis.readInt();
	}
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return a big array filled with the content of the specified file.
	*/
public static int[][] loadIntsBig(final CharSequence filename) throws IOException {
	return loadIntsBig(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeInts(final int array[][], final long offset, final long length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.ints.IntBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final int[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dataOutput.writeInt(t[d]);
	}
}
/** Stores a big array to a given data output.
	*
	* @param array a big array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeInts(final int array[][], final DataOutput dataOutput) throws IOException {
	for(int i = 0; i < array.length; i++) {
	 final int[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dataOutput.writeInt(t[d]);
	}
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeInts(final int array[][], final long offset, final long length, final File file) throws IOException {
	it.unimi.dsi.fastutil.ints.IntBigArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final int[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dos.writeInt(t[d]);
	}
	dos.close();
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
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeInts(final int array[][], final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < array.length; i++) {
	 final int[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dos.writeInt(t[d]);
	}
	dos.close();
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeInts(final int array[][], final CharSequence filename) throws IOException {
	storeInts(array, new File(filename.toString()));
}
/** Stores the element returned by an iterator to a given data output.
	*
	* @param i an iterator whose output will be written to {@code dataOutput}.
	* @param dataOutput a filename.
	*/
public static void storeInts(final IntIterator i, final DataOutput dataOutput) throws IOException {
	while(i.hasNext()) dataOutput.writeInt(i.nextInt());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeInts(final IntIterator i, final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	while(i.hasNext()) dos.writeInt(i.nextInt());
	dos.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeInts(final IntIterator i, final CharSequence filename) throws IOException {
	storeInts(i, new File(filename.toString()));
}
/** A wrapper that exhibits the content of a data input stream as a type-specific iterator. */
private static final class IntDataInputWrapper implements IntIterator {
	private final DataInput dataInput;
	private boolean toAdvance = true;
	private boolean endOfProcess = false;
	private int next;
	public IntDataInputWrapper(final DataInput dataInput) {
	 this.dataInput = dataInput;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return ! endOfProcess;
	 toAdvance = false;
	 try { next = dataInput.readInt(); }
	 catch(EOFException eof) { endOfProcess = true; }
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 return ! endOfProcess;
	}
	@Override
	public int nextInt() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given data input stream into an iterator.
	*
	* @param dataInput a data input.
	*/
public static IntIterator asIntIterator(final DataInput dataInput) {
	return new IntDataInputWrapper(dataInput);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static IntIterator asIntIterator(final File file) throws IOException {
	return new IntDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
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
/** Loads elements from a given data input, storing them in a given array fragment.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static int loadLongs(final DataInput dataInput, final long[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.longs.LongArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dataInput.readLong();
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given data input, storing them in a given array.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static int loadLongs(final DataInput dataInput, final long[] array) throws IOException {
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dataInput.readLong();
	}
	catch(EOFException itsOk) {}
	return i;
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
	it.unimi.dsi.fastutil.longs.LongArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dis.readLong();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array fragment.
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
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dis.readLong();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadLongs(final CharSequence filename, final long[] array) throws IOException {
	return loadLongs(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return an array filled with the content of the specified file.
	*/
public static long[] loadLongs(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Long.SIZE / 8);
	if (length > Integer.MAX_VALUE) {
	 fis.close();
	 throw new IllegalArgumentException("File too long: " + fis.getChannel().size()+ " bytes (" + length + " elements)");
	}
	final long[] array = new long[(int)length];
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < length; i++) array[i] = dis.readLong();
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return an array filled with the content of the specified file.
	*/
public static long[] loadLongs(final CharSequence filename) throws IOException {
	return loadLongs(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeLongs(final long array[], final int offset, final int length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.longs.LongArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) dataOutput.writeLong(array[offset + i]);
}
/** Stores an array to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeLongs(final long array[], final DataOutput dataOutput) throws IOException {
	final int length = array.length;
	for(int i = 0; i < length; i++) dataOutput.writeLong(array[i]);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeLongs(final long array[], final int offset, final int length, final File file) throws IOException {
	it.unimi.dsi.fastutil.longs.LongArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeLong(array[offset + i]);
	dos.close();
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
	final int length = array.length;
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeLong(array[i]);
	dos.close();
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeLongs(final long array[], final CharSequence filename) throws IOException {
	storeLongs(array, new File(filename.toString()));
}
/** Loads elements from a given data input, storing them in a given big-array fragment.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code bigArray} to be filled.
	* @param length the number of elements of {@code bigArray} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static long loadLongs(final DataInput dataInput, final long[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.longs.LongBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final long[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dataInput.readLong();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given data input, storing them in a given big array.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static long loadLongs(final DataInput dataInput, final long[][] array) throws IOException {
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final long[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dataInput.readLong();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
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
	it.unimi.dsi.fastutil.longs.LongBigArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final long[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dis.readLong();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadLongs(final CharSequence filename, final long[][] array, final long offset, final long length) throws IOException {
	return loadLongs(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadLongs(final File file, final long[][] array) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final long[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dis.readLong();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadLongs(final CharSequence filename, final long[][] array) throws IOException {
	return loadLongs(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return a big array filled with the content of the specified file.
	*/
public static long[][] loadLongsBig(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Long.SIZE / 8);
	final long[][] array = LongBigArrays.newBigArray(length);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < array.length; i++) {
	 final long[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) t[d] = dis.readLong();
	}
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return a big array filled with the content of the specified file.
	*/
public static long[][] loadLongsBig(final CharSequence filename) throws IOException {
	return loadLongsBig(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeLongs(final long array[][], final long offset, final long length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.longs.LongBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final long[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dataOutput.writeLong(t[d]);
	}
}
/** Stores a big array to a given data output.
	*
	* @param array a big array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeLongs(final long array[][], final DataOutput dataOutput) throws IOException {
	for(int i = 0; i < array.length; i++) {
	 final long[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dataOutput.writeLong(t[d]);
	}
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeLongs(final long array[][], final long offset, final long length, final File file) throws IOException {
	it.unimi.dsi.fastutil.longs.LongBigArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final long[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dos.writeLong(t[d]);
	}
	dos.close();
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
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeLongs(final long array[][], final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < array.length; i++) {
	 final long[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dos.writeLong(t[d]);
	}
	dos.close();
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeLongs(final long array[][], final CharSequence filename) throws IOException {
	storeLongs(array, new File(filename.toString()));
}
/** Stores the element returned by an iterator to a given data output.
	*
	* @param i an iterator whose output will be written to {@code dataOutput}.
	* @param dataOutput a filename.
	*/
public static void storeLongs(final LongIterator i, final DataOutput dataOutput) throws IOException {
	while(i.hasNext()) dataOutput.writeLong(i.nextLong());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeLongs(final LongIterator i, final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	while(i.hasNext()) dos.writeLong(i.nextLong());
	dos.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeLongs(final LongIterator i, final CharSequence filename) throws IOException {
	storeLongs(i, new File(filename.toString()));
}
/** A wrapper that exhibits the content of a data input stream as a type-specific iterator. */
private static final class LongDataInputWrapper implements LongIterator {
	private final DataInput dataInput;
	private boolean toAdvance = true;
	private boolean endOfProcess = false;
	private long next;
	public LongDataInputWrapper(final DataInput dataInput) {
	 this.dataInput = dataInput;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return ! endOfProcess;
	 toAdvance = false;
	 try { next = dataInput.readLong(); }
	 catch(EOFException eof) { endOfProcess = true; }
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 return ! endOfProcess;
	}
	@Override
	public long nextLong() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given data input stream into an iterator.
	*
	* @param dataInput a data input.
	*/
public static LongIterator asLongIterator(final DataInput dataInput) {
	return new LongDataInputWrapper(dataInput);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static LongIterator asLongIterator(final File file) throws IOException {
	return new LongDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
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
/** Loads elements from a given data input, storing them in a given array fragment.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static int loadDoubles(final DataInput dataInput, final double[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.doubles.DoubleArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dataInput.readDouble();
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given data input, storing them in a given array.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static int loadDoubles(final DataInput dataInput, final double[] array) throws IOException {
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dataInput.readDouble();
	}
	catch(EOFException itsOk) {}
	return i;
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
	it.unimi.dsi.fastutil.doubles.DoubleArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dis.readDouble();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array fragment.
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
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dis.readDouble();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadDoubles(final CharSequence filename, final double[] array) throws IOException {
	return loadDoubles(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return an array filled with the content of the specified file.
	*/
public static double[] loadDoubles(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Double.SIZE / 8);
	if (length > Integer.MAX_VALUE) {
	 fis.close();
	 throw new IllegalArgumentException("File too long: " + fis.getChannel().size()+ " bytes (" + length + " elements)");
	}
	final double[] array = new double[(int)length];
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < length; i++) array[i] = dis.readDouble();
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return an array filled with the content of the specified file.
	*/
public static double[] loadDoubles(final CharSequence filename) throws IOException {
	return loadDoubles(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeDoubles(final double array[], final int offset, final int length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.doubles.DoubleArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) dataOutput.writeDouble(array[offset + i]);
}
/** Stores an array to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeDoubles(final double array[], final DataOutput dataOutput) throws IOException {
	final int length = array.length;
	for(int i = 0; i < length; i++) dataOutput.writeDouble(array[i]);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeDoubles(final double array[], final int offset, final int length, final File file) throws IOException {
	it.unimi.dsi.fastutil.doubles.DoubleArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeDouble(array[offset + i]);
	dos.close();
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
	final int length = array.length;
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeDouble(array[i]);
	dos.close();
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeDoubles(final double array[], final CharSequence filename) throws IOException {
	storeDoubles(array, new File(filename.toString()));
}
/** Loads elements from a given data input, storing them in a given big-array fragment.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code bigArray} to be filled.
	* @param length the number of elements of {@code bigArray} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static long loadDoubles(final DataInput dataInput, final double[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.doubles.DoubleBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final double[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dataInput.readDouble();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given data input, storing them in a given big array.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static long loadDoubles(final DataInput dataInput, final double[][] array) throws IOException {
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final double[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dataInput.readDouble();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
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
	it.unimi.dsi.fastutil.doubles.DoubleBigArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final double[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dis.readDouble();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadDoubles(final CharSequence filename, final double[][] array, final long offset, final long length) throws IOException {
	return loadDoubles(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadDoubles(final File file, final double[][] array) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final double[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dis.readDouble();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadDoubles(final CharSequence filename, final double[][] array) throws IOException {
	return loadDoubles(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return a big array filled with the content of the specified file.
	*/
public static double[][] loadDoublesBig(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Double.SIZE / 8);
	final double[][] array = DoubleBigArrays.newBigArray(length);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < array.length; i++) {
	 final double[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) t[d] = dis.readDouble();
	}
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return a big array filled with the content of the specified file.
	*/
public static double[][] loadDoublesBig(final CharSequence filename) throws IOException {
	return loadDoublesBig(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeDoubles(final double array[][], final long offset, final long length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.doubles.DoubleBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final double[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dataOutput.writeDouble(t[d]);
	}
}
/** Stores a big array to a given data output.
	*
	* @param array a big array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeDoubles(final double array[][], final DataOutput dataOutput) throws IOException {
	for(int i = 0; i < array.length; i++) {
	 final double[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dataOutput.writeDouble(t[d]);
	}
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeDoubles(final double array[][], final long offset, final long length, final File file) throws IOException {
	it.unimi.dsi.fastutil.doubles.DoubleBigArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final double[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dos.writeDouble(t[d]);
	}
	dos.close();
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
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeDoubles(final double array[][], final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < array.length; i++) {
	 final double[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dos.writeDouble(t[d]);
	}
	dos.close();
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeDoubles(final double array[][], final CharSequence filename) throws IOException {
	storeDoubles(array, new File(filename.toString()));
}
/** Stores the element returned by an iterator to a given data output.
	*
	* @param i an iterator whose output will be written to {@code dataOutput}.
	* @param dataOutput a filename.
	*/
public static void storeDoubles(final DoubleIterator i, final DataOutput dataOutput) throws IOException {
	while(i.hasNext()) dataOutput.writeDouble(i.nextDouble());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeDoubles(final DoubleIterator i, final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	while(i.hasNext()) dos.writeDouble(i.nextDouble());
	dos.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeDoubles(final DoubleIterator i, final CharSequence filename) throws IOException {
	storeDoubles(i, new File(filename.toString()));
}
/** A wrapper that exhibits the content of a data input stream as a type-specific iterator. */
private static final class DoubleDataInputWrapper implements DoubleIterator {
	private final DataInput dataInput;
	private boolean toAdvance = true;
	private boolean endOfProcess = false;
	private double next;
	public DoubleDataInputWrapper(final DataInput dataInput) {
	 this.dataInput = dataInput;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return ! endOfProcess;
	 toAdvance = false;
	 try { next = dataInput.readDouble(); }
	 catch(EOFException eof) { endOfProcess = true; }
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 return ! endOfProcess;
	}
	@Override
	public double nextDouble() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given data input stream into an iterator.
	*
	* @param dataInput a data input.
	*/
public static DoubleIterator asDoubleIterator(final DataInput dataInput) {
	return new DoubleDataInputWrapper(dataInput);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static DoubleIterator asDoubleIterator(final File file) throws IOException {
	return new DoubleDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
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
/** Loads elements from a given data input, storing them in a given array fragment.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static int loadBooleans(final DataInput dataInput, final boolean[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.booleans.BooleanArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dataInput.readBoolean();
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given data input, storing them in a given array.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static int loadBooleans(final DataInput dataInput, final boolean[] array) throws IOException {
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dataInput.readBoolean();
	}
	catch(EOFException itsOk) {}
	return i;
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
	it.unimi.dsi.fastutil.booleans.BooleanArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dis.readBoolean();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array fragment.
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
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dis.readBoolean();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadBooleans(final CharSequence filename, final boolean[] array) throws IOException {
	return loadBooleans(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return an array filled with the content of the specified file.
	*/
public static boolean[] loadBooleans(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size();
	if (length > Integer.MAX_VALUE) {
	 fis.close();
	 throw new IllegalArgumentException("File too long: " + fis.getChannel().size()+ " bytes (" + length + " elements)");
	}
	final boolean[] array = new boolean[(int)length];
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < length; i++) array[i] = dis.readBoolean();
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return an array filled with the content of the specified file.
	*/
public static boolean[] loadBooleans(final CharSequence filename) throws IOException {
	return loadBooleans(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeBooleans(final boolean array[], final int offset, final int length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.booleans.BooleanArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) dataOutput.writeBoolean(array[offset + i]);
}
/** Stores an array to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeBooleans(final boolean array[], final DataOutput dataOutput) throws IOException {
	final int length = array.length;
	for(int i = 0; i < length; i++) dataOutput.writeBoolean(array[i]);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeBooleans(final boolean array[], final int offset, final int length, final File file) throws IOException {
	it.unimi.dsi.fastutil.booleans.BooleanArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeBoolean(array[offset + i]);
	dos.close();
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
	final int length = array.length;
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeBoolean(array[i]);
	dos.close();
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeBooleans(final boolean array[], final CharSequence filename) throws IOException {
	storeBooleans(array, new File(filename.toString()));
}
/** Loads elements from a given data input, storing them in a given big-array fragment.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code bigArray} to be filled.
	* @param length the number of elements of {@code bigArray} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static long loadBooleans(final DataInput dataInput, final boolean[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.booleans.BooleanBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final boolean[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dataInput.readBoolean();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given data input, storing them in a given big array.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static long loadBooleans(final DataInput dataInput, final boolean[][] array) throws IOException {
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final boolean[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dataInput.readBoolean();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
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
	it.unimi.dsi.fastutil.booleans.BooleanBigArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final boolean[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dis.readBoolean();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadBooleans(final CharSequence filename, final boolean[][] array, final long offset, final long length) throws IOException {
	return loadBooleans(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadBooleans(final File file, final boolean[][] array) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final boolean[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dis.readBoolean();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadBooleans(final CharSequence filename, final boolean[][] array) throws IOException {
	return loadBooleans(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return a big array filled with the content of the specified file.
	*/
public static boolean[][] loadBooleansBig(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size();
	final boolean[][] array = BooleanBigArrays.newBigArray(length);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < array.length; i++) {
	 final boolean[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) t[d] = dis.readBoolean();
	}
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return a big array filled with the content of the specified file.
	*/
public static boolean[][] loadBooleansBig(final CharSequence filename) throws IOException {
	return loadBooleansBig(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeBooleans(final boolean array[][], final long offset, final long length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.booleans.BooleanBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final boolean[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dataOutput.writeBoolean(t[d]);
	}
}
/** Stores a big array to a given data output.
	*
	* @param array a big array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeBooleans(final boolean array[][], final DataOutput dataOutput) throws IOException {
	for(int i = 0; i < array.length; i++) {
	 final boolean[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dataOutput.writeBoolean(t[d]);
	}
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeBooleans(final boolean array[][], final long offset, final long length, final File file) throws IOException {
	it.unimi.dsi.fastutil.booleans.BooleanBigArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final boolean[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dos.writeBoolean(t[d]);
	}
	dos.close();
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
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeBooleans(final boolean array[][], final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < array.length; i++) {
	 final boolean[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dos.writeBoolean(t[d]);
	}
	dos.close();
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeBooleans(final boolean array[][], final CharSequence filename) throws IOException {
	storeBooleans(array, new File(filename.toString()));
}
/** Stores the element returned by an iterator to a given data output.
	*
	* @param i an iterator whose output will be written to {@code dataOutput}.
	* @param dataOutput a filename.
	*/
public static void storeBooleans(final BooleanIterator i, final DataOutput dataOutput) throws IOException {
	while(i.hasNext()) dataOutput.writeBoolean(i.nextBoolean());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeBooleans(final BooleanIterator i, final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	while(i.hasNext()) dos.writeBoolean(i.nextBoolean());
	dos.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeBooleans(final BooleanIterator i, final CharSequence filename) throws IOException {
	storeBooleans(i, new File(filename.toString()));
}
/** A wrapper that exhibits the content of a data input stream as a type-specific iterator. */
private static final class BooleanDataInputWrapper implements BooleanIterator {
	private final DataInput dataInput;
	private boolean toAdvance = true;
	private boolean endOfProcess = false;
	private boolean next;
	public BooleanDataInputWrapper(final DataInput dataInput) {
	 this.dataInput = dataInput;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return ! endOfProcess;
	 toAdvance = false;
	 try { next = dataInput.readBoolean(); }
	 catch(EOFException eof) { endOfProcess = true; }
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 return ! endOfProcess;
	}
	@Override
	public boolean nextBoolean() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given data input stream into an iterator.
	*
	* @param dataInput a data input.
	*/
public static BooleanIterator asBooleanIterator(final DataInput dataInput) {
	return new BooleanDataInputWrapper(dataInput);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static BooleanIterator asBooleanIterator(final File file) throws IOException {
	return new BooleanDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
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
/** Loads elements from a given data input, storing them in a given array fragment.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static int loadShorts(final DataInput dataInput, final short[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.shorts.ShortArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dataInput.readShort();
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given data input, storing them in a given array.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static int loadShorts(final DataInput dataInput, final short[] array) throws IOException {
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dataInput.readShort();
	}
	catch(EOFException itsOk) {}
	return i;
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
	it.unimi.dsi.fastutil.shorts.ShortArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dis.readShort();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array fragment.
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
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dis.readShort();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadShorts(final CharSequence filename, final short[] array) throws IOException {
	return loadShorts(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return an array filled with the content of the specified file.
	*/
public static short[] loadShorts(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Short.SIZE / 8);
	if (length > Integer.MAX_VALUE) {
	 fis.close();
	 throw new IllegalArgumentException("File too long: " + fis.getChannel().size()+ " bytes (" + length + " elements)");
	}
	final short[] array = new short[(int)length];
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < length; i++) array[i] = dis.readShort();
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return an array filled with the content of the specified file.
	*/
public static short[] loadShorts(final CharSequence filename) throws IOException {
	return loadShorts(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeShorts(final short array[], final int offset, final int length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.shorts.ShortArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) dataOutput.writeShort(array[offset + i]);
}
/** Stores an array to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeShorts(final short array[], final DataOutput dataOutput) throws IOException {
	final int length = array.length;
	for(int i = 0; i < length; i++) dataOutput.writeShort(array[i]);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeShorts(final short array[], final int offset, final int length, final File file) throws IOException {
	it.unimi.dsi.fastutil.shorts.ShortArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeShort(array[offset + i]);
	dos.close();
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
	final int length = array.length;
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeShort(array[i]);
	dos.close();
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeShorts(final short array[], final CharSequence filename) throws IOException {
	storeShorts(array, new File(filename.toString()));
}
/** Loads elements from a given data input, storing them in a given big-array fragment.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code bigArray} to be filled.
	* @param length the number of elements of {@code bigArray} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static long loadShorts(final DataInput dataInput, final short[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.shorts.ShortBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final short[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dataInput.readShort();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given data input, storing them in a given big array.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static long loadShorts(final DataInput dataInput, final short[][] array) throws IOException {
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final short[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dataInput.readShort();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
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
	it.unimi.dsi.fastutil.shorts.ShortBigArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final short[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dis.readShort();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadShorts(final CharSequence filename, final short[][] array, final long offset, final long length) throws IOException {
	return loadShorts(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadShorts(final File file, final short[][] array) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final short[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dis.readShort();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadShorts(final CharSequence filename, final short[][] array) throws IOException {
	return loadShorts(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return a big array filled with the content of the specified file.
	*/
public static short[][] loadShortsBig(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Short.SIZE / 8);
	final short[][] array = ShortBigArrays.newBigArray(length);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < array.length; i++) {
	 final short[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) t[d] = dis.readShort();
	}
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return a big array filled with the content of the specified file.
	*/
public static short[][] loadShortsBig(final CharSequence filename) throws IOException {
	return loadShortsBig(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeShorts(final short array[][], final long offset, final long length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.shorts.ShortBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final short[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dataOutput.writeShort(t[d]);
	}
}
/** Stores a big array to a given data output.
	*
	* @param array a big array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeShorts(final short array[][], final DataOutput dataOutput) throws IOException {
	for(int i = 0; i < array.length; i++) {
	 final short[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dataOutput.writeShort(t[d]);
	}
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeShorts(final short array[][], final long offset, final long length, final File file) throws IOException {
	it.unimi.dsi.fastutil.shorts.ShortBigArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final short[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dos.writeShort(t[d]);
	}
	dos.close();
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
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeShorts(final short array[][], final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < array.length; i++) {
	 final short[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dos.writeShort(t[d]);
	}
	dos.close();
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeShorts(final short array[][], final CharSequence filename) throws IOException {
	storeShorts(array, new File(filename.toString()));
}
/** Stores the element returned by an iterator to a given data output.
	*
	* @param i an iterator whose output will be written to {@code dataOutput}.
	* @param dataOutput a filename.
	*/
public static void storeShorts(final ShortIterator i, final DataOutput dataOutput) throws IOException {
	while(i.hasNext()) dataOutput.writeShort(i.nextShort());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeShorts(final ShortIterator i, final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	while(i.hasNext()) dos.writeShort(i.nextShort());
	dos.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeShorts(final ShortIterator i, final CharSequence filename) throws IOException {
	storeShorts(i, new File(filename.toString()));
}
/** A wrapper that exhibits the content of a data input stream as a type-specific iterator. */
private static final class ShortDataInputWrapper implements ShortIterator {
	private final DataInput dataInput;
	private boolean toAdvance = true;
	private boolean endOfProcess = false;
	private short next;
	public ShortDataInputWrapper(final DataInput dataInput) {
	 this.dataInput = dataInput;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return ! endOfProcess;
	 toAdvance = false;
	 try { next = dataInput.readShort(); }
	 catch(EOFException eof) { endOfProcess = true; }
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 return ! endOfProcess;
	}
	@Override
	public short nextShort() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given data input stream into an iterator.
	*
	* @param dataInput a data input.
	*/
public static ShortIterator asShortIterator(final DataInput dataInput) {
	return new ShortDataInputWrapper(dataInput);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static ShortIterator asShortIterator(final File file) throws IOException {
	return new ShortDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
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
/** Loads elements from a given data input, storing them in a given array fragment.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static int loadChars(final DataInput dataInput, final char[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.chars.CharArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dataInput.readChar();
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given data input, storing them in a given array.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static int loadChars(final DataInput dataInput, final char[] array) throws IOException {
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dataInput.readChar();
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array fragment.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadChars(final File file, final char[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.chars.CharArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dis.readChar();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static int loadChars(final CharSequence filename, final char[] array, final int offset, final int length) throws IOException {
	return loadChars(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given array.
	*
	* @param file a file.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadChars(final File file, final char[] array) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dis.readChar();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadChars(final CharSequence filename, final char[] array) throws IOException {
	return loadChars(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return an array filled with the content of the specified file.
	*/
public static char[] loadChars(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Character.SIZE / 8);
	if (length > Integer.MAX_VALUE) {
	 fis.close();
	 throw new IllegalArgumentException("File too long: " + fis.getChannel().size()+ " bytes (" + length + " elements)");
	}
	final char[] array = new char[(int)length];
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < length; i++) array[i] = dis.readChar();
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return an array filled with the content of the specified file.
	*/
public static char[] loadChars(final CharSequence filename) throws IOException {
	return loadChars(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeChars(final char array[], final int offset, final int length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.chars.CharArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) dataOutput.writeChar(array[offset + i]);
}
/** Stores an array to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeChars(final char array[], final DataOutput dataOutput) throws IOException {
	final int length = array.length;
	for(int i = 0; i < length; i++) dataOutput.writeChar(array[i]);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeChars(final char array[], final int offset, final int length, final File file) throws IOException {
	it.unimi.dsi.fastutil.chars.CharArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeChar(array[offset + i]);
	dos.close();
}
/** Stores an array fragment to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeChars(final char array[], final int offset, final int length, final CharSequence filename) throws IOException {
	storeChars(array, offset, length, new File(filename.toString()));
}
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeChars(final char array[], final File file) throws IOException {
	final int length = array.length;
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeChar(array[i]);
	dos.close();
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeChars(final char array[], final CharSequence filename) throws IOException {
	storeChars(array, new File(filename.toString()));
}
/** Loads elements from a given data input, storing them in a given big-array fragment.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code bigArray} to be filled.
	* @param length the number of elements of {@code bigArray} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static long loadChars(final DataInput dataInput, final char[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.chars.CharBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final char[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dataInput.readChar();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given data input, storing them in a given big array.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static long loadChars(final DataInput dataInput, final char[][] array) throws IOException {
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final char[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dataInput.readChar();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big-array fragment.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadChars(final File file, final char[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.chars.CharBigArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final char[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dis.readChar();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadChars(final CharSequence filename, final char[][] array, final long offset, final long length) throws IOException {
	return loadChars(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadChars(final File file, final char[][] array) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final char[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dis.readChar();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadChars(final CharSequence filename, final char[][] array) throws IOException {
	return loadChars(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return a big array filled with the content of the specified file.
	*/
public static char[][] loadCharsBig(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Character.SIZE / 8);
	final char[][] array = CharBigArrays.newBigArray(length);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < array.length; i++) {
	 final char[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) t[d] = dis.readChar();
	}
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return a big array filled with the content of the specified file.
	*/
public static char[][] loadCharsBig(final CharSequence filename) throws IOException {
	return loadCharsBig(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeChars(final char array[][], final long offset, final long length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.chars.CharBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final char[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dataOutput.writeChar(t[d]);
	}
}
/** Stores a big array to a given data output.
	*
	* @param array a big array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeChars(final char array[][], final DataOutput dataOutput) throws IOException {
	for(int i = 0; i < array.length; i++) {
	 final char[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dataOutput.writeChar(t[d]);
	}
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeChars(final char array[][], final long offset, final long length, final File file) throws IOException {
	it.unimi.dsi.fastutil.chars.CharBigArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final char[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dos.writeChar(t[d]);
	}
	dos.close();
}
/** Stores a big-array fragment to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param filename a filename.
	*/
public static void storeChars(final char array[][], final long offset, final long length, final CharSequence filename) throws IOException {
	storeChars(array, offset, length, new File(filename.toString()));
}
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeChars(final char array[][], final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < array.length; i++) {
	 final char[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dos.writeChar(t[d]);
	}
	dos.close();
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeChars(final char array[][], final CharSequence filename) throws IOException {
	storeChars(array, new File(filename.toString()));
}
/** Stores the element returned by an iterator to a given data output.
	*
	* @param i an iterator whose output will be written to {@code dataOutput}.
	* @param dataOutput a filename.
	*/
public static void storeChars(final CharIterator i, final DataOutput dataOutput) throws IOException {
	while(i.hasNext()) dataOutput.writeChar(i.nextChar());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeChars(final CharIterator i, final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	while(i.hasNext()) dos.writeChar(i.nextChar());
	dos.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeChars(final CharIterator i, final CharSequence filename) throws IOException {
	storeChars(i, new File(filename.toString()));
}
/** A wrapper that exhibits the content of a data input stream as a type-specific iterator. */
private static final class CharDataInputWrapper implements CharIterator {
	private final DataInput dataInput;
	private boolean toAdvance = true;
	private boolean endOfProcess = false;
	private char next;
	public CharDataInputWrapper(final DataInput dataInput) {
	 this.dataInput = dataInput;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return ! endOfProcess;
	 toAdvance = false;
	 try { next = dataInput.readChar(); }
	 catch(EOFException eof) { endOfProcess = true; }
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 return ! endOfProcess;
	}
	@Override
	public char nextChar() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given data input stream into an iterator.
	*
	* @param dataInput a data input.
	*/
public static CharIterator asCharIterator(final DataInput dataInput) {
	return new CharDataInputWrapper(dataInput);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static CharIterator asCharIterator(final File file) throws IOException {
	return new CharDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
}
/** Wraps a file given by a pathname into an iterator.
	*
	* @param filename a filename.
	*/
public static CharIterator asCharIterator(final CharSequence filename) throws IOException {
	return asCharIterator(new File(filename.toString()));
}
/** Wraps a file given by a {@link File} object into an iterable object.
	*
	* @param file a file.
	*/
public static CharIterable asCharIterable(final File file) {
	return () -> {
	 try { return asCharIterator(file); }
	 catch(IOException e) { throw new RuntimeException(e); }
	};
}
/** Wraps a file given by a pathname into an iterable object.
	*
	* @param filename a filename.
	*/
public static CharIterable asCharIterable(final CharSequence filename) {
	return () -> {
	 try { return asCharIterator(filename); }
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
/** Loads elements from a given data input, storing them in a given array fragment.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static int loadFloats(final DataInput dataInput, final float[] array, final int offset, final int length) throws IOException {
	it.unimi.dsi.fastutil.floats.FloatArrays.ensureOffsetLength(array, offset, length);
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dataInput.readFloat();
	}
	catch(EOFException itsOk) {}
	return i;
}
/** Loads elements from a given data input, storing them in a given array.
	*
	* @param dataInput a data input.
	* @param array an array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static int loadFloats(final DataInput dataInput, final float[] array) throws IOException {
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dataInput.readFloat();
	}
	catch(EOFException itsOk) {}
	return i;
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
	it.unimi.dsi.fastutil.floats.FloatArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 for(i = 0; i < length; i++) array[i + offset] = dis.readFloat();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array fragment.
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
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	int i = 0;
	try {
	 final int length = array.length;
	 for(i = 0; i < length; i++) array[i] = dis.readFloat();
	}
	catch(EOFException itsOk) {}
	dis.close();
	return i;
}
/** Loads elements from a file given by a pathname, storing them in a given array.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static int loadFloats(final CharSequence filename, final float[] array) throws IOException {
	return loadFloats(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return an array filled with the content of the specified file.
	*/
public static float[] loadFloats(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Float.SIZE / 8);
	if (length > Integer.MAX_VALUE) {
	 fis.close();
	 throw new IllegalArgumentException("File too long: " + fis.getChannel().size()+ " bytes (" + length + " elements)");
	}
	final float[] array = new float[(int)length];
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < length; i++) array[i] = dis.readFloat();
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new array.
	*
	* <p>Note that the length of the returned array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return an array filled with the content of the specified file.
	*/
public static float[] loadFloats(final CharSequence filename) throws IOException {
	return loadFloats(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeFloats(final float array[], final int offset, final int length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.floats.FloatArrays.ensureOffsetLength(array, offset, length);
	for(int i = 0; i < length; i++) dataOutput.writeFloat(array[offset + i]);
}
/** Stores an array to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeFloats(final float array[], final DataOutput dataOutput) throws IOException {
	final int length = array.length;
	for(int i = 0; i < length; i++) dataOutput.writeFloat(array[i]);
}
/** Stores an array fragment to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeFloats(final float array[], final int offset, final int length, final File file) throws IOException {
	it.unimi.dsi.fastutil.floats.FloatArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeFloat(array[offset + i]);
	dos.close();
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
	final int length = array.length;
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < length; i++) dos.writeFloat(array[i]);
	dos.close();
}
/** Stores an array to a file given by a pathname.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeFloats(final float array[], final CharSequence filename) throws IOException {
	storeFloats(array, new File(filename.toString()));
}
/** Loads elements from a given data input, storing them in a given big-array fragment.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @param offset the index of the first element of {@code bigArray} to be filled.
	* @param length the number of elements of {@code bigArray} to be filled.
	* @return the number of elements actually read from {@code dataInput} (it might be less than {@code length} if {@code dataInput} ends).
	*/
public static long loadFloats(final DataInput dataInput, final float[][] array, final long offset, final long length) throws IOException {
	it.unimi.dsi.fastutil.floats.FloatBigArrays.ensureOffsetLength(array, offset, length);
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final float[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dataInput.readFloat();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
}
/** Loads elements from a given data input, storing them in a given big array.
	*
	* @param dataInput a data input.
	* @param array a big array which will be filled with data from {@code dataInput}.
	* @return the number of elements actually read from {@code dataInput} (it might be less than the array length if {@code dataInput} ends).
	*/
public static long loadFloats(final DataInput dataInput, final float[][] array) throws IOException {
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final float[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dataInput.readFloat();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	return c;
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
	it.unimi.dsi.fastutil.floats.FloatBigArrays.ensureOffsetLength(array, offset, length);
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	  final float[] t = array[i];
	  final int l = (int)Math.min(t.length, offset + length - start(i));
	  for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) {
	   t[d] = dis.readFloat();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big-array fragment.
	*
	* @param filename a filename.
	* @param array an array which will be filled with data from the specified file.
	* @param offset the index of the first element of {@code array} to be filled.
	* @param length the number of elements of {@code array} to be filled.
	* @return the number of elements actually read from the given file (it might be less than {@code length} if the file is too short).
	*/
public static long loadFloats(final CharSequence filename, final float[][] array, final long offset, final long length) throws IOException {
	return loadFloats(new File(filename.toString()), array, offset, length);
}
/** Loads elements from a file given by a {@link File} object, storing them in a given big array.
	*
	* @param file a file.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadFloats(final File file, final float[][] array) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	long c = 0;
	try {
	 for(int i = 0; i < array.length; i++) {
	  final float[] t = array[i];
	  final int l = t.length;
	  for(int d = 0; d < l; d++) {
	   t[d] = dis.readFloat();
	   c++;
	  }
	 }
	}
	catch(EOFException itsOk) {}
	dis.close();
	return c;
}
/** Loads elements from a file given by a pathname, storing them in a given big array.
	*
	* @param filename a filename.
	* @param array a big array which will be filled with data from the specified file.
	* @return the number of elements actually read from the given file (it might be less than the array length if the file is too short).
	*/
public static long loadFloats(final CharSequence filename, final float[][] array) throws IOException {
	return loadFloats(new File(filename.toString()), array);
}
/** Loads elements from a file given by a {@link File} object, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param file a file.
	* @return a big array filled with the content of the specified file.
	*/
public static float[][] loadFloatsBig(final File file) throws IOException {
	final FileInputStream fis = new FileInputStream(file);
	final long length = fis.getChannel().size() / (Float.SIZE / 8);
	final float[][] array = FloatBigArrays.newBigArray(length);
	final DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
	for(int i = 0; i < array.length; i++) {
	 final float[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) t[d] = dis.readFloat();
	}
	dis.close();
	return array;
}
/** Loads elements from a file given by a filename, storing them in a new big array.
	*
	* <p>Note that the length of the returned big array will be computed
	* dividing the specified file size by the number of bytes used to
	* represent each element.
	*
	* @param filename a filename.
	* @return a big array filled with the content of the specified file.
	*/
public static float[][] loadFloatsBig(final CharSequence filename) throws IOException {
	return loadFloatsBig(new File(filename.toString()));
}
/** Stores an array fragment to a given data output.
	*
	* @param array an array whose elements will be written to {@code dataOutput}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param dataOutput a data output.
	*/
public static void storeFloats(final float array[][], final long offset, final long length, final DataOutput dataOutput) throws IOException {
	it.unimi.dsi.fastutil.floats.FloatBigArrays.ensureOffsetLength(array, offset, length);
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final float[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dataOutput.writeFloat(t[d]);
	}
}
/** Stores a big array to a given data output.
	*
	* @param array a big array whose elements will be written to {@code dataOutput}.
	* @param dataOutput a data output.
	*/
public static void storeFloats(final float array[][], final DataOutput dataOutput) throws IOException {
	for(int i = 0; i < array.length; i++) {
	 final float[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dataOutput.writeFloat(t[d]);
	}
}
/** Stores a big-array fragment to a file given by a {@link File} object.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param offset the index of the first element of {@code array} to be written.
	* @param length the number of elements of {@code array} to be written.
	* @param file a file.
	*/
public static void storeFloats(final float array[][], final long offset, final long length, final File file) throws IOException {
	it.unimi.dsi.fastutil.floats.FloatBigArrays.ensureOffsetLength(array, offset, length);
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = segment(offset); i < segment(offset + length + SEGMENT_MASK); i++) {
	 final float[] t = array[i];
	 final int l = (int)Math.min(t.length, offset + length - start(i));
	 for(int d = (int)Math.max(0, offset - start(i)); d < l; d++) dos.writeFloat(t[d]);
	}
	dos.close();
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
/** Stores an array to a file given by a {@link File} object.
	*
	* @param array an array whose elements will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeFloats(final float array[][], final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	for(int i = 0; i < array.length; i++) {
	 final float[] t = array[i];
	 final int l = t.length;
	 for(int d = 0; d < l; d++) dos.writeFloat(t[d]);
	}
	dos.close();
}
/** Stores a big array to a file given by a pathname.
	*
	* @param array a big array whose elements will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeFloats(final float array[][], final CharSequence filename) throws IOException {
	storeFloats(array, new File(filename.toString()));
}
/** Stores the element returned by an iterator to a given data output.
	*
	* @param i an iterator whose output will be written to {@code dataOutput}.
	* @param dataOutput a filename.
	*/
public static void storeFloats(final FloatIterator i, final DataOutput dataOutput) throws IOException {
	while(i.hasNext()) dataOutput.writeFloat(i.nextFloat());
}
/** Stores the element returned by an iterator to a file given by a {@link File} object.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param file a file.
	*/
public static void storeFloats(final FloatIterator i, final File file) throws IOException {
	final DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
	while(i.hasNext()) dos.writeFloat(i.nextFloat());
	dos.close();
}
/** Stores the element returned by an iterator to a file given by a pathname.
	*
	* @param i an iterator whose output will be written to {@code filename}.
	* @param filename a filename.
	*/
public static void storeFloats(final FloatIterator i, final CharSequence filename) throws IOException {
	storeFloats(i, new File(filename.toString()));
}
/** A wrapper that exhibits the content of a data input stream as a type-specific iterator. */
private static final class FloatDataInputWrapper implements FloatIterator {
	private final DataInput dataInput;
	private boolean toAdvance = true;
	private boolean endOfProcess = false;
	private float next;
	public FloatDataInputWrapper(final DataInput dataInput) {
	 this.dataInput = dataInput;
	}
	@Override
	public boolean hasNext() {
	 if (! toAdvance) return ! endOfProcess;
	 toAdvance = false;
	 try { next = dataInput.readFloat(); }
	 catch(EOFException eof) { endOfProcess = true; }
	 catch(IOException rethrow) { throw new RuntimeException(rethrow); }
	 return ! endOfProcess;
	}
	@Override
	public float nextFloat() {
	 if (! hasNext()) throw new NoSuchElementException();
	 toAdvance = true;
	 return next;
	}
}
/** Wraps the given data input stream into an iterator.
	*
	* @param dataInput a data input.
	*/
public static FloatIterator asFloatIterator(final DataInput dataInput) {
	return new FloatDataInputWrapper(dataInput);
}
/** Wraps a file given by a {@link File} object into an iterator.
	*
	* @param file a file.
	*/
public static FloatIterator asFloatIterator(final File file) throws IOException {
	return new FloatDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
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
