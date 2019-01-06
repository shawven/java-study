package datastructure;

import java.util.Random;

/**
 * @author XW
 * @since 2018-12-06 16:28
 */
public class Heap<E extends Comparable<E>> {

    private Array<E> data;

    private int size;

    public Heap() {
        this.data = new Array<>();
    }

    public Heap(int capacity) {
        this.data = new Array<>(capacity);
    }

    public Heap(E[] data) {
        this.data = new Array<>(data);
        this.size = this.data.size();
        heapfiy();
    }

    public void add(E e) {
        data.add(e);
        size ++;
        shiftUp(size - 1);
    }


    private void shiftUp(int index) {
        while (index > 0 && data.get(parent(index)).compareTo(data.get(index)) < 0) {
            data.swap(index, parent(index));
            index = parent(index);
        }
    }

    public E extractMax() {
        E maxValue = findMax();
        data.swap(0, size - 1);
        data.remove(size - 1);
        size --;
        shiftDown(0);
        return maxValue;
    }

    private void shiftDown(int index) {
        int leftIndex;

        while ((leftIndex = leftChild(index)) < size){
            if ((leftIndex + 1) < size
                    && data.get(leftIndex + 1).compareTo(data.get(leftIndex)) > 0) {
                leftIndex ++;
            }

            if ((data.get(index).compareTo(data.get(leftIndex)) >= 0) ) {
                break;
            }
            data.swap(index, leftIndex);
            index = leftIndex;
        }
    }

    public E findMax() {
        return isEmpty() ? null: data.getFirst();
    }

    private void heapfiy() {
        if (isEmpty()) {
            return;
        }

        int parentIndexOfLastLeaf = parent(size - 1);
        for (int i = parentIndexOfLastLeaf; i >= 0; i--) {
            shiftDown(i);
        }
    }

    private int parent(int index) {
        if (index == 0) {
            throw new IllegalArgumentException("Index: 0 had not parent");
        }
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public String toString() {
        return "Heap{" +
                "data=" + data +
                '}';
    }


    public static void main(String[] args) {
        Heap<Integer> heap = new Heap<>();

        for (int i = 0; i < 100000; i++) {
            heap.add(new Random().nextInt(Integer.MAX_VALUE));
        }

        int[] ints = new int[heap.size];
        for (int i = 0; i< ints.length; i++) {
            ints[i] = heap.extractMax();
        }

        for (int i = 1; i< ints.length; i++) {
            if (ints[i - 1] < ints[i]) {
                throw new RuntimeException("error");
            }
        }
    }
}
