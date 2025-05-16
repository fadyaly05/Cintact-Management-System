package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ContactManagementSystemGUI {
    private JFrame frame;
    private ContactManager contactManager;
    private JTextArea textArea;

    public ContactManagementSystemGUI() {
        contactManager = new ContactManager();
        frame = new JFrame("Contact Management System");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Display area for contacts
        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Buttons Panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Contact");
        JButton listButton = new JButton("List Contacts");
        JButton searchButton = new JButton("Search Contact");
        JButton deleteButton = new JButton("Delete Contact");

        panel.add(deleteButton);
        panel.add(addButton);
        panel.add(listButton);
        panel.add(searchButton);

        frame.add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> displayAddContactForm());
        listButton.addActionListener(e -> displayContactList());
        searchButton.addActionListener(e -> displaySearchContactForm());


        frame.setVisible(true);
    }

    public void displayContactList() {
        textArea.setText("");
        for (Contact contact : contactManager.contacts) {
            textArea.append(contact + "\n");
        }
    }

    public void displayAddContactForm() {
        String name = JOptionPane.showInputDialog("Enter Name:");
        String phone = JOptionPane.showInputDialog("Enter Phone Number:");
        String address = JOptionPane.showInputDialog("Enter Address:");
        String email = JOptionPane.showInputDialog("Enter Email:");

        Contact newContact = new Contact(name, phone, address, email);
        contactManager.addContact(newContact);
        JOptionPane.showMessageDialog(frame, "Contact Added");
    }

    public void displaySearchContactForm() {
        String searchOption = JOptionPane.showInputDialog("Search by (1) Name or (2) Phone Number");
        
        if (searchOption == null || searchOption.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid option.");
            return;  // Exit if no valid input is provided
        }

        if (searchOption.equals("1")) {
            String name = JOptionPane.showInputDialog("Enter Name:");
            if (name != null && !name.trim().isEmpty()) {
                Contact contact = contactManager.searchContactByName(name);
                if (contact != null) {
                    textArea.setText(contact.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "Contact not found");
                }
            }
        } else if (searchOption.equals("2")) {
            String phoneNumber = JOptionPane.showInputDialog("Enter Phone Number:");
            if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
                Contact contact = contactManager.searchContactByPhoneNumber(phoneNumber);
                if (contact != null) {
                    textArea.setText(contact.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "Contact not found");
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid option. Please enter 1 or 2.");
        }
    }
}