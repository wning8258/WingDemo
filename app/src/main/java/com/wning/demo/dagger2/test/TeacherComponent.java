package com.wning.demo.dagger2.test;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wning on 2017/6/8.
 */
@Singleton
@Component(modules = TeacherModule.class)
public interface TeacherComponent {
    void injectStudent(Teacher teacher);
}
