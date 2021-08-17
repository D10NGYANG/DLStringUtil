package com.d10ng.stringlib

import kotlinx.coroutines.*
import java.nio.charset.Charset

/**
 * ByteArray 数据分割处理工具
 *
 * @Author: D10NG
 * @Time: 2021/8/17 11:59 上午
 */
class ByteArraySplitTools(
    /**
     * 开始标记
     */
    private val splitStart: ByteArray,
    /**
     * 结束标记
     */
    private val splitEnd: ByteArray,
    /**
     * 一帧数据返回
     */
    private val handlerOneBack:(data: ByteArray) -> Unit
) {
    /** 接收到数据 */
    private var receiveData: MutableList<Byte> = mutableListOf()
    /** 工作任务 */
    private var workerJob: Job? = null

    /** 开始任务 */
    private fun startJob() {
        // 如果任务正在运行就返回
        if (workerJob?.isActive == true) return
        // 开启新的任务
        workerJob = CoroutineScope(Dispatchers.IO).launch {
            // 临时数据
            var tempData = mutableListOf<Byte>()
            while (receiveData.isNotEmpty()) {
                // 每一次抓50个包出来处理
                val dataObj = changeReceiveData(byteArrayOf(), Type.COPY, 50)
                tempData.addAll(dataObj)
                changeReceiveData(byteArrayOf(), Type.REMOVE, 50)
                // 找到开始标记所在位置
                val startIndex = tempData.toByteArray().findFirstIndex(splitStart)
                if (startIndex < 0) continue
                // 找到结束标记所在位置
                val endIndex = tempData.toByteArray().findFirstIndex(splitEnd) + splitEnd.size
                if (endIndex < splitEnd.size) continue
                // 如果结束标记在开始标记之前，说明这个结束标记是上一条数据的结束标记，抛弃掉
                if (startIndex > endIndex) {
                    tempData.removeAll(tempData.subList(0, endIndex))
                    continue
                }
                // 将完整一条数据拿出来，推出去
                handlerOneBack.invoke(tempData.subList(startIndex, endIndex).toByteArray())
                // 将剩余的数据存到临时数据里，等待下一次拼接处理
                tempData = tempData.subList(endIndex, tempData.size)
                // 如果接收数据空了，就再等一点时间，可能下一句消息就收到了
                if (receiveData.isEmpty()) {
                    delay(100)
                }
            }
        }
    }

    /**
     * 添加数据
     * @param data ByteArray
     */
    @Synchronized
    fun addData(data: ByteArray) {
        changeReceiveData(data, Type.ADD)
        startJob()
    }

    /**
     * 修改数据状态
     * @param data ByteArray 数据
     * @param type Type 操作类型
     * @param endIndex Int 最后一个数据的位置
     * @return List<Byte>
     */
    @Synchronized
    private fun changeReceiveData(data: ByteArray, type: Type, endIndex: Int = 1): List<Byte> {
        when (type) {
            Type.COPY -> return receiveData.subList(0, endIndex.coerceAtMost(receiveData.size))
            Type.ADD -> receiveData.addAll(data.toList())
            Type.REMOVE -> {
                receiveData = if (endIndex >= receiveData.size) mutableListOf()
                else receiveData.subList(endIndex, receiveData.size)
            }
        }
        return listOf()
    }

    /** 操作类型 */
    enum class Type {
        COPY,
        ADD,
        REMOVE
    }
}

/*
fun main() = runBlocking {
    val json = "{\"type\":\"SERIAL_PORT_DATA\",\"portName\":\"COM3\",\"dataHex\":\"2442444253492c30352c30302c302c312c302c302c342c302c302c322c302c302a35430d0a\"}"
    val builder = StringBuilder()
    val max = 1000
    for (i in 0 until max) {
        builder.append(json)
    }
    val startTime = System.currentTimeMillis()
    var i = 0
    val tools = ByteArraySplitTools(byteArrayOf('{'.code.toByte()), byteArrayOf('}'.code.toByte())) {
        println(it.toString(Charset.forName("GBK")))
        i ++
        if (i >= max) {
            println("${System.currentTimeMillis() - startTime}, $i")
        }
    }
    tools.addData(builder.toString().toByteArray(Charset.forName("GBK")))

    delay(1000)
}*/
