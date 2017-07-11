package org.smileLee.smilescript.expression.symbolExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.reformator.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.`object`.*

/**
 * Created by Administrator on 2017/6/9.
 * @author smileLee
 */
class ObjectExpression(val fields: HashMap<String, IExpression>) : IExpression {
    override fun invoke(s: Stack): Value {
        val fields = HashMap<String, Value>()
        for ((key, value) in this.fields) {
            fields.put(key, value(s))
        }
        return Value(ObjectValueData(fields))
    }

    override fun toCodeString(tab: String): CodeString {
        val codeString = CodeString("{")
        for ((key, value) in fields) {
            codeString.append(" ").append(key).append(":").append(value.toCodeString(tab)).append(";")
        }
        codeString.append("}")
        return codeString
    }
}