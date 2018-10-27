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
import it.unimi.dsi.fastutil.Stack;
/** A type-specific {@link Stack}; provides some additional methods that use polymorphism to avoid (un)boxing. */
public interface DoubleStack extends Stack<Double> {
	/** Pushes the given object on the stack.
	 * @param k the object to push on the stack.
	 * @see Stack#push(Object)
	 */
	void push(double k);
	/** Pops the top off the stack.
	 *
	 * @return the top of the stack.
	 * @see Stack#pop()
	 */
	double popDouble();
	/** Peeks at the top of the stack (optional operation).
	 * @return the top of the stack.
	 * @see Stack#top()
	 */
	double topDouble();
	/** Peeks at an element on the stack (optional operation).
	 * @param i an index from the stop of the stack (0 represents the top).
	 * @return the <code>i</code>-th element on the stack.
	 * @see Stack#peek(int)
	 */
	double peekDouble(int i);
	/** {@inheritDoc}
	 * <p>This default implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead. */
	@Deprecated
	@Override
	default void push(Double o) {
	 push(o.doubleValue());
	}
	/** {@inheritDoc}
	 * <p>This default implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead. */
	@Deprecated
	@Override
	default Double pop() {
	 return Double.valueOf(popDouble());
	}
	/** {@inheritDoc}
	 * <p>This default implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead. */
	@Deprecated
	@Override
	default Double top() {
	 return Double.valueOf(topDouble());
	}
	/** {@inheritDoc}
	 * <p>This default implementation delegates to the corresponding type-specific method.
	 * @deprecated Please use the corresponding type-specific method instead. */
	@Deprecated
	@Override
	default Double peek(final int i) {
	 return Double.valueOf(peekDouble(i));
	}
}
