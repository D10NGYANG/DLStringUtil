package com.d10ng.stringlib

/**
 * 将 Unicode格式的16进制代码字符串 转换成 明文字符串
 * - 可以带"\\u"，也可以不带"\\u"
 * - 字符串中可以带空格
 * @receiver [String]
 * @return [String]
 */
fun String.unicodeHex2string(): String {
    val value = this.replace("\\u", "")
        .replace(" ", "")
    val regex = "[A-Fa-f0-9]+".toRegex()
    val isMatch = regex.matches(value)
    if (!isMatch) return ""
    val builder = StringBuilder()
    var str = value
    if (value.length % 4 != 0) {
        str = value.up2Length((value.length / 4 + 1) * 4, '0', false)
    }
    for (i in str.indices step 4) {
        val end = i + 4
        if (str.length >= end) {
            val item = str.substring(i, end)
            val data = item.toIntOrNull(16)?: 0
            if (data != 0) builder.append(data.toChar())
        }
    }
    return builder.toString()
}

/**
 * 将 明文字符串 转换成 Unicode格式的16进制代码字符串
 * @receiver [String]
 * @param isNeedU [Boolean] 是否需要带"\\u"，默认true
 * @return [String]
 */
fun String.toUnicodeHex(isNeedU: Boolean = true): String {
    val builder = StringBuilder()
    for (c in this.iterator()) {
        val item = c.code.toString(16)
        if (isNeedU) builder.append("\\u")
        builder.append(item.up2Length(4))
    }
    return builder.toString()
}

/**
 * 将 ASCII格式的16进制代码字符串 转换成 明文字符串
 * @receiver [String]
 * @return [String]
 */
fun String.asciiHex2string(): String {
    val value = this.replace(" ", "")
    val regex = "[A-Fa-f0-9]+".toRegex()
    val isMatch = regex.matches(value)
    if (!isMatch) return ""
    val builder = StringBuilder()
    var str = value
    if (value.length % 2 != 0) {
        str = value.up2Length((value.length / 2 + 1) * 2, '0', false)
    }
    for (i in str.indices step 2) {
        val temp = str.substring(i, i + 2)
        builder.append(temp.toInt(16).toChar())
    }
    return builder.toString()
}

/**
 * 将 明文字符串 转换成 ASCII格式的16进制代码字符串
 * - ASCII 只支持英文数字和英文符号
 * @receiver [String]
 * @return [String]
 */
fun String.toAsciiHex(): String {
    val regex = "[\\u0020-\\u007e]+".toRegex()
    val isMatch = regex.matches(this)
    if (!isMatch) return ""
    val builder = StringBuilder()
    for (c in this.iterator()) {
        val item = c.code.toString(16)
        builder.append(item.up2Length(2))
    }
    return builder.toString()
}