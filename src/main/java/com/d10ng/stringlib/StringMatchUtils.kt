package com.d10ng.stringlib

/**
 * 判断字符串是否符合身份证规则
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isIdCard(): Boolean = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)".toRegex().matches(this)

/**
 * 判断字符串是否符合手机号码规则
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isMobileNumber(): Boolean = "^1[1-9]\\d{9}$".toRegex().matches(this)

/**
 * 判断字符串是否符合邮箱规则
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isEmail(): Boolean = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex().matches(this)

/**
 * 判断字符串是否只有中英文数字，不包含特殊字符
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isOnlyChEnNum(): Boolean = "[a-zA-Z0-9\u4E00-\u9FA5]+".toRegex().matches(this)

/**
 * 判断字符串是否只有中文
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isOnlyChinese(): Boolean = "[\u4E00-\u9FA5]+".toRegex().matches(this)

/**
 * 判断字符串是否只有英文
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isOnlyEnglish(): Boolean = "[a-zA-Z]+".toRegex().matches(this)

/**
 * 判断字符串是否只有数字
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isOnlyNumber(): Boolean = "[0-9]+".toRegex().matches(this)