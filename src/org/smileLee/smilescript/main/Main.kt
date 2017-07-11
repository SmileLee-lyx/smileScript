package org.smileLee.smilescript.main

import org.smileLee.smilescript.exception.*
import org.smileLee.smilescript.expression.controlExpression.*
import org.smileLee.smilescript.stack.*

/**
 * Created by Administrator on 2017/6/6.

 * @author smileLee
 */
object Main {
    @JvmStatic fun main(args: Array<String>) {
        val s: Stack = Stack()
        val b = try {
            Block.parse("""
a=3;
b=5;
f=a+b+f
""")                                                             //解析
        } catch (e: SyntaxException) {
            e.printStackTrace()
            println("Syntax Error!")
            Block()                                              //解析失败则返回空的块
        }
        println(b(s))                                                     //运行
    }
}