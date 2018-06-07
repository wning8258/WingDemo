package com.wning.demo.dagger2.test;

public class Book {

    private String name;


    public Book() {
    }

   // @Inject
    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changeBlack() {
        System.out.println(name+"我一本新书被翻黑了。。。。");
    }
}