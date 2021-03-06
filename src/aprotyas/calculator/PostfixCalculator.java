/*
 *  File name: PostfixCalculator.java
 *  Package: aprotyas.calculator
 *  File description: `PostfixCalculator` is the class used to evaluate postfix
 *  expressions.
 *  This class is also composed of static functions entirely,
 *  the first of which is parsePostfix() - parses a postfix expression (String)
 *  and returns an ArrayList<String> of tokens to perform the evaluation on.
 *  The second function is computeExpression() - performs postfix evaluation
 *  algorithm.
 */

package aprotyas.calculator;

import static aprotyas.util.Util.*;

import aprotyas.util.Stack;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PostfixCalculator {
  static String computeExpression(String expression) {
    String output = "";

    // stack holds operands
    Stack<String> operands = new Stack<String>();

    // split string at commas, get an ArrayList of tokens
    ArrayList<String> parsed_postfix = parsePostfix(expression);
    if (parsed_postfix.get(0).equals("Error")) {
      output += parsed_postfix.get(1); // error message contained here. How smart Abrar :-)
      return output;
    }

    // iterating by token through postfix expression
    for (String token : parsed_postfix) {
      if (isNumber(token)) {
        operands.push(token);
      } else if (isOperator(token)) {
        if (isBinaryOperator(token)) {
          if (operands.size() < 2) {
            // should not come here. invalid expression
            output += "Error during expression evaluation";
            return output;
          } else {
            // pop top two operands from stack
            // top -> right operand
            // perform binary operation, then push back to stack

            float right = Float.parseFloat(operands.pop());
            float left = Float.parseFloat(operands.pop());
            try {
              float result = binaryOperation(left, right, token);
              // formatting to match expected output
              operands.push(String.format("%.2f", result));
            } catch (Exception err) {
              output += err.getMessage();
              return output;
            }
          }
        } else if (isUnaryOperator(token)) {
          if (operands.size() < 1) {
            // should not come here. invalid expression
            output += "Error during expression evaluation";
            return output;
          } else {
            // pop one operand from stack
            // perform unary operation, then push back to stack

            float operand = Float.parseFloat(operands.pop());
            try {
              float result = unaryOperation(operand, token);
              operands.push(String.format("%.2f", result));
            } catch (Exception err) {
              output += err.getMessage();
              return output;
            }
          }
        }
      } else {
        // not supposed to come here, just say invalid token found
        output += "Invalid token in postfix expression";
        return output;
      }
    }

    if (operands.size() != 1) {
      // if there isn't just a single element remaining in
      // the stack, something must have gone wrong. Report error here
      output += "Error during expression evaluation";
      return output;
    } else {
      // final result of evaluated expression
      output += operands.pop();
    }
    return output;
  }

  public static ArrayList<String> parsePostfix(String postfix_expression) {
    /*
     * This function parses an postfix_expression string using Java scanner's
     * pattern matching functionality.
     *
     * The scanner in this function splits by commas
     *
     * It returns an ArrayList of Strings representing parsed tokens in a postfix
     * expression
     */

    ArrayList<String> parsed_postfix = new ArrayList<String>();

    String search_pattern = ",";
    Scanner scanner = new Scanner(postfix_expression);
    scanner.useDelimiter(search_pattern);

    try {
      while (scanner.hasNext()) {
        // add tokens separated by commas
        parsed_postfix.add(scanner.next());
      }
      scanner.close();
    } catch (InputMismatchException err) {
      System.out.println("Problem parsing infix expression: " + postfix_expression);
    }
    return parsed_postfix;
  }
}
