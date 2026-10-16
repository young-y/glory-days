/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.test;

import com.glory.magic.core.domain.node.ResultKey;
import com.glory.magic.core.domain.node.ResultNode;
import com.glory.magic.core.domain.node.ResultSet;
import com.glory.magic.core.helper.MagicHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YY
 * @date 2026/9/7
 * @description: TODO
 */
public class ResultSetTest {
    public static void main(String[] args) {
        ResultSet resultSet = new ResultSet();
        resultSet.addResultNode(ResultKey.create("aa#bb-*#cc1#dd1")).getParent().addChild("hhhh",9999);
        resultSet.addResultNode(ResultKey.create("aa#bb-*#cc1#dd2")).setValue("1111");
        resultSet.addResultNode(ResultKey.create("aa#bb-*#cc1#dd2")).setValue("555");
         resultSet.addResultNode(ResultKey.create("aa#bb-*#ccc-*#dd2")).setValue("sdfsdf");
        resultSet.addResultNode(ResultKey.create("aa#bb-*#ccc-*#dd3")).setValue("2222");
         resultSet.addResultNode(ResultKey.create("aa#bb-*#ccc-*#dd3"));
        resultSet.addResultNode(ResultKey.create("aa#bb-*#cc4#dd4"));
        resultSet.addResultNode(ResultKey.create("sdf#dff#lks-*#xxxx")).setValue("kkk");
        resultSet.addResultNode(ResultKey.create("sdf#dff#lks-*#oooo")).setValue("ooo");
        ResultNode node = resultSet.getResultNode();
        Map<String, Object> map = node.transToMap();
        System.out.println(JsonUtils.toJson(map,true,true));
    }
}
