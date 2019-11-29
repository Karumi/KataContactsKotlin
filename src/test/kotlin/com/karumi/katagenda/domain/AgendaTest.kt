package com.karumi.katagenda.domain

import com.karumi.katagenda.common.repository.InMemoryDataSource
import com.karumi.katagenda.domain.repository.ContactsRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AgendaTest {

    companion object {
        private const val ANY_FIRST_NAME = "Pedro Vicente"
        private const val ANY_LAST_NAME = "Gomez Sanchez"
        private const val ANY_PHONE_NUMBER = "666666666"
    }

    @Test
    fun shouldReturnAnEmptyListOfContactsIfTheAgendaIsEmpty() {
        val agenda = givenAnAgenda()

        val contacts = agenda.contacts

        assertTrue(contacts.isEmpty())
    }

    @Test
    fun shouldReturnTheContactCreatedOnContactAdded() {
        val agenda = givenAnAgenda()
        val contactToAdd = givenAnyContact()

        val createdContact = agenda.addContact(contactToAdd)

        assertEquals(contactToAdd, createdContact)
    }

    @Test
    fun shouldReturnTheNewContactAfterTheCreationUsingGetContacts() {
        val agenda = givenAnAgenda()
        val contact = givenAnyContact()

        agenda.addContact(contact)

        val contacts = agenda.contacts
        assertTrue(contacts.contains(contact))
        assertEquals(1, contacts.size)
    }

    private fun givenAnyContact(): Contact = Contact(ANY_FIRST_NAME, ANY_LAST_NAME, ANY_PHONE_NUMBER)

    private fun givenAnAgenda(): Agenda {
        val dataSource = InMemoryDataSource<Contact>()
        val contactsRepository = ContactsRepository(dataSource)
        return Agenda(contactsRepository)
    }
}