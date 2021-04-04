package com.company;

import java.util.Random;

public class Customer implements Runnable {
    private Sklad st;

    // Запуск потребителей
    Customer(Sklad s)
    {
        st = s;
        Thread customer = new Thread(this);
        customer.start();
    }

    // Работа Потребителя
    public void run()
    {
        Random R = new Random();
        try {
            for (int i = 0; i < 9; i++) {
                int k = R.nextInt(st.razmer() - 1) + 1;
                for (int j = 0; j < k; j++) {
                    st.get();
                }
                Thread.sleep(200);
            }
        } catch (Exception e) {
        }
    }
}

