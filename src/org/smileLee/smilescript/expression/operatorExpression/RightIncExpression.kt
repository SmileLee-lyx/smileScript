package org.smileLee.smilescript.expression.operatorExpression

import org.smileLee.smilescript.expression.*
import org.smileLee.smilescript.stack.*

/**
 * Created by Administrator on 2017/6/6.
 * @author smileLee
 */

class RightIncExpression(val left: IExpression) : IExpression {
    override fun invoke(s: Stack) = left(s).rightInc()

    override fun toCodeString(tab: String) = left.toCodeString(tab).appendFront("--")
}