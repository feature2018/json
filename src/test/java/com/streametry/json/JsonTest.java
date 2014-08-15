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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;


public class JsonTest {

	@Test
	public void newJsonIsEmpty() throws Exception {
		assertTrue( new Json().isEmpty() );
		assertEquals(Json.EMPTY, new Json());
	}

	@Test
	public void containsConstructorKeyValue() throws Exception {
		Json json = new Json("key", 123);
		assertTrue( json.containsKey("key") );
		assertEquals(123, json.get("key"));
	}

	@Test
	public void containsConstructorMap() throws Exception {
		Object val = 123;
		Map<String, Object> map = Collections.singletonMap("key", val);
		Json json = new Json( map );
		assertTrue( json.containsKey("key") );
		assertEquals(val, json.get("key"));
	}

	@Test
	@SuppressWarnings("unused")
	public void containsAnonymousClassFields() throws Exception {
		Json json = new Json() {
			int key = 123;
			String key2 = "val";
		};
		assertEquals(123, json.get("key"));
		assertEquals("val", json.get("key2"));
	}

	@Test
	public void createdWithHasValues() throws Exception {
		assertEquals(new Json("key", 123), new Json("{key: 123}"));
	}

	@Test
	public void listPropertyProducesArrayInJson() throws Exception {
		Json json = new Json("key", Arrays.asList(1, 2));
		assertEquals("{\"key\":[1,2]}", json.toString());
	}

	@Test
	public void toStringGiveJson() throws Exception {
		Json json = new Json("key", 123);
		assertEquals("{\"key\":123}", json.toString());
	}

	@Test
	public void twoJsonsWithSamePropertiesAreEqual() throws Exception {
		Json json = new Json("key", 123).set("key2", "val");
		Json json2 = new Json("key2", "val").set("key", 123);

		assertEquals( json, json2 );
		assertEquals( json.hashCode(), json2.hashCode() );
	}

	@Test
	public void getReturnsDefaultValueForMissingKey() throws Exception {
		assertEquals("val", new Json().get("key", "val") );

		assertEquals("val", new Json("key", "val").get("key", "val2") );
	}

	@Test
	public void atReturnsNestedJson() throws Exception {
		Json j = new Json("a", new Json("b", new Json("c", 1)));

		assertEquals(new Json("c", 1), j.at("a", "b"));
		assertTrue( j.at("a", "c").isEmpty() );
	}

}
