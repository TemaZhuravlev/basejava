package com.urise.webapp;

public class MainDeadblock {
    public static void main(String[] args) throws InterruptedException {
        Account account1 = new Account(10000);
        Account account2 = new Account(10000);
        AccountThread accountThread1 = new AccountThread(account1, account2);
        AccountThread accountThread2 = new AccountThread(account2, account1);
        accountThread1.start();
        accountThread2.start();
        accountThread1.join();
        accountThread2.join();

        System.out.println(account1);
        System.out.println(account2);
    }
}

class Account {
    private static int generator = 1;
    private int id;
    private int money;

    public Account(int money) {
        this.money = money;
        this.id = generator++;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public boolean takeMoney(int money) {
        if (this.money >= money) {
            this.money -= money;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", money=" + money +
                '}';
    }
}
class AccountThread extends Thread {

    private Account accFrom;
    private Account accTo;

    public AccountThread(Account accFrom, Account accTo) {
        this.accFrom = accFrom;
        this.accTo = accTo;
    }

    @Override
    public void run() {
        System.out.println("Попытка захватить монитор объекта " + accFrom + " потоком " + Thread.currentThread().getName());
        synchronized (accFrom) {
            System.out.println("Захвачен монитор объекта " + accFrom + " потоком " + Thread.currentThread().getName());
            System.out.println("Попытка захватить монитор объекта " + accTo + " потоком " + Thread.currentThread().getName());
            synchronized (accTo) {
                System.out.println("Захвачен монитор объекта " + accTo + " потоком " + Thread.currentThread().getName());
                if (accFrom.takeMoney(10)) {
                    accTo.addMoney(10);
                }
            }
        }
    }
}

