package org.smileLee.smilescript.value

import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.value.Number.*
import org.smileLee.smilescript.value.`object`.*

/**
 * Created by Administrator on 2017/6/6.
 *
 * @author smileLee
 */
interface IValueData : IParsableToCodeString {
    companion object {
        fun parse(n: Number?): IValueData {
            return when (n) {
                is Int    -> IntValueData(n)
                is Double -> DoubleValueData(n)
                is Float  -> FloatValueData(n)
                is Long   -> LongValueData(n)
                else      -> NullValueData()
            }
        }
    }

    fun typeName(): String

    fun toInt(): Int
    fun toDouble(): Double
    fun toBoolean(): Boolean
    fun toFloat(): Float
    fun toChar(): Char
    fun toLong(): Long
    override fun toString(): String

    fun castToObject(): ObjectValueData

    fun clone(): IValueData
}