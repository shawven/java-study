package datastructure.queue;

import datastructure.Array;

/**
 * @author XW
 * @since 2018-12-04 15:38
 */
public class ArrayQueue<E> {

    private Array<E> array;

    public ArrayQueue() {
        array = new Array<>();
    }

    public ArrayQueue(int capacity) {
        array = new Array<>(capacity);
    }

    public E enqueue(E element) {
        array.add(element);
        return element;
    }

    public E dequeue() {
        return array.remove(0);
    }

    public E front() {
        return array.getFirst();
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
        int size = array.size();

        sb.append("[");
        for(int i = 0; i < size; ++i) {
            sb.append(String.valueOf(array.get(i)));
            if (i == size - 1) {
                sb.append("]");
                break;
            }
            sb.append(",");
        }

        return "ArrayQueue{" +
                "size=" + size +
                ", data=" + sb.toString() +
                '}';
    }
}
