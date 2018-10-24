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
package it.unimi.dsi.fastutil.longs;
import java.util.Set;
/** A type-specific {@link Set}; provides some additional methods that use polymorphism to avoid (un)boxing.
	*
	* <p>Additionally, this interface strengthens (again) {@link #iterator()}.
	*
	* @see Set
	*/
public interface LongSet extends LongCollection , Set<Long> {
	/** Returns a type-specific iterator on the elements of this set.
	 *
	 * <p>Note that this specification strengthens the one given in {@link java.lang.Iterable#iterator()},
	 * which was already strengthened in the corresponding type-specific class,
	 * but was weakened by the fact that this interface extends {@link Set}.
	 *
	 * @return a type-specific iterator on the elements of this set.
	 */
	@Override
	LongIterator iterator();
	/** Removes an element from this set.
	 *
	 * <p>Note that the corresponding method of a type-specific collection is {@code rem()}.
	 * This unfortunate situation is caused by the clash
	 * with the similarly named index-based method in the {@link java.util.List} interface.
	 *
	 * @see java.util.Collection#remove(Object)
	 */
	boolean remove(long k);
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@SuppressWarnings("deprecation")
	@Deprecated
	@Override
	default boolean remove(final Object o) {
	 return LongCollection.super.remove(o);
	}
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@SuppressWarnings("deprecation")
	@Deprecated
	@Override
	default boolean add(final Long o) {
	 return LongCollection.super.add(o);
	}
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead.
	 */
	@SuppressWarnings("deprecation")
	@Deprecated
	@Override
	default boolean contains(final Object o) {
	 return LongCollection.super.contains(o);
	}
	/** Removes an element from this set.
	 *
	 * <p>This method is inherited from the type-specific collection this
	 * type-specific set is based on, but it should not used as
	 * this interface reinstates {@code remove()} as removal method.
	 *
	 * @deprecated Please use {@code remove()} instead.
	 */
	@Deprecated
	@Override
	default boolean rem(long k) {
	 return remove(k);
	}
}
