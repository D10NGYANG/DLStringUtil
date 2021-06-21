package com.d10ng.stringlib

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.reflect.Field

/**
 * 复制
 * @receiver T
 * @return T
 */
fun <T> T.copyObj() : T {
    // 写入字节流
    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)
    oos.writeObject(this)
    // 读取字节流
    val bais = ByteArrayInputStream(baos.toByteArray())
    val ois = ObjectInputStream(bais)
    return ois.readObject() as T
}

/**
 * 获取类中的所有字段，包含父类的
 * @receiver Any
 * @return List<Field>
 */
fun Any.getAllField(): List<Field> {
    val fs: MutableList<Field> = mutableListOf()
    var tempClass: Class<in Any>? = this.javaClass
    while (tempClass != null) {
        fs.addAll(tempClass.declaredFields)
        tempClass = tempClass.superclass
    }
    return fs
}