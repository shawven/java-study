package datastructure.collection;

import datastructure.tree.AVLTree;

/**
 * 平衡二叉树
 *
 * @author XW
 * @since 2018-12-06 0:52
 */
public class AVTMap<K extends Comparable<K>, V> implements Map<K, V>{

    private AVLTree<K, V> avt = new AVLTree<>();

    @Override
    public void put(K key, V value) {
        avt.add(key, value);
    }

    @Override
    public V get(K key) {
        return avt.get(key);
    }

    @Override
    public V remove(K key) {
        return avt.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return avt.contains(key);
    }

    @Override
    public int size() {
        return avt.size();
    }

    @Override
    public boolean isEmpty() {
        return avt.isEmpty();
    }
}
