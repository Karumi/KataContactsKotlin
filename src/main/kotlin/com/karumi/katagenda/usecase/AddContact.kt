package com.karumi.katagenda.usecase

import com.karumi.katagenda.domain.Agenda
import com.karumi.katagenda.domain.Contact

class AddContact(private val agenda: Agenda) {

    operator fun invoke(contact: Contact): Contact = agenda.addContact(contact)
}