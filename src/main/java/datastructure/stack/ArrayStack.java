package datastructure.stack;

import datastructure.Array;

/**
 * @author XW
 * @since 2018-12-04 12:29
 */
public class ArrayStack<E> {

    private Array<E> array;

    public ArrayStack() {
        array = new Array<E>();
    }

    public ArrayStack(int capacity) {
        array = new Array<E>(capacity);
    }

    public E push(E element) {
        array.add(element);
        return element;
    }

    public E pop() {
        return array.remove(array.size() - 1);
    }

    public E peek() {
        return array.getLast();
    }

    public int size() {
        return array.size();
    }

    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int size = array.size();
        for (int i = 0; i < size; ++i) {
            sb.append(String.valueOf(array.get(i)));
            if (i == size - 1) {
                sb.append("]");
                break;
            }
            sb.append(",");
        }

        return "ArrayStack{" +
                "size=" + size +
                ", elements=" + sb.toString() +
                "}";
    }
}
