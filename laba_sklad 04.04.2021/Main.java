package com.company;

public class Main {
    public static void main(String[] args) {

        // Создаём склад
        Sklad q = new Sklad();

        // Запускаем производителей
        Producer p = new Producer(q);

        // Запускаем 1 потребителя
        Customer cus1 = new Customer(q);

        // Запускаем 2 потребителя
        Customer cus2 = new Customer(q);
    }
}
