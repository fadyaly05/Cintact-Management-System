package main;

import java.util.*;

public class ContactManager {
    public List<Contact> contacts;
    private DatabaseHandler dbHandler;

    public ContactManager() {
        this.dbHandler = new DatabaseHandler();
        loadContacts();
    }

    public void loadContacts() {
        contacts = dbHandler.readDataBase();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        dbHandler.saveDataBase(contacts); // Save contacts to the database
    }

    public void listContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public Contact searchContactByName(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public Contact searchContactByPhoneNumber(String phoneNumber) {
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                return contact;
            }
        }
        return null;
    }

    public void editContact(String oldName, Contact updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equalsIgnoreCase(oldName)) {
                contacts.set(i, updatedContact);
                dbHandler.updateDatabase(contacts); // Update the database
                return;
            }
        }
    }

    public void deleteContact(String name) {
        contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
        dbHandler.deleteContact(name); // Delete contact from the database
    }
}