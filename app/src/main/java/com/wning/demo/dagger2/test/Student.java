package com.wning.demo.dagger2.test;

import javax.inject.Inject;

/**
 * Created by wning on 2017/6/8.
 */

public class Student {

    private int id;
    private String name;

    private Book book;

    public Student() {
        this.book = new Book("对不起我没有书啊");
        System.out.println("Student create without book!!!");

    }

    @Inject
    public Student(Book book) {
        this.book=book;
        System.out.println("Student create with book!!!");
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void study() {
        System.out.println("study==========");
        book.changeBlack();
    }
}
