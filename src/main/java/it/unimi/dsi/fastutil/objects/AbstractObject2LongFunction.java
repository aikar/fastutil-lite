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
public abstract class AbstractObject2LongFunction <K> implements Object2LongFunction <K>, java.io.Serializable {
	private static final long serialVersionUID = -4940583368468432370L;
	protected AbstractObject2LongFunction() {}
	/**
	 * The default return value for {@code get()}, {@code put()} and
	 * {@code remove()}.
	 */
	protected long defRetValue;
	@Override
	public void defaultReturnValue(final long rv) {
	 defRetValue = rv;
	}
	@Override
	public long defaultReturnValue() {
	 return defRetValue;
	}
}
