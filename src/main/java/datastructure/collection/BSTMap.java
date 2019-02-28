package datastructure.collection;

import java.util.Stack;
import java.util.UUID;

/**
 * 二分搜索树
 *
 * @author XW
 * @since 2018-12-06 0:52
 */
public class BSTMap<K extends Comparable<K>, V> implements Map<K, V>{

    private Node root;

    private int size;

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node node, K key, V value) {
        if (node == null) {
            size ++;
            return new Node(key, value);
        }

        int result = key.compareTo(node.key);
        if (result > 0) {
            node.right = put(node.right, key, value);
        } else if (result < 0) {
            node.left = put(node.left, key,value);
        } else {
            node.value = value;
        }

        return node;
    }

    @Override
    public V get(K key) {
        Node node = get(root, key);
        return node != null ? node.value : null;
    }

    private Node get(Node node, K key) {
        if (node == null) {
            return null;
        }

        int result = key.compareTo(node.key);
        if (result > 0) {
            return get(node.right, key);
        } else if (result < 0) {
            return get(node.left, key);
        } else {
            return node;
        }
    }

    @Override
    public boolean contains(K key) {
        return get(root, key) != null;
    }

    @Override
    public V remove(K key) {
        V v = get(key);
        root = remove(root, key);
        return v;
    }

    private Node remove(Node node, K key) {
        int result = key.compareTo(node.key);
        if (result > 0) {
            node.right = remove(node.right, key);
            return node;
        } else if (result < 0) {
            node.left = remove(node.left, key);
            return node;
        } else {

            if (node.left == null) {
                Node right = node.right;
                node.right = null;
                size --;
                return right;
            }

            if (node.right == null) {
                Node left = node.left;
                node.left = null;
                size --;
                return left;
            }

            Node beforeTopNode = node;
            Node beforeNode = node.left;

            while (beforeNode.right != null) {
                beforeTopNode = beforeNode;
                beforeNode = beforeNode.right;
            }

            beforeTopNode.right = null;
            beforeNode.right = node.right;

            if (beforeNode != node.left) {
                beforeNode.left = node.left;
            }

            size --;
            node.left = node.right = null;

            return beforeNode;
        }
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node() {
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + " : " + value;
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

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
                sb.append("\t").append(node.toString()).append("\n");
                stack.push(node.right);
            }
        }

        return sb.append("}").toString();
    }

    public static void main(String[] args) {
        BSTMap<Integer, String> bstMap = new BSTMap<>();
        int[] nums = {6,2,4,3,5,8,1,9,7};
        for (int i = 0; i < nums.length; i ++) {
            bstMap.put(nums[i], UUID.randomUUID().toString().replace("-", ""));
        }

        System.out.println(bstMap);
        System.out.println(bstMap.remove(3));
        System.out.println(bstMap.remove(5));
        bstMap.put(4, "4444");
        System.out.println(bstMap);
    }
}
