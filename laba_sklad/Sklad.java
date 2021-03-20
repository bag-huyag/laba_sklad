package com.company;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
public class Sklad
{
    private static  int s=10; //Размер склада
    private static  int[] q;      //Массив склада
    private static int f, e; //Указатели на начало, конец склада
    private ReentrantLock lock;
    private Condition hasspace,hasproduct;
    private boolean prov=true;
    public Sklad()      //Создаём склад
    {
        lock = new ReentrantLock();
        hasspace = lock.newCondition();
        hasproduct = lock.newCondition();
        q=new int [s];
        f=0;
        e=-1;
    }
    private int next(int i)//Номер следующего элемента
    {
        int s;
        s= (i+1)%q.length;
        return (s);
    }
    private void tail(int element)//Добавляет элемент
    {
        e=next(e);
        q[e]=element;
    }
    private void print() //Вывод элементов склада
    {
        int i=f;
        while(i!=next(e))
        {
            System.out.print(q[i]+" ");
            i=next(i);
        }
    }
    private boolean  full()
    {
        return(next(next(e))==f);
    } //Проверка на заполненность склада
    private boolean empty()
    {
        return(next(e)==f);
    } //Проверка на пустоту
    public void put (int el) throws InterruptedException //Добавляем элемент на склад
    {
        lock.lock();
        try
        {
            while (full())
            {
                System.out.println("Sorry, the sklad is full");
                hasspace.await();
            }
            prov=false;
            tail(el);
            System.out.println("Producer put a new product" + " "+el);
            System.out.print(" On sklad "+" ");
            print();
            System.out.println();
            hasspace.signal();
        }
        finally
        {
            lock.unlock();
        }
    }
    public int get () throws InterruptedException //Достаём элемент со склада
    {
        prov=true;
        int k;
        lock.lock();
        try
        {
            while (empty())
            {
                System.out.println("Sorry, the sklad is empty");
                hasproduct.await();
            }
            System.out.println("Customer get a new product "+q[f]);
            k=q[f];
            f=next(f);
            System.out.print("On sklad ");
            print();
            System.out.println();
            hasproduct.signal();
            return(k);
        }
        finally
        {
            lock.unlock();
        }
    }
    public int razmer()
    {
        return(s);
    } //Размер склада
    public boolean proverit () { return(prov);}//Проверка на пустоту/полноту
}
