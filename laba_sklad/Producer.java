package com.company;

import java.util.Random;

public class Producer implements Runnable {
    private int pr = 0;
    private Sklad st;

    // Запуск Производителя
    Producer(Sklad e)
    {
        st = e;
        Thread producer = new Thread(this);
        producer.start();
    }

    // Работа Производителя
    public void run()
    {
        Random R = new Random();
        try {
            for (int i = 0; i < 9; i++) {
                if (st.proverit()) {
                    pr += 1;
                }
                int k = R.nextInt(st.razmer() - 1) + 1;
                for (int j = 0; j < k; j++) {
                    st.put(pr);
                }
                Thread.sleep(200);
            }

        } catch (Exception e) {

        }
    }
}
