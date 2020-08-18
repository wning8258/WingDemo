package com.wning.demo.test.asm;

public class Main {

    Main(){

    }

    @AsmTestAnnotation
    public static void main(String[] args) {
        System.out.println("main");
    }
}
