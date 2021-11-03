package com.d10ng.stringlib

import com.d10ng.stringlib.constant.EndNumType
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.experimental.xor

/**
 * 检验校验和
 * - 最后一位为校验和
 * @receiver [ByteArray]
 * @param type [EndNumType] 计算方式
 * @return [Boolean] true:检验成功; false:检验失败;
 */
fun ByteArray.checkEndNum(type: EndNumType = EndNumType.XOR) : Boolean {
    if (this.isEmpty()) return false
    var num = (0).toByte()
    for (i in 0 until this.size -1) {
        num = when(type) {
            EndNumType.AND -> {
                (num and this[i])
            }
            EndNumType.OR -> {
                (num or this[i])
            }
            EndNumType.XOR -> {
                (num xor this[i])
            }
        }
    }
    return num == this[this.size -1]
}

/**
 * 获取校验和
 * @param type [EndNumType] 计算方式
 * @return [Byte] 校验和
 */
fun ByteArray.getChecksum(type: EndNumType = EndNumType.XOR) : Byte {
    var num = (0).toByte()
    for (element in this) {
        num = when(type) {
            EndNumType.AND -> {
                (num and element)
            }
            EndNumType.OR -> {
                (num or element)
            }
            EndNumType.XOR -> {
                (num xor element)
            }
        }
    }
    return num
}

/**
 * 添加较验和
 * @param type [EndNumType] 计算方式
 * @return [ByteArray] 增加较验和后的Byte数组
 */
fun ByteArray.addChecksum(type: EndNumType = EndNumType.XOR): ByteArray {
    val list = this.toMutableList()
    list.add(this.getChecksum(type))
    return list.toByteArray()
}