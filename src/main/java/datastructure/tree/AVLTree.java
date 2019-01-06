package datastructure.tree;

import datastructure.FileOperation;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author XW
 * @since 2018-12-05 10:52
 */
public class AVLTree<K extends Comparable<K>, V> {

    private Node root;

    private int size;

    public void add(K key, V value) {
        root = add(root, key, value);
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

        return keepBalance(node);
    }

    // 对节点y进行向右旋转操作，返回旋转后新的根节点x
    //        y                              x
    //       / \                           /   \
    //      x   T4     向右旋转 (y)        z     y
    //     / \       - - - - - - - ->    / \   / \
    //    z   T3                       T1  T2 T3 T4
    //   / \
    // T1   T2
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T3 = x.right;

        // 向右旋转过程
        x.right = y;
        y.left = T3;

        // 更新height
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    // 对节点y进行向左旋转操作，返回旋转后新的根节点x
    //    y                             x
    //  /  \                          /   \
    // T1   x      向左旋转 (y)       y     z
    //     / \   - - - - - - - ->   / \   / \
    //   T2  z                     T1 T2 T3 T4
    //      / \
    //     T3 T4
    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;

        // 向左旋转过程
        x.left = y;
        y.right = T2;

        // 更新height
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
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

    public V remove(K key) {
        Node removed = get(root, key);
        if (removed != null) {
            root = remove(root, key);
            return removed.value;
        }
        return null;
    }

    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }
        int compare = key.compareTo(node.key);
        if (compare > 0) {
            node.right = remove(node.right, key);
            return node;
        } else if (compare < 0) {
            node.left = remove(node.left, key);
            return node;
        } else {
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return keepBalance(rightNode);
            }
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return keepBalance(leftNode);
            }

            Node afterNode = minNode(node.right);
            if (node.right != afterNode) {
                afterNode.right = remove(node.right, afterNode.key);
            }
            afterNode.left = node.left;
            node.left = node.right = null;

            return keepBalance(afterNode);
        }
    }

    private Node keepBalance(Node node) {
        if (node == null) {
            return null;
        }

        // 更新height
        int newHeight = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        if (node.height == newHeight) {
            return node;
        }
        node.height = newHeight;

        // 平衡维护
        int balanceFactor = getBalanceFactor(node);

        // LL
        if (balanceFactor > 1 && getBalanceFactor(node.left) > 0) {
            return rightRotate(node);
        }
        // RR
        if (balanceFactor < -1 && getBalanceFactor(node.right) < 0) {
            return leftRotate(node);
        }
        // LR
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // RL
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
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

    public boolean isBalance() {
        return isBalance(root);
    }

    private boolean isBalance(Node node) {
        if(node == null) {
            return true;
        }

        int balanceFactor = getBalanceFactor(node);
        if(Math.abs(balanceFactor) > 1) {
            return false;
        }
        return isBalance(node.left) && isBalance(node.right);
    }

    public boolean isBST() {
        ArrayList<K> keys = new ArrayList<>();
        getInOrderKeys(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }


    private void getInOrderKeys(Node node, ArrayList<K> keys) {
        if (node == null) {
            return;
        }
        getInOrderKeys(node.left, keys);
        keys.add(node.key);
        getInOrderKeys(node.right, keys);
    }

    public void inOrder() {
        if (root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(root);
        Node node;

        while (!stack.isEmpty()) {
            while ((node = stack.peek()) != null) {
                stack.push(node.left);
            }

            stack.pop();

            if (!stack.isEmpty()) {
                node = stack.pop();
                System.out.print(node.key);
                stack.push(node.right);
            }
        }

        System.out.println();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private class Node {

        private K key;

        private V value;

        private Node left;

        private Node right;

        private int height = 1;

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

        AVLTree<String, Integer> avt = new AVLTree<>();

        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile("pride-and-prejudice.txt", words)) {

            for (String word : words) {
                avt.add(word, avt.contains(word) ? avt.get(word) + 1 : 1);
            }

            System.out.println("Total words: " + words.size());
            System.out.println("Total different words: " + avt.size());
            System.out.println("Frequency of PRIDE: " + avt.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + avt.get("prejudice"));
            System.out.println(avt.isBalance());
            System.out.println(avt.isBST());
        }
    }
}
