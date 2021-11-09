package com.d10ng.stringlib.type

import kotlin.reflect.KClass

/**
 * 类型识别工具
 *
 * @author D10NG
 * @date on 2019-10-23 16:33
 */

fun KClass<*>.isString() =
    this == String::class

fun KClass<*>.isBoolean() =
    this == Boolean::class

fun KClass<*>.isInt() =
    this == Int::class || this == Short::class

fun KClass<*>.isLong() =
    this == Long::class

fun KClass<*>.isDouble() =
    this == Double::class

fun KClass<*>.isFloat() =
    this == Float::class

fun KClass<*>.isByte() =
    this == Byte::class

fun KClass<*>.isByteArray() =
    this == ByteArray::class

fun KClass<*>.isList() =
    this == List::class