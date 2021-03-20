package com.company;

public class Main
{
    public static void main (String[] args)
    {
        Sklad q=new Sklad();//Создаём склад
        Producer p=new Producer(q);//Запускаем производителей
        Customer cus1=new Customer(q);//Запускаем 1 потребителя
        Customer cus2=new Customer(q);//Запускаем 2 потребителя
    }
}
