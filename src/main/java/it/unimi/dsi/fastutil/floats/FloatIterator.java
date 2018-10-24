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
import java.util.Objects;
import java.util.function.Consumer;
/** A type-specific {@link Iterator}; provides an additional method to avoid (un)boxing, and
	* the possibility to skip elements.
	*
	* @see Iterator
	*/
public interface FloatIterator extends Iterator<Float> {
	/**
	 * Returns the next element as a primitive type.
	 *
	 * @return the next element in the iteration.
	 * @see Iterator#next()
	 */
	float nextFloat();
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead. */
	@Deprecated
	@Override
	default Float next() {
	 return Float.valueOf(nextFloat());
	}
	/**
	 * Performs the given action for each remaining element until all elements
	 * have been processed or the action throws an exception.
	 * @param action the action to be performed for each element.
	 * @see java.util.Iterator#forEachRemaining(java.util.function.Consumer)
	 * @since 8.0.0
	 */
	default void forEachRemaining(final FloatConsumer action) {
	 Objects.requireNonNull(action);
	 while (hasNext()) {
	  action.accept(nextFloat());
	 }
	}
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead. */
	@Deprecated
	@Override
	default void forEachRemaining(final Consumer<? super Float> action) {
	 forEachRemaining((FloatConsumer) action::accept);
	}
	/** Skips the given number of elements.
	 *
	 * <p>The effect of this call is exactly the same as that of calling {@link #next()} for {@code n} times (possibly stopping if {@link #hasNext()} becomes false).
	 *
	 * @param n the number of elements to skip.
	 * @return the number of elements actually skipped.
	 * @see Iterator#next()
	 */
	default int skip(final int n) {
	 if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n);
	 int i = n;
	 while(i-- != 0 && hasNext()) nextFloat();
	 return n - i - 1;
	}
}
