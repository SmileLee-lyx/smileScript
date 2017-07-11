package org.smileLee.smilescript.expression.operatorExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*

/**
 * Created by Administrator on 2017/6/6.
 * @author smileLee
 */

class LeftDecExpression(val right: IExpression) : IExpression {
    override fun invoke(s: Stack): Value = right(s).leftDec()

    override fun toCodeString(tab: String) = right.toCodeString(tab).appendFront("--")
}