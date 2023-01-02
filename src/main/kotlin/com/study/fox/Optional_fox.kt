package com.study.fox

/**
 *
 * @author foxrain user has created this file at 2022/12/28, 18:25 on 2022
 **/
class Optional_fox<T> private constructor(
    private val data: T?
) {
    companion object {
        fun <T> of(v: T?): Optional_fox<T> {
            return Optional_fox(v)
        }
    }

    fun <R> map( //functor
        functor: ((T) -> (R)),
    ): Optional_fox<R> {
        if (data == null) {
            return Optional_fox(null)
        }
        val r: R = functor(data)
        return Optional_fox(r)
    }

    fun filter(
        predicate: ((T) -> Boolean),
    ): Optional_fox<T> {
        if (data == null) {
            return Optional_fox(null)
        }
        val result = predicate(data)
        if (result) {
            return this@Optional_fox
        } else {
            return Optional_fox(null)
        }
    }

    fun get(): T? {
        return data;
    }

    fun orElse(value : T): T {
        return data ?: value
    }

    inline fun <reified U, reified T> apply(value : Optional_fox<T>): Optional_fox<U> {
        if ( data is ((T) -> (U))) {

        }
    }
}

fun <R, U, T> applyi( //applicative
    functionOptional: Optional_fox<T>,
    dataOptional: Optional_fox<R>
): Optional_fox<U> where T : ((R) -> (U)){

    val fn = functionOptional.get()
    if (fn == null) {
        return Optional_fox.of(null)
    } else {
        val data = dataOptional.get()
        if (data == null) {
            return Optional_fox.of(null)
        } else {
            val invoke = fn.invoke(data)
            return Optional_fox.of(invoke)
        }
    }
}