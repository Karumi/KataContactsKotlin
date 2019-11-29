package com.karumi.katagenda

import com.karumi.katagenda.common.ui.Presenter
import com.karumi.katagenda.servicelocator.AgendaServiceLocator
import com.karumi.katagenda.ui.ContactsListPresenter

class AgendaApplication {

    private val presenter: ContactsListPresenter by AgendaServiceLocator.getContactsListPresenter()

    companion object {
        @JvmStatic
        fun main(args: Array<String>) =
                AgendaApplication().main()
    }

    private fun main() {
        initPresenter()
        presenter.onInitialize()
        while (true) {
            presenter.onAddContactOptionSelected()
        }
    }

    private fun initPresenter() {
        hookPresenterStopEvent(presenter)
    }

    private fun <T : Presenter.View> hookPresenterStopEvent(presenter: Presenter<T>) {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                presenter.onStop()
            }
        })
    }
}