package com.example.librarymanagementsystem;

import javafx.fxml.FXML;

public class SavedBookController {

    @FXML
    public void showBooksBtn() throws Exception{
        HelloApplication.changeScene("showBooks.fxml");
    }
    @FXML
    public void issueBooksBtn() throws Exception{
        HelloApplication.changeScene("issueBook.fxml");
    }
    @FXML
    public void returnBooksBtn() throws Exception{
        HelloApplication.changeScene("returnBook.fxml");
    }
}
