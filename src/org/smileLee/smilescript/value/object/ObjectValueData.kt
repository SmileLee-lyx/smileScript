package org.smileLee.smilescript.value.`object`

import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.function.*

/**
 * Created by Administrator on 2017/6/8.
 *
 * @author smileLee
 */

open class ObjectValueData : IValueData {
    val fields: HashMap<String, Value>

    companion object {
        val Object: ObjectValueData
        val Number: ObjectValueData
        val String: ObjectValueData
        val Array: ObjectValueData
        val Function: ObjectValueData

        init {
            Object = ObjectValueData(false)
            Object["clone"] = Value(ObjectAutoCloneFunction())
            Object["toString"] = Value(ObjectAutoToStringFunction())
            Object["valueOf"] = Value(ObjectAutoValueOfFunction())
            Number = ObjectValueData(Object)
            Number["toString"] = Value(NumberAutoToStringFunction())
            String = ObjectValueData(Number)
            Array = ObjectValueData(Number)
            Array["get"] = Value(ArrayAutoGetFunction())
            Array["length"] = Value(ArrayAutoLengthFunction())
            Function = ObjectValueData(Number)
        }

        fun newStack(): HashMap<String, Value> {
            val System = ObjectValueData(false)
            System["print"] = Value(PrintFunction(false))
            System["println"] = Value(PrintFunction(true))
            val m: HashMap<String, Value> = HashMap()
            m["Object"] = Value(Object)
            m["Number"] = Value(Number)
            m["String"] = Value(String)
            m["Array"] = Value(Array)
            m["Function"] = Value(Function)
            m["System"] = Value(System)
            return m
        }
    }

    override fun typeName() = "object"

    override fun toInt() = 1
    override fun toDouble() = 1.toDouble()
    override fun toBoolean() = true
    override fun toFloat() = 1.toFloat()
    override fun toChar() = 1.toChar()
    override fun toLong() = 1.toLong()
    override fun toString() = this["toString"](ArrayList(), ThisReference(this)).toString()

    fun valueOf() = this["valueOf"](ArrayList(), ThisReference(this))

    override fun clone(): ObjectValueData {
        val fields: HashMap<String, Value> = HashMap()
        fields.putAll(this.fields)
        return ObjectValueData(fields, false)
    }

    override fun castToObject() = this

    constructor() : this(Object)
    constructor(x: Boolean) : this(HashMap(), x)
    constructor(fields: HashMap<String, Value>) : this(Object) {
        this.fields.putAll(fields)
    }

    constructor(fields: HashMap<String, Value>, x: Boolean) {
        this.fields = fields
        if (x) return
    }

    constructor(obj: ObjectValueData) : this(HashMap(), false) {
        fields.putAll(obj.fields)
    }

    constructor(value: IValueData, obj: ObjectValueData) : this(obj) {
        fields.put("value", Value(value))
    }

    operator fun get(fieldName: String): Value {
        if (!fields.containsKey(fieldName)) {
            fields[fieldName] = Value()
        }
        return fields[fieldName] ?: throw Error("unexpected null")
    }

    operator fun set(fieldName: String, value: Value) {
        fields[fieldName] = value
    }

    override fun toCodeString(tab: String) = CodeString()
}