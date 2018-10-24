/*
	* Copyright (C) 2017 Sebastiano Vigna
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
package it.unimi.dsi.fastutil.chars;
import java.util.Objects;
import java.util.function.Consumer;
/** A type-specific {@link Consumer}; provides methods to consume a primitive type both as object
	* and as primitive.
	*
	* @see Consumer
	* @since 8.0.0
	*/
@FunctionalInterface
public interface CharConsumer extends Consumer<Character>, java.util.function.IntConsumer {
	void accept(char t);
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead. */
	@Deprecated
	@Override
	default void accept(final int t) {
	 accept(it.unimi.dsi.fastutil.SafeMath.safeIntToChar(t));
	}
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead. */
	@Deprecated
	@Override
	default void accept(final Character t) {
	 this.accept(t.charValue());
	}
	default CharConsumer andThen(final CharConsumer after) {
	 Objects.requireNonNull(after);
	 return (char t) -> { accept(t); after.accept(t); };
	}
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead. */
	@Deprecated
	@Override
	default CharConsumer andThen(final java.util.function.IntConsumer after) {
	 Objects.requireNonNull(after);
	 return (char t) -> { accept(t); after.accept(t); };
	}
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead. */
	@Deprecated
	@Override
	default Consumer<Character> andThen(final Consumer<? super Character> after) {
	 return Consumer.super.andThen(after);
	}
}
