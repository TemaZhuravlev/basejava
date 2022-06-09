package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("Name");
//        Field field = r.getClass().getDeclaredFields()[0];
//        field.setAccessible(true);
//        System.out.println(field.getName());
//        System.out.println(field.get(r));
//        field.set(r, "new_uuid");
        // TODO : invoke r.toString via reflection
        System.out.println(r);
        Method methodToString = r.getClass().getMethod("getContacts");
        System.out.println(methodToString.invoke(r));
    }
}
