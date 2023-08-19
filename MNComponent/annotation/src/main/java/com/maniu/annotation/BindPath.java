package com.maniu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//编译时的技术
@Target(ElementType.TYPE)   //声明注解作用域
@Retention(RetentionPolicy.CLASS)   //源码期  <  编译期  <  运行期   决定了注解的存在周期
public @interface BindPath {
    //key
    String value();
}
