package com.karumi.katagenda.ui

import com.karumi.katagenda.domain.Contact
import java.util.*

class SysOutContactsListView : ContactsListPresenter.View {

    override fun showWelcomeMessage() {
        print("Welcome to your awesome agenda!")
        print("I'm going to ask you about some of your contacts information :)")
    }

    override fun showGoodbyeMessage() {
        print("\nSee you soon!")
    }

    override fun showContacts(contactList: List<Contact>) {
        for ((firstName, lastName, phoneNumber) in contactList) {
            print("$firstName - $lastName - $phoneNumber")
        }
    }

    override val newContactFirstName: String
        get() {
            print("First name:")
            return readLine()
        }

    override val newContactLastName: String
        get() {
            print("Last name:")
            return readLine()
        }

    override val newContactPhoneNumber: String
        get() {
            print("Phone number:")
            return readLine()
        }

    override fun showDefaultError() {
        print("Ups, something went wrong :( Try again!")
    }

    override fun showEmptyCase() {
        print("Your agenda is empty!")
    }

    private fun print(line: String) {
        println(line)
    }

    private fun readLine(): String {
        val scanner = Scanner(System.`in`)
        return scanner.nextLine()
    }
}