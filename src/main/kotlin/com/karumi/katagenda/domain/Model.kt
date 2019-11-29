package com.karumi.katagenda.domain

import com.karumi.katagenda.common.repository.Repository

class Agenda(private val contactsRepository: Repository<Contact>) {

    val contacts: List<Contact>
        get() = contactsRepository.all

    fun addContact(contact: Contact): Contact = contactsRepository.add(contact)
}

data class Contact(val firstName: String, val lastName: String, val phoneNumber: String)
