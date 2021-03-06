/*
 *  File name: LinkedList.java
 *  Package: aprotyas.util
 *  File description: The `LinkedList` class implements a LinkedList
 *  data structure. It is parametrized by a genetic <T> parameter.
 *  Implementation is limited to the standard set of methods available
 *  for a LinkedList, plus a few peek and print operations.
 */

package aprotyas.util;

public class LinkedList<T> {
  // only need to keep track of first `Node`
  Node<T> first;

  public void addFirst(T o) {
    // make new `Node` instance and assign to first
    Node<T> toAdd = new Node<T>();
    toAdd.data = o;
    if (first == null) {
      first = toAdd;
      first.next = null;
    } else {
      toAdd.next = first;
      first = toAdd;
    }
  }

  public void addLast(T o) {
    // iterate till last node, and assign its next field to new `Node` instance
    Node<T> toAdd = new Node<T>();
    toAdd.data = o;
    if (first == null) {
      first = toAdd;
      first.next = null;
    } else {
      Node<T> iterNode = first;
      while (iterNode.next != null) {
        iterNode = iterNode.next;
      }
      toAdd.next = null;
      iterNode.next = toAdd;
    }
  }

  public T getFirst() {
    // reassign first to the node its pointing to and return the data in the first
    // node
    if (first == null) {
      return null;
    }
    T toReturn = first.data;
    first = first.next;
    return toReturn;
  }

  public T peekFirst() {
    // getFirst() minus removing the first node
    if (first == null) {
      return null;
    }
    return first.data;
  }

  public T getLast() {
    // iterate till the last node, set its predecessor's `next` field to null and
    // return the data
    if (first == null) {
      return null;
    }
    Node<T> iterNode = first;
    Node<T> prevNode = null;
    while (iterNode.next != null) {
      prevNode = iterNode;
      iterNode = iterNode.next;
    }
    T toReturn = iterNode.data;
    if (size() != 1) prevNode.next = null;
    else first = null;
    return toReturn;
  }

  public T peekLast() {
    // getLast() minus removing the last node
    if (first == null) {
      return null;
    }
    Node<T> iterNode = first;
    while (iterNode.next != null) {
      iterNode = iterNode.next;
    }
    return iterNode.data;
  }

  public int size() {
    // counts node traversals till null
    Node<T> iterNode = first;
    int listSize = 0;
    while (iterNode != null) {
      iterNode = iterNode.next;
      ++listSize;
    }
    return listSize;
  }

  public void printList() {
    // helper function to print the linked list
    if (first == null) {
      System.out.println("[Empty linked list]");
    }
    System.out.print("[");
    Node<T> iterNode = first;
    while (iterNode != null) {
      System.out.print(iterNode.data + ",");
      iterNode = iterNode.next;
    }
    System.out.println("]");
  }
}
