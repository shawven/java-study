package datastructure.queue;

import java.util.Arrays;

/**
 * @author XW
 * @since 2018-12-04 15:59
 */
public class LoopQueue<E> {

    private static final Object[] EMPTY_ARRAY = new Object[]{};

    private static final int DEFAULT_CAPACITY = 10;

    private int size;

    private int head;

    private int tail;

    private E[] data;

    public LoopQueue() {
        this(DEFAULT_CAPACITY);
    }

    public LoopQueue(int capacity) {
        this.data = (E[]) new Object[capacity + 1];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public E enqueue(E e) {
        if ((tail + 1) % data.length == head) {
            ensureCapacity(capacity() * 2);
        }
        data[tail] = e;
        size++;
        tail = (tail + 1) % data.length;
        return e;
    }

    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue form an empty queue");
        }

        E value = data[head];
        data[head] = null;
        size--;
        head = (head + 1) % data.length;
        if (size == capacity() / 4 && size != 0) {
            ensureCapacity(capacity() / 2);
        }
        return value;
    }

    public void ensureCapacity(int capacity) {
        E[] newData = (E[]) new Object[capacity + 1];
        for (int i = 0; i < size; i++) {
            newData[i] = data[(i + head) % data.length];
        }
        data = newData;
        head = 0;
        tail = size;
    }

    public boolean isEmpty() {
        return tail == head;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return data.length - 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(data[(i + head) % data.length]);
            if ((i + head + 1) % data.length != tail) {
                sb.append(",");
            }

        }
        return "LoopQueue{" +
                "size=" + size +
                ", capacity=" + capacity() +
                ", head[" + sb.toString() + "]tail" +
                '}';
    }
}
