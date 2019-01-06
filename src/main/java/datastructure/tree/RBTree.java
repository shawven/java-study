package datastructure.tree;

import datastructure.FileOperation;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author XW
 * @since 2018-12-05 10:52
 */
public class RBTree<K extends Comparable<K>, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private Node root;

    private int size;

    public void add(K key, V value) {
        root = add(root, key, value);
        root.color = BLACK;
    }

    private Node add(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }

        int compareResult = key.compareTo(node.key);
        if (compareResult > 0) {
            node.right = add(node.right, key, value);
        } else if (compareResult < 0) {
            node.left = add(node.left, key, value);
        } else {
            node.value = value;
        }

        if (!isRed(node.left) && isRed(node.right)) {
            node = leftRotate(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

    // 对节点x进行向右旋转操作，返回旋转后新的根节点x
    //     node                   x
    //    /   \     右旋转       /  \
    //   x    T2   ------->   y   node
    //  / \                       /  \
    // y  T1                     T1  T2
    private Node rightRotate(Node node) {
        Node x = node.left;
        Node T1 = x.right;

        // 向右旋转过程
        x.right = node;
        node.left = T1;

        x.color = node.color;
        // 红色表明与x节点的临时融合关系
        node.color = RED;
        return x;
    }

    // 对节点x进行向左旋转操作，返回旋转后新的根节点x
    //   node                     x
    //  /   \     左旋转         /  \
    // T1   x   --------->   node   T3
    //     / \              /   \
    //    T2 T3            T1   T2
    private Node leftRotate(Node node) {
        Node x = node.right;
        Node T2 = x.left;

        x.left = node;
        node.right = T2;

        x.color = node.color;
        // 红色表明与x节点的临时融合关系
        node.color = RED;
        return x;
    }

    private void flipColors(Node node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    public V get(K key) {
        Node node = get(root, key);
        return node == null ? null : node.value;
    }

    private Node get(Node node, K key) {
        if (node == null) {
            return null;
        }

        boolean equals = key.equals(node.key);
        if (equals) {
            return node;
        } else if (key.compareTo(node.key) > 0) {
            return get(node.right, key);
        } else {
            return get(node.left, key);
        }
    }

    public boolean contains(K key) {
        return get(root, key) != null;
    }

    private Node minNode(Node node) {
        if (node.left == null) {
            return node;
        }
        return minNode(node.left);
    }

    private Node maxNode(Node node) {
        if (node.right == null) {
            return node;
        }
        return maxNode(node.right);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isRed(Node node) {
        if (node == null) {
            return false;
        }
        return !node.color;
    }

    private class Node {

        private K key;

        private V value;

        private Node left;

        private Node right;

        private boolean color = RED;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(key + " : " + value);
        }
    }

    public static void main(String[] args) {

        RBTree<String, Integer> avt = new RBTree<>();

        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile("pride-and-prejudice.txt", words)) {

            for (String word : words) {
                avt.add(word, avt.contains(word) ? avt.get(word) + 1 : 1);
            }

            System.out.println("Total words: " + words.size());
            System.out.println("Total different words: " + avt.size());
            System.out.println("Frequency of PRIDE: " + avt.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + avt.get("prejudice"));
        }
    }
}
