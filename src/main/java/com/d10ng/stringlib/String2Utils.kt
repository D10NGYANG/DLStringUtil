package com.d10ng.stringlib

/**
 * Double类型转字符串
 * @receiver [Double]
 * @param keepNDecimal [Int] 保留N位小数
 * @return [String]
 */
fun Double.toDString(keepNDecimal: Int): String =
    String.format("%.${keepNDecimal}f", this)

/**
 * Float类型转字符串
 * @receiver [Float]
 * @param keepNDecimal [Int] 保留N位小数
 * @return [String]
 */
fun Float.toDString(keepNDecimal: Int): String = this.toDouble().toDString(keepNDecimal)