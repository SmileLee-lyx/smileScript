package org.smileLee.smilescript.value.Number

import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.`object`.*

/**
 * Created by Administrator on 2017/6/6.
 *
 * @author smileLee
 */
class StringValueData(var value: String) : IValueData {
    override fun typeName() = "string"

    override fun toInt() = value.toInt()
    override fun toDouble() = value.toDouble()
    override fun toBoolean() = value != ""
    override fun toFloat() = value.toFloat()
    override fun toChar() = value[0]
    override fun toLong() = value.toLong()
    override fun toString() = value.toString()

    override fun clone() = StringValueData(value)

    override fun castToObject() = ObjectValueData(this, ObjectValueData.String)

    override fun toCodeString(tab: String): CodeString {
        return CodeString("\"" + value + "\"")
    }
}