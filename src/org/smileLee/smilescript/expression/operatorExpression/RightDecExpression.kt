package org.smileLee.smilescript.expression.operatorExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.stack.*
import org.smileLee.smilescript.value.*

/**
 * Created by Administrator on 2017/6/6.
 * @author smileLee
 */

class RightDecExpression(val left: IExpression) : IExpression {
    override fun invoke(s: Stack): Value = left(s).rightDec()

    override fun toCodeString(tab: String) = left.toCodeString(tab).append("--")
}