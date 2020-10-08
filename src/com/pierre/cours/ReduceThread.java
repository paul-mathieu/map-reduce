package com.pierre.cours;
import java.lang.Thread;

public class ReduceThread extends Thread{
    Coordinator coord;

    public ReduceThread(Coordinator coordinator){
        coord = coordinator;
        coord.reduces.add(this);
    }

    @Override
    public void run(){
        //code
    }
}
