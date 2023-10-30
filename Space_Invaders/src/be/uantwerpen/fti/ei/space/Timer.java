package be.uantwerpen.fti.ei.space;

import static java.lang.System.currentTimeMillis;

public class Timer {
    int period;
    long start;

    /**
     * Maakt een timer aan met een bepaalde frequentie
     * @param freq_Hz de frequentie voor de timer
     */
    public Timer(int freq_Hz) {
        this.period = freq_Hz;
    }

    /**
     * Deze funcite steekt de huidige tijd in de variabele start
     */
    public void start(){
        this.start = currentTimeMillis();
    }

    /**
     * Deze funcite steekt de huidige tijd in de variabele stop en voert een Thread.sleep uit
     */
    public void sleep(){
        long stop = currentTimeMillis();
        try {
            Thread.sleep(Math.max((long) ((1000*(1.0/period)) - (stop - start)),0));
            start = currentTimeMillis();
        } catch (InterruptedException e) {
            System.out.println(e.getStackTrace());
        }
    }
}