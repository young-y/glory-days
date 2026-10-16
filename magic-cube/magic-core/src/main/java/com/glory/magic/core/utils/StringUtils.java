package com.glory.magic.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public final class StringUtils {
    private static boolean isMatch(final String regex, final String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        final Pattern pattern = Pattern.compile(regex);
        final Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    /**
     * 正整数
     *
     * @param orginal
     * @return
     */
    public static boolean isPositiveInteger(final String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    /**
     * 负整数
     *
     * @param orginal
     * @return
     */
    public static boolean isNegativeInteger(final String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    /**
     * 整 数
     *
     * @param orginal
     * @return
     */
    public static boolean isWholeNumber(final String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    /**
     * 正浮点
     *
     * @param orginal
     * @return
     */
    public static boolean isPositiveDecimal(final String orginal) {
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    /**
     * 负浮点
     *
     * @param orginal
     * @return
     */
    public static boolean isNegativeDecimal(final String orginal) {
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
    }

    /**
     * 浮点
     *
     * @param orginal
     * @return
     */
    public static boolean isDecimal(final String orginal) {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    /**
     * 实数，
     *
     * @param orginal
     * @return
     */
    public static boolean isRealNumber(final String orginal) {
        return isWholeNumber(orginal) || isDecimal(orginal);
    }

    /**
     * 字符串转换为Int或是double
     *
     * @param value
     * @return
     */
    public static Number stringToNumber(final String value) {
        if (isRealNumber(value)) {
            if (isWholeNumber(value)) {
                return Integer.parseInt(value);
            }
            return Double.parseDouble(value);
        }
        return null;
    }

    public static String firstUpperCase(final String string) {
        if (string == null || string.trim().length()<1) {
            return string;
        }
        final char[] ch = string.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    public static  String lowersCase(String string){
        if (string == null || string.trim().length()<1) {
            return string;
        }
        final char[] ch = string.toCharArray();
        List<Character> chs = new ArrayList<>();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] >= 'A' && ch[i] <= 'Z') {
                ch[i] = (char) (ch[i] + 32);
                if (i>0){
                    chs.add('_');
                }
                chs.add(ch[i]);
            }else{
                chs.add(ch[i]);
            }
        }
        Character[] cs = chs.toArray(new Character[0]);
        char[] chars = new char[cs.length];
        for (int i = 0; i < cs.length; i++) {
            chars[i] = cs[i].charValue();
        }
        return new String(chars);
    }
}
