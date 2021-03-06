/*
 *  File name: Queue.java
 *  Package: aprotyas.util
 *  File description: The `Queue` class implements a FIFO Queue ADT using
 *  a LinkedList data structure. Implementation is limited to the standard
 *  set of methods available for a Queue.
 */

package aprotyas.util;

public class Queue<T> {
  LinkedList<T> queue; // internal data structure

  public Queue() {
    queue = new LinkedList<T>();
  }

  public void enqueue(T o) {
    queue.addLast(o);
  }

  public T dequeue() {
    return queue.getFirst();
  }

  public T peek() {
    return queue.peekFirst();
  }

  public int size() {
    return queue.size();
  }

  public boolean empty() {
    return queue.size() == 0;
  }
}
