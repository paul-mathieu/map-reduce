package com.pierre.cours;
import java.io.*;
import java.lang.Thread;
import java.util.*;

public class MapThread extends Thread{
    Scanner file;
    HashMap<String, Integer> map = new HashMap<>();
    Coordinator coord;
    Integer num;
    File outputFile;


    public MapThread(String name, Coordinator coordinator) throws FileNotFoundException {
        file = new Scanner(new File(System.getProperty("user.dir") + coordinator.dataFolder + name));
        coord = coordinator;
    }


    public void addToMaps(){
        coord.maps.add(this);
        num = coord.maps.size();
    }

    @Override
    public void run(){
        this.addToMaps();
        try {
            this.createJSON(this.count());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> count(){
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
        return map;
    }

    public void createJSON (HashMap<String, Integer> map) throws IOException {
        outputFile = new File(String.format(System.getProperty("user.dir") + "/map-output/output%02d.json", num));
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
