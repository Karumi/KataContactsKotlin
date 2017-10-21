package com.karumi.katagenda.ui

import com.karumi.katagenda.domain.Contact
import com.karumi.katagenda.usecase.AddContact
import com.karumi.katagenda.usecase.GetContacts
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

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

        argumentCaptor<List<Contact>>().apply {
            verify(view, times(2))?.showContacts(capture())

            assertTrue(allValues[1].contains(contactToCreate))
        }

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
        whenever(view?.newContactFirstName).thenReturn(t)
        whenever(view?.newContactLastName).thenReturn(anyLastName)
        whenever(view?.newContactPhoneNumber).thenReturn(anyPhoneNumber)
    }

    private fun givenTheContactIsAddedCorrectly(contact: Contact) {
        whenever(addContact!!.execute(contact)).thenReturn(contact)
        val newContacts = ArrayList<Contact>()
        newContacts.add(contact)
        whenever(getContacts!!.execute()).thenReturn(newContacts)
    }

    private fun givenTheAgendaIsEmpty() {
        whenever(getContacts!!.execute()).thenReturn(emptyList<Contact>())
    }

    private fun givenTheAgendaIsNotEmpty(): List<Contact> {
        val contacts = ArrayList<Contact>()
        contacts.addAll(
                (0 to ANY_NUMBER_OF_CONTACTS - 1)
                        .toList()
                        .map {
                            Contact(ANY_FIRST_NAME, ANY_LAST_NAME, ANY_PHONE_NUMBER)
                        })
        whenever(getContacts!!.execute()).thenReturn(contacts)
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