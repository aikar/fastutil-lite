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
package it.unimi.dsi.fastutil.booleans;
import java.util.Objects;
import java.util.function.Consumer;
/** A type-specific {@link Consumer}; provides methods to consume a primitive type both as object
	* and as primitive.
	*
	* @see Consumer
	* @since 8.0.0
	*/
@FunctionalInterface
public interface BooleanConsumer extends Consumer<Boolean> {
	void accept(boolean t);
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead. */
	@Deprecated
	@Override
	default void accept(final Boolean t) {
	 this.accept(t.booleanValue());
	}
	default BooleanConsumer andThen(final BooleanConsumer after) {
	 Objects.requireNonNull(after);
	 return (boolean t) -> { accept(t); after.accept(t); };
	}
	/** {@inheritDoc}
	 * @deprecated Please use the corresponding type-specific method instead. */
	@Deprecated
	@Override
	default Consumer<Boolean> andThen(final Consumer<? super Boolean> after) {
	 return Consumer.super.andThen(after);
	}
}
