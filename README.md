# Functionality

* ### Proceeds to lexical analysis, according to src/main/jflex/lexer.flex
* ### Then to syntax analysis, according to the rules in src/main/cup/grammar.cup 
* ### Prints the Abstract Syntax Tree (AST)
* ### Creates SymbolTable, and inserts Symbols and Types in it
* ### Produces 3-Address Code, but in wrong order (and doesn't support arrays)

# Compilation Instructions

### 1. Install maven 

### 2. Compile using maven
```
mvn clean install
```

### 3. Execute

java -jar target/compiler-0.1.5.jar input-filename

eg.
```
java -jar target/compiler-0.1.5.jar examples/Hello.gra
```

### or 

java -cp target/compiler-0.1.5.jar Compiler input-filename

eg.
```
java -cp target/compiler-0.1.5.jar Compiler examples/Hello.gra
```

# Alternatives
* ### If you want to just print the AST, just comment out the rest of the lines in src/main/java/Compiler.java
* ### Do the same if you want another functionality to be run separately.