package com.wning.demo.dagger2.demo;


import dagger.Component;

/**
 * Created by wning on 2017/6/8.
 */
@Component(modules = TeacherModule.class)
public interface TeacherComponent {
    void injectStudent(Teacher teacher);
}
