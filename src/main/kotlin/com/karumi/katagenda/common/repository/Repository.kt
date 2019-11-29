package com.karumi.katagenda.common.repository

open class Repository<T>(private val dataSource: DataSource<T>) {

    val all: List<T>
        get() = dataSource.all

    fun add(item: T): T = dataSource.add(item)
}