package org.smileLee.smilescript.expression.operatorExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*

/**
 * Created by Administrator on 2017/6/6.
 * @author smileLee
 */

class GreaterExpression(val left: IExpression, val right: IExpression) : IExpression {
    override fun invoke(s: Stack): Value = Value(left(s) > right(s))

    override fun toCodeString(tab: String) = left.toCodeString(tab).append(" > ").append(right.toCodeString(tab))
}