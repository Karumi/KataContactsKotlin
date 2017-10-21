package com.karumi.katagenda.usecase

import com.karumi.katagenda.domain.Agenda
import com.karumi.katagenda.domain.Contact

class GetContacts(private val agenda: Agenda) {

    fun execute(): List<Contact> {
        return agenda.contacts
    }
}