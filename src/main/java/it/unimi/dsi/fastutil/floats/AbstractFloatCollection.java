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
import java.util.AbstractCollection;
/** An abstract class providing basic methods for collections implementing a type-specific interface.
	*
	* <p>In particular, this class provide {@link #iterator()}, {@code add()}, {@link #remove(Object)} and
	* {@link #contains(Object)} methods that just call the type-specific counterpart.
	*
	* <p><strong>Warning</strong>: Because of a name clash between the list and collection interfaces
	* the type-specific deletion method of a type-specific abstract
	* collection is {@code rem()}, rather then {@code remove()}. A
	* subclass must thus override {@code rem()}, rather than
	* {@code remove()}, to make all inherited methods work properly.
	*/
public abstract class AbstractFloatCollection extends AbstractCollection<Float> implements FloatCollection {
	protected AbstractFloatCollection() {}
	@Override
	public abstract FloatIterator iterator();
	/** {@inheritDoc}
	 *
	 * <p>This implementation always throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public boolean add(final float k) {
	 throw new UnsupportedOperationException();
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation iterates over the elements in the collection,
	 * looking for the specified element.
	 */
	@Override
	public boolean contains(final float k) {
	 final FloatIterator iterator = iterator();
	 while (iterator.hasNext()) if (k == iterator.nextFloat()) return true;
	 return false;
	}
	/** {@inheritDoc}
	 *
	 * <p>This implementation iterates over the elements in the collection,
	 * looking for the specified element and tries to remove it.
	 */
	@Override
	public boolean rem(final float k) {
	 final FloatIterator iterator = iterator();
	 while (iterator.hasNext())
	  if (k == iterator.nextFloat()) {
	   iterator.remove();
	   return true;
	  }
	 return false;
	}
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@SuppressWarnings("deprecation")
	@Deprecated
	@Override
	public boolean add(final Float key) {
	 return FloatCollection.super.add(key);
	}
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	public boolean contains(final Object key) {
	 return FloatCollection.super.contains(key);
	}
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@Deprecated
	@Override
	public boolean remove(final Object key) {
	 return FloatCollection.super.remove(key);
	}
	@Override
	public float[] toArray(float a[]) {
	 if (a == null || a.length < size()) a = new float[size()];
	 FloatIterators.unwrap(iterator(), a);
	 return a;
	}
	@Override
	public float[] toFloatArray() {
	 return toArray((float[]) null);
	}
	/** {@inheritDoc}
	 * @deprecated Please use {@code toArray()} instead&mdash;this method is redundant and will be removed in the future.
	 */
	@Deprecated
	@Override
	public float[] toFloatArray(final float a[]) {
	 return toArray(a);
	}
	@Override
	public boolean addAll(final FloatCollection c) {
	 boolean retVal = false;
	 for(final FloatIterator i = c.iterator(); i.hasNext();)
	  if (add(i.nextFloat())) retVal = true;
	 return retVal;
	}
	@Override
	public boolean containsAll(final FloatCollection c) {
	 for(final FloatIterator i = c.iterator(); i.hasNext();)
	  if (! contains(i.nextFloat())) return false;
	 return true;
	}
	@Override
	public boolean removeAll(final FloatCollection c) {
	 boolean retVal = false;
	 for(final FloatIterator i = c.iterator(); i.hasNext();)
	  if (rem(i.nextFloat())) retVal = true;
	 return retVal;
	}
	@Override
	public boolean retainAll(final FloatCollection c) {
	 boolean retVal = false;
	 for(final FloatIterator i = iterator(); i.hasNext();)
	  if (! c.contains(i.nextFloat())) {
	   i.remove();
	   retVal = true;
	  }
	 return retVal;
	}
	@Override
	public String toString() {
	 final StringBuilder s = new StringBuilder();
	 final FloatIterator i = iterator();
	 int n = size();
	 float k;
	 boolean first = true;
	 s.append("{");
	 while(n-- != 0) {
	  if (first) first = false;
	  else s.append(", ");
	  k = i.nextFloat();
	   s.append(String.valueOf(k));
	 }
	 s.append("}");
	 return s.toString();
	}
}
