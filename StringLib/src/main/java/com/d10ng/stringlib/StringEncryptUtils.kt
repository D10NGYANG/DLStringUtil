package com.d10ng.stringlib

import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.security.KeyFactory
import java.security.MessageDigest
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/** 加密key  */
private var PASSWORD_ENC_SECRET = "hailiao-20210318"
/** 加密算法 */
private const val KEY_ALGORITHM = "AES"
/** 字符编码 */
private val CHARSET = Charset.forName("UTF-8")
/** 加解密算法/工作模式/填充方式 */
private const val CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding"

/**
 * 对字符串加密
 * @receiver [String]
 * @param key [String] 加密key
 * @param keyAlgorithm [String] 加密算法
 * @param cipherAlgorithm [String] 加解密算法/工作模式/填充方式
 * @param charset [Charset] 字符编码
 * @return [String]
 */
fun String.encrypt(
    key: String = PASSWORD_ENC_SECRET,
    keyAlgorithm: String = KEY_ALGORITHM,
    cipherAlgorithm: String = CIPHER_ALGORITHM,
    charset: Charset = CHARSET
): String {
    val cipher = Cipher.getInstance(cipherAlgorithm)
    val byteArray = key.toByteArray(charset)
    val keySpec = SecretKeySpec(byteArray, keyAlgorithm)
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, IvParameterSpec(byteArray))
    val encrypted = cipher.doFinal(this.toByteArray(charset))
    return Base64.getEncoder().encodeToString(encrypted)
}

/**
 * 对字符串解密
 * @receiver [String]
 * @param key [String] 加密key
 * @param keyAlgorithm [String] 加密算法
 * @param cipherAlgorithm [String] 加解密算法/工作模式/填充方式
 * @param charset [Charset] 字符编码
 * @return [String]
 */
fun String.decrypt(
    key: String = PASSWORD_ENC_SECRET,
    keyAlgorithm: String = KEY_ALGORITHM,
    cipherAlgorithm: String = CIPHER_ALGORITHM,
    charset: Charset = CHARSET
): String {
    val encrypted = Base64.getDecoder().decode(this.toByteArray(charset))
    val cipher = Cipher.getInstance(cipherAlgorithm)
    val byteArray = key.toByteArray(charset)
    val keySpec = SecretKeySpec(byteArray, keyAlgorithm)
    cipher.init(Cipher.DECRYPT_MODE, keySpec, IvParameterSpec(byteArray))
    val original = cipher.doFinal(encrypted)
    return String(original, charset)
}

/**
 * 公钥加密
 * @receiver [String]
 * @param publicKey [String]
 * @param keyAlgorithm [String]
 * @param cipherAlgorithm [String]
 * @param charset [Charset]
 * @return [String]
 */
fun String.encryptByPublicKey(
    publicKey: String,
    keyAlgorithm: String = "RSA",
    cipherAlgorithm: String = "RSA/None/PKCS1Padding",
    charset: Charset = CHARSET
): String {
    // 得到公钥对象
    val keySpec = X509EncodedKeySpec(Base64.getDecoder().decode(publicKey.toByteArray(charset)))
    val keyFactory = KeyFactory.getInstance(keyAlgorithm)
    val pubKey = keyFactory.generatePublic(keySpec)
    // 加密数据
    val cipher = Cipher.getInstance(cipherAlgorithm)
    cipher.init(Cipher.ENCRYPT_MODE, pubKey)
    val encrypted = cipher.doLongerCipherFinal(this.toByteArray(charset))
    return Base64.getEncoder().encodeToString(encrypted)
}

/**
 * 服务端提供
 * @receiver [Cipher]
 * @param source [ByteArray]
 * @return [ByteArray]
 */
private fun Cipher.doLongerCipherFinal(source: ByteArray): ByteArray {
    var offset = 0
    val totalSize = source.size
    val out = ByteArrayOutputStream()
    while (totalSize - offset > 0) {
        val size = (1024 / 8 - 11).coerceAtMost(totalSize - offset)
        out.write(doFinal(source, offset, size))
        offset += size
    }
    out.close()
    return out.toByteArray()
}

/**
 * md5加密
 * @receiver [String]
 * @return [String]
 */
fun String.md5(): String {
    val hash = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    val hex = StringBuilder(hash.size * 2)
    for (b in hash) {
        var str = Integer.toHexString(b.toInt())
        if (b < 0x10) {
            str = "0$str"
        }
        hex.append(str.substring(str.length -2))
    }
    return hex.toString()
}

