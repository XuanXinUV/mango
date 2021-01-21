package com.miyou.miyouapp.utils;

import java.lang.annotation.*;

/**
 *
 * 自定义操作日志注解
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {
    String operModel() default "";
    String operType() default "";
    String operDesc() default "";
}