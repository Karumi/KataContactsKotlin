package com.karumi.katagenda.ui

import com.karumi.katagenda.common.ui.Presenter
import com.karumi.katagenda.domain.Contact
import com.karumi.katagenda.usecase.AddContact
import com.karumi.katagenda.usecase.GetContacts

class ContactsListPresenter(view: ContactsListPresenter.View, private val getContacts: GetContacts,
                            private val addContact: AddContact) : Presenter<ContactsListPresenter.View>(view) {

    override fun onInitialize() {
        view.showWelcomeMessage()
        loadContactsList()
    }

    override fun onStop() {
        view.showGoodbyeMessage()
    }

    fun onAddContactOptionSelected() {
        val contactToAdd = requestNewContact()
        if (contactToAdd == null) {
            view.showDefaultError()
        } else {
            addContact(contactToAdd)
            loadContactsList()
        }
    }

    private fun requestNewContact(): Contact? {
        val firstName = view.newContactFirstName
        val lastName = view.newContactLastName
        val phoneNumber = view.newContactPhoneNumber
        var contact: Contact? = null
        if (isContactInfoValid(firstName, lastName, phoneNumber)) {
            contact = Contact(firstName, lastName, phoneNumber)
        }
        return contact
    }

    private fun isContactInfoValid(firstName: String, lastName: String, phoneNumber: String): Boolean {
        return !firstName.isEmpty() && !lastName.isEmpty() && !phoneNumber.isEmpty()
    }

    private fun loadContactsList() {
        val contactList = getContacts()

        when {
            contactList.isEmpty() -> view.showEmptyCase()
            else -> view.showContacts(contactList)
        }
    }

    interface View : Presenter.View {

        fun showWelcomeMessage()

        fun showGoodbyeMessage()

        fun showContacts(contactList: List<Contact>)

        val newContactFirstName: String

        val newContactLastName: String

        val newContactPhoneNumber: String
    }
}