package com.d10ng.stringlib

import java.nio.charset.Charset

/**
 * 将 byte 转换成无符号整型
 * @receiver [Byte]
 * @return [Int]
 */
fun Byte.toDInt(): Int = toInt() and 0xFF

/**
 * 将两个字节的 byte 数组合并成无符号整型
 * @receiver [ByteArray]
 * @return [Int]
 */
fun ByteArray.toDInt(): Int {
    val ba = up2Length(2)
    return ba[0].toDInt() shl 8 or ba[1].toDInt()
}

/**
 * 获取整型数据的 高位 byte
 * @receiver [Int]
 * @return [Byte]
 */
fun Int.takeHighByte(): Byte =
    ushr(8).toByte()

/**
 * 获取整型数据的 低位 byte
 * @receiver [Int]
 * @return [Byte]
 */
fun Int.takeLowByte(): Byte =
    (this and 0xff).toByte()

/**
 * 将 byte 转换成无符号 Long
 * @receiver [Byte]
 * @return [Long]
 */
fun Byte.toDLong(): Long = toLong() and 0xFF

/**
 * 将8个字节的 Byte 合并成无符号 Long
 * - Long 的最大值为 0x7FFFFFFFFFFFFFFF
 * @receiver [ByteArray]
 * @return [Long]
 */
fun ByteArray.toDLong(): Long {
    val ba = up2Length(8)
    return (ba[0].toDLong() shl 56) or
            (ba[1].toDLong() shl 48) or
            (ba[2].toDLong() shl 40) or
            (ba[3].toDLong() shl 32) or
            (ba[4].toDLong() shl 24) or
            (ba[5].toDLong() shl 16) or
            (ba[6].toDLong() shl 8) or
            (ba[7].toDLong())
}

/**
 * 将 Long 转 8个字节以内的 ByteArray
 * @receiver [Long]
 * @return [ByteArray]
 */
fun Long.toByteArray(): ByteArray {
    val ba = byteArrayOf(
        ushr(56).toByte(),
        ushr(48).toByte(),
        ushr(40).toByte(),
        ushr(32).toByte(),
        ushr(24).toByte(),
        ushr(16).toByte(),
        ushr(8).toByte(),
        (this and 0xFF).toByte()
    )
    var startIndex = 0
    for (i in ba.indices) {
        if (ba[i] != (0x00).toByte()) {
            startIndex = i
            break
        }
    }
    return ba.copyOfRange(startIndex, 8)
}

/**
 * 将 Byte 转为 8位 二进制字符串 "00110011"
 * @receiver [Byte]
 * @return [String]
 */
fun Byte.toBinString(): String =
    this.toUByte().toString(2).up2Length(8)

/**
 * 将 8位 二进制字符串 "00110011" 转为 Byte
 * @receiver [String]
 * @return [Byte]
 */
fun String.binString2Byte(): Byte {
    val value = replace(" ", "")
    val regex = "[01]+".toRegex()
    val isMatch = regex.matches(value)
    if (!isMatch) return 0x00
    val str = value.up2Length(8)
    return str.toInt(2).toByte()
}

/**
 * 将 ByteArray 转为 8*N 二进制字符串 "00110011"
 * @receiver [ByteArray]
 * @param space [Boolean] 每个byte中间是否需要空格
 * @return [String]
 */
fun ByteArray.toBinString(space: Boolean = false): String {
    val builder = StringBuilder()
    for (byte in this) {
        builder.append(byte.toBinString())
        if (space) builder.append(" ")
    }
    return builder.toString()
}

/**
 * 将 8*N 二进制字符串 "00110011" 转为 ByteArray
 * @receiver [String]
 * @return [Byte]
 */
fun String.binString2ByteArray(): ByteArray {
    val value = replace(" ", "")
    val regex = "[01]+".toRegex()
    val isMatch = regex.matches(value)
    if (!isMatch) return byteArrayOf()
    var str = value
    if (value.length % 8 != 0) {
        str = value.up2Length((value.length / 8 + 1) * 8)
    }
    val list = mutableListOf<Byte>()
    for (i in str.indices step 8) {
        list.add(str.substring(i, i + 8).binString2Byte())
    }
    return list.toByteArray()
}

/**
 * 将 Byte 转为 2位 16进制字符串 "fc"
 * @receiver [Byte]
 * @param uppercase [Boolean] 是否需要大写
 * @return [String]
 */
fun Byte.toHexString(uppercase: Boolean = false): String {
    val result = toDInt().toString(16).up2Length(2)
    return if (uppercase) result.uppercase() else result.lowercase()
}

/**
 * 将 2位 16进制字符串 "fc" 转为 Byte
 * @receiver [String]
 * @return [Byte]
 */
fun String.hexString2Byte(): Byte {
    val value = replace(" ", "")
    val regex = "[A-Fa-f0-9]+".toRegex()
    val isMatch = regex.matches(value)
    if (!isMatch) return 0x00
    val str = value.up2Length(8)
    return str.toInt(16).toByte()
}

/**
 * 将 ByteArray 转为 2*N 16进制字符串 "fcfc"
 * @receiver [ByteArray]
 * @param space [Boolean] 是否需要空格
 * @param uppercase [Boolean] 是否需要大写
 * @return [String]
 */
fun ByteArray.toHexString(space: Boolean = false, uppercase: Boolean = false): String {
    val builder = StringBuilder()
    for (byte in this) {
        builder.append(byte.toHexString())
        if (space) builder.append(" ")
    }
    val result = builder.toString()
    return if (uppercase) result.uppercase() else result.lowercase()
}

/**
 * 将 2*N 16进制字符串 "fcfc" 转为 ByteArray
 * @receiver [String]
 * @return [Byte]
 */
fun String.hexString2ByteArray(): ByteArray {
    val value = replace(" ", "")
    val regex = "[A-Fa-f0-9]+".toRegex()
    val isMatch = regex.matches(value)
    if (!isMatch) return byteArrayOf()
    var str = value
    if (value.length % 2 != 0) {
        str = value.up2Length((value.length / 2 + 1) * 2)
    }
    val list = mutableListOf<Byte>()
    for (i in str.indices step 2) {
        list.add(str.substring(i, i + 2).hexString2Byte())
    }
    return list.toByteArray()
}

/**
 * 将 ByteArray 转中文字符串
 * @receiver [ByteArray]
 * @return [String]
 */
fun ByteArray.toHanZiString() = toString(Charset.forName("GBK"))

/**
 * 将中文字符串转 ByteArray
 * @receiver [String]
 * @return [ByteArray]
 */
fun String.toHanZiByteArray() = toByteArray(Charset.forName("GBK"))

/**
 * 将 CharArray 转换成 ByteArray
 * @receiver [CharArray]
 * @return [ByteArray]
 */
fun CharArray.toByteArray(): ByteArray {
    val byteList = mutableListOf<Byte>()
    for (char in this) {
        byteList.add(char.code.toByte())
    }
    return byteList.toByteArray()
}