/*
 *  File name: Util.java
 *  Package: aprotyas.util
 *  File description: The `Util` class consists of a collection of
 *  functions used frequently in both the `InfixCalculator` and the
 *  `PostfixCalculator` classes, for operator identification and
 *  operation evaluation purposes.
 */

package aprotyas.util;

import java.util.ArrayList;

public class Util {
  public static <T> void printList(ArrayList<T> list) {
    // helper function to print an arrayList
    System.out.println("[");
    for (T elem : list) {
      System.out.println(elem);
    }
    System.out.println("]");
  }

  public static boolean isLeftParen(String s) {
    return s.equals("(");
  }

  public static boolean isRightParen(String s) {
    return s.equals(")");
  }

  public static boolean isNumber(String s) {
    // if reinterpreting to a float does not throw an exception, then `s` was a
    // number
    try {
      Float.parseFloat(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static boolean isOperator(String s) {
    // more operators can go here as needed
    return s.equals("+")
        || s.equals("-")
        || s.equals("*")
        || s.equals("/")
        || s.equals(">")
        || s.equals("<")
        || s.equals("=")
        || s.equals("&")
        || s.equals("|")
        || s.equals("!")
        || s.equals("^")
        || s.equals("%")
        || s.equals("sin")
        || s.equals("cos")
        || s.equals("tan");
  }

  public static boolean isUnaryOperator(String s) {
    // isOperator() check is unnecessary but am paranoid
    return isOperator(s)
        && (s.equals("!") || s.equals("sin") || s.equals("cos") || s.equals("tan"));
  }

  public static boolean isBinaryOperator(String s) {
    // isOperator() check is doubly unnecessary but am doubly paranoid
    return isOperator(s) && !isUnaryOperator(s);
  }

  public static int precedence(String s) {
    // precedence list of operators, higher means greater precedence of course
    // http://www.cs.bilkent.edu.tr/~guvenir/courses/CS101/op_precedence.html
    switch (s) {
      case ")":
        return 10;
      case "!":
        return 9;
      case "^":
        return 8;
      case "*":
      case "/":
      case "%":
        return 7;
      case "+":
      case "-":
        return 6;
      case "sin":
      case "cos":
      case "tan":
        return 5; // not sure about this
      case "<":
      case ">":
        return 4;
      case "=":
        return 3;
      case "&":
        return 2;
      case "|":
        return 1;
      case "(":
        return 0;
      default:
        return -1; // should not happen
    }
  }

  public static boolean isRightAssociative(String s) {
    return s.equals("!") || s.equals("^") || s.equals("%");
  }

  public static boolean isLeftAssociative(String s) {
    return !isRightAssociative(s);
  }

  public static float binaryOperation(float left, float right, String operator) throws Exception {
    // if only I could borrow python's eval() :-)
    boolean left_bool = left != 0; // for logical operations
    boolean right_bool = right != 0; // for logical operations
    switch (operator) {
      case "+":
        return left + right;
      case "-":
        return left - right;
      case "*":
        return left * right;
      case "/":
        if (right == 0) {
          throw new Exception(
              "Divide by 0 error"); // should be arithmetic error, but I'm too lazy to catch
          // multiple exception types in PostfixCalculator
        }
        return left / right;
      case "%":
        return left % right;
      case "^":
        return (float) Math.pow(left, right); // Math.pow() -> double

        // for this group, can't return a boolean. 1 = true, 0 = false
      case ">":
        return left > right ? 1 : 0;
      case "<":
        return left < right ? 1 : 0;
      case "=":
        return left == right ? 1 : 0;
      case "&":
        return left_bool && right_bool ? 1 : 0;
      case "|":
        return left_bool || right_bool ? 1 : 0;
      default:
        throw new Exception(
            "Error during expression evaluation"); // should not come here, but if thrown deal with
        // it in PostfixCalculator
    }
  }

  public static float unaryOperation(float operand, String operator) throws Exception {
    boolean operand_bool = operand != 0; // for logical not
    switch (operator) {
      case "!":
        return !operand_bool ? 1 : 0;
      case "sin":
        return (float) Math.sin(operand);
      case "cos":
        return (float) Math.cos(operand);
      case "tan":
        return (float)
            Math.tan(
                operand); // may return NaN (or really large number) - not a problem, I think...
      default:
        throw new Exception(
            "Error during expression evaluation"); // should not come here, but if thrown deal with
                                                   // it in PostfixCalculator
    }
  }
}
