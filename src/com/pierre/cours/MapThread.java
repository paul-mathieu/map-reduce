package com.pierre.cours;
import java.io.*;
import java.lang.Thread;
import java.util.*;

public class MapThread extends Thread{
    Scanner file;
    HashMap<String, Integer> map = new HashMap<>();
    Shuffler shuf;
    Integer num;
    File outputFile;


    public MapThread(String name, Shuffler shuffler) throws FileNotFoundException {
        file = new Scanner(new File(System.getProperty("user.dir") + shuffler.dataFolder + name));
        shuf = shuffler;
    }


    public void addToMaps(){
        shuf.maps.add(this);
        this.num = shuf.maps.size();
    }

    @Override
    public void run(){
        this.addToMaps();
        try {
            this.count();
            this.createJSON(this.map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void count(){
        while (file.hasNext()){
            String word = file.next().toLowerCase();
            if (word.matches("[A-zÀ-ú]+"))
            if (map.containsKey(word)){
                map.put(word, map.get(word) + 1);
            }
            else{
                map.put(word, 1);
            }
        }
        System.out.println(map);
    }

    public void createJSON (HashMap<String, Integer> map) throws IOException {
        outputFile = new File(String.format(System.getProperty("user.dir") + "/map-output/output%02d.json", this.num));
        BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(outputFile) );
        bufferedWriter.write("{");
        bufferedWriter.newLine();
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            bufferedWriter.write( '"' + entry.getKey() + '"' + ":" + entry.getValue() + ",");
            bufferedWriter.newLine();
        }
        bufferedWriter.write("}");
    }
}
