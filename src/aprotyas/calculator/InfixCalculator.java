/*
 *  File name: InfixCalculator.java
 *  Package: aprotyas.calculator
 *  File description: `InfixCalculator` is the main class for the infix calculator
 *  program - as the namesake suggests. The main() function in this class:
 *  	a) Uses command line arguments for input/output file names
 *  	b) Reads input infix expressions - IODriver.readExpressions()
 *  	c) Converts from infix to postfix form using the Shunting-Yard
 *  	   algorithm - infixToPostfix()
 *  	d) Evaluates expressions in postfix form - PostfixCalculator.computeExpression()
 *  	e) Outputs results into specified file name - IODriver.writeResults()
 *
 */

package aprotyas.calculator;

import static aprotyas.util.Util.*;

import aprotyas.util.IODriver;
import aprotyas.util.Queue;
import aprotyas.util.Stack;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InfixCalculator {

  public static void main(String[] args) {

    // check if user supplied input/output file names
    if (args.length != 2) {
      System.out.println("Usage: `java InfixCalculator [input-file] [output-file]`");
      return;
    }

    // file names
    String in_name = args[0];
    String out_name = args[1];

    // extract each line of `in_name` as a single infix expression
    ArrayList<String> infix_expressions = IODriver.readExpressions(in_name);

    // convert infix expressions to postfix expressions
    ArrayList<String> postfix_expressions = new ArrayList<String>();
    for (String s : infix_expressions) {
      try {
        postfix_expressions.add(infixToPostfix(s));
      } catch (Exception e) {
        postfix_expressions.add("Error," + e.getMessage());
      }
    }

    // compute postfix expressions
    ArrayList<String> outputs = new ArrayList<String>();
    for (String s : postfix_expressions) {
      outputs.add(PostfixCalculator.computeExpression(s));
    }

    // write to output
    IODriver.writeResults(out_name, outputs);

    return;
  }

  public static String infixToPostfix(String infix_expression) throws Exception {
    // queue to arrange overall postfix expression
    // stack to fix operator precedence
    Queue<String> expression = new Queue<String>();
    Stack<String> operators = new Stack<String>();

    ArrayList<String> parsed_infix = parseInfix(infix_expression);

    for (String token : parsed_infix) {
      if (isNumber(token)) {
        // operands uncondtionally enqueued
        expression.enqueue(token);
      } else if (isLeftParen(token)) {
        operators.push(token);
      } else if (isRightParen(token)) {
        // pop -> enqueue operators till matching paren found
        while (true) {
          if (operators.empty()) {
            // throw exception for imbalanced expression
            throw new Exception("Imbalanced infix expression");
          }
          String temp_token = operators.pop();
          if (isLeftParen(temp_token)) {
            // matching opening paren found, done with paren balancing
            break;
          } else {
            expression.enqueue(temp_token);
          }
        }
      } else if (isOperator(token)) {
        // pop -> enqueue operators till lower precedence
        // or right-associate equal precedence is found

        while (true) {
          if (operators.empty()) {
            // if stack empty, just push
            operators.push(token);
            break;
          } else if (precedence(operators.peek()) == 0) {
            // top of stack is left paren, have to push current token to stack
            operators.push(token);
            break;
          } else {
            // check for left-associative operator of lower precedence
            boolean found_lower_left =
                (precedence(token) > precedence(operators.peek()))
                    && isLeftAssociative(operators.peek());
            // check for right-associative operator of equal (or lower) precedence
            boolean found_equal_right =
                (precedence(token) >= precedence(operators.peek()))
                    && isRightAssociative(operators.peek());
            if (found_lower_left || found_equal_right) {
              // symbol of lower precedence (or right-associative equal precedence) found
              // push current token to stack
              operators.push(token);
              break;
            } else {
              // pop from stack and enqueue to expression
              expression.enqueue(operators.pop());
            }
          }
        }
      } else {
        // invalid token present in infix expression
        throw new Exception("Invalid token in infix expression");
      }
    }

    // pop -> enqueue any remaining operators in the stack
    while (!operators.empty()) {
      expression.enqueue(operators.pop());
    }

    // convert expression queue to a comma separated string of tokens
    String postfix_expression = new String("");
    while (!expression.empty()) {
      postfix_expression += expression.dequeue();
      postfix_expression += expression.empty() ? "" : ",";
    }

    return postfix_expression;
  }

  public static ArrayList<String> parseInfix(String infix_expression) {
    /*
     * This function parses an infix_expression string using Java scanner's pattern
     * matching functionality.
     *
     * The scanner in this function looks for any whitespace or punctuation to do
     * its pattern matching, which coincidentally covers the entire space of
     * operators we need to match against.
     *
     * It returns an ArrayList of Strings, where each String can be: i) A number
     * ("5.2") ii) A parentheses ("(") iii) An operator ("&")
     */

    ArrayList<String> parsed_infix = new ArrayList<String>();

    // https://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
    String search_pattern =
        "((?<=\\p{javaWhitespace})|(?=\\p{javaWhitespace})|(?<=\\p{Punct})|(?=\\p{Punct}))";
    Scanner scanner = new Scanner(infix_expression);
    scanner.useDelimiter(search_pattern);

    try {
      while (scanner.hasNext()) {
        // if next token is a full stop, create a floating point number
        if (scanner.hasNext("\\.")) {
          int last_idx = parsed_infix.size() - 1;
          parsed_infix.set(last_idx, parsed_infix.get(last_idx) + scanner.next() + scanner.next());
        }
        // skip parsed whitespace
        else if (scanner.hasNext("\\s")) {
          scanner.next();
        }
        // add everything else
        else parsed_infix.add(scanner.next());
      }
      scanner.close();
    } catch (InputMismatchException err) {
      System.out.println("Problem parsing infix expression: " + infix_expression);
    }
    return parsed_infix;
  }
}
