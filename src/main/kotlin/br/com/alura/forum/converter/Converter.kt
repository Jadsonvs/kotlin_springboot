package br.com.alura.forum.converter

interface Converter<T, U> {

    fun converterFrom(from: T): U


}
