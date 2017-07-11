package org.smileLee.smilescript.value.Number

import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.`object`.*

/**
 * Created by Administrator on 2017/6/6.
 *
 * @author smileLee
 */
class CharValueData(var value: Char) : IValueData {

    override fun typeName(): String = "char"

    override fun toInt() = value.toInt()
    override fun toDouble() = value.toDouble()
    override fun toBoolean() = value != 0.toChar()
    override fun toFloat() = value.toFloat()
    override fun toChar() = value.toChar()
    override fun toLong() = value.toLong()
    override fun toString() = value.toString()

    override fun clone() = CharValueData(value)

    override fun castToObject() = ObjectValueData(this, ObjectValueData.Number)

    override fun toCodeString(tab: String): CodeString {
        return CodeString("\'" + value.toString() + "\'")
    }
}