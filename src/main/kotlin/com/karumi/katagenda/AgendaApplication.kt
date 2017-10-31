package com.karumi.katagenda

import com.karumi.katagenda.common.ui.Presenter
import com.karumi.katagenda.servicelocator.AgendaServiceLocator
import com.karumi.katagenda.ui.ContactsListPresenter

class AgendaApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) =
                AgendaApplication().main()

    }

    private fun main() {
        val presenter = getPresenter()
        presenter.onInitialize()
        while (true) {
            presenter.onAddContactOptionSelected()
        }
    }

    private fun getPresenter(): ContactsListPresenter {
        val contactsListPresenter = AgendaServiceLocator.contactsListPresenter
        hookPresenterStopEvent(contactsListPresenter)
        return contactsListPresenter
    }

    private fun <T : Presenter.View> hookPresenterStopEvent(presenter: Presenter<T>) {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                presenter.onStop()
            }
        })
    }
}