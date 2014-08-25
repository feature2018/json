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

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer {

	final static ObjectMapper serializer = new ObjectMapper()
		.configure(ALLOW_COMMENTS, true).configure(ALLOW_UNQUOTED_CONTROL_CHARS, true)
		.configure(ALLOW_SINGLE_QUOTES, true).configure(ALLOW_UNQUOTED_FIELD_NAMES, true);

	public static String toJsonString(Map<String, Object> val) {
		try {
			return serializer.writeValueAsString(val);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/** Get JSON with indentation **/
	public static String toPrettyString(Map<String, Object> val) {
		try {
			return serializer.writerWithDefaultPrettyPrinter().writeValueAsString(val);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> parse(String jsonString) {
		try {
			if( jsonString.isEmpty() ) return new LinkedHashMap<String, Object>();
			return serializer.readValue(jsonString, Map.class);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> serialize(Object pojoBean) {
		try {
			return serializer.convertValue(pojoBean, Map.class);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static Object getFieldValue(Field f, Object o) {
		try {
			f.setAccessible(true);
			return f.get(o);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
}
