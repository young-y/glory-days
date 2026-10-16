/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.app.function;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author YY
 * @date 2026/10/8
 * @description: TODO
 */
public class StringTest {
    public static void main(String[] args) {
        System.out.println(WordUtils.capitalize("helLo world"));
        System.out.println(WordUtils.capitalizeFully("helLo world"));
        System.out.println(StringUtils.capitalize("helLo world"));
//        System.out.println(StringUtils.to("helLo world"));
        String str = "members-\\d+#${name}-\\d+#\\w+";
        Pattern pattern = Pattern.compile("(?<=\\$\\{).*?(?=})");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
        String aaaa = str.replace("${name}", "AAAA");
        System.out.println(aaaa);
        Map<String, String> map = new HashMap<>();
        map.put("name", "YY");
        System.out.println("-------------");
        List<String> list = List.of("AAAA","bbbb","cccc","ddddd");
        while (!list.isEmpty()){
            System.out.println(list);
            list = list.stream().skip(1).toList();
        }
        System.out.println("---------------------");
        List<String> list2 = List.of("AAAA","bbbb","cccc","ddddd");
        while (!list2.isEmpty()){
            System.out.println(list2);
            list2 = list2.subList(1, list2.size());
        }
    }
}
