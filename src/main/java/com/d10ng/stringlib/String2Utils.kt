package com.d10ng.stringlib

import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Double类型转字符串
 * @receiver [Double]
 * @param keepNDecimal [Int] 保留N位小数
 * @return [String]
 */
fun Double.toDString(keepNDecimal: Int): String {
    val builder = StringBuilder("0.")
    for (i in 0 until keepNDecimal) {
        builder.append("#")
    }
    val format = DecimalFormat(builder.toString())
    // 未保留小数的舍弃规则，RoundingMode.FLOOR表示直接舍弃。
    format.roundingMode = RoundingMode.FLOOR
    return format.format(this)
}

/**
 * Float类型转字符串
 * @receiver [Float]
 * @param keepNDecimal [Int] 保留N位小数
 * @return [String]
 */
fun Float.toDString(keepNDecimal: Int): String = this.toDouble().toDString(keepNDecimal)