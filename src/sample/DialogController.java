package sample;

import dataModel.Contact;
import dataModel.ContactData;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextArea notesField;

    public void processNewContact() {
        String firstName = firstNameField.getText().trim();
        if(firstName.length()<1){
            firstName = "---";
        }
        String lastName = lastNameField.getText().trim();
        if(lastName.length()<1){
            lastName = "---";
        }
        String phoneNumber = phoneNumberField.getText().trim();
        if(phoneNumber.length()<1){
            phoneNumber = "---";
        }
        String notes = notesField.getText().trim();
        if(notes.length()<1){
            notes = "---";
        }
        Contact newContact = new Contact(firstName,lastName,phoneNumber,notes);
        System.out.println("Contact successfully created");
        ContactData.getInstance().addContact(newContact);
        System.out.println("Contact successfully added");
    }

    public void populateEditContact(Contact oldContact){
        firstNameField.setText(oldContact.getFirstName());
        lastNameField.setText(oldContact.getLastName());
        phoneNumberField.setText(oldContact.getPhoneNumber());
        notesField.setText(oldContact.getNotes());
    }

    public void processEditContact(Contact oldContact) {
        String firstName = firstNameField.getText().trim();
        if(firstName.length()<1){
            firstName = "---";
        }
        String lastName = lastNameField.getText().trim();
        if(lastName.length()<1){
            lastName = "---";
        }
        String phoneNumber = phoneNumberField.getText().trim();
        if(phoneNumber.length()<1){
            phoneNumber = "---";
        }
        String notes = notesField.getText().trim();
        if(notes.length()<1){
            notes = "---";
        }
        Contact newContact = new Contact(firstName,lastName,phoneNumber,notes);
        ContactData.getInstance().editContact(oldContact, newContact);
    }
}
