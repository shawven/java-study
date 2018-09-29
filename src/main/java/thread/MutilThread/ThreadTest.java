package thread.MutilThread;

public class ThreadTest {

    public static void main(String[] args) {

        Account account = new Account(1200.0);

        new Thread(() -> {
            while (account.getAmount() > 0) {
                account.withDraw(300.0);
            }
        }, "取钱人A").start();
        new Thread(()->{
            while (account.getAmount() > 0) {
                account.deposit(100.0);
            }
        }, "取钱人B").start();
    }
}


