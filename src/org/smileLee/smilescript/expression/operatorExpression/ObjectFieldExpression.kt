package org.smileLee.smilescript.expression.operatorExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*
import org.smileLee.smilescript.value.`object`.*

/**
 * Created by Administrator on 2017/6/8.
 * @author smileLee
 */

class ObjectFieldExpression(val obj: IExpression, val field: String) : IExpression {

    override fun invoke(s: Stack) = this(s, ThisReference())
    override fun invoke(s: Stack, thisReference: ThisReference): Value {
        val obj = obj(s).castToObject()
        thisReference.thisReference = obj
        return obj[field]
    }

    override fun toCodeString(tab: String) = obj.toCodeString(tab).append(".").append(field)
}