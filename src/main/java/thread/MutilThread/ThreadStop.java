package thread.MutilThread;

import java.io.IOException;

public class ThreadStop {

    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

        });

        thread.start();
        System.out.println("50M秒内按任意停止程序");
        System.in.read();

        thread.interrupt();
        System.out.println("停止程序");
    }
}

class Example2 extends Thread {
    volatile boolean stop = false;// 线程中断信号量

    public static void main(String args[]) throws Exception {
        Example2 thread = new Example2();
        System.out.println("Starting thread...");
        thread.start();
        Thread.sleep(3000);
        System.out.println("Asking thread to stop...");
        // 设置中断信号量
        thread.stop = true;

        System.out.println("Stopping application...");
    }

    public void run() {
        // 每隔一秒检测一下中断信号量
        while (!stop) {
            System.out.println("Thread is running...");
            long time = System.currentTimeMillis();
            /*
             * 使用while循环模拟 sleep 方法，这里不要使用sleep，否则在阻塞时会 抛
             * InterruptedException异常而退出循环，这样while检测stop条件就不会执行，
             * 失去了意义。
             */
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread exiting under request...");
    }
}


class Example3 extends Thread {
    public static void main(String args[]) throws Exception {
        Example3 thread = new Example3();
        System.out.println("Starting thread...");
        thread.start();
        Thread.sleep(3000);
        System.out.println("Asking thread to stop...");
        // 发出中断请求
        thread.interrupt();
        Thread.sleep(3000);
        System.out.println("Stopping application...");
    }

    public void run() {
        // 每隔一秒检测是否设置了中断标示
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Thread is running...");
            long time = System.currentTimeMillis();
            // 使用while循环模拟 sleep
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        System.out.println("Thread exiting under request...");
    }
}
