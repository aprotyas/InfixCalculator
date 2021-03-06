# Infix Calculator

## Compiling/running/verifying

The snippet below shows the compile and run steps of my code. The `javac`
compiler performs the compilation step, and `java` runs the actual
`aprotyas.calculator.InfixCalculator.class` file, with `sample_input.txt`
and `test_out.txt` being the input and output files for the calculator
program respectively. 

```
calculator > javac -d bin -sourcepath src -classpath src .\src\aprotyas\calculator\*.java .\src\aprotyas\util\*.java
calculator > java -cp bin aprotyas.calculator.InfixCalculator .\sample_input.txt .\test_out.txt
calculator > fc.exe .\test_out.txt .\sample_out.txt
Comparing files .\test_out.txt and .\sample_out.txt
FC: no differences encountered
```

Note that to replicate these compile and run steps, the $PATH variable needs
to include the local directory containing the JDK binaries (in my case
JDK15 SE). If testing on a Unix platform, replace `fc.exe` with `diff -s` to
assert that the two output files are indeed identical.

## Synopsis
The main method in the InfixCalculator class performs the following
steps in running this program:

1. Read, line-by-line, the input infix expressions.

2. Convert each infix expression to postfix expression using the Shunting-Yard
algorithm.

3. Use functions defined in the PostfixCalculator class to compute the
converted postfix expressions.

4. Record, line-by-line, the output o feach infix expression.
