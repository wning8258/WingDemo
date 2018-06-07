package com.wning.demo.dagger2.demo;

import javax.inject.Inject;

/**
 * Created by wning on 2017/7/13.
 */

public class Student {
    private int id;
    private String name;

    @Inject
    Book book;

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

    public void startLessons() {
        System.out.println("学生开始上课了");
        book.changeBlack();
    }
}
