package com.d10ng.stringlib.type

/**
 * 类型识别工具
 *
 * @author D10NG
 * @date on 2019-10-23 16:33
 */

fun Class<*>.isString() =
    this == String::class.java
            || this == java.lang.String::class.java

fun Class<*>.isBoolean() =
    this == Boolean::class.java
            || this == java.lang.Boolean::class.java

fun Class<*>.isInt() =
    this == Int::class.java
            || this == Integer::class.java
            || this == Short::class.java
            || this == java.lang.Integer::class.java
            || this == java.lang.Short::class.java

fun Class<*>.isLong() =
    this == Long::class.java
            || this == java.lang.Long::class.java

fun Class<*>.isDouble() =
    this == Double::class.java
            || this == java.lang.Double::class.java

fun Class<*>.isFloat() =
    this == Float::class.java
            || this == java.lang.Float::class.java

fun Class<*>.isByte() =
    this == Byte::class.java
            || this == java.lang.Byte::class.java

fun Class<*>.isByteArray() =
    this == ByteArray::class.java

fun Class<*>.isList() =
    this == List::class.java