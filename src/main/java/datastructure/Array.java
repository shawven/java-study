package datastructure;

import java.util.Arrays;

/**
 * @author XW
 * @since 2018-12-02 18:29
 */
public class Array<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] EMPTY_ARRAY = {};

    private E[] elements;

    private int size;

    public Array(E[] elements) {
        this.elements = elements;
        size = elements.length;
    }

    public Array() {
        elements = (E[]) EMPTY_ARRAY;
    }

    public Array(int capacity) {
        if (capacity == 0) {
            elements = (E[]) EMPTY_ARRAY;
        } else if (capacity > 0) {
            elements = (E[]) new Object[capacity];
        } else {
            throw new IllegalArgumentException("Illegal capacity:" + capacity);
        }
    }

    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    public E getFirst() {
        return get(0);
    }

    public E getLast() {
        return get(size - 1);
    }

    public boolean add(E element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
        return true;
    }

    public boolean add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacity(size + 1);

        int movedNum = size - index;
        if (movedNum > 0) {
            System.arraycopy(elements, index, elements, index + 1, movedNum);
        }

        elements[index] = element;
        size ++;
        return true;
    }

    public E set(int index, E element) {
        rangeCheck(index);
        E oldValue = elements[index];
        elements[index] = element;
        return oldValue;
    }

    public E remove(int index) {
        rangeCheck(index);
        E element = elements[index];

        int movedNum = size - index - 1;
        if (movedNum > 0) {
            System.arraycopy(elements, index + 1, elements, index, movedNum);
        }

        elements[--size] = null;
        return element;
    }

    public boolean remove(E element) {
        int index = indexOf(element);
        if (-1 != index) {
            remove(index);
            return true;
        }
        return false;
    }

    public void swap(int i, int j) {
        E oldValue = get(i);
        elements[i] = elements[j];
        elements[j] = oldValue;
    }

    public boolean isEmpty() {
        return 0 == size;
    }

    public int size() {
        return size;
    }

    public boolean contains(E element) {
        return indexOf(element) > -1;
    }

    public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i <= size; ++i) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i <= size; ++i) {
                if (element.equals(elements[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    public int lastIndexOf(E element) {
        if (element == null) {
            for (int i = size - 1; i >= 0; --i) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; --i) {
                if (element.equals(elements[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    private void rangeCheck(int index) {
        if (size <= index) {
            throw new IndexOutOfBoundsException("Illegal Index: " + index);
        }
    }

    private void rangeCheckForAdd(int index) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException("Illegal Index: " + index);
        }
    }

    private void ensureCapacity(int capacity) {
        if (size <= capacity) {
            int exceptedCapacity = Math.max(DEFAULT_CAPACITY, 2 * capacity);
            elements = Arrays.copyOf(elements, exceptedCapacity);
        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; i < size ; i++) {
            b.append(String.valueOf(elements[i]));
            if (i == size - 1) {
                 b.append(']');
                 break;
            }
            b.append(",");
        }

        return "Array{" +
                "size=" + size +
                ", elements=" + b.toString() +
                '}';
    }
}
