package datastructure.collection;

import datastructure.FileOperation;
import datastructure.tree.AVLTree;
import datastructure.tree.RBTree;

import java.util.ArrayList;
import java.util.Collections;

public class TestMapMain {

    private static double testMap(Map<String, Integer> map, String filename){

        long startTime = System.nanoTime();

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile(filename, words)) {
            Collections.sort(words);
            for (String word : words) {
                if(map.contains(word)) {
                    map.put(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                }
            }
            System.out.println("Total words: " + words.size());
            System.out.println("Total different words: " + map.size());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) {

        String filename = "pride-and-prejudice.txt";

        BSTMap<String, Integer> bstMap = new BSTMap<>();
        AVTMap<String, Integer> avtMap = new AVTMap<>();
        RBTMap<String, Integer> rbtmap = new RBTMap<>();

//        System.out.println(testMap(bstMap, filename));
//        System.out.println(testMap(avtMap, filename));
//        System.out.println(testMap(rbtmap, filename));


        int n = 30000000;

        ArrayList<Integer> testData = new ArrayList<>(n);
        for(int i = 0 ; i < n ; i ++)
            testData.add(i);

        // Test AVL
        long startTime = System.nanoTime();

        AVLTree<Integer, Integer> avl = new AVLTree<>();
        for (Integer x: testData)
            avl.add(x, null);

        long endTime = System.nanoTime();

        double time = (endTime - startTime) / 1000000000.0;
        System.out.println("AVL: " + time + " s");


        // Test RBTree
        startTime = System.nanoTime();

        RBTree<Integer, Integer> rbt = new RBTree<>();
        for (Integer x: testData)
            rbt.add(x, null);

        endTime = System.nanoTime();

        time = (endTime - startTime) / 1000000000.0;
        System.out.println("RBTree: " + time + " s");
    }
}
