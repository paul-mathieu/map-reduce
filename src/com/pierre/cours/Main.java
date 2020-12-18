package com.pierre.cours;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        long startTime = System.nanoTime();
        Shuffler shuf = new Shuffler(4);

        ArrayList<Thread> maps = new ArrayList<Thread>();


        Thread map1 = new Thread(new MapThread("Zola-verite.txt", shuf));
        Thread map2 = new Thread(new MapThread("Zola-travail.txt", shuf));
        Thread map3 = new Thread(new MapThread("Zola-Rome.txt", shuf));
        Thread map4 = new Thread(new MapThread("Zola-raquin.txt", shuf));


        map1.start();
        map2.start();
        map3.start();
        map4.start();

        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        maps.add(map4);

        for(int i=0; i<maps.size(); i++){
            maps.get(i).join();
        }

        System.out.println();
        System.out.println("shuffled lists");

        shuf.parseData();
        shuf.createJSONs();

        ArrayList<Thread> reds = new ArrayList<Thread>();

        System.out.println();
        System.out.println("reduced lists");
        Thread red1 = new Thread(new ReduceThread(shuf.ad, shuf));
        Thread red2 = new Thread(new ReduceThread(shuf.el, shuf));
        Thread red3 = new Thread(new ReduceThread(shuf.mq, shuf));
        Thread red4 = new Thread(new ReduceThread(shuf.rz, shuf));

        red1.start();
        red2.start();
        red3.start();
        red4.start();

        reds.add(red1);
        reds.add(red2);
        reds.add(red3);
        reds.add(red4);

        for(int i=0; i<reds.size(); i++){
            reds.get(i).join();
        }

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("temps d'execution : " + (totalTime * 0.000000001) + " secondes");

    }
}
