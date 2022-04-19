package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        // TODO : invoke r.toString via reflection
        System.out.println(r);
//        Method methodToString = r.getClass().getMethods()[1];
        Method methodToString = r.getClass().getMethod("toString");
//        System.out.println("Name of method: " + methodToString.getName() +
//                "\nReturn type: " + methodToString.getReturnType() +
//                "\nParameter types: " + Arrays.toString(methodToString.getParameterTypes()));
//        System.out.println(methodToString);
        System.out.println(methodToString.invoke(r));
    }
}
