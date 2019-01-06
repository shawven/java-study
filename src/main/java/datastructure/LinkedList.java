package datastructure;

/**
 * @author XW
 * @since 2018-12-04 23:11
 */
public class LinkedList<E> {

    private Node virtualHead;

    private int size;

    public LinkedList() {
        virtualHead = new Node(null, null);
    }

    public void add(int index, E e) {
        rangeCheck(index);

        Node prev = virtualHead;
        for(int i = 0; i < index; ++i) {
             prev = prev.next;
        }

        prev.next = new Node(e, prev.next);
        size++;
    }

    public E get(int index) {
        rangeCheck(index);

        Node cur = virtualHead.next;
        for(int i = 0; i < index; ++i) {
            cur = cur.next;
        }

        return cur.e;
    }

    public E remove(int index) {
        rangeCheck(index);

        Node prev = virtualHead;
        Node cur = virtualHead.next;
        for(int i = 0; i < index; ++i) {
            prev = cur;
            cur = cur.next;
        }

        prev.next = cur.next;
        cur.next = null;
        size--;

        return cur.e;
    }

    public void addFirst(E e) {
        add(0, e);
    }

    public E getFirst() {
        return get(0);
    }

    public E removeFirst() {
        return remove(0);
    }

    public void addLast(E e) {
        add(size, e);
    }

    public E getLast() {
        return get(size - 1);
    }

    public E removeLast() {
        return remove(size - 1);
    }

    public boolean isEmpty() {
        return size != 0;
    }

    public int size() {
        return size;
    }

    private void rangeCheck(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Illegal index:" + index);
        }
    }

    private class Node {

        private E e;

        private Node next;

        public Node() {
        }

        private Node(E e) {
            this.e = e;
        }

        private Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        @Override
        public String toString() {
            return String.valueOf(e);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node cur = virtualHead.next;
        while (cur != null) {
            sb.append(cur.e).append("->");
            cur = cur.next;
        }
        return sb.append("NUll").toString();
    }

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            linkedList.addLast(i);
        }
        linkedList.remove(2);
        System.out.println(linkedList);
    }
}
