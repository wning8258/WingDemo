package com.wning.demo.dagger2.demo;

import javax.inject.Inject;

/**
 * Created by wning on 2017/7/13.
 */

public class Teacher {

    @Inject
    Student student;

    public Teacher(){
        DaggerTeacherComponent.builder().teacherModule(new TeacherModule()).build().injectStudent(this);
    }

    public void teacher(){
        student.startLessons();
    }

    public static void main(String[] args) {
        new Teacher().teacher();
    }
}
