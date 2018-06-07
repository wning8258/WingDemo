package com.wning.demo.dagger2.demo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wning on 2017/7/25.
 */
@Module
public class TeacherModule {

    @Provides
    Book provideBook(){
        return new Book();
    }
}
