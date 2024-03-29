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

import static com.streametry.json.JsonSerializer.getFieldValue;
import static com.streametry.json.JsonSerializer.parse;
import static com.streametry.json.JsonSerializer.toJsonString;
import static com.streametry.json.MapOps.EMPTY_MAP;
import static com.streametry.json.MapOps.deepCopyMap;
import static com.streametry.json.MapOps.getNested;
import static com.streametry.json.MapOps.mergeMaps;
import static java.lang.reflect.Modifier.isStatic;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/** JSON Object **/
public class Json extends MapBindings {

	public static final Json EMPTY = new Json(EMPTY_MAP);

	/** Map where all the properties are stored. Nested Json objects are stored as Maps **/
	Map<String, Object> map;

	/** Construct empty JSON Object **/
	public Json() {
	}

	/**  Construct a JSON Object by parsing a string **/
	public Json(String jsonString) {
		map = parse(jsonString);
	}

	/**  Construct a JSON Object from a map. Also usable as a copy constructor **/
	public Json(Map<String, Object> properties) {
		map = unwrap(properties);
	}

	/**  Construct a JSON Object with single key and value **/
	public Json(String key, Object value) {
		set(key, value);
	}

	@Override
	/** Get the underlying map that stores keys and values **/
	public Map<String, Object> toMap() {
		if( map == null )
			map = readFields();

		return map;
	}

	public boolean hasOwnProperty(String key) {
		return containsKey(key);
	}

	@Override
	/** Convert to JSON string representation **/
	public String toString() {
		return toJsonString(toMap());
	}

	@Override
	public boolean equals(Object it) {
		return (it instanceof Json) && toMap().equals( ((Json)it).toMap() );
	}

	@Override
	public int hashCode() {
		return toMap().hashCode();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	/** Get a value or a default value **/
	public <T> T get(String key, T defaultValue) {
		Object v = toMap().get(key);
		if( defaultValue instanceof Json && v instanceof Map ) v = new Json( (Map) v );
		return v==null ? defaultValue : (T) v;
	}

	/** Same as {@link #put(String, Object)} but returns itself for chaining **/
	public Json set(String key, Object val) {
		put(key, val);
		return this;
	}

	@Override
	public Object put(String key, Object value) {
		return toMap().put(key, unwrap(value));
	}

	@SuppressWarnings("unchecked")
	/** Get the map out of Json. Store nested Json objects as Maps **/
	static <T> T unwrap(T val) {
		if( val instanceof Json ) return (T) ((Json) val).toMap();
		return val;
	}

	/** Merge other json into this one recursively. Overwrites values from other if they exist in this.
	 * @return this
	 **/
	public Json merge(Json other) {
		mergeMaps(toMap(), other.toMap());
		return this;
	}

	/**
	 * Get nested JSON object
	 * @param keys path to nested object
	 * @return nested JSON object or {@link #EMPTY} JSON object if nothing found
	 */
	public Json at(String ... keys) {
		Map<String, Object> nested = getNested(toMap(), keys);
		return (nested == EMPTY_MAP) ? EMPTY : new Json(nested);
	}

	/** Create a (deep) copy of this Json. **/
	public Json copy() {
		return new Json( deepCopyMap(toMap()) );
	}

	/** Read declared field values using reflection **/
	Map<String, Object> readFields() {
		Map<String, Object> fieldVals = new LinkedHashMap<String, Object>();
		for(Field f: getClass().getDeclaredFields()) {
			Object val = getFieldValue(f, this);
			if( val != null && !isStatic(f.getModifiers()) )
				fieldVals.put( f.getName() , val);
		}
		return fieldVals;
	}

}

