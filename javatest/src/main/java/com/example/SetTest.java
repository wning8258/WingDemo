package com.example;


import java.util.HashSet;
import java.util.Iterator;

public class SetTest {
    public static void main(String[] args){

        HashSet set=new HashSet();
        for(int i=0;i<10;i++){
            set.add(i);
        }
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

}
