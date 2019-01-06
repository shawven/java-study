package datastructure.stack;

import datastructure.stack.ArrayStack;
import datastructure.stack.LinkedListStack;

import java.util.Random;

/**
 * @author XW
 * @since 2018-12-04 10:38
 */
public class Main {

    public static void main(String[] args) {

        int times = 100000;
        ArrayStack<Integer> queue1 = new ArrayStack<>();
        LinkedListStack<Integer> queue2 = new LinkedListStack<>();
        long startTime1 = System.nanoTime();
        for (int i = 0; i < times; ++i) {
            queue1.push(new Random().nextInt(Integer.MAX_VALUE));
        }
        for (int i = 0; i < times; ++i) {
            queue1.pop();
        }
        long endTime1 = System.nanoTime();

        long startTime2 = System.nanoTime();
        for (int i = 0; i < times; ++i) {
            queue2.push(new Random().nextInt(Integer.MAX_VALUE));
        }
        for (int i = 0; i < times; ++i) {
            queue2.pop();
        }
        long endTime2 = System.nanoTime();

        System.out.println("ArrayStack: " + (double) (endTime1 - startTime1) / 1000000000.0);
        System.out.println("LinkedListStack: " + (double) (endTime2 - startTime2) / 100000000.0);
    }

}
