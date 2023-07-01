Compilation Instructions
------------------------

1. Install maven 

2. Compile using maven
```
mvn clean install
```

3. Execute

java -jar target/compiler-0.1.5.jar input-filename

eg.
```
java -jar target/compiler-0.1.5.jar examples/Hello.gra
```

or 

java -cp target/compiler-0.1.5.jar Compiler input-filename

eg.
```
java -cp target/compiler-0.1.5.jar Compiler examples/Hello.gra
```

