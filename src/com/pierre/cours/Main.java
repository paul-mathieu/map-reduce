package com.pierre.cours;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	Coordinator coord = new Coordinator();

    Thread map1 = new Thread(new MapThread("Zola-verite.txt", coord));
    Thread map2 = new Thread(new MapThread("Zola-travail.txt", coord));
    Thread map3 = new Thread(new MapThread("Zola-Rome.txt", coord));
    Thread map4 = new Thread(new MapThread("Zola-raquin.txt", coord));



    map1.start();
    map2.start();
    map3.start();
    map4.start();

    }
}
