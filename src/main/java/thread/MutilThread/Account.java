package thread.MutilThread;



public class Account {

    private Double amount;

    private boolean flag = false;

    public Account() {
        this.amount = 0.0;
    }

    public Account(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public synchronized void withDraw(Double amount) {
        if (this.flag) {
            try {
                this.wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        action(amount);
        this.flag = true;
        this.notify();
    }

    public synchronized void deposit(Double amount) {
        if (!this.flag) {
            try {
                this.wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        action(amount);
        this.flag = false;
        this.notify();

    }

    public void  action(Double amount) {
        if (this.amount <= 0) {
            return;
        }
        this.amount -= amount;
        System.out.println("当前账户余额：" + this.amount + "元    " + "\"" + Thread.currentThread().getName() + "\""+ "：取了" + amount + "元");
    }
}
