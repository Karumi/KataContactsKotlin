package com.karumi.katagenda.usecase

import com.karumi.katagenda.domain.Agenda
import com.karumi.katagenda.domain.Contact

class GetContacts(private val agenda: Agenda) {

    operator fun invoke(): List<Contact> = agenda.contacts
}