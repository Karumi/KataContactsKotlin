package com.karumi.katagenda.common.ui

abstract class Presenter<T : Presenter.View>(protected val view: T) {

    abstract fun onInitialize()

    abstract fun onStop()

    interface View {

        fun showDefaultError()

        fun showEmptyCase()
    }
}