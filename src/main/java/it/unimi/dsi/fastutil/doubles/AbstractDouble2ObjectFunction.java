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
/** An abstract class providing basic methods for functions implementing a type-specific interface.
	*
	* <p>This class handles directly a default return
	* value (including {@linkplain #defaultReturnValue() methods to access
	* it}). Instances of classes inheriting from this class have just to return
	* {@code defRetValue} to denote lack of a key in type-specific methods. The value
	* is serialized.
	*
	* <p>Implementing subclasses have just to provide type-specific {@code get()},
	* type-specific {@code containsKey()}, and {@code size()} methods.
	*
	*/
public abstract class AbstractDouble2ObjectFunction <V> implements Double2ObjectFunction <V>, java.io.Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected AbstractDouble2ObjectFunction() {}
	/**
	 * The default return value for {@code get()}, {@code put()} and
	 * {@code remove()}.
	 */
	protected V defRetValue;
	@Override
	public void defaultReturnValue(final V rv) {
	 defRetValue = rv;
	}
	@Override
	public V defaultReturnValue() {
	 return defRetValue;
	}
}
