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

import static com.streametry.json.JsonSerializer.deserialize;
import static com.streametry.json.JsonSerializer.getFieldValue;
import static java.lang.reflect.Modifier.isStatic;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/** JSON Object **/
public class Json extends MapBindings {

	public static final Json EMPTY = new Json(EMPTY_MAP);

	Map<String, Object> map;

	public Json() {
	}

	public Json(String jsonString) {
		map = deserialize(jsonString);
	}

	public Json(Map<String, Object> properties) {
		map = properties;
	}

	public Json(String key, Object value) {
		toMap().put(key, value);
	}

	@Override
	public Map<String, Object> toMap() {
		if( map == null )
			map = readFields();

		return map;
	}

	public boolean hasOwnProperty(String key) {
		return containsKey(key);
	}

	@Override
	public String toString() {
		return JsonSerializer.toString(toMap());
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Json) && toMap().equals( ((Json)obj).toMap() );
	}

	@Override
	public int hashCode() {
		return toMap().hashCode();
	}

	@SuppressWarnings("unchecked")
	/** Get a value or a default value **/
	public <T> T get(String key, T defaultValue) {
		Object v = toMap().get(key);
		return v==null ? defaultValue : (T) v;
	}

	/** Same as {@link #put(String, Object)} but returns itself for chaining **/
	public Json set(String key, Object val) {
		toMap().put(key, val);
		return this;
	}

	/**
	 * Get nested JSON object or {@link #EMPTY} JSON object if nothing found
	 * @param keys path to nested object
	 */
	public Json at(String ... keys) {
		Json cur = this;

		for(String key : keys)
			if( cur.get(key) instanceof Json )
				cur = cur.get(key, EMPTY);
			else if( cur.get(key) instanceof Map )
				cur = new Json( cur.get(key, EMPTY_MAP) );
			else return EMPTY;

		return cur;
	}

	/** Read declared field values using reflection **/
	Map<String, Object> readFields() {
		Map<String, Object> fieldVals = new LinkedHashMap<>();
		for(Field f: getClass().getDeclaredFields()) {
			Object val = getFieldValue(f, this);
			if( val != null && !isStatic(f.getModifiers()) ) fieldVals.put( f.getName() , val);
		}
		return fieldVals;
	}

}
