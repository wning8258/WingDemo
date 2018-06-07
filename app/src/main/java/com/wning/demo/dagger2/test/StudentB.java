package com.wning.demo.dagger2.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by wning on 2017/6/14.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface StudentB {
}
