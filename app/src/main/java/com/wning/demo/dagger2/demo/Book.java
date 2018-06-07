package com.wning.demo.dagger2.demo;

import javax.inject.Inject;

/**
 * Created by wning on 2017/7/20.
 */

public class Book {

    private String name;

    @Inject
    public Book() {
    }

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
        System.out.println(name + "我一本新书被翻黑了。。。。");
    }
}
