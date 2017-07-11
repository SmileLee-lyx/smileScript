package org.smileLee.smilescript.value.Number

import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.`object`.*

/**
 * Created by Administrator on 2017/6/6.
 *
 * @author smileLee
 */
class NullValueData : IValueData {
    override fun typeName() = "null"

    override fun toInt() = 0
    override fun toDouble() = 0.toDouble()
    override fun toBoolean() = false
    override fun toFloat() = 0.toFloat()
    override fun toChar() = 0.toChar()
    override fun toLong() = 0.toLong()
    override fun toString() = "null"

    override fun clone() = NullValueData()

    override fun castToObject() = ObjectValueData(this, ObjectValueData.Number)

    override fun toCodeString(tab: String) = CodeString("null")
}