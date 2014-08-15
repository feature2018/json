# json

JSON handling library aiming to minimise verbosity and maximise convenience

    Json addr = new Json() {
    		String host = "localhost";
    		int port = 80;    };
    
    out.println( addr ); // {"host": "localhost", "port": 80}
    out.println( addr.get("port") ); // 80

## Features

 - Simple and convenient constructors (see below)
 - Implements `Map` interface so you can treat it like a Map
 - JDK8 Nashorn compatible - use it in JavaScript functions
 - Convenience methods for building and getting values
 - Serialize to/from String
 - cast-less value access
 - Provides equals/hashCode

## Constructors

    new Json("{port: 80}"); // from string
    new Json( hashMap );  // from map
    new Json("port", 80); // key-value
    new Json() { int port = 80; } // form fields     
## Get/set values

    Json json = new Json().set("port", 80).set("host", "localhost"); // chain
    
    Json nested = json.at("path", "to", "nested", "object");
    
    int port = json.get("port", 0); // avoid casting using default value
    
## Scripting usage

You can pass `Json` to Nashorn JavaScript functions and access/modify it:

    Json addr = new Json("port", 80);

    scriptEngine.eval("function nextPort(addr) { addr.port++; }");

    invocable.invokeFunction("nextPort", addr);
		
    out.println( addr.get("port") ); // 81
             
## Build

    gradle jar
    
Note: running tests requires JDK 8 due to Nashorn tests. JDK 7 is required for compiling and building the jar.

## License

MIT


