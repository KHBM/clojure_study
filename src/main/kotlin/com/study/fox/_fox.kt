package com.study.fox

/**
 *
 * @author foxrian user has created this file at 2023/01/02, 23:37 on 2023
 * This can be modified via 'Preferences' > 'Editor' > 'File and Code Templates' interface
 **/
interface _fox<T> {

    fun <R> map(functor: ((T) -> (R))): _fox<R>
    fun <R> flatMap(applier: ((T) -> _fox<R>)): _fox<R>
    fun filter(predicate: ((T) -> Boolean)): _fox<T>
}