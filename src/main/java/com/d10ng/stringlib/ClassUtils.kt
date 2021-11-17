package com.d10ng.stringlib

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * 深复制
 * - 耗时操作，避免放在主线程
 * @receiver [T]
 * @return [T]
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
 * @receiver [Any]
 * @return [List<Field>]
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

/**
 * 查找类中的注解方法
 * @receiver [Class<*>]
 * @param annotation [Class<out Annotation>]
 * @param params [Array<out String>]
 * @return [Method]?
 */
fun Class<*>.findAnnotationMethod(
    annotation: Class<out Annotation>,
    vararg params: String
): Method? {
    val method = this.methods
    for (m in method) {
        // 看是否有注解
        m.getAnnotation(annotation) ?: continue
        // 判断返回类型
        val genericReturnType = m.genericReturnType.toString()
        if ("void" != genericReturnType) {
            // 方法的返回类型必须为void
            throw RuntimeException("The return type of the method【${m.name}】 must be void!")
        }
        // 检查参数
        val parameterTypes = m.genericParameterTypes
        if (parameterTypes.size != params.size) {
            throw RuntimeException("The parameter types size of the method【${m.name}】must be ${params.size}")
        } else {
            var isOk = true
            val builder = StringBuilder("The return type of the method【${m.name}】not true (")
            for (i in params.indices) {
                if (!parameterTypes[i].toString().contains(params[i])) {
                    isOk = false
                    builder.append("${parameterTypes[i]} != ${params[i]}")
                }
            }
            builder.append(")")
            if (!isOk) {
                throw RuntimeException(builder.toString())
            }
        }
        return m
    }
    return null
}
