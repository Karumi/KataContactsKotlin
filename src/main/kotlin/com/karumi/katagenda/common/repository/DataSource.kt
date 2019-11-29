package com.karumi.katagenda.common.repository

interface DataSource<T> {
    val all: List<T>
    fun add(item: T): T
}
