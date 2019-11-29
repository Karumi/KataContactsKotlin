package com.karumi.katagenda.common.repository

class InMemoryDataSource<T> : DataSource<T> {

    private val values = ArrayList<T>()

    override val all: List<T>
        get() = values

    override fun add(item: T): T {
        values.add(item)
        return item
    }
}
