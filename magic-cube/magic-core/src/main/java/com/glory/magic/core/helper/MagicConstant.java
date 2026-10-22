/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.glory.magic.core.helper;

/**
 * @author YY
 * @date 2026/10/22
 * @description: TODO
 */
public interface MagicConstant {
    // strategy
    String STRATEGY_FIXED_VALUE = "fixedValue";
    String STRATEGY_DATE_FORMAT = "dateFormat";
    String STRATEGY_CODE_MAP = "codeMap";
    String STRATEGY_PUBLISH_CACHE_EVENT = "publishCacheEvent";
    String STRATEGY_CONSUME_CACHE_EVENT = "consumeCacheEvent";
    String STRATEGY_GROOVY_FORMULA = "groovyFormula";
    String STRATEGY_NUMBER_FORMAT = "numberFormat";
    String STRATEGY_STRING_FORMAT = "stringFormat";
    String STRATEGY_VALUE_COPY = "valueCopy";
    //key prefix
    String KEY_PREFIX_ROOT_NODE = "$R_";//get value from root node
    String KEY_PREFIX_CURRENT_SOURCE = "$N_";//get value from the same level node
    String KEY_PREFIX_CACHE_SIGNAL = "$C_";//signal publish cache
    String KEY_PREFIX_CACHE_SIGNAL_SPLIT = ";";//if key start with '$C_',the value format cacheEventKey;nodePath or cacheEventKey;nodePath
    String KEY_PREFIX_CACHE_ALL = "$CA_";//all publish cache
    //rule extend properties
    String RULE_SRC_VALUE_1_KEY = "srcValue1Key";//codeMap
    String RULE_FORMAT_ALGORITHM = "algorithm";//stringFormat,
    String RULE_FORMAT_PATTERN = "pattern";//stringFormat,
    String RULE_FORMAT_REPLACEMENT= "replacement";//stringFormat,
    String RULE_FORMAT_MINLENGTH= "minLength";//stringFormat,
    String RULE_FORMAT_MINLENGTH_STR= "minLengthStr";//stringFormat,
    String RULE_FORMAT_FORMAT_TYPE= "formatType";//numberFormat,0 number;1 currency;2 percentage
    String RULE_FORMAT_DATE_FORMAT= "dateFormat";//numberFormat,0 number;1 currency;2 percentage
}
