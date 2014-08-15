/*
The MIT License (MIT)

Copyright (c) 2014 The original author or authors ("Streametry")

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package com.streametry.json;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.script.Bindings;

public abstract class MapBindings implements Bindings {

	public static Map<String, Object> EMPTY_MAP = Collections.emptyMap();

	public abstract Map<String, Object> toMap();

	@Override
	public int size() {
		return toMap().size();
	}

	@Override
	public boolean isEmpty() {
		return toMap().isEmpty();
	}

	@Override
	public boolean containsValue(Object value) {
		return toMap().containsValue(value);
	}

	@Override
	public void clear() {
		toMap().clear();
	}

	@Override
	public Set<String> keySet() {
		return toMap().keySet();
	}

	@Override
	public Collection<Object> values() {
		return toMap().values();
	}

	@Override
	public Set<Map.Entry<String, Object>> entrySet() {
		return toMap().entrySet();
	}

	@Override
	public Object put(String name, Object value) {
		return toMap().put(name, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> toMerge) {
		toMap().putAll(toMerge);
	}

	@Override
	public boolean containsKey(Object key) {
		return toMap().containsKey(key);
	}

	@Override
	public Object get(Object key) {
		return toMap().get(key);
	}

	@Override
	public Object remove(Object key) {
		return toMap().get(key);
	}

}
