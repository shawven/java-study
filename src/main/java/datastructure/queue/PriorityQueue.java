package datastructure.queue;

import datastructure.Array;
import datastructure.Heap;

import java.util.Random;

/**
 * @author XW
 * @since 2018-12-06 18:35
 */
public class PriorityQueue<E extends Comparable<E>> {

    private Heap<E> heap;

    public PriorityQueue() {
        heap = new Heap<>();
    }

    public PriorityQueue(int capacity) {
        heap = new Heap<>(capacity);
    }

    public E enqueue(E element) {
        heap.add(element);
        return element;
    }

    public E dequeue() {
        return heap.extractMax();
    }

    public E front() {
        return heap.findMax();
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = heap.size();

        sb.append("[");
        for (int i = 0; i < size; ++i) {
            sb.append(String.valueOf(heap.extractMax()));
            if (i == size - 1) {
                sb.append("]");
                break;
            }
            sb.append(",");
        }

        return "PriorityQueue{" +
                "size=" + size +
                ", data=" + sb.toString() +
                '}';
    }

    public static void main(String[] args) {

        Integer[] ints1 = new Integer[1000000];
        int[] ints2 = new int[1000000];
        for (int i = 0; i < 1000000; i++) {
            ints1[i] = new Random().nextInt(Integer.MAX_VALUE);
        }

        Heap<Integer> heap = new Heap<>(ints1);

        for (int i = 0; i < ints1.length; i++) {
            ints2[i] = heap.extractMax();
        }

        for (int i = 1; i < ints2.length; i++) {
            if (ints2[i - 1] < ints2[i]) {
                System.err.println("error");
            }
        }

    }
}
