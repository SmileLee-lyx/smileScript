# smileScript
一个自制的脚本语言解释器。

# 功能
解释执行类似javascript的脚本语言。
### 普通运算
a=2+3;
b=a-5;
c=a*b;
a=[2,3,5,7];
a_0=a[0];
### 控制流：if，for
t=if(b>0){b} else -b;
if(x==0){++b;}
for(i=0;i<100;++i)s=s+i;
### 函数：
f=function(x){return if(x>=0)x else -x;}
y=f(30);
### 对象：
o={x:3;f:function(){return x;}};
t=o.x;
### 标准库
o.clone=Object.clone;
System.println(o.f());

# 使用方法
在程序中将jar包作为dependency即可使用。
使用示例：
```java
import org.smileLee.smilescript.stack.Stack;
import org.smileLee.smilescript.expression.controlExpression.Block;
import org.smileLee.smilescript.value.Value;

String expression="......";
Stack stack = new Stack();
Block block = Block.parse(expression);
Value result = block.invoke(stack);
```

```kotlin
import org.smileLee.smilescript.stack.Stack
import org.smileLee.smilescript.expression.controlExpression.Block

val expression="......"
val stack = Stack()
val block = Block.parse(expression)
val result = block(stack)
```