package org.smileLee.smilescript.expression.operatorExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.stack.*

/**
 * Created by Administrator on 2017/6/7.
 * @author smileLee
 */

class EvaluationExpression(val left: IExpression, val right: IExpression) : IExpression {
    override fun invoke(s: Stack) = left(s).set(right(s))

    override fun toCodeString(tab: String) = left.toCodeString(tab).append(" = ").append(right.toCodeString(tab))
}