package org.smileLee.smilescript.reformator

/**
 * Created by Administrator on 2017/6/9.
 *
 * @author smileLee
 */
class CodeString(var code: StringBuilder) {
    val length
        get() = code.length

    constructor() : this("")
    constructor(s: String) : this(StringBuilder(s))

    fun append(o: Any): CodeString {
        code.append(o)
        return this
    }

    fun appendFront(o: Any): CodeString {
        code.insert(0, o)
        return this
    }

    fun removeFromEnd(i: Int): CodeString {
        code.delete(length - i, length)
        return this
    }

    override fun toString() = code.toString()
}