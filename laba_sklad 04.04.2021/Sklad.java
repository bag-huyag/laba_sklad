package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Sklad {


    private static final int sizeSklad = 10;    // Размер склада
    private static int[] arraySklad;   // Массив склада
    private static int firstPoint, endPoint;    // Указатели на начало, конец склада  (в java нет указателей)
    private final ReentrantLock lock;   /// замораживает все потоки
    private final Condition hasspace;
    private final Condition hasproduct; /// блок/анблок потоков
    private boolean prov = true;    // Проверка на пустоту/полноту

    public Sklad()      // Создаём склад
    {
        lock = new ReentrantLock();
        hasspace = lock.newCondition();
        hasproduct = lock.newCondition();
        arraySklad = new int[sizeSklad];
        firstPoint = 0;
        endPoint = -1;
    }

    // Номер следующего элемента
    private int next(int index) {
        return (index + 1) % arraySklad.length;
    }

    // Добавляет элемент
    private void tail(int element) {
        endPoint = next(endPoint);
        arraySklad[endPoint] = element;
    }

    // Вывод элементов склада
    private void print() {
        int i = firstPoint;
        while (i != next(endPoint)) {
            System.out.print(arraySklad[i] + " ");
            i = next(i);
        }
    }

    // Проверка на заполненность склада
    private boolean full() {
        return (next(next(endPoint)) == firstPoint);
    }

    // Проверка на пустоту
    private boolean empty() {
        return (next(endPoint) == firstPoint);
    }

    // Добавляем элемент на склад
    public void put(int el) throws InterruptedException {
        lock.lock();
        try {
            while (full()) {
                System.out.println("Sorry, the sklad is full");
                hasspace.await();
            }
            prov = false;
            tail(el);
            System.out.println("Producer put a new product" + " " + el);
            System.out.print(" On sklad " + " ");
            print();
            System.out.println();
            hasspace.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Доставка элемента со склада
     *
     * @return индекс элемент
     * @throws InterruptedException
     */
    public int get() throws InterruptedException {
        prov = true;
        int k;
        lock.lock();
        try {
            while (empty()) {
                System.out.println("Sorry, the sklad is empty");
                hasproduct.await();
            }
            System.out.println("Customer get a new product " + arraySklad[firstPoint]);
            k = arraySklad[firstPoint];
            firstPoint = next(firstPoint);
            System.out.print("On sklad ");
            print();
            System.out.println();
            hasproduct.signal();
            return (k);
        } finally {
            lock.unlock();
        }
    }

    /*
     * Размер склада
     */
    public int razmer() {
        return (sizeSklad);
    }

    /*
     * Проверка на пустоту/полноту
     */
    public boolean proverit() {
        return (prov);
    }
}
