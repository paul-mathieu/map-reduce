package com.pierre.cours;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReduceThread extends Thread{
    Shuffler shuf;
    ArrayList<ArrayList<String>> suffledList;
    HashMap<String, Integer> reduced = new HashMap<String, Integer>();
    Integer num;

    public ReduceThread(ArrayList<ArrayList<String>> shuffledList, Shuffler shuffler){
        this.shuf = shuffler;
        this.suffledList = shuffledList;
    }

    @Override
    public void run(){
        this.addToReduce();
        this.reduce(this.suffledList);
        try {
            this.createJSON(reduced);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reduce(ArrayList<ArrayList<String>> shuffled){
        for(ArrayList<String> pair: shuffled){
            if(reduced.containsKey(pair.get(0))){
                Integer value = Integer.valueOf(pair.get(1));
                Integer newWalue = value + reduced.get(pair.get(0));
                reduced.put(pair.get(0), newWalue);
            }else{
                reduced.put(pair.get(0), Integer.valueOf(pair.get(1)));
            }
        }
        System.out.println(this.reduced);
    }

    public void addToReduce(){
        shuf.reduces.add(this);
        this.num = shuf.reduces.size();
        System.out.println(shuf.reduces);
    }

    public void createJSON (HashMap<String, Integer> map) throws IOException {
        File outputFile = new File(String.format(System.getProperty("user.dir") + "/reduce-output/output%02d.json", this.num));
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
