package com.karumi.katagenda.common.repository

import java.util.*

class InMemoryDataSource<T> : DataSource<T> {

    private val values = LinkedList<T>()

    override val all: List<T>
        get() = values

    override fun add(item: T): T {
        values.add(item)
        return item
    }
}