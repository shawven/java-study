package datastructure.stack;

import datastructure.LinkedList;

/**
 * @author XW
 * @since 2018-12-04 23:47
 */
public class LinkedListStack<E> {

    private LinkedList<E> data;

    public LinkedListStack() {
        data = new LinkedList<>();
    }

    public void push(E e) {
        data.addFirst(e);
    }

    public E pop() {
        return data.removeFirst();
    }

    public E peek() {
        return data.getFirst();
    }

    @Override
    public String toString() {
        return "LinkedListStack{" +
                "data=" + data +
                '}';
    }


}
