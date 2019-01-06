package datastructure.collection;

import datastructure.tree.AVLTree;
import datastructure.tree.RBTree;

/**
 * @author XW
 * @since 2018-12-06 0:52
 */
public class RBTMap<K extends Comparable<K>, V> implements Map<K, V>{

    private RBTree<K, V> rbt = new RBTree<>();

    @Override
    public void put(K key, V value) {
        rbt.add(key, value);
    }

    @Override
    public V get(K key) {
        return rbt.get(key);
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public boolean contains(K key) {
        return rbt.contains(key);
    }

    @Override
    public int size() {
        return rbt.size();
    }

    @Override
    public boolean isEmpty() {
        return rbt.isEmpty();
    }
}
