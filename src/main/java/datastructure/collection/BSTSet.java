package datastructure.collection;

import datastructure.LinkedList;

import java.util.UUID;

/**
 * @author XW
 * @since 2018-12-06 0:49
 */
public class BSTSet<E extends Comparable<E>> implements Set<E>{


    private BSTMap<E, Object> map;

    public BSTSet() {
        map = new BSTMap<>();
    }

    @Override
    public void add(E e) {
        map.put(e, null);
    }

    @Override
    public void remove(E e) {
        map.remove(e);
    }

    @Override
    public boolean contains(E e) {
        return map.contains(e);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public String toString() {
        return map.toString().replaceAll(" : null", "");
    }

    public static void main(String[] args) {
        BSTSet<Integer> bstSet = new BSTSet<>();

        int[] nums = {6,2,4,3,5,8,1,9,7};
        for (int i = 0; i < nums.length; i ++) {
            bstSet.add(nums[i]);
        }

        bstSet.remove(6);
        System.out.println(bstSet);

    }

}
