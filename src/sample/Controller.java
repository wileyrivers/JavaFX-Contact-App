package sample;
import dataModel.Contact;
import dataModel.ContactData;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private TableView<Contact> contactTableView;
    @FXML
    private TableColumn<Contact, String> firstNameColumn;
    @FXML
    private TableColumn<Contact, String> lastNameColumn;
    @FXML
    private TableColumn<Contact, String> numberColumn;
    @FXML
    private TableColumn<Contact, String> notesColumn;
    @FXML
    private BorderPane mainBorderPane;




    @FXML
    public void initialize() {
        ObservableList<Contact> data = ContactData.getInstance().getContacts();
        contactTableView.setItems(data);
//        contactTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
//            @Override
//            public void changed(ObservableValue<? extends Contact> observableValue, Contact contact, Contact t1) {
//                if(t1 != null){
//                    Contact selectedContact = contactTableView.getSelectionModel().getSelectedItem();
//                    firstNameColumn.setText(selectedContact.getFirstName());
//                    lastNameColumn.setText(selectedContact.getLastName());
//                    numberColumn.setText(selectedContact.getPhoneNumber());
//                    notesColumn.setText(selectedContact.getNotes());
//                }
//            }
//        });
    }

    @FXML
    public void showNewContactDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Create New Contact");
        dialog.setHeaderText("Use this window to create a new contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("createContactDialog.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Cant load dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            controller.processNewContact();
            ContactData.getInstance().saveContacts();
        }
    }

    @FXML
    public void editSelected() {
        Contact selected = contactTableView.getSelectionModel().getSelectedItem();
        if(selected != null) {
            showEditContactDialog(selected);
        }
    }

    public void showEditContactDialog(Contact contact) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(mainBorderPane.getScene().getWindow());
            dialog.setTitle("Edit Selected Contact");
            dialog.setHeaderText("Make Edits to" + contact.getFirstName() + " " + contact.getLastName());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("createContactDialog.fxml"));
            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());
            } catch (IOException e) {
                System.out.println("Cant load dialog");
                e.printStackTrace();
                return;
            }

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            DialogController controller = fxmlLoader.getController();
            controller.populateEditContact(contact);


            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                controller.processEditContact(contact);
                ContactData.getInstance().saveContacts();
            }
    }

    @FXML
    public void deleteSelection() {
        Contact selectedContact = contactTableView.getSelectionModel().getSelectedItem();
        if(selectedContact != null){
            deleteContact(selectedContact);
        }
    }

    public void deleteContact(Contact contact) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText("Delete "+contact.getFirstName()+" "+contact.getLastName()+" from contact list");
        alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            ContactData.getInstance().deleteContact(contact);
        }
    }

    @FXML
    public void handleKeyPressed(KeyEvent key) {
        Contact selected = contactTableView.getSelectionModel().getSelectedItem();
        if(selected != null){
            if(key.getCode().equals(KeyCode.DELETE) || key.getCode().equals(KeyCode.D)){
                deleteContact(selected);
            } else if (key.getCode().equals(KeyCode.E)){
                showEditContactDialog(selected);
            } else if (key.getCode().equals(KeyCode.N)){
                showNewContactDialog();
            } else if (key.getCode().equals(KeyCode.ESCAPE)){
                handleExit();
            }
        } else {
            if (key.getCode().equals(KeyCode.N)){
                showNewContactDialog();
            } else if (key.getCode().equals(KeyCode.ESCAPE)){
                handleExit();
            }
        }
    }
    @FXML
    public void handleExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit application");
        alert.setHeaderText("Are you sure you want to exit so soon?");
        alert.setContentText("Press OK to exit, or press cancel");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == (ButtonType.OK)){
            Platform.exit();
        }
    }

}
