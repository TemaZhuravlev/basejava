package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainStream {

    public static void main(String[] args) {
/**
 * реализовать метод через стрим int minValue(int[] values).
 * Метод принимает массив цифр от 1 до 9, надо выбрать уникальные и вернуть минимально возможное число,
 * составленное из этих уникальных цифр. Не использовать преобразование в строку и обратно.
 * Например {1,2,3,3,2,3} вернет 123, а {9,8} вернет 89
 */
        int[] ints = {1, 2, 3, 3, 2, 3};
        System.out.println("Минимально возможное число: " + minValue(ints));
/**
 * реализовать метод List<Integer> oddOrEven(List<Integer> integers)
 * если сумма всех чисел нечетная - удалить все нечетные,
 * если четная - удалить все четные. Сложность алгоритма должна быть O(N). Optional - решение в один стрим.
 */
        List<Integer> intList = new ArrayList<>();
        intList.add(1);
        intList.add(12);
        intList.add(5);
        intList.add(7);
        intList.add(21);

        System.out.println(addOrEven(intList));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .sorted()
                .distinct()
                .reduce(0, (accumulator, element) -> accumulator * 10 + element);
    }

    public static List<Integer> addOrEven(List<Integer> integers) {
        boolean odd = integers.stream()
                .reduce(Integer::sum)
                .filter(sum -> sum % 2 != 0)
                .isPresent();
        if (odd) {
            return integers.stream().filter(el -> el % 2 == 0).toList();
        }
        return integers.stream().filter(el -> el % 2 != 0).toList();
    }
}
