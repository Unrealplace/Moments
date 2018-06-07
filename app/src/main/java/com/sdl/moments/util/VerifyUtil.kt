package com.sdl.moments.util

import java.util.regex.Pattern

object VerifyUtil {

    /**
     * 校验是否是手机号
     */
    @JvmStatic
    fun isMobile(mobile: String): Boolean {
        val p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$") // 验证手机号
        val m = p!!.matcher(mobile)
        return m!!.matches()
    }

    /**
     * 验证短信验证码
     *
     * @param code
     * @return
     */
    @JvmStatic
    fun isSmsCode(code: String): Boolean {
        val pattern = Pattern.compile("^[0-9]{4}$")
        val matcher = pattern.matcher(code)
        return matcher.matches()
    }

}