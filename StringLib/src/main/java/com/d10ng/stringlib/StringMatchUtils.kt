package com.d10ng.stringlib

import java.util.regex.Pattern

/**
 * 判断字符串是否符合身份证规则
 *
 * @receiver String
 * @return Boolean
 */
fun String.isIdCard(): Boolean {
    return Pattern.matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)", this)
}

/**
 * 判断字符串是否符合手机号码规则
 *
 * @receiver String
 * @return Boolean
 */
fun String.isMobileNumber(): Boolean {
    return Pattern.matches("^1[1-9]\\d{9}$", this)
}

/**
 * 判断字符串是否符合邮箱规则
 *
 * @receiver String
 * @return Boolean
 */
fun String.isEmail(): Boolean {
    return Pattern.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", this)
}

/**
 * 判断字符串是否只有中英文数字，不包含特殊字符
 *
 * @receiver String
 * @return Boolean
 */
fun String.isOnlyChEnNum(): Boolean {
    return Pattern.matches("[a-zA-Z0-9\u4E00-\u9FA5]+", this)
}

/**
 * 判断字符串是否只有中文
 *
 * @receiver String
 * @return Boolean
 */
fun String.isOnlyChinese(): Boolean {
    return Pattern.matches("[\u4E00-\u9FA5]+", this)
}

/**
 * 判断字符串是否只有英文
 *
 * @receiver String
 * @return Boolean
 */
fun String.isOnlyEnglish(): Boolean {
    return Pattern.matches("[a-zA-Z]+", this)
}

/**
 * 判断字符串是否只有数字
 *
 * @receiver String
 * @return Boolean
 */
fun String.isOnlyNumber(): Boolean {
    return Pattern.matches("[0-9]+", this)
}