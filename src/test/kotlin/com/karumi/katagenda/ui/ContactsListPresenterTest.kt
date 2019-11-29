package com.karumi.katagenda.ui

import com.karumi.katagenda.domain.Contact
import com.karumi.katagenda.usecase.AddContact
import com.karumi.katagenda.usecase.GetContacts
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ContactsListPresenterTest {

    companion object {
        private const val ANY_NUMBER_OF_CONTACTS = 7
        private const val ANY_FIRST_NAME = "Pedro Vicente"
        private const val ANY_LAST_NAME = "Gomez Sanchez"
        private const val ANY_PHONE_NUMBER = "666666666"
    }

    @Mock private lateinit var view: ContactsListPresenter.View
    @Mock private lateinit var getContacts: GetContacts
    @Mock private lateinit var addContact: AddContact

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun shouldShowWelcomeMessageOnInitialize() {
        val presenter = givenAContactsListPresenter()

        presenter.onInitialize()

        verify(view).showWelcomeMessage()
    }

    @Test
    fun shouldShowEmptyCaseIfTheAgendaIsEmpty() {
        val presenter = givenAContactsListPresenter()
        givenTheAgendaIsEmpty()

        presenter.onInitialize()

        verify(view).showEmptyCase()
    }

    @Test
    fun shouldShowContactsFromTheAgendaOnInitialize() {
        val presenter = givenAContactsListPresenter()
        val contacts = givenTheAgendaIsNotEmpty()

        presenter.onInitialize()

        verify(view).showContacts(contacts)
    }

    @Test
    fun shouldShowGoodbyeMessageOnStop() {
        val presenter = givenAContactsListPresenter()

        presenter.onStop()

        verify(view).showGoodbyeMessage()
    }

    @Test
    fun shouldShowTheContactsListWithTheNewContactOnContactAdded() {
        val presenter = givenAContactsListPresenter()
        val contactToCreate = givenTheUserAddsAContact()
        givenTheContactIsAddedCorrectly(contactToCreate)

        presenter.onInitialize()
        presenter.onAddContactOptionSelected()

        argumentCaptor<List<Contact>>().apply {
            verify(view, times(2)).showContacts(capture())

            assertTrue(allValues[1].contains(contactToCreate))
        }
    }

    @Test
    fun shouldShowAnErrorIfTheFirstNameOfTheNewContactIsEmpty() {
        val presenter = givenAContactsListPresenter()
        givenTheUserTypesContactInfo("", ANY_LAST_NAME, ANY_PHONE_NUMBER)

        presenter.onInitialize()
        presenter.onAddContactOptionSelected()

        verify(view).showDefaultError()
    }

    @Test
    fun shouldShowAnErrorIfTheLastNameOfTheNewContactIsEmpty() {
        val presenter = givenAContactsListPresenter()
        givenTheUserTypesContactInfo(ANY_FIRST_NAME, "", ANY_PHONE_NUMBER)

        presenter.onInitialize()
        presenter.onAddContactOptionSelected()

        verify(view).showDefaultError()
    }

    @Test
    fun shouldShowAnErrorIfTheNameOfTheNewContactIsEmpty() {
        val presenter = givenAContactsListPresenter()
        givenTheUserTypesContactInfo(ANY_FIRST_NAME, ANY_LAST_NAME, "")

        presenter.onInitialize()
        presenter.onAddContactOptionSelected()

        verify(view).showDefaultError()
    }

    private fun givenTheUserTypesContactInfo(t: String, anyLastName: String, anyPhoneNumber: String) {
        whenever(view.newContactFirstName).thenReturn(t)
        whenever(view.newContactLastName).thenReturn(anyLastName)
        whenever(view.newContactPhoneNumber).thenReturn(anyPhoneNumber)
    }

    private fun givenTheContactIsAddedCorrectly(contact: Contact) {
        whenever(addContact.invoke(contact)).thenReturn(contact)
        val newContacts = ArrayList<Contact>()
        newContacts.add(contact)
        whenever(getContacts()).thenReturn(newContacts)
    }

    private fun givenTheAgendaIsEmpty() {
        whenever(getContacts.invoke()).thenReturn(emptyList())
    }

    private fun givenTheAgendaIsNotEmpty(): List<Contact> {
        val contacts = (0 to ANY_NUMBER_OF_CONTACTS - 1)
                .toList()
                .map {
                    Contact(ANY_FIRST_NAME, ANY_LAST_NAME, ANY_PHONE_NUMBER)
                }
        whenever(getContacts.invoke()).thenReturn(contacts)
        return contacts
    }

    private fun givenAContactsListPresenter(): ContactsListPresenter =
        ContactsListPresenter(view, getContacts, addContact)

    private fun givenTheUserAddsAContact(): Contact {
        val contact = Contact(ANY_FIRST_NAME, ANY_LAST_NAME, ANY_PHONE_NUMBER)
        givenTheUserTypesContactInfo(contact.firstName, contact.lastName,
                contact.phoneNumber)
        return contact
    }
}
