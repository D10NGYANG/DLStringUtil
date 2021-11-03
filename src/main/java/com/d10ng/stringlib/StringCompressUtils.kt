package com.d10ng.stringlib

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

/**
 * 压缩字符串
 * @receiver [String]
 * @param charsetName [String]
 * @return [String]
 */
fun String.compress(charsetName: String = "ISO-8859-1"): String {
    if (this.isEmpty()) return this
    val os = ByteArrayOutputStream()
    val gzipOs = GZIPOutputStream(os)
    gzipOs.write(this.toByteArray())
    gzipOs.close()
    return os.toString(charsetName)
}

/**
 * 解压字符串
 * @receiver [String]
 * @param charsetName [String]
 * @return [String]
 */
fun String.uncompress(charsetName: String = "ISO-8859-1"): String {
    if (this.isEmpty()) return this
    val os = ByteArrayOutputStream()
    val ins = ByteArrayInputStream(this.toByteArray(Charset.forName(charsetName)))
    val gzipIs = GZIPInputStream(ins)
    val buffer = ByteArray(256)
    var n: Int
    while (gzipIs.read(buffer).also { n = it } >= 0) {
        os.write(buffer, 0, n)
    }
    return os.toString()
}