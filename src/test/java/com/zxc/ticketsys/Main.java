package com.zxc.ticketsys;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Main {
    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        ListIterator<String> iterator=list.listIterator();
//        System.out.println(iterator.next());
        while(iterator.hasNext()){
            System.out.println(iterator.next());
            System.out.println(iterator.nextIndex());
        }
        System.out.println(iterator.nextIndex());
        System.out.println(iterator.previousIndex());
        while(iterator.hasPrevious()){
            System.out.println(iterator.previousIndex());
            System.out.println(iterator.previous());
        }
    }
}
