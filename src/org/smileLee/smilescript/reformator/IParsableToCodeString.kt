package org.smileLee.smilescript.reformator

/**
 * Created by Administrator on 2017/6/9.
 * @author smileLee
 */
interface IParsableToCodeString {
    fun toCodeString(tab: String): CodeString
}