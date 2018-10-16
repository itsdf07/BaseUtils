package com.itsdf07.utils;

import java.lang.reflect.ParameterizedType;

/**
 * @Description 其他
 * 1、泛型类转换初始化:getT
 * @Author itsdf07
 * @Time 2018/10/16/016
 */

public class FUtils {

    public static <T> T getT(Object o, int i) {
        try {

            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
