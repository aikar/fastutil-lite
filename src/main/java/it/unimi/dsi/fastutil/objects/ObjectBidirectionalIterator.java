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
package it.unimi.dsi.fastutil.objects;
import it.unimi.dsi.fastutil.BidirectionalIterator;
/** A type-specific bidirectional iterator; provides an additional method to avoid (un)boxing,
	* and the possibility to skip elements backwards.
	*
	* @see BidirectionalIterator
	*/
public interface ObjectBidirectionalIterator <K> extends ObjectIterator <K>, BidirectionalIterator<K> {
	/** Moves back for the given number of elements.
	 *
	 * <p>The effect of this call is exactly the same as that of
	 * calling {@link #previous()} for {@code n} times (possibly stopping
	 * if {@link #hasPrevious()} becomes false).
	 *
	 * @param n the number of elements to skip back.
	 * @return the number of elements actually skipped.
	 * @see #previous()
	 */
	default int back(final int n) {
	 int i = n;
	 while(i-- != 0 && hasPrevious()) previous();
	 return n - i - 1;
	}
	/** {@inheritDoc} */
	@Override
	default int skip(final int n) {
	 return ObjectIterator.super.skip(n);
	}
}
