package org.smileLee.smilescript.value.Number

import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.`object`.*

/**
 * Created by Administrator on 2017/6/6.
 *
 * @author smileLee
 */
class LongValueData(var value: Long) : IValueData {
    override fun typeName() = "null"

    override fun toInt() = value.toInt()
    override fun toDouble() = value.toDouble()
    override fun toBoolean() = value != 0L
    override fun toFloat() = value.toFloat()
    override fun toChar() = value.toChar()
    override fun toLong() = value.toLong()
    override fun toString() = value.toString()

    override fun clone() = LongValueData(value)

    override fun castToObject() = ObjectValueData(this, ObjectValueData.Number)

    override fun toCodeString(tab: String): CodeString {
        return CodeString(value.toString() + "L")
    }
}