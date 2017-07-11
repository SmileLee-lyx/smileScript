package org.smileLee.smilescript.stack

import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.`object`.*

/**
 * Created by Administrator on 2017/6/6.
 * @author smileLee
 */
class Stack(val m: HashMap<String, Value>) {

    constructor() : this(ObjectValueData.newStack())

    constructor(x: Boolean) : this(HashMap()) //no initialization

    operator fun get(str: String): Value {
        if (!m.containsKey(str)) {
            m[str] = Value()
        }
        return m[str] ?: throw Error("unexpected null")
    }

    operator fun set(str: String, value: Value?) {
        m[str] = value ?: Value()
    }

    fun clone(): Stack {
        val m = HashMap<String, Value>()
        m.putAll(this.m)
        return Stack(m)
    }

    override fun toString(): String {
        val s = StringBuilder()
        for ((key, value) in m) {
            s.append(key).append(":(").append(value.typeName()).append(")").append(value).append("\n")
        }
        return s.toString()
    }

    fun with(thisReference: ThisReference) {
        if (thisReference.thisReference != null) {
            val capture = HashMap<String, Value>()
            capture.putAll(m)
            m["capture"] = Value(ObjectValueData(capture, false))
            m.putAll(thisReference.thisReference!!.fields)
            m["this"] = Value(thisReference.thisReference!!)
        }
    }
}