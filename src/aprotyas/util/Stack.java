/*
 *  File name: Stack.java
 *  Package: aprotyas.util
 *  File description: The `Stack` class implements a LIFO Stack ADT using
 *  a LinkedList data structure. Implementation is limited to the standard
 *  set of methods available for a Stack.
 */

package aprotyas.util;

public class Stack<T> {
  LinkedList<T> stack; // internal data structure

  public Stack() {
    stack = new LinkedList<T>();
  }

  public void push(T o) {
    stack.addLast(o);
  }

  public T pop() {
    return stack.getLast();
  }

  public T peek() {
    return stack.peekLast();
  }

  public int size() {
    return stack.size();
  }

  public boolean empty() {
    return size() == 0;
  }
}
