package com.xlkk.wordtopdf.utils;


import antlr.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

    /**
     * 转换为下划线
     *
     * @param camelCaseName
     * @return
     */
    public static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    /**
     * 转换为驼峰
     *
     * @param underscoreName
     * @return
     */
    public static String camelCaseName(String underscoreName) {
        StringBuilder result = new StringBuilder();
        if (underscoreName != null && underscoreName.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }


    /**
     * 截取文件后缀
     */
    public static String cutName(String fileName, String cutString) {
        int i = fileName.lastIndexOf(cutString);
        if (i == -1) {
            return null;
        } else {
            return fileName.substring(i + 1);
        }
    }

    /**
     * 劈开某个字符串得到list
     */
    public static List<String> splitString(String source, String str) {
        List<String> list = new ArrayList<>();
        String[] split = source.split(str);
        Collections.addAll(list, split);
        return list;
    }


    /**
     * 去除正则的特殊符号 ( )
     */
    public static String removeRegex(String source) {
        return source.replaceAll("\\(|\\)", "");
    }

    /**
     * 去除空白符 再去除 正则特殊符号
     */
    public static String removeBlankAndRegex(String source) {
        return removeRegex(removeBlank(source));
    }

    /**
     * 去除空白符
     */
    public static String removeBlank(String source) {
        return source.replaceAll("\\s", "");
    }

    /**
     * 正则匹配
     *
     * @param source
     * @param rule
     * @return
     */
    public static Matcher match(String source, String rule) {
        Pattern compile = Pattern.compile(rule);
        Matcher matcher = compile.matcher(source);
        if (matcher.find()) {
            return matcher;
        }
        return null;
    }


    public static String mybitsIds(List<Integer> list, String splits) {
        String join = org.apache.commons.lang3.StringUtils.join(list, splits);
        return addAfterAndBefore("(", join, ")");
    }

    /**
     * 在string的前后添加字符
     *
     * @param after
     * @param source
     * @param before
     * @return
     */
    public static String addAfterAndBefore(String after, String source, String before) {
        StringBuilder stringBuilder = new StringBuilder(after);
        return stringBuilder.append(source).append(before).toString();
    }

    /**
     * 两个字符串中间添加字符串
     *
     * @param header
     * @param tail
     * @param middle
     * @return
     */
    public static String addMiddle(String header, String tail, String... middle) {
        StringBuilder stringBuilder = new StringBuilder(header);
        for (String s : middle) {
            stringBuilder.append(s);
        }
        return stringBuilder.append(tail).toString();
    }


    /**
     * 拼接带有参数的url,url后面有?拼接成 &xxx=xxx,没有?拼接?
     *
     * @param url
     * @param param
     * @return
     */
    public static String concatUrl(String url, String... param) {
        StringBuilder stringBuilder = new StringBuilder(url);
        String join = String.join("&", param);
        if (url.indexOf("?") != -1) {
            return stringBuilder.append("&").append(join).toString();
        } else {
            StringBuilder append = stringBuilder.append("?");
            return append.append(join).toString();
        }
    }
}
