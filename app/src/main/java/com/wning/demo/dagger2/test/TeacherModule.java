package com.wning.demo.dagger2.test;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wning on 2017/6/13.
 */

@Module
public class TeacherModule {

    @Provides
    Book provideBook() {
        return new Book("book");
    }

    /**
     * 提供有书的学生
     * @param book
     * @return
     */
    @Provides
    @StudentA
    Student provideStudentA(Book book) {
        return new Student(book);
    }

    /**
     * 提供没书的学生
     * @return
     */
    @Provides
    @StudentB
    @Singleton
    Student provideStudentB() {
        return new Student();
    }
}