package org.smileLee.smilescript.value

import org.smileLee.smilescript.value.Number.*
import org.smileLee.smilescript.value.`object`.*
import org.smileLee.smilescript.value.array.*
import org.smileLee.smilescript.value.function.*

/**
 * Created by Administrator on 2017/6/6.
 * @author smileLee
 */
class Value(var value: IValueData) : IValueData {
    override fun typeName(): String = value.typeName()

    override fun toInt(): Int = value.toInt()
    override fun toDouble(): Double = value.toDouble()
    override fun toBoolean(): Boolean = value.toBoolean()
    override fun toFloat(): Float = value.toFloat()
    override fun toChar(): Char = value.toChar()
    override fun toLong(): Long = value.toLong()
    override fun toString(): String = value.toString()

    constructor() : this(NullValueData())
    constructor(b: Boolean) : this(BooleanValueData(b))

    override fun clone(): Value = Value(value)

    fun set(v: Value): Value {
        value = v.value
        return this
    }

    override fun equals(other: Any?): Boolean = when (other) {
        null     -> false
        is Value -> compareTo(other) == 0
        else     -> false
    }

    operator fun compareTo(a: Value): Int = if (value is ObjectValueData)
        (value as ObjectValueData).valueOf().compareTo(a)
    else if (a.value is ObjectValueData)
        this.compareTo((a.value as ObjectValueData).valueOf())
    else when (value) {
        is NullValueData    -> when (a.value) {
            is NullValueData -> 0
            else             -> -1
        }
        is IntValueData     -> when (a.value) {
            is NullValueData    -> 1
            is IntValueData     -> toInt().compareTo(a.toInt())
            is DoubleValueData  -> toInt().compareTo(a.toDouble())
            is BooleanValueData -> toInt().compareTo(a.toInt())
            is FloatValueData   -> toInt().compareTo(a.toFloat())
            is CharValueData    -> toInt().compareTo(a.toInt())
            is LongValueData    -> toInt().compareTo(a.toLong())
            is StringValueData  -> toString().compareTo(a.toString())
            else                -> 0
        }
        is DoubleValueData  -> when (a.value) {
            is NullValueData    -> 1
            is IntValueData     -> toInt().compareTo(a.toInt())
            is DoubleValueData  -> toInt().compareTo(a.toDouble())
            is BooleanValueData -> toInt().compareTo(a.toInt())
            is FloatValueData   -> toInt().compareTo(a.toFloat())
            is CharValueData    -> toInt().compareTo(a.toInt())
            is LongValueData    -> toInt().compareTo(a.toLong())
            is StringValueData  -> toString().compareTo(a.toString())
            else                -> 0
        }
        is BooleanValueData -> when (a.value) {
            is NullValueData    -> 1
            is IntValueData     -> toInt().compareTo(a.toInt())
            is DoubleValueData  -> toInt().compareTo(a.toDouble())
            is BooleanValueData -> toBoolean().compareTo(a.toBoolean())
            is FloatValueData   -> toInt().compareTo(a.toFloat())
            is CharValueData    -> toInt().compareTo(a.toInt())
            is LongValueData    -> toInt().compareTo(a.toLong())
            is StringValueData  -> toString().compareTo(a.toString())
            else                -> 0
        }
        is FloatValueData   -> when (a.value) {
            is NullValueData    -> 1
            is IntValueData     -> toFloat().compareTo(a.toInt())
            is DoubleValueData  -> toFloat().compareTo(a.toDouble())
            is BooleanValueData -> toFloat().compareTo(a.toInt())
            is FloatValueData   -> toFloat().compareTo(a.toFloat())
            is CharValueData    -> toFloat().compareTo(a.toInt())
            is LongValueData    -> toFloat().compareTo(a.toLong())
            is StringValueData  -> toString().compareTo(a.toString())
            else                -> 0
        }
        is CharValueData    -> when (a.value) {
            is NullValueData    -> 1
            is IntValueData     -> toInt().compareTo(a.toInt())
            is DoubleValueData  -> toInt().compareTo(a.toDouble())
            is BooleanValueData -> toInt().compareTo(a.toInt())
            is FloatValueData   -> toInt().compareTo(a.toFloat())
            is CharValueData    -> toInt().compareTo(a.toInt())
            is LongValueData    -> toInt().compareTo(a.toLong())
            is StringValueData  -> toString().compareTo(a.toString())
            else                -> 0
        }
        is LongValueData    -> when (a.value) {
            is NullValueData    -> 1
            is IntValueData     -> toLong().compareTo(a.toInt())
            is DoubleValueData  -> toLong().compareTo(a.toDouble())
            is BooleanValueData -> toLong().compareTo(a.toInt())
            is FloatValueData   -> toLong().compareTo(a.toFloat())
            is CharValueData    -> toLong().compareTo(a.toInt())
            is LongValueData    -> toLong().compareTo(a.toLong())
            is StringValueData  -> toString().compareTo(a.toString())
            else                -> 0
        }
        is StringValueData  -> toString().compareTo(a.toString())
        else                -> 0
    }

    infix fun and(a: Value): Value = when (toBoolean()) {
        true  -> +a
        false -> +this
    }

    infix fun or(a: Value): Value = when (toBoolean()) {
        true  -> +this
        false -> +a
    }

    fun not(): Value = when (value) {
        is NullValueData -> +this
        is IntValueData,
        is DoubleValueData,
        is BooleanValueData,
        is FloatValueData,
        is CharValueData,
        is LongValueData -> Value(BooleanValueData(!toBoolean()))
        else             -> clone()
    }

    operator fun unaryPlus(): Value = clone()

    operator fun unaryMinus(): Value = when (value) {
        is IntValueData    -> Value(IntValueData(-value.toInt()))
        is DoubleValueData -> Value(DoubleValueData(-value.toDouble()))
        else               -> clone()
    }

    operator fun plus(a: Value): Value = if (value is ObjectValueData)
        if (a.value is StringValueData)
            Value(StringValueData((value as ObjectValueData).toString())) + a
        else
            (value as ObjectValueData).valueOf() + a
    else if (a.value is ObjectValueData)
        if (value is StringValueData)
            this + Value(StringValueData((a.value as ObjectValueData).toString()))
        else
            this + (a.value as ObjectValueData).valueOf()
    else when (value) {
        is NullValueData    -> +a
        is IntValueData     -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(IntValueData(toInt() + a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toInt() + a.toDouble()))
            is BooleanValueData -> Value(IntValueData(toInt() + a.toInt()))
            is FloatValueData   -> Value(FloatValueData(toInt() + a.toFloat()))
            is CharValueData    -> Value(IntValueData(toInt() + a.toInt()))
            is LongValueData    -> Value(LongValueData(toInt() + a.toLong()))
            is StringValueData  -> Value(StringValueData(toString() + a.toString()))
            else                -> Value()
        }
        is DoubleValueData  -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(DoubleValueData(toDouble() + a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toDouble() + a.toDouble()))
            is BooleanValueData -> Value(DoubleValueData(toDouble() + a.toInt()))
            is FloatValueData   -> Value(DoubleValueData(toDouble() + a.toFloat()))
            is CharValueData    -> Value(DoubleValueData(toDouble() + a.toInt()))
            is LongValueData    -> Value(DoubleValueData(toDouble() + a.toLong()))
            is StringValueData  -> Value(StringValueData(toString() + a.toString()))
            else                -> Value()
        }
        is BooleanValueData -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(IntValueData(toInt() + a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toInt() + a.toDouble()))
            is BooleanValueData -> Value(BooleanValueData(toBoolean() || a.toBoolean()))
            is FloatValueData   -> Value(FloatValueData(toInt() + a.toFloat()))
            is CharValueData    -> Value(IntValueData(toInt() + a.toInt()))
            is LongValueData    -> Value(LongValueData(toInt() + a.toLong()))
            is StringValueData  -> Value(StringValueData(toString() + a.toString()))
            else                -> Value()
        }
        is FloatValueData   -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(FloatValueData(toFloat() + a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toFloat() + a.toDouble()))
            is BooleanValueData -> Value(FloatValueData(toFloat() + a.toInt()))
            is FloatValueData   -> Value(FloatValueData(toFloat() + a.toFloat()))
            is CharValueData    -> Value(FloatValueData(toFloat() + a.toInt()))
            is LongValueData    -> Value(FloatValueData(toFloat() + a.toLong()))
            is StringValueData  -> Value(StringValueData(toString() + a.toString()))
            else                -> Value()
        }
        is CharValueData    -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(IntValueData(toInt() + a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toInt() + a.toDouble()))
            is BooleanValueData -> Value(IntValueData(toInt() + a.toInt()))
            is FloatValueData   -> Value(FloatValueData(toInt() + a.toFloat()))
            is CharValueData    -> Value(IntValueData(toInt() + a.toInt()))
            is LongValueData    -> Value(LongValueData(toInt() + a.toLong()))
            is StringValueData  -> Value(StringValueData(toString() + a.toString()))
            else                -> Value()
        }
        is LongValueData    -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(LongValueData(toLong() + a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toLong() + a.toDouble()))
            is BooleanValueData -> Value(LongValueData(toLong() + a.toInt()))
            is FloatValueData   -> Value(FloatValueData(toLong() + a.toFloat()))
            is CharValueData    -> Value(LongValueData(toLong() + a.toInt()))
            is LongValueData    -> Value(LongValueData(toLong() + a.toLong()))
            is StringValueData  -> Value(StringValueData(toString() + a.toString()))
            else                -> Value()
        }
        is StringValueData  -> Value(StringValueData(toString() + a.toString()))
        is ArrayValueData   -> when (a.value) {
            is ArrayValueData -> {
                val m = ArrayList<Value>()
                m.addAll((value as ArrayValueData).elements)
                m.addAll((a.value as ArrayValueData).elements)
                Value(ArrayValueData(m))
            }
            else              -> Value()
        }
        else                -> Value()
    }

    operator fun minus(a: Value): Value = if (value is ObjectValueData)
        (value as ObjectValueData).valueOf() - a
    else if (a.value is ObjectValueData)
        this - (a.value as ObjectValueData).valueOf()
    else when (value) {
        is NullValueData    -> -a
        is IntValueData     -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(IntValueData(toInt() - a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toInt() - a.toDouble()))
            is BooleanValueData -> Value(IntValueData(toInt() - a.toInt()))
            is FloatValueData   -> Value(FloatValueData(toInt() - a.toFloat()))
            is CharValueData    -> Value(IntValueData(toInt() - a.toInt()))
            is LongValueData    -> Value(LongValueData(toInt() - a.toLong()))
            else                -> Value()
        }
        is DoubleValueData  -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(DoubleValueData(toDouble() - a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toDouble() - a.toDouble()))
            is BooleanValueData -> Value(DoubleValueData(toDouble() - a.toInt()))
            is FloatValueData   -> Value(DoubleValueData(toDouble() - a.toFloat()))
            is CharValueData    -> Value(DoubleValueData(toDouble() - a.toInt()))
            is LongValueData    -> Value(DoubleValueData(toDouble() - a.toLong()))
            else                -> Value()
        }
        is BooleanValueData -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(IntValueData(toInt() - a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toInt() - a.toDouble()))
            is BooleanValueData -> Value(BooleanValueData(toBoolean() && !a.toBoolean()))
            is FloatValueData   -> Value(FloatValueData(toInt() - a.toFloat()))
            is CharValueData    -> Value(IntValueData(toInt() - a.toInt()))
            is LongValueData    -> Value(LongValueData(toInt() - a.toLong()))
            else                -> Value()
        }
        is FloatValueData   -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(FloatValueData(toFloat() - a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toFloat() - a.toDouble()))
            is BooleanValueData -> Value(FloatValueData(toFloat() - a.toInt()))
            is FloatValueData   -> Value(FloatValueData(toFloat() - a.toFloat()))
            is CharValueData    -> Value(FloatValueData(toFloat() - a.toInt()))
            is LongValueData    -> Value(FloatValueData(toFloat() - a.toLong()))
            else                -> Value()
        }
        is CharValueData    -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(IntValueData(toInt() - a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toInt() - a.toDouble()))
            is BooleanValueData -> Value(IntValueData(toInt() - a.toInt()))
            is FloatValueData   -> Value(FloatValueData(toInt() - a.toFloat()))
            is CharValueData    -> Value(IntValueData(toInt() - a.toInt()))
            is LongValueData    -> Value(LongValueData(toInt() - a.toLong()))
            else                -> Value()
        }
        is LongValueData    -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(LongValueData(toLong() - a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toLong() - a.toDouble()))
            is BooleanValueData -> Value(LongValueData(toLong() - a.toInt()))
            is FloatValueData   -> Value(FloatValueData(toLong() - a.toFloat()))
            is CharValueData    -> Value(LongValueData(toLong() - a.toInt()))
            is LongValueData    -> Value(LongValueData(toLong() - a.toLong()))
            else                -> Value()
        }
        else                -> Value()
    }

    operator fun times(a: Value): Value = if (value is ObjectValueData)
        (value as ObjectValueData).valueOf() * a
    else if (a.value is ObjectValueData)
        this * (a.value as ObjectValueData).valueOf()
    else when (value) {
        is IntValueData     -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(IntValueData(toInt() * a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toInt() * a.toDouble()))
            is BooleanValueData -> Value(IntValueData(toInt() * a.toInt()))
            is FloatValueData   -> Value(FloatValueData(toInt() * a.toFloat()))
            is CharValueData    -> Value(IntValueData(toInt() * a.toInt()))
            is LongValueData    -> Value(LongValueData(toInt() * a.toLong()))
            else                -> Value()
        }
        is DoubleValueData  -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(DoubleValueData(toDouble() * a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toDouble() * a.toDouble()))
            is BooleanValueData -> Value(DoubleValueData(toDouble() * a.toInt()))
            is FloatValueData   -> Value(DoubleValueData(toDouble() * a.toFloat()))
            is CharValueData    -> Value(DoubleValueData(toDouble() * a.toInt()))
            is LongValueData    -> Value(DoubleValueData(toDouble() * a.toLong()))
            else                -> Value()
        }
        is BooleanValueData -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(IntValueData(toInt() * a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toInt() * a.toDouble()))
            is BooleanValueData -> Value(BooleanValueData(toBoolean() && a.toBoolean()))
            is FloatValueData   -> Value(FloatValueData(toInt() * a.toFloat()))
            is CharValueData    -> Value(IntValueData(toInt() * a.toInt()))
            is LongValueData    -> Value(LongValueData(toInt() * a.toLong()))
            else                -> Value()
        }
        is FloatValueData   -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(FloatValueData(toFloat() * a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toFloat() * a.toDouble()))
            is BooleanValueData -> Value(FloatValueData(toFloat() * a.toInt()))
            is FloatValueData   -> Value(FloatValueData(toFloat() * a.toFloat()))
            is CharValueData    -> Value(FloatValueData(toFloat() * a.toInt()))
            is LongValueData    -> Value(FloatValueData(toFloat() * a.toLong()))
            else                -> Value()
        }
        is CharValueData    -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(IntValueData(toInt() * a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toInt() * a.toDouble()))
            is BooleanValueData -> Value(IntValueData(toInt() * a.toInt()))
            is FloatValueData   -> Value(FloatValueData(toInt() * a.toFloat()))
            is CharValueData    -> Value(IntValueData(toInt() * a.toInt()))
            is LongValueData    -> Value(LongValueData(toInt() * a.toLong()))
            else                -> Value()
        }
        is LongValueData    -> when (a.value) {
            is NullValueData    -> +this
            is IntValueData     -> Value(LongValueData(toLong() * a.toInt()))
            is DoubleValueData  -> Value(DoubleValueData(toLong() * a.toDouble()))
            is BooleanValueData -> Value(LongValueData(toLong() * a.toInt()))
            is FloatValueData   -> Value(FloatValueData(toLong() * a.toFloat()))
            is CharValueData    -> Value(LongValueData(toLong() * a.toInt()))
            is LongValueData    -> Value(LongValueData(toLong() * a.toLong()))
            else                -> Value()
        }
        else                -> Value()
    }

    operator fun div(a: Value): Value = if (value is ObjectValueData)
        (value as ObjectValueData).valueOf() / a
    else if (a.value is ObjectValueData)
        this / (a.value as ObjectValueData).valueOf()
    else when (value) {
        is IntValueData    -> when (a.value) {
            is NullValueData   -> +this
            is IntValueData    -> Value(IntValueData(toInt() / a.toInt()))
            is DoubleValueData -> Value(DoubleValueData(toInt() / a.toDouble()))
            is FloatValueData  -> Value(FloatValueData(toInt() / a.toFloat()))
            is CharValueData   -> Value(IntValueData(toInt() / a.toInt()))
            is LongValueData   -> Value(LongValueData(toInt() / a.toLong()))
            else               -> Value()
        }
        is DoubleValueData -> when (a.value) {
            is NullValueData   -> +this
            is IntValueData    -> Value(DoubleValueData(toDouble() / a.toInt()))
            is DoubleValueData -> Value(DoubleValueData(toDouble() / a.toDouble()))
            is FloatValueData  -> Value(DoubleValueData(toDouble() / a.toFloat()))
            is CharValueData   -> Value(DoubleValueData(toDouble() / a.toInt()))
            is LongValueData   -> Value(DoubleValueData(toDouble() / a.toLong()))
            else               -> Value()
        }
        is FloatValueData  -> when (a.value) {
            is NullValueData   -> +this
            is IntValueData    -> Value(FloatValueData(toFloat() / a.toInt()))
            is DoubleValueData -> Value(DoubleValueData(toFloat() / a.toDouble()))
            is FloatValueData  -> Value(FloatValueData(toFloat() / a.toFloat()))
            is CharValueData   -> Value(FloatValueData(toFloat() / a.toInt()))
            is LongValueData   -> Value(FloatValueData(toFloat() / a.toLong()))
            else               -> Value()
        }
        is CharValueData   -> when (a.value) {
            is NullValueData   -> +this
            is IntValueData    -> Value(IntValueData(toInt() / a.toInt()))
            is DoubleValueData -> Value(DoubleValueData(toInt() / a.toDouble()))
            is FloatValueData  -> Value(FloatValueData(toInt() / a.toFloat()))
            is CharValueData   -> Value(IntValueData(toInt() / a.toInt()))
            is LongValueData   -> Value(LongValueData(toInt() / a.toLong()))
            else               -> Value()
        }
        is LongValueData   -> when (a.value) {
            is NullValueData   -> +this
            is IntValueData    -> Value(LongValueData(toLong() / a.toInt()))
            is DoubleValueData -> Value(DoubleValueData(toLong() / a.toDouble()))
            is FloatValueData  -> Value(FloatValueData(toLong() / a.toFloat()))
            is CharValueData   -> Value(LongValueData(toLong() / a.toInt()))
            is LongValueData   -> Value(LongValueData(toLong() / a.toLong()))
            else               -> Value()
        }
        else               -> Value()
    }

    operator fun rem(a: Value): Value = if (value is ObjectValueData)
        (value as ObjectValueData).valueOf() % a
    else if (a.value is ObjectValueData)
        this % (a.value as ObjectValueData).valueOf()
    else when (value) {
        is IntValueData  -> when (a.value) {
            is NullValueData -> +this
            is IntValueData  -> Value(IntValueData(toInt() % a.toInt()))
            is CharValueData -> Value(IntValueData(toInt() % a.toInt()))
            is LongValueData -> Value(LongValueData(toInt() % a.toLong()))
            else             -> Value()
        }
        is CharValueData -> when (a.value) {
            is NullValueData -> +this
            is IntValueData  -> Value(IntValueData(toInt() % a.toInt()))
            is CharValueData -> Value(IntValueData(toInt() % a.toInt()))
            is LongValueData -> Value(LongValueData(toInt() % a.toLong()))
            else             -> Value()
        }
        is LongValueData -> when (a.value) {
            is NullValueData -> +this
            is IntValueData  -> Value(LongValueData(toLong() % a.toInt()))
            is CharValueData -> Value(LongValueData(toLong() % a.toInt()))
            is LongValueData -> Value(LongValueData(toLong() % a.toLong()))
            else             -> Value()
        }
        else             -> Value()
    }

    fun leftInc(): Value {
        when (value) {
            is IntValueData     -> value = IntValueData(toInt() + 1)
            is DoubleValueData  -> value = DoubleValueData(toDouble() + 1)
            is BooleanValueData -> value = BooleanValueData(true)
            is FloatValueData   -> value = FloatValueData(toFloat() + 1)
            is CharValueData    -> value = CharValueData(toChar() + 1)
            is LongValueData    -> value = LongValueData(toLong() + 1)
        }
        return this
    }

    fun leftDec(): Value {
        when (value) {
            is IntValueData     -> value = IntValueData(toInt() - 1)
            is DoubleValueData  -> value = DoubleValueData(toDouble() - 1)
            is BooleanValueData -> value = BooleanValueData(false)
            is FloatValueData   -> value = FloatValueData(toFloat() - 1)
            is CharValueData    -> value = CharValueData(toChar() - 1)
            is LongValueData    -> value = LongValueData(toLong() - 1)
        }
        return this
    }

    fun rightInc(): Value {
        val a = clone()
        when (value) {
            is IntValueData     -> value = IntValueData(toInt() + 1)
            is DoubleValueData  -> value = DoubleValueData(toDouble() + 1)
            is BooleanValueData -> value = BooleanValueData(true)
            is FloatValueData   -> value = FloatValueData(toFloat() + 1)
            is CharValueData    -> value = CharValueData(toChar() + 1)
            is LongValueData    -> value = LongValueData(toLong() + 1)
        }
        return a
    }

    fun rightDec(): Value {
        val a: Value = clone()
        when (value) {
            is IntValueData     -> value = IntValueData(toInt() - 1)
            is DoubleValueData  -> value = DoubleValueData(toDouble() - 1)
            is BooleanValueData -> value = BooleanValueData(false)
            is FloatValueData   -> value = FloatValueData(toFloat() - 1)
            is CharValueData    -> value = CharValueData(toChar() - 1)
            is LongValueData    -> value = LongValueData(toLong() - 1)
        }
        return a
    }

    operator fun invoke(providedParams: List<Value?>): Value = this(providedParams, ThisReference())

    operator fun invoke(providedParams: List<Value?>, thisReference: ThisReference): Value = if (value is ObjectValueData)
        (value as ObjectValueData).valueOf()(providedParams, thisReference)
    else when (value) {
        is FunctionValueData -> (value as FunctionValueData)(providedParams, thisReference)
        else                 -> Value()
    }

    operator fun get(index: Value): Value = if (value is ObjectValueData)
        (value as ObjectValueData).valueOf()[index]
    else if (index.value is ObjectValueData)
        this[(index.value as ObjectValueData).valueOf()]
    else when (value) {
        is ArrayValueData -> when (index.value) {
            else -> (value as ArrayValueData)[index.toInt()]
        }
        else              -> Value()
    }

    override fun castToObject(): ObjectValueData {
        val obj = value.castToObject()
        value = obj
        return obj
    }

    override fun toCodeString(tab: String) = value.toCodeString(tab)
}
