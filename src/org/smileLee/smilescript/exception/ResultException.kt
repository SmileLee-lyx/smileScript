package org.smileLee.smilescript.exception

import org.smileLee.smilescript.value.*

/**
 * Created by Administrator on 2017/6/8.
 * @author smileLee
 */

class ResultException(val result: Value, val label: String) : RuntimeException()