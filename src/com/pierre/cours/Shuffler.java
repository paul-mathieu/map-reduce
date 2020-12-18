package com.pierre.cours;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class Shuffler {
    ArrayList<MapThread> maps = new ArrayList<>();
    ArrayList<ReduceThread> reduces = new ArrayList<>();
    Integer number;
    String dataFolder = "/data/zola_txt/";

    ArrayList<ArrayList<String>> ad = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> el = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> mq = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> rz = new ArrayList<ArrayList<String>>();



    public Shuffler(int number){
        this.number = number;
    }

    public void parseData(){
        for(MapThread mapT: this.maps){
            HashMap<String, Integer> currentMap = mapT.map;
            for(Map.Entry<String, Integer> entry : currentMap.entrySet()){
                String key = entry.getKey();
                Integer value = entry.getValue();
                ArrayList<String> pair = new ArrayList<String>();
                pair.add(key);
                pair.add(value.toString());
                char firstChar = Character.toLowerCase(key.charAt(0));
                int fistAscii = (int) firstChar;
                if(97 <= fistAscii && 100 >= fistAscii){
                    ad.add(pair);
                }
                if(101 <= fistAscii && 108 >= fistAscii){
                    el.add(pair);
                }
                if(109 <= fistAscii && 113 >= fistAscii){
                    mq.add(pair);
                }
                if(114 <= fistAscii && 122 >= fistAscii){
                    rz.add(pair);
                }
            }
        }
        System.out.println(ad);
        System.out.println(el);
        System.out.println(mq);
        System.out.println(rz);
    }

    public void createJSON (ArrayList<ArrayList<String>> map, Integer num) throws IOException {

        File outputFile = new File(String.format(System.getProperty("user.dir") + "/shuffled-output/output%02d.json", num));
        BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(outputFile) );
        bufferedWriter.write("{");
        bufferedWriter.newLine();
        for(ArrayList<String> pair : map){
            bufferedWriter.write( '"' + pair.get(0) + '"' + ":" + pair.get(1) + ",");
            bufferedWriter.newLine();
        }
        bufferedWriter.write("}");
    }

    public void createJSONs() throws IOException {
        this.createJSON(ad, 1);
        this.createJSON(el, 2);
        this.createJSON(mq, 3);
        this.createJSON(rz, 4);
    }

}
