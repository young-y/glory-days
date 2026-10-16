/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.app.function;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YY
 * @date 2026/9/27
 * @description: TODO
 */
public class SortedTest {

    public static void main(String[] args) {
        List<Integer> list = List.of(12, 2, 21, 4, 7, 23, 27, 8, 16, 1);
        String collect = list.stream().sorted((o1, o2)->o2-o1).map(o -> o + "").collect(Collectors.joining(","));
        System.out.println(collect);
        String reg = "^\\w*m\\w+";
        System.out.println("name".matches(reg));
        System.out.println("mobile".matches(reg));
        List<Metadata> metas = List.of(new Metadata(66, "phone")
                , new Metadata(72, "mobile")
        , new Metadata(72, "test")
        , new Metadata(72, "phone"));
        metas.stream().sorted(Comparator.comparing(Metadata::value).thenComparing(m->m.name().length(),Comparator.reverseOrder())).forEach(System.out::println);
    }

    record Metadata(int value, String name) {
    }
}
