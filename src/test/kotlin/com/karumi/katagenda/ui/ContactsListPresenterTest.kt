package com.karumi.katagenda.ui

import com.karumi.katagenda.domain.Contact
import com.karumi.katagenda.usecase.AddContact
import com.karumi.katagenda.usecase.GetContacts
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class ContactsListPresenterTest {

    companion object {

        private val ANY_NUMBER_OF_CONTACTS = 7
        private val ANY_FIRST_NAME = "Pedro Vicente"
        private val ANY_LAST_NAME = "Gomez Sanchez"
        private val ANY_PHONE_NUMBER = "666666666"
    }

    @Mock private val view: ContactsListPresenter.View? = null
    @Mock private val getContacts: GetContacts? = null
    @Mock private val addContact: AddContact? = null
    @Captor private val contactsCaptor: ArgumentCaptor<List<Contact>>? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun shouldShowWelcomeMessageOnInitialize() {
        val presenter = givenAContactsListPresenter()

        presenter.onInitialize()

        verify(view)?.showWelcomeMessage()
    }

    @Test
    fun shouldShowEmptyCaseIfTheAgendaIsEmpty() {
        val presenter = givenAContactsListPresenter()
        givenTheAgendaIsEmpty()

        presenter.onInitialize()

        verify(view)?.showEmptyCase()
    }

    @Test
    fun shouldShowContactsFromTheAgendaOnInitialize() {
        val presenter = givenAContactsListPresenter()
        val contacts = givenTheAgendaIsNotEmpty()

        presenter.onInitialize()

        verify(view)?.showContacts(contacts)
    }

    @Test
    fun shouldShowGoodbyeMessageOnStop() {
        val presenter = givenAContactsListPresenter()

        presenter.onStop()

        verify(view)?.showGoodbyeMessage()
    }

    @Test
    fun shouldShowTheContactsListWithTheNewContactOnContactAdded() {
        val presenter = givenAContactsListPresenter()
        val contactToCreate = givenTheUserAddsAContact()
        givenTheContactIsAddedCorrectly(contactToCreate)

        presenter.onInitialize()
        presenter.onAddContactOptionSelected()

        verify(view, times(2))?.showContacts(contactsCaptor!!.capture())
        val newContacts = contactsCaptor!!.getAllValues().get(1)
        assertTrue(newContacts.contains(contactToCreate))
    }

    @Test
    fun shouldShowAnErrorIfTheFirstNameOfTheNewContactIsEmpty() {
        val presenter = givenAContactsListPresenter()
        givenTheUserTypesContactInfo("", ANY_LAST_NAME, ANY_PHONE_NUMBER)

        presenter.onInitialize()
        presenter.onAddContactOptionSelected()

        verify(view)?.showDefaultError()
    }

    @Test
    fun shouldShowAnErrorIfTheLastNameOfTheNewContactIsEmpty() {
        val presenter = givenAContactsListPresenter()
        givenTheUserTypesContactInfo(ANY_FIRST_NAME, "", ANY_PHONE_NUMBER)

        presenter.onInitialize()
        presenter.onAddContactOptionSelected()

        verify(view)?.showDefaultError()
    }

    @Test
    fun shouldShowAnErrorIfTheNameOfTheNewContactIsEmpty() {
        val presenter = givenAContactsListPresenter()
        givenTheUserTypesContactInfo(ANY_FIRST_NAME, ANY_LAST_NAME, "")

        presenter.onInitialize()
        presenter.onAddContactOptionSelected()

        verify(view)?.showDefaultError()
    }

    private fun givenTheUserTypesContactInfo(t: String, anyLastName: String, anyPhoneNumber: String) {
        `when`(view?.newContactFirstName).thenReturn(t)
        `when`(view?.newContactLastName).thenReturn(anyLastName)
        `when`(view?.newContactPhoneNumber).thenReturn(anyPhoneNumber)
    }

    private fun givenTheContactIsAddedCorrectly(contact: Contact) {
        `when`(addContact!!.execute(contact)).thenReturn(contact)
        val newContacts = LinkedList<Contact>()
        newContacts.add(contact)
        `when`(getContacts!!.execute()).thenReturn(newContacts)
    }

    private fun givenTheAgendaIsEmpty() {
        `when`(getContacts!!.execute()).thenReturn(emptyList<Contact>())
    }

    private fun givenTheAgendaIsNotEmpty(): List<Contact> {
        val contacts = LinkedList<Contact>()
        for (i in 0..ANY_NUMBER_OF_CONTACTS - 1) {
            val contact = Contact(ANY_FIRST_NAME, ANY_LAST_NAME, ANY_PHONE_NUMBER)
            contacts.add(contact)
        }
        `when`(getContacts!!.execute()).thenReturn(contacts)
        return contacts
    }

    private fun givenAContactsListPresenter(): ContactsListPresenter {
        return ContactsListPresenter(view!!, getContacts!!, addContact!!)
    }

    private fun givenTheUserAddsAContact(): Contact {
        val contact = Contact(ANY_FIRST_NAME, ANY_LAST_NAME, ANY_PHONE_NUMBER)
        givenTheUserTypesContactInfo(contact.firstName, contact.lastName,
                contact.phoneNumber)
        return contact
    }

}