package org.smileLee.smilescript.expression.symbolExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.array.*

/**
 * Created by Administrator on 2017/6/8.
 *
 * @author smileLee
 */
open class ArrayExpression(val elements: List<IExpression>) : IExpression {
    override fun invoke(s: Stack): Value {
        val elements = ArrayList<Value>()
        this.elements.mapTo(elements) { it(s) }
        return Value(ArrayValueData(elements))
    }

    override fun toCodeString(tab: String): CodeString {
        val codeString = CodeString("[")
        for (element in elements) {
            codeString.append(element.toCodeString(tab)).append(", ")
        }
        if (elements.isNotEmpty()) codeString.removeFromEnd(2)
        codeString.append("]")
        return codeString
    }

    data class ConstValueCapture(val captureName: String, val init: IExpression)
}