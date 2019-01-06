package datastructure.tree;

import java.util.Stack;

/**
 * @author XW
 * @since 2018-12-05 10:52
 */
public class BSTree<E extends Comparable<E>> {

    private Node root;

    private int size;


    public void add(E e) {
        root = add(root, e);
    }

    private Node add(Node node, E e) {
        if (node == null) {
            size ++;
            return new Node(e);
        }
        int compareResult = e.compareTo(node.e);
        if (compareResult > 0) {
            node.right = add(node.right, e);
        } else if (compareResult < 0) {
            node.left = add(node.left, e);
        }
        return node;
    }

    public boolean contains(E e) {
        return contains(root, e);
    }

    private boolean contains(Node node, E e) {
        if (node == null) {
            return false;
        }
        int compareResult = e.compareTo(node.e);
        if (compareResult > 0) {
            return contains(node.right, e);
        } else if (compareResult < 0) {
            return contains(node.left, e);
        } else {
            return true;
        }
    }

    public void preOrderNR() {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.print(node.e);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        System.out.println();
    }

    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node node) {
        if (node == null) {
            return;
        }

        System.out.print(node.e + "");
        preOrder(node.left);
        preOrder(node.right);
    }


    public void inOrderNR() {
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
                System.out.print(node.e);
                stack.push(node.right);
            }
        }

        System.out.println();
    }

    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node node) {
        if (node == null) {
            return;
        }

        inOrder(node.left);
        System.out.print(node.e + "");
        inOrder(node.right);
    }

    public void postOrderNR() {
        if (root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(root);

        boolean visitLeft;
        Node node, prevNode ;

        while (!stack.isEmpty()) {
            while ((node = stack.peek()) != null) {
                stack.push(node.left);
            }

            prevNode = null;
            visitLeft = true;
            stack.pop();

            while (visitLeft && !stack.isEmpty()) {
                node = stack.peek();
                // 右子树为空或者右子树已经被访问才可以访问根节点
                if (node.right == prevNode) {
                    node = stack.pop();
                    System.out.print(node.e);
                    prevNode = node;
                // 访问右节点
                } else {
                    stack.push(node.right);
                    visitLeft = false;
                }
            }
        }

        System.out.println();
    }

    public void postOrder() {
        postOrder(root);
        System.out.println();
    }

    private void postOrder(Node node) {
        if (node == null) {
            return;
        }

        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.e + "");
    }

    public boolean remove(E e) {
        root = remove(root, e);
        return true;
    }

    private Node remove(Node node, E e) {
        if (node == null) {
            return null;
        }
        int compare = e.compareTo(node.e);
        if (compare > 0) {
            node.right = remove(node.right, e);
            return node;
        } else if (compare < 0) {
            node.left = remove(node.left, e);
            return node;
        } else {
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }

            // 待删除节点右子树为空的情况
            else if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }

            Node afterNode = minNode(node.right);
            if (node.right != afterNode) {
                afterNode.right = removeMin(node.right);
            }
            afterNode.left = node.left;
            node.left = node.right = null;

            return afterNode;
        }
    }

    public E removeMax() {
        Node node = maxNode(root);
        root = removeMax(root);
        return node.e;
    }

    private Node removeMax(Node node) {
        if (node.right == null) {
            Node left = node.left;
            node.left = null;
            size --;
            return left;
        }

        node.right = removeMax(node.right);

        return node;
    }

    public E removeMin() {
        Node node = minNode(root);
        root = removeMin(root);
        return node.e;
    }

    private Node removeMin(Node node) {
        if (node.left == null) {
            Node right = node.right;
            node.right = null;
            size --;
            return right;
        }

        node.left = removeMin(node.left);
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

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private class Node {
        private E e;

        private Node left;

        private Node right;

        public Node(E e) {
            this.e = e;
        }

        @Override
        public String toString() {
            return String.valueOf(e);
        }
    }

    public static void main(String[] args) {
        BSTree<Integer> tree = new BSTree<>();
        int[] nums = {5,3,6,8,4,2,9,10,1};
        for (int i = 0; i < nums.length; i ++) {
            tree.add(nums[i]);
        }

        tree.inOrder();
        System.out.println(tree.removeMax());
        System.out.println(tree.removeMin());
        tree.inOrder();
        tree.postOrder();
        tree.postOrderNR();
    }
}
