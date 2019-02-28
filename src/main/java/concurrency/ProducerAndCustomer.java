package concurrency;

import thread.MutilThread.ThreadLocal;

import java.beans.BeanInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author XW
 * @since 2019-02-24 21:46
 */
public class ProducerAndCustomer {

    public static void main(String[] args) {
        Factory factory = new Factory();
        Producer producer = new Producer(factory);
        Customer customer = new Customer(factory);

        new Thread(producer).start();
        new Thread(customer, "customer1 ").start();
        new Thread(customer, "customer2 ").start();
        new Thread(customer, "customer3 ").start();
        new Thread(customer, "customer4 ").start();
        new Thread(customer, "customer5 ").start();

    }
}

class Product {
    private String name;

    public Product(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                '}';
    }
}

class Factory {

    private int capacity = 20;

    private boolean fulled = false;

    private List<Product> products = new ArrayList<>();

    private boolean showdown;

    public synchronized void put(Product product) {
        while (fulled) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        products.add(product);

        System.out.println(Thread.currentThread().getName() + " put a product: " + product.toString());

        if (products.size() >= capacity) {
            fulled = true;
            notifyAll();
        }
    }

    public synchronized void take() {
        while (!fulled) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (showdown) {
            return;
        }

        Iterator<Product> iterator = products.iterator();
        if (iterator.hasNext()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " : " + iterator.next());
            iterator.remove();

        }

        if (products.size() == 0) {
            fulled = false;
        }

        notify();
    }

    public synchronized void shutdown() {
        this.showdown = true;
    }
}


class Producer implements Runnable{

    private Factory factory;



    public Producer(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            System.out.println("I'm a producer, i am put product");
            factory.put(new Product(System.currentTimeMillis() + ""));
        }

        factory.shutdown();
    }
}

class Customer implements Runnable {
    private Factory factory;

    public Customer(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void run() {
        while (true) {
            factory.take();
        }
    }
}
