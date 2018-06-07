package com.wning.demo.dagger2.test;

import javax.inject.Inject;

/**
 * Created by wning on 2017/6/8.
 */

public class Teacher {
    //想持有学生对象
    @Inject
   // @StudentA
    @StudentB
    Student student1; //带了书的

    @Inject
    @StudentB
    Student student2; //没带了书的

    public Teacher() {
      //  DaggerTeacherComponent.create().injectStudent(this);

        DaggerTeacherComponent.builder().teacherModule(new TeacherModule()).build().injectStudent(this);
    }

    public void teacher() {
        student1.study();
        student2.study();
    }

    public static void main(String[] args) {
        new Teacher().teacher();
    }
}
