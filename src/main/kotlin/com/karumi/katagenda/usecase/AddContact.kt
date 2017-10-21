package com.karumi.katagenda.usecase

import com.karumi.katagenda.domain.Agenda
import com.karumi.katagenda.domain.Contact

class AddContact(private val agenda: Agenda) {

    fun execute(contact: Contact): Contact {
        return agenda.addContact(contact)
    }
}