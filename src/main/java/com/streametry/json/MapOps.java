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

import static java.util.Collections.emptyMap;

import java.util.*;
import java.util.Map.Entry;

public class MapOps {

	public static Map<String, Object> EMPTY_MAP = Collections.emptyMap();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	/** Get nested map or emptyMap() if nothing found **/
	public static <K, V> Map<K, V> getNested(Map<K, V> map, K ... keys) {
		Map<K, V> cur = map;

		for(K key : keys) {
			if( cur.get(key) instanceof Map )
				cur = (Map) cur.get(key);
			else return emptyMap();
		}

		return cur;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	/** Merge two maps recursively **/
	public static void mergeMaps(Map<String, Object> m1, Map<String, Object> m2) {

		for(Entry<String, Object> e: m2.entrySet()) {
			Object otherVal = e.getValue();
			if( otherVal instanceof Map ) {
				Object myVal = m1.get(e.getKey());
				if( myVal instanceof Map )
					mergeMaps( (Map) myVal, (Map) otherVal );
				else
					m1.put(e.getKey(), otherVal);

			} else {
				m1.put(e.getKey(), otherVal);
			}
		}
	}

	/** Create a deep copy of map. If a value is a {@link List}, deep copy it as well. **/
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> deepCopyMap(Map<K, V> map) {
		Map<K, V> ret = new LinkedHashMap<K, V>(map.size());

		for(Entry<K, V> e: map.entrySet())
			ret.put(e.getKey(), (V) deepCopyCollection(e.getValue()));

		return ret;
	}

	@SuppressWarnings("unchecked")
	static <E> List<E> deepCopyList(List<E> list) {
		List<E> ret = new ArrayList<E>(list.size());

		for(E e: list)
			ret.add( (E) deepCopyCollection(e) );

		return ret;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static Object deepCopyCollection(Object val) {
		if( val instanceof List )
			return deepCopyList( (List) val);
		else if(val instanceof Map)
			return deepCopyMap( (Map) val );
		else
			return val;
	}
}
