# Streametry Json

Mini library for handling JSON objects in Java. Aims to increase convenience, minimise verbosity and play well with Nashorn JavaScript engine

    Json addr = new Json() {
    		String host = "localhost";
    		int port = 80;    };
    
    out.println( addr ); // {"host": "localhost", "port": 80}
    out.println( addr.get("port") ); // 80


## Features

 - Simple and convenient constructors
 - Implements `Map` interface so you can treat it like a Map
 - JDK8 Nashorn compatible - use it in JavaScript functions
 - Convenience methods for building and getting values
 - Serialize to/from String
 - Access properties without explicit casting
 - Provides equals()/hashCode() methods, so you can compare objects

## Download

Grab it from [Maven central](http://search.maven.org/#search%7Cga%7C1%7Cstreametry-json):

- **group**: 'com.streametry', **name**: 'streametry-json', **version**: '1.0.1'

## Constructors

    new Json("{port: 80}"); // from string
    new Json( hashMap );  // from map
    new Json("port", 80); // key-value
    new Json() { int port = 80; } // form fields 
## Get/set values

    Json json = new Json().set("port", 80)
                          .set("host", "localhost"); // chain calls
    
    int port = json.get("port", 0); // avoid casting 
     
    Json nested = json.at("path", "to", "nested", "object");
    
    json.merge( anotherJson ); // merge values recursively

As well as all the methods from Map:

   - isEmpty(), keySet(), containsKey(), entrySet(), ...
    
## Scripting usage

You can pass `Json` to Nashorn JavaScript functions and treat it like a native JSON object:

    Json addr = new Json("port", 80);

    scriptEngine.eval("function nextPort(addr) { addr.port++; }");

    invocable.invokeFunction("nextPort", addr);
		
    out.println( addr.get("port") ); // 81
             
## Build

    gradle jar
    
Note: running tests requires JDK 8 due to Nashorn tests. Java 6 is required for compiling and building the jar.

## License

MIT


